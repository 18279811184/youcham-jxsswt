package io.youcham.common.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DataMapUtil {
	 private static Map<String, Object> dataMap = new HashMap<String, Object>();
	 
	 /**
	     * 将对象转换成Map
	     * @param obj 对象类
	     * @return
	     */
	    public static Map<String,Object> setObjToMap(Object obj){
	        Class c;
	        try {
	            c = Class.forName(obj.getClass().getName());
	            Method[] methods = c.getMethods();
	            for(int i=0,l=methods.length;i<l;i++){
	                String method = methods[i].getName();
	                System.out.println("The method is:" + method);
	                if(method.startsWith("get")){
	                    Object value = methods[i].invoke(obj);
	                    if(value != null){
	                        if(value.getClass().getClassLoader() != null){  //处理自定义的对象类型
	                            setObjToMap(value);
	                        }
	                        String key = method.substring(3);
	                        key = key.substring(0, 1).toLowerCase() + key.substring(1);
	                      
	                        dataMap.put(key, value);
	                    }
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return dataMap;
	    }
	    
	    
	    public static Map<String,Object> setObjToNewMap(Object obj){
	    	Map<String, Object> dataMapNew = new HashMap<String, Object>();
	    	
	        Class c;
	        try {
	            c = Class.forName(obj.getClass().getName());
	            Method[] methods = c.getMethods();
	            for(int i=0,l=methods.length;i<l;i++){
	                String method = methods[i].getName();
	                System.out.println("The method is:" + method);
	                if(method.startsWith("get")){
	                    Object value = methods[i].invoke(obj);
	                    if(value != null){
	                        if(value.getClass().getClassLoader() != null){  //处理自定义的对象类型
	                            setObjToMap(value);
	                        }
	                        String key = method.substring(3);
	                        key = key.substring(0, 1).toLowerCase() + key.substring(1);
	                      
	                        dataMapNew.put(key, value);
	                    }
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return dataMapNew;
	    }
	    
}
