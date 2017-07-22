package framework.helper;

import java.util.Properties;

import framework.utils.Config;
import framework.utils.PropsUtil;

public class ConfigHelper {

	private static final Properties prop = PropsUtil.loadProps(Config.CONFIG_FILE);
	
	/**
	 * 获取jdbc驱动
	 */
	public static String getJdbcDriver(){
		return PropsUtil.getString(prop, Config.JDBC_DRIVER);
	}
	
	
	/**
	 * 获取jdbc URL
	 */
	public static String getJdbcUrl(){
		return PropsUtil.getString(prop, Config.JDBC_URL);
	}
	
	
	/**
	 * 获取jdbc驱动
	 */
	public static String getJdbcUsername(){
		return PropsUtil.getString(prop, Config.JDBC_USERNAME);
	}
	
	
	/**
	 * 获取jdbc驱动
	 */
	public static String getJdbcPassword(){
		return PropsUtil.getString(prop, Config.JDBC_PASSWORD);
	}
	
	
	/**
	 * 获取应用基础包名
	 */
	public static String getAppBasePackage(){
		return PropsUtil.getString(prop, Config.APP_BASE_PACKAGE);
	}
	
	
	/**
	 * 获取jsp路径
	 */
	public static String getAppJspPath(){
		return PropsUtil.getString(prop, Config.APP_JSP_PATH);
	}
	
	/**
	 * 获取视图解析后缀
	 */
	public static String getAppJspSuffix() {
		return PropsUtil.getString(prop, Config.APP_JSP_SUFFIX);
	}
	
	/**
	 * 获取静态资源路径
	 */
	public static String getAppAssetPath(){
		
		return PropsUtil.getString(prop, Config.APP_ASSET_PATH);
		
	}
	
	/**
	 * 获取静态资源的映射
	 */
	public static String getAppAssetMapping() {
		return PropsUtil.getString(prop, Config.APP_ASSET_MAPPING);
	}
	/**
	 * 获取文件的最大值
	 * @param defaultUploadLimit
	 * @return
	 */
	public static int getUploadLimit( int defaultUploadLimit) {
		try {
			int upload_Limit = Integer.parseInt(prop.getProperty(Config.UPLOAD_LIMIT)) ;
			return upload_Limit;
		}catch(Exception e) {
			return defaultUploadLimit;
		}
	}

	/**
	 * 获取网站编码
	 * @return
	 */
	public static String getEncode() {
		// TODO Auto-generated method stub
		return PropsUtil.getString(prop, Config.ENCODE);
	}

	/**
	 * 获取文件上传的路径
	 * @return
	 */
	public static String getFileUploadDir() {
		// TODO Auto-generated method stub
		return PropsUtil.getString(prop, Config.FILE_UPLOAD_DIR);
	}

	/**
	 * 获取数据库连接池的配置
	 * @return
	 */
	public static String getDataSource() {
		// TODO Auto-generated method stub
		return PropsUtil.getString(prop, Config.DATASOURCE);
	}

	
}
