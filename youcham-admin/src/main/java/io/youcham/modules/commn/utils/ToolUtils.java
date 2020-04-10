package io.youcham.modules.commn.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ToolUtils {
	/**
	 * 获取昨天17点至今天17点两个时间段
	 * @return
	 */
	public static Map<String,String> createStartAndEndTime(boolean isT){
		Map<String,String> map=new HashMap<String, String>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR,-1);
		Date d=calendar.getTime();
		
		String end = "";
		String start = "";
		if(isT) {
			end=sdf.format(date)+"T17:00:00";
			start=sdf.format(d)+"T17:00:00";
		} else {
			end=sdf.format(date)+" 17:00:00";
			start=sdf.format(d)+" 17:00:00";
		}
		
		System.out.println(end);
		map.put("endTime",end);
		map.put("startTime",start);
		
		return map;
	}
}
