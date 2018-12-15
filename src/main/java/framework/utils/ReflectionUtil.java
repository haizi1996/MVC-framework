package framework.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 *反射工具类
 */

public class ReflectionUtil {
	
	/**
	 * 创建实例
	 */
//	public static Object newInstance(Class<?> clazz){
//		Object object = null;
//		try {
//			object = clazz.newInstance();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return object;
//	}
	/**
	 * 创建实例
	 */
	public static<T> T newInstance(Class<T> clazz){
		T object = null;
		try {
			object = (T)clazz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}
	/**
	 * 调用方法
	 */
	public  static Object invokeMethod(Object obj,Method method,Object...args) {
		Object rest = null;
		try {
			rest = method.invoke(obj, args);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rest;
	}
	/**
	 * 设置成员变量
	 */
	public static void setField(Object obj , Field field , Object value){
		try {
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
}
