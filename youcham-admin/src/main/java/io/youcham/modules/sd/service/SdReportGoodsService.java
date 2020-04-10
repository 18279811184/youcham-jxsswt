package io.youcham.modules.sd.service;

import com.baomidou.mybatisplus.service.IService;
import io.youcham.common.utils.PageUtils;
import io.youcham.modules.sd.entity.SdReportGoodsEntity;

import java.util.List;
import java.util.Map;

/**
 * 申报物资清单
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2020-03-31 16:55:28
 */
public interface SdReportGoodsService extends IService<SdReportGoodsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<String> queryReportByGoodsNames(List<String> goodsNames);

}

