package io.youcham.modules.sd.service.impl;

import io.youcham.common.utils.ListUtils;
import io.youcham.modules.sd.dao.SdReportDataDao;
import io.youcham.modules.sd.entity.SdInternationalLocationEntity;
import io.youcham.modules.sd.entity.SdReportDataEntity;
import io.youcham.modules.sd.entity.SdReportGoodsEntity;
import io.youcham.modules.sd.service.SdInternationalLocationService;
import io.youcham.modules.sd.service.SdReportDataService;
import io.youcham.modules.sd.service.SdReportGoodsService;
import io.youcham.modules.swzj.entity.SwzjCountyEntity;
import io.youcham.modules.swzj.service.impl.SwzjCountyService;
import io.youcham.modules.sys.entity.SysDictEntity;
import io.youcham.modules.sys.service.SysDictService;
import io.youcham.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;
import org.springframework.transaction.annotation.Transactional;


@Service("sdReportDataService")
public class SdReportDataServiceImpl extends ServiceImpl<SdReportDataDao, SdReportDataEntity> implements SdReportDataService {

    @Autowired
    private SdReportGoodsService sdReportGoodsService;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private SwzjCountyService swzjCountyService;
    @Autowired
    private SdInternationalLocationService sdInternationalLocationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String  needProvide = (String) params.get("needProvide");

        Page<SdReportDataEntity> page = this.selectPage(
                new Query<SdReportDataEntity>(params).getPage(),
                new EntityWrapper<SdReportDataEntity>()
                .eq(StringUtils.isNotBlank(needProvide), "report_Type", needProvide)
                .eq("be_delete",0)
        );
        for (SdReportDataEntity entity : page.getRecords()) {
            Map map = new HashMap();
            SwzjCountyEntity countyEntity = swzjCountyService.selectById(entity.getProvince());
            if (countyEntity != null) {
                map.put("provinceDesc", countyEntity.getName());
            }
            countyEntity = swzjCountyService.selectById(entity.getCity());
            if (countyEntity != null) {
                map.put("cityDesc", countyEntity.getName());
            }
            SysDictEntity dictEntity = sysDictService.selectById(entity.getCustomerType());
            if (dictEntity != null) {
                map.put("typeDesc", dictEntity.getValue());
            }
            dictEntity = sysDictService.selectById(entity.getSourceType());
            if (dictEntity != null) {
                map.put("sourceDesc", dictEntity.getValue());
            }
            SdInternationalLocationEntity internationalLocationEntity = sdInternationalLocationService.selectById(entity.getCountry());
            if (internationalLocationEntity != null) {
                map.put("countryDesc", internationalLocationEntity.getName());
            }

            entity.setMapDesc(map);
        }
        return new PageUtils(page);
    }

    /**
     * 获取申报数据详细 （分页）
     * @param params
     * @return
     */
    @Override
    public PageUtils queryReportInfoPage(Map<String, Object> params) {
        String searchword = (String)params.get("searchword");
        String reportType = (String)params.get("reportType");
        String cityName = (String) params.get("cityName");


        List<String> reportIdList = null;
        if(StringUtils.isNotEmpty(searchword)){
            reportIdList = sdReportGoodsService.queryReportByGoodsNames(Arrays.asList(searchword.split(",")));
        }

        Page<SdReportDataEntity> page = this.selectPage(
                new Query<SdReportDataEntity>(params).getPage(),
                new EntityWrapper<SdReportDataEntity>()
                .in(StringUtils.isNotEmpty(searchword) && ListUtils.isNotEmpty(reportIdList),"id", reportIdList)
                .and(StringUtils.isNotEmpty(searchword) && ListUtils.isEmpty(reportIdList),"1=2")
                .eq(StringUtils.isNotEmpty(reportType),"report_Type",reportType)
                .and(StringUtils.isNotEmpty(cityName), "EXISTS(select 1 from SWZJ_COUNTY where SWZJ_COUNTY.id=SD_REPORT_DATA.city and SWZJ_COUNTY.name like {0})","%"+cityName+"%")
                .orderBy("create_Time desc")
        );

        for (SdReportDataEntity record : page.getRecords()) {
            // 客户类型
            SysDictEntity customer_type = sysDictService.selectById(record.getCustomerType());
            if(null != customer_type){
                record.setCustomerTypeDesc(customer_type.getValue());
            }
            // 需求来源
            if(StringUtils.isNotEmpty(record.getAcceptTypes())){
                List<SysDictEntity> accept_types = sysDictService.selectList(
                        new EntityWrapper<SysDictEntity>().in("id",record.getAcceptTypes().split(",")));
                if(ListUtils.isNotEmpty(accept_types)){
                    record.setAcceptTypesList(accept_types);
                }
            }
            // 需求单
            List<SdReportGoodsEntity> goodList = sdReportGoodsService.selectList(
                    new EntityWrapper<SdReportGoodsEntity>().eq("report_Data_Id",record.getId()));
            if(ListUtils.isNotEmpty(goodList)){
                record.setGoodsList(goodList);
            }

        }


        return new PageUtils(page);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SdReportDataEntity dataEntity, List<SdReportGoodsEntity> list) {
        dataEntity.setCreateTime(new Date());
        boolean flag = this.insert(dataEntity);
        if (flag) {
            for (SdReportGoodsEntity sdReportGoodsEntity : list) {
                sdReportGoodsEntity.setReportDataId(dataEntity.getId());
                sdReportGoodsEntity.setCreateTime(new Date());
                flag  =  sdReportGoodsService.insert(sdReportGoodsEntity);
            }
        }
        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(SdReportDataEntity dataEntity, List<SdReportGoodsEntity> list, List<String> deleteIds) {
        dataEntity.setUpdateTime(new Date());
        Boolean flag = this.updateById(dataEntity);
        if (flag && deleteIds.size() > 0) {
            /* 删除 */
            for (String id : deleteIds) {
                flag = sdReportGoodsService.deleteById(id);
            }
        }
        if (flag) {
            for (SdReportGoodsEntity sdReportGoodsEntity : list) {
                if (sdReportGoodsEntity.getId() != null) {
                    sdReportGoodsEntity.setUpdateTime(new Date());
                    flag = sdReportGoodsService.updateById(sdReportGoodsEntity);
                } else {
                    sdReportGoodsEntity.setReportDataId(dataEntity.getId());
                    sdReportGoodsEntity.setCreateTime(new Date());
                    flag = sdReportGoodsService.insert(sdReportGoodsEntity);
                }
            }
        }
        return flag;
    }

    @Override
    public List<Map<String, Object>> getMapDateForjx() {
        List<Map<String, Object>> list = baseMapper.getMapDateForjx();
        return list;
    }
}
