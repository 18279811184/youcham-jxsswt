package io.youcham.modules.api.controller;


import io.youcham.common.utils.R;

import io.youcham.modules.sd.service.SdReportDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 供需申报数据API
 *
 * @author youcham
 * @email http://www.youcham.com/
 * @date 2019-12-31 16:57:31
 */
@RestController
@RequestMapping("api/sd/mapData")
public class MapDataController {
    @Autowired
    private SdReportDataService sdReportDataService;

    /**
     * 获取申报数据详细 （分页）
     */
    @RequestMapping("/getMapDateForjx")
    public R getMapDateForjx() {
        List<Map<String, Object>> list = sdReportDataService.getMapDateForjx();
        for (Map<String, Object> map : list) {
            String cityName = (String) map.get("name");
            cityName = cityName.replace("市","");
            map.put("name",cityName);
        }
        return R.ok().put("listMapDate", list);
    }


}
