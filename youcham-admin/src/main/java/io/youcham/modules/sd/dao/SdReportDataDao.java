package io.youcham.modules.sd.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.youcham.modules.sd.entity.SdReportDataEntity;

import java.util.List;
import java.util.Map;

/**
 * 申报/提供信息
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2020-03-31 16:55:28
 */
public interface SdReportDataDao extends BaseMapper<SdReportDataEntity> {
    /**
     * 查询江西省的地图数据
     * @return
     */
    List<Map<String, Object>> getMapDateForjx();

}
