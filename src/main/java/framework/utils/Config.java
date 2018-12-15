package framework.utils;

/**
 * 提供相关配置项常量
 */
public interface Config {
	String CONFIG_FILE = "default.properties" ;
	//数据库的基本信息
	String JDBC_DRIVER = "JDBC_DRIVER" ;
	String JDBC_URL = "JDBC_URL" ;
	String JDBC_USERNAME = "JDBC_USERNAME" ;
	String JDBC_PASSWORD = "JDBC_PASSWORD";
	
	//数据库连接池
	String DATASOURCE = "DATASOURCE";
	
	String APP_BASE_PACKAGE = "APP_BASE_PACKAGE";
	//视图解析前缀
	String APP_JSP_PATH = "APP_JSP_PATH";
	//视图解析后缀
	String APP_JSP_SUFFIX="APP_JSP_SUFFIX";
	
	/**
	 * 多个静态资源用逗号隔开，与映射文件依依对应
	 */
	//配置静态资源
	String APP_ASSET_PATH = "APP_ASSET_PATH";
	//静态资源映射
	String APP_ASSET_MAPPING = "APP_ASSET_MAPPING";
	
	
	//上传文件的路径
	String FILE_UPLOAD_DIR = "FILE_UPLOAD_DIR";
	//文件大小的限制
	String UPLOAD_LIMIT="UPLOAD_LIMIT";
	//网站的编码
	String ENCODE = "UTF-8";

	/**
	 * DataSourceFactory
	 */
	String DS_FACTORY = "my.framework.custom.ds_factory";
}
