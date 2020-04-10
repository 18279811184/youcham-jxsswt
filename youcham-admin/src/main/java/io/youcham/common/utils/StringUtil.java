package io.youcham.common.utils;

public class StringUtil {
	public static String checkNull(Object info){
		if(null==info || info.toString().equalsIgnoreCase("null")){
			return "";
		}else{
			return info.toString();
		}
	}
}
