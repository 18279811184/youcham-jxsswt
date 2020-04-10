  
/**    
 * @Title: ExcelUtil.java  
 * @Package cn.com.thinvent.redcross.volunteer.web.controller.web.util  
 * @Description: TODO  
 * @author liuxg  
 * @date 2017年8月23日  
 * @version V1.0    
 */  
    
package io.youcham.modules.ins.fileentity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//import org.apache.log4j.Logger;

/**
 * @author xucg
 *
 */
public class FileConfigReader {
	private  static Properties properties=null;
	
	static{
		loadProps();
	}
	private synchronized static void loadProps(){
		properties = new Properties();
		InputStream inputStream = null;
		try {
			//LOG.info("------------------------加载配置文件-------------------------");
			inputStream = FileConfigReader.class.getClassLoader().getResourceAsStream("path_config.properties");
			properties.load(inputStream);
			//LOG.info("-------------------------加载配置文件成功----------------------");
		} catch (IOException e) {
			e.printStackTrace();
			//LOG.info("-------------------------加载配置文件失败----------------------");
		}finally {
			if (inputStream!=null) {
				try {
					inputStream.close();
				//	LOG.info("-------------------------关闭志愿输入流----------------------");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static String getSimfangFontPath(){
		if (properties==null) {
			loadProps();
		}
		return properties.getProperty("create_simfangfont_path");
	}	
	public static String getSimHeiFontPath(){
		if (properties==null) {
			loadProps();
		}
		return properties.getProperty("create_simheifont_path");
	}	
	public static String getUploadFilePath(){
		if (properties==null) {
			loadProps();
		}
		return properties.getProperty("upload_img_path");
	}	
	public static String getWxsendurl(){
		if (properties==null) {
			loadProps();
		}
		return properties.getProperty("sendurlwx");
	}	
	public static String getUploadFileMaxSize(){
		if (properties==null) {
			loadProps();
		}
		return properties.getProperty("upload_img_maxsize");
	}
	public static String getUploadDocTemplate(){
		if (properties==null) {
			loadProps();
		}
		return properties.getProperty("upload_doc_template");
	}	
	public static Properties getFileProperties(){
		if (properties==null) {
			loadProps();
		}
		return properties;
	}
	public static String getProperties(String key){
		if (properties==null) {
			loadProps();
		}
		return properties.getProperty(key);
	}
}
