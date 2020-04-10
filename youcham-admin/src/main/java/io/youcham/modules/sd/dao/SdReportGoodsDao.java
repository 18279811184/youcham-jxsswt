package io.youcham.modules.sd.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import feign.Param;
import io.youcham.modules.sd.entity.SdReportGoodsEntity;

import java.util.List;

/**
 * 申报物资清单
 * 
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2020-03-31 16:55:28
 */
public interface SdReportGoodsDao extends BaseMapper<SdReportGoodsEntity> {

    List<String> queryReportByGoodsNames(@Param("goodsNames") List<String> goodsNames);

}
