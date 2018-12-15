package framework.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import framework.dataSource.DataSourceFactory;
import framework.dataSource.DefaultDataSourceFactory;
import framework.helper.ConfigHelper;
import framework.servlet.AbstractResourceHandler;
import framework.servlet.ConcreteResourceHandler;
import framework.utils.ClassUtil;
import framework.utils.Config;
import framework.utils.ObjectUtil;
import framework.utils.ReflectionUtil;
import framework.utils.StringUtil;

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

	/**
	 * 获取 DataSourceFactory
	 */
	public static DataSourceFactory getDataSourceFactory() {
		return getInstance(Config.DS_FACTORY, DefaultDataSourceFactory.class);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getInstance(String cacheKey, Class<T> defaultImplClass) {
		// 若缓存中存在对应的实例，则返回该实例
		if (cache.containsKey(cacheKey)) {
			return (T) cache.get(cacheKey);
		}
		// 从配置文件中获取相应的接口实现类配置
		String implClassName = ConfigHelper.getString(cacheKey);
		// 若实现类配置不存在，则使用默认实现类
		if (StringUtil.isEmpty(implClassName)) {
			implClassName = defaultImplClass.getName();
		}
		// 通过反射创建该实现类对应的实例
		T instance = ObjectUtil.newInstance(implClassName);
		// 若该实例不为空，则将其放入缓存
		if (instance != null) {
			cache.put(cacheKey, instance);
		}
		// 返回该实例
		return instance;
	}
}
