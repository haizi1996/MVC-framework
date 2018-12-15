package framework.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import framework.core.ClassHelper;

public class BeanUtil {

	/**
	 * 定义Bean映射
	 */
	private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

	static {

		Set<Class<?>> set = ClassHelper.getAllBeanClassSet();
		for (Class<?> clazz : set) {
			try {
				BEAN_MAP.put(clazz, clazz.newInstance());
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 获取Bean映射
	 */
	public static Map<Class<?>, Object> getBeanMap() {
		return BEAN_MAP;
	}

	/**
	 * 获取Bean实例
	 */
	public static <T> T getBean(Class<T> clazz) {
		T t = null;
		if (BEAN_MAP.containsKey(clazz)) {
			try {
				t = (T) clazz.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return t;
	}

	public static void setBean(Class<?> cls, Object obj) {
		BEAN_MAP.put(cls, obj);
	}

	public static void copyValueFileName(Class<?> t, Object obj, Map<String, Object> paramValue) {
		// TODO Auto-generated method stub
		Field[] fields = t.getDeclaredFields();
		String key;
		for (int i = 0; i < fields.length; i++) {
			key = fields[i].getName();
			fields[i].setAccessible(true);
			try {
				if (ClassUtil.isInt(fields[i].getClass())) {
					fields[i].set(obj, Integer.parseInt((String) paramValue.get(key)));
				} else if (ClassUtil.isLong(fields[i].getClass())) {
					fields[i].set(obj, Long.parseLong((String) paramValue.get(key)));
				} else if (ClassUtil.isDouble(fields[i].getClass())) {
					fields[i].set(obj, Double.parseDouble((String) paramValue.get(key)));
				} else if (ClassUtil.isString(fields[i].getClass())) {
					fields[i].set(obj, (String) paramValue.get(key));
				}

			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
