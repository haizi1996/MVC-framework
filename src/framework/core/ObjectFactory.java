package framework.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import framework.helper.ConfigHelper;
import framework.servlet.AbstractResourceHandler;
import framework.servlet.ConcreteResourceHandler;
import framework.utils.ClassUtil;
import framework.utils.ReflectionUtil;

public class ObjectFactory {
	
	private static Map<String,Object> cache = new ConcurrentHashMap<String,Object>();
	
	
	@SuppressWarnings("unchecked")
	public static <T> T getObject(Class<T> clazz) {
		if(cache.containsKey(clazz.getName())) {
			return (T)cache.get(clazz.getName());
		}
		T t =  ReflectionUtil.newInstance(clazz);
		if(t != null)
			cache.put(clazz.getName(), t);
		return t;
		
	}

	public static AbstractResourceHandler getResourceHandler() {
		if(cache.get(AbstractResourceHandler.class.getName())==null) {
			List<String> mapping = new ArrayList<String>();
			List<String> location = new ArrayList<String>();
			if(mapping.size() > 0 && location.size() > 0) {
				AbstractResourceHandler resourceHandler = new ConcreteResourceHandler(mapping , location , 0);
				cache.put(AbstractResourceHandler.class.getName(), resourceHandler);
				return resourceHandler;
			}
			return null;
		}else {
			return (AbstractResourceHandler)cache.get(AbstractResourceHandler.class.getName());
		}
	}
	
	/**
	 * 获取容器中的Bean
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> cls) throws Exception{
		if(ClassHelper.isContain(cls)) {
			Object t = cache.get(cls.getName());
			if(t == null) {
				t =  BeanHelper.getBean(cls);
				cache.put(cls.getName(), t);
			}
			return (T)t;
		}else {
			throw new RuntimeException("容器中不存在该类!!");
		}
		
	}

	public static DataSource getDataSource() {
			DataSource dataSource = null; 
			String dataSourceName = ConfigHelper.getDataSource();
			if(dataSourceName == null) {
				Class<?> cls = ClassUtil.loadClass(dataSourceName, false);
				if(cls!= null) {
					dataSource = (DataSource) getObject(cls);
				}else {
					//TODO
				}
			}
			return dataSource;
	}

}
