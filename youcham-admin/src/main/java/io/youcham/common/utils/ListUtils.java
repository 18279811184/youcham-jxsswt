package io.youcham.common.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;

public class ListUtils {
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(List data) {
        boolean isEmpty = true;
        if (data != null && data.size() > 0) {
            isEmpty = false;
        }
        return isEmpty;
    }

    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(List data) {
        return !isEmpty(data);
    }

    public static Map<String, Object> getListMap(List<Map<String, Object>> list,int i){
        if(isNotEmpty(list) && list.size()>0){
            return list.get(i);
        }
        return null;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Map<String, Object>> getListIndex(List list,int i){
        if(isNotEmpty(list) && list.size()>0 && i < list.size()){
            return (List<Map<String, Object>>) list.get(i);
        }
        return null;
    }
    /**
     * 对list进行排序
     * @param sortList 需要排序的list
     * @param param1   排序的参数名称
     * @param orderType 排序类型：正序-asc；倒序-desc  
     */

    @SuppressWarnings({"rawtypes", "unchecked" })
    public static List sort(List sortList, String param1, String orderType){
        Comparator mycmp1 = ComparableComparator.getInstance ();
        if("desc".equals(orderType)){
            mycmp1 = ComparatorUtils. reversedComparator(mycmp1); //逆序（默认为正序）
        }

        ArrayList<Object> sortFields = new ArrayList<Object>();
        sortFields.add( new BeanComparator(param1 , mycmp1)); //主排序（第一排序）

        ComparatorChain multiSort = new ComparatorChain(sortFields);
        Collections.sort (sortList , multiSort);

        return sortList;
    }

    /**
     * 对list进行排序
     * @param sortList 需要排序的list
     * @param param1   排序的参数名称:参数长度
     * @param param2   排序的参数名称:排序参数
     * @param orderType 排序类型：正序-asc；倒序-desc  
     */
    @SuppressWarnings({"rawtypes", "unchecked" })
    public static List sortParam2(List sortList, String param1,String param2, String orderType){
        Comparator mycmp1 = ComparableComparator.getInstance ();
        Comparator mycmp2 = ComparableComparator.getInstance ();
        if("desc".equals(orderType)){
            mycmp1 = ComparatorUtils. reversedComparator(mycmp1); //逆序（默认为正序）
        }

        ArrayList<Object> sortFields = new ArrayList<Object>();
        sortFields.add( new BeanComparator(param1 , mycmp1)); //主排序（第一排序）
        sortFields.add( new BeanComparator(param2 , mycmp2)); //主排序（第一排序）

        ComparatorChain multiSort = new ComparatorChain(sortFields);
        Collections.sort (sortList , multiSort);

        return sortList;
    }

    @SuppressWarnings({"rawtypes", "unchecked" })
    public static List testMapSort(){
        Map map = new HashMap();
        map.put("name", "3");
        map.put("age", "1");

        Map map2 = new HashMap();
        map2.put("name", "2");
        map2.put("age", "1");

        Map map1 = new HashMap();
        map1.put("name", "2");
        map1.put("age", "12.0");

        Map map3 = new HashMap();
        map3.put("name", "2");
        map3.put("age", "11.052");

        List list = new ArrayList();
        list.add(map);
        list.add(map1);
        list.add(map2);
        list.add(map3);
        return sort(list, "age", "asc");
    }
    
    /** 
     * @Description: 集合提取字段转为字符串 
     * @author : guoqiang
     * @date : 2018年7月6日 上午11:46:21 
     * @param list
     * @param param1 指定字段
     * @param param2 分割字符串
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static String convertElementPropertyToString(List list, String param1,String param2){
    	String reStr = "";
    	if(isNotEmpty(list)){
    		for (Object object : list) {
    			reStr += method(object,param1).toString()+param2;
			}
    		reStr = reStr.substring(0,reStr.lastIndexOf(param2));
    	}
    	return reStr;
    }
    
    @SuppressWarnings("rawtypes")
	public static Object method(Object obj,String filed)   {
 	   try {
 		   Class clazz = obj.getClass();
 		   PropertyDescriptor pd = new PropertyDescriptor(filed,clazz);
 		   Method getMethod = pd.getReadMethod();//获得get方法
 	       Object o = getMethod.invoke(obj);//执行get方法返回一个Object
 	       return o;
 	   }catch (Exception e) {
 		   e.printStackTrace();
 		   return null;
 	   } 
 	 }

    
}
