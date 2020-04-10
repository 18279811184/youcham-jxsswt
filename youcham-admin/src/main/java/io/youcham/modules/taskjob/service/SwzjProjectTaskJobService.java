package io.youcham.modules.taskjob.service;

import io.youcham.modules.qrtz.service.QrtzRunHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 省外利用资金系统-统计数据项目 定时任务
 */

@Service
public class SwzjProjectTaskJobService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private QrtzRunHistoryService qrtzRunHistoryService;

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format1 = new SimpleDateFormat("MM");
        String time = "2020-12-31";

        try {
            calendar.setTime(format.parse(time));
            calendar.add(Calendar.DATE,1);
            System.out.println(format.format(calendar.getTime()));
            System.out.println("/" + 1 + 2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public Map<String, Object> handleQueryYear(String year, String month) {
        Map<String, Object> map = new HashMap<>();
        /* 今年开始月份 201901 */
        String thisYearStartTime = year + "" + "01";
        /* 今年结束月份 201912 */
        String thisYearEndTime = year + "" + month;
        /* 去年年份 2018 */
        Integer lastYearStartYear = Integer.parseInt(year) - 1;
        /* 去年开始月份 201801 */
        String lastYearStartTime = lastYearStartYear + "" + "01";
        /* 去年结束月份 201812 */
        String lastYearEndTime = lastYearStartYear + "" + month;

        map.put("thisYearStartYear", year);

        map.put("thisYearStartTime", thisYearStartTime);
        map.put("thisYearEndTime", thisYearEndTime);
        map.put("lastYearStartYear", lastYearStartYear);
        map.put("lastYearStartTime", lastYearStartTime);
        map.put("lastYearEndTime", lastYearEndTime);
        /* 开始月份 */
        map.put("startMonth", "01");
        map.put("endMonth", month);
        // 月份数字类型去零
        map.put("startMonthInt", 1);
        map.put("endMonthInt", Integer.valueOf(month));
//        map.put("cityId", ShiroUtils.getUserEntity().getCity());
//        map.put("countyId",ShiroUtils.getUserEntity().getCounty());
//        map.put("areaLevel",ShiroUtils.getUserEntity().getAreaLevel());

        /* 洽谈使用 */
        map.put("startTime",thisYearStartTime);
        map.put("endTime",thisYearEndTime);
        map.put("year", year);
        map.put("month", month);
        return map;
    }
}
