package io.youcham.modules.sd.service;

import com.baomidou.mybatisplus.service.IService;
import io.youcham.common.utils.PageUtils;
import io.youcham.modules.sd.entity.SdReportDataEntity;
import io.youcham.modules.sd.entity.SdReportGoodsEntity;

import java.util.List;
import java.util.List;
import java.util.Map;

/**
 * 申报/提供信息
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2020-03-31 16:55:28
 */
public interface SdReportDataService extends IService<SdReportDataEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 获取申报数据详细 （分页）
     * @param params
     * @return
     */
    PageUtils queryReportInfoPage(Map<String, Object> params);

    /**
     * 保存
     * @param dataEntity
     * @param list
     * @return
     */
    boolean save(SdReportDataEntity dataEntity, List<SdReportGoodsEntity> list);

    /**
     * 保存
     * @param dataEntity
     * @param list
     * @return
     */
    boolean update(SdReportDataEntity dataEntity, List<SdReportGoodsEntity> list,  List<String> deleteIds);

    /**
     * 查询江西省的地图数据
     *
     * @return
     */
    List<Map<String, Object>> getMapDateForjx();

}



