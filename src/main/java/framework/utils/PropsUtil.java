package framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropsUtil {

	/**
	 * 加载属性配置文件
	 */
	public static Properties loadProps(String fileName){
		Properties prop = null; 
		InputStream in = null;
		
		in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		if(in != null){
			
			prop = new Properties();
			try {
				prop.load(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(in != null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
	
	/**
     * 加载属性文件，并转为 Map
     */
    public static Map<String, String> loadPropsToMap(String propsPath) {
        Map<String, String> map = new HashMap<String, String>();
        Properties props = loadProps(propsPath);
        for (String key : props.stringPropertyNames()) {
            map.put(key, props.getProperty(key));
        }
        return map;
    }
	
	/**
	 * 获取字符型属性(默认java接口常量)
	 */
	public static String getString(Properties prop , String key){
		String value = null;
		if(prop != null){
			value = (String) prop.get(key);
		}
		return value;
	}
	/**
	 *获取整型值属性 
	 */
	public static int getInt(Properties prop , String key, int defaultVaule){
		int value = defaultVaule;
		if(prop != null){
			value = (Integer) prop.get(key);
		}
		return value;
	}
}
