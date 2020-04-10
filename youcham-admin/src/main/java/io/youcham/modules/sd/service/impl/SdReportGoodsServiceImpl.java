package io.youcham.modules.sd.service.impl;

import io.youcham.modules.sd.dao.SdReportGoodsDao;
import io.youcham.modules.sd.entity.SdReportGoodsEntity;
import io.youcham.modules.sd.service.SdReportGoodsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.youcham.common.utils.PageUtils;
import io.youcham.common.utils.Query;


@Service("sdReportGoodsService")
public class SdReportGoodsServiceImpl extends ServiceImpl<SdReportGoodsDao, SdReportGoodsEntity> implements SdReportGoodsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String reportId = (String) params.get("reportDataId");
        String name = (String) params.get("name");
        Page<SdReportGoodsEntity> page = this.selectPage(
                new Query<SdReportGoodsEntity>(params).getPage(),
                new EntityWrapper<SdReportGoodsEntity>()
                .eq(StringUtils.isNotBlank(reportId), "report_Data_Id", reportId)
                .like(StringUtils.isNotBlank(name), "goods_name", name)
        );

        return new PageUtils(page);
    }

    @Override
    public List<String> queryReportByGoodsNames(List<String> goodsNames) {
        return baseMapper.queryReportByGoodsNames(goodsNames);
    }
}
