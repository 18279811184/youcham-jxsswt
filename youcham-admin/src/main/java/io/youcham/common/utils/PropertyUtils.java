package io.youcham.common.utils;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyUtils {

	private static Logger logger = LoggerFactory.getLogger(PropertyUtils.class);
	 
	public static String getPropertyValueByKey(String key) {
		Properties props = new Properties();
		try {
			
			props.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("configure.properties"));
			return props.getProperty(key).trim();
		} catch (Exception e) {
			logger.error(e.toString(),e);
			return null;
		}

	}
	public static String getPropertyValueByKeyToPathConfig(String key) {
		Properties props = new Properties();
		try {

			props.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("path_config.properties"));
			return props.getProperty(key).trim();
		} catch (Exception e) {
			logger.error(e.toString(),e);
			return null;
		}

	}


	/**
	 * 测试代码，请勿使用在开发中
	 * @param key
	 * @param value
	 * @param path
	 */
	public static void setPropertyValueByKey(String key, String value, String path) {
		Properties props = new Properties();
		
		try {
			System.out.println("属性文件：" + PropertyUtils.class.getClassLoader().getResource("configure.properties").getPath());
			OutputStream fos = new FileOutputStream( PropertyUtils.class.getClassLoader().getResource("configure.properties").getPath());   
			props.setProperty(key, value);
			props.store(fos, "Update '" + key + "' value"); 
			fos.close();
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
	}
	
	public static String getPropertyValueByKey(String propertyName,String key) {

		Properties props = new Properties();
		try {
			props.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(propertyName));
			return props.getProperty(key).trim();
		} catch (Exception e) {
			logger.error(e.toString(),e);
			return null;
		}

	}
	
	public static Properties getProperty(String propertyName) {
		Properties props = new Properties();
		try {
			props.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(propertyName));
			
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
		return props;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*List l = PropertyUtils.getText();
		System.out.println("pch--->"+l.get(0));
		System.out.println("zh--->"+l.get(1));
		System.out.println("qyzb--->"+l.get(2));
		System.out.println("qysl--->"+l.get(3)); 
		System.out.println("zfry--->"+l.get(4));
		System.out.println("a--->"+l.get(5));
		System.out.println("b--->"+l.get(6));
		System.out.println("c--->"+l.get(7));
		System.out.println("d--->"+l.get(8));
		System.out.println("比例："+l.get(9));*/
		//System.out.println(getPropertyValueByKey("filepath"));
		
		String imagPath = getPropertyValueByKey("imagepath");
		System.out.println("22" + imagPath);
		
		setPropertyValueByKey("imagepath","F:/apache-tomcat-7.0.63/images","");
		String imagPath2 = getPropertyValueByKey("imagepath");
		System.out.println(imagPath2);
		
	}
	
	public static List<Object> getText(){
		List<Object> list = new ArrayList<Object>();
		String probability =PropertyUtils.getPropertyValueByKey("probability");
		int pch = Integer.parseInt(PropertyUtils.getPropertyValueByKey("pch"));
		int zh = Integer.parseInt(PropertyUtils.getPropertyValueByKey("zh"));
		int qysl = Integer.parseInt(PropertyUtils.getPropertyValueByKey("qysl"));
		int zfry = Integer.parseInt(PropertyUtils.getPropertyValueByKey("zfry"));
		int qyzb = Integer.parseInt(PropertyUtils.getPropertyValueByKey("qyzb"));
		/**
		 * 正常企业所占比例
		 */
		String[] r = probability.split(":");
		int Normal1=Integer.parseInt(r[0]);
		int Normal2=Integer.parseInt(r[1]);
		int Normal3=Integer.parseInt(r[2]);
		int Normal4=Integer.parseInt(r[3]);

		/**
		 * 总比例
		 */
		int Total_proportion = Normal1+Normal2+Normal3+Normal4;
		/**
		 * 正常企业抽取人数
		 */
		int A = qysl * Normal1/Total_proportion;
		
		/**
		 * 非正常企业抽查人数
		 */
		int Not_A = qysl-A;
		/**
		 * 黄色警示
		 */
		int B = qysl * Normal2/Total_proportion;
		/**
		 * 橙色警示
		 */
		int C = qysl * Normal3/Total_proportion;
		/**
		 * 红色警示
		 */
		int D = qysl * Normal4/Total_proportion;
		
		list.add(pch);
		list.add(zh);
		list.add(qyzb);
		list.add(qysl);
		list.add(zfry);
		list.add(A);
		list.add(B);
		list.add(C);
		list.add(D);
		list.add(probability);
		list.add(Not_A);
		return list;
	}

}
