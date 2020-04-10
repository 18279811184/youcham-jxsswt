package io.youcham.modules.api.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.R;
import io.youcham.common.utils.StringUtil;
import io.youcham.common.validator.Assert;
import io.youcham.common.validator.ValidatorUtils;
import io.youcham.common.validator.group.AddGroup;
import io.youcham.modules.ins.entity.SysFiletableEntity;
import io.youcham.modules.ins.service.SysFiletableService;
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
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 供需申报数据API
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-31 16:57:31
 */
@RestController
@RequestMapping("api/sd/reportData")
public class ApiSdReportDataController {
    @Autowired
    private SdReportDataService sdReportDataService;
    @Autowired
    private SdReportGoodsService sdReportGoodsService;
    @Autowired
    private SwzjCountyService swzjCountyService;
    @Autowired
    private SysFiletableService sysFiletableService;
    @Autowired
    private SdInternationalLocationService sdInternationalLocationService;
    @Autowired
    private SysDictService sysDictService;
    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody Map<String, Object> params){
        /* 解码 */
//            sdReportData = java.net.URLDecoder.decode(sdReportData,"UTF-8");
//            resourceList = java.net.URLDecoder.decode(resourceList,"UTF-8");
            String data  = String.valueOf(params.get("sdReportData"));
            String good  = String.valueOf(params.get("goodsList"));
            List<SdReportGoodsEntity> list =  JSONArray.parseArray(good, SdReportGoodsEntity.class);
            SdReportDataEntity jsonObject= JSONObject.parseObject(data, SdReportDataEntity.class);
            ValidatorUtils.validateEntity(jsonObject, AddGroup.class);
//            jsonObject.setCreateId(ShiroUtils.getUserId());
//            jsonObject.setCreateName(ShiroUtils.getUserEntity().getName());
         boolean flag =   sdReportDataService.save(jsonObject, list);


        return flag ?  R.ok() : R.error();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody Map<String, Object> params){
        String data  = String.valueOf(params.get("sdReportData"));
        String good  = String.valueOf(params.get("goodsList"));
        String deleteId  = String.valueOf(params.get("deleteIds"));

        List<SdReportGoodsEntity> list =  JSONArray.parseArray(good, SdReportGoodsEntity.class);
        List<String> deleteIds =  JSONArray.parseArray(deleteId, String.class);
        SdReportDataEntity jsonObject= JSONObject.parseObject(data, SdReportDataEntity.class);

        ValidatorUtils.validateEntity(jsonObject,AddGroup.class);
//        jsonObject.setUpdateId(ShiroUtils.getUserId());
//        jsonObject.setUpdateName(ShiroUtils.getUserEntity().getName());
        Boolean re = sdReportDataService.update(jsonObject, list, deleteIds);//全部更新

        return re ? R.ok() : R.oldVersion();
    }
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
        SdReportDataEntity sdReportData = sdReportDataService.selectById(id);
        List<SdReportGoodsEntity> goodsEntity = sdReportGoodsService.selectList(new EntityWrapper<SdReportGoodsEntity>()
        .eq("report_Data_Id", sdReportData.getId())
        );

        sdReportData.setGoodsList(goodsEntity);
        return R.ok().put("sdReportData", sdReportData);
    }
    /**
     * 是否可以修改
     */
    @RequestMapping("/isuUpdate")
    public R isuUpdate(String id, String password){
        SdReportDataEntity sdReportData = sdReportDataService.selectById(id);
            if (sdReportData.getPassword().equals(password)) {
                return R.ok();
            }
        return R.error();
    }

    /**
     * 获取申报数据详细 （分页）
     */
    @RequestMapping("/queryReportInfoPage")
    public R queryReportInfoPage(@RequestParam Map<String, Object> params){
        String reportType = (String)params.get("reportType");
        Assert.isBlank(reportType, "申报类型不能为空");

        PageUtils page = sdReportDataService.queryReportInfoPage(params);

        return R.ok().put("page",page);
    }

    /**
     * 获取国家或省
     */
    @RequestMapping("/getCountryOrProvince")
    public R getCountryOrProvince(@RequestParam Map<String, Object> params){
        String level = (String)params.get("level");
        String pid = (String)params.get("pid");

        List<SdInternationalLocationEntity> entities = sdInternationalLocationService.selectList(
                new EntityWrapper<SdInternationalLocationEntity>()
                .eq(StringUtils.isNotBlank(level), "area_level",level)
                .eq(StringUtils.isNotBlank(pid), "pid",pid)
        );

        return R.ok().put("list",entities);
    }
    /**
     * 查询字典
     */
    @RequestMapping("/finddic")
    //@RequiresPermissions("sys:dict:list")
    public R list(@RequestParam String type){
        List<SysDictEntity> geilist = sysDictService.queryDetpIdList(type);

        return  R.ok().put("templateList", geilist);
    }
    /**
     * 查询本省所有的市
     * @return
     */
    @RequestMapping("/selectCityByJiangXi")
    public R selectCityByJiangXi(){
        /* 江西省 */
        SwzjCountyEntity entity = swzjCountyService.selectOne(new EntityWrapper<SwzjCountyEntity>()
                .eq("code","360000")
                .eq("city_level",0)
        );
        List<SwzjCountyEntity> list  = new ArrayList<>();
        if (entity != null) {
            list = swzjCountyService
                    .selectList(new EntityWrapper<SwzjCountyEntity>()
                                    .eq("first_Level_Parent", entity.getId())
                                    .eq("city_level",1)
//                            .eq(level == 1 || level == 2,"id", city)
                    );
        }
        return R.ok().put("list", list);
    }
}
