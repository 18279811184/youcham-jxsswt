package io.youcham.common.utils;

import io.youcham.modules.ins.fileentity.FileConfigReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;


/**
 * 系统使用的静态变量.
 * 
 * @author 尔演&Eryan eryanwcp@gmail.com
 * @date 2013-03-17 上午8:25:36
 */
public class AppConstants {
	/**
	 * 修改用户密码 个人(需要输入原始密码)
	 */
	public static final String USER_UPDATE_PASSWORD_YES = "1";
	/**
	 * 修改用户密码 个人(不需要输入原始密码)
	 */
	public static final String USER_UPDATE_PASSWORD_NO = "0";

	private static Properties properties = null;

	/**
	 * 配置文件路径
	 */
	public static final String CONFIG_FILE_PATH = "config.properties";
	/**
	 * 配置文件日志保留时间 key
	 */
	public static final String CONFIG_LOGKEEPTIME = "logKeepTime";

	/**
	 * 门户记录数
	 */
	public static final String CONFIG_PORTAL_NUM = "portalNum";
	public static final String CONFIG_PIC_NUM = "picNum";
	/**
	 * 门户记录数
	 */
	public static final String CONFIG_URL = "url";
	public static final String CODE_URL = "codeUrl";

	/**
	 * 认证配置
	 */
	public static final String ACF_FILE_NAME = "acfFileName";
	
	/**
	 * corpId
	 */
	public static final String CORPID = "corpId";
	/**
	 * secret
	 */
	public static final String SECRET = "secret";
	
	/**
	 * @Name:getProperties
	 * @Title:
	 * @Description:
	 * @Rules:
	 * @author
	 * @date 2014-4-25 下午10:43:42
	 * @modify kimy_lu
	 * @version 1.0
	 * @return PropertiesLoader
	 */
	public synchronized static Properties getProperties() {
		if (properties == null) {
			try {
				properties = new Properties();
				InputStream inputStream = FileConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE_PATH);
				properties.load(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return properties;
	}

	/**
	 * 日志保留时间 天(默认值:30).
	 */
	public static int getLogKeepTime() {
		String logKeepTime = AppConstants.getProperties().getProperty(CONFIG_LOGKEEPTIME);
				
		if(StringUtils.isNotBlank(logKeepTime)){
			return Integer.valueOf(logKeepTime);
		}
		
		return 30;
	}

	/**
	 * 门户显示记录数
	 */
	public static int getPortalNum() {
		String portalNum = AppConstants.getProperties().getProperty(CONFIG_PORTAL_NUM);
		
		if(StringUtils.isNotBlank(portalNum)){
			return Integer.valueOf(portalNum);
		}
		
		return 10;
	}
	
	/**
	 * 图片新闻显示记录数
	 */
	public static int getPicNum() {
		String picNum = AppConstants.getProperties().getProperty(CONFIG_PIC_NUM);
		
		if(StringUtils.isNotBlank(picNum)){
			return Integer.valueOf(picNum);
		}
		
		return 5;
	}
	/**
	 * 系统部署url
	 */
	public static String getUrl() {
		return AppConstants.getProperties().getProperty(CONFIG_URL);
	}
	/**
	 * 二维码url
	 */
	public static String getCodeUrl() {
		return AppConstants.getProperties().getProperty(CODE_URL);
	}
	/**
	 * 认证代理配置文件名
	 */
	public static String getAcfFileName() {
		return AppConstants.getProperties().getProperty(ACF_FILE_NAME);
	}
	/**
	 * CORPID
	 */
	public static String getCorpid() {
		return AppConstants.getProperties().getProperty(CORPID);
	}
	/**
	 * secret
	 */
	public static String getSecret() {
		return AppConstants.getProperties().getProperty(SECRET);
	}
}
