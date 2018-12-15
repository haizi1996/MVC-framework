package framework.core;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import framework.annotation.Controller;
import framework.annotation.Service;
import framework.helper.ConfigHelper;

public class ClassHelper {
	
	/**
	 * 存放所有加载类集合
	 */
	private static final Set<Class<?>> CLASS_SET;
	static {
		String basePackage = ConfigHelper.getAppBasePackage();
		CLASS_SET = ClassScanner.getClassSet(basePackage);
	}
	
	/**
	 * 获取应用包下某父类或接口的所有子类
	 */
	public static List<Class<?>> getClassSetBySuper(Class<?> superClass){
		List<Class<?>> classSet = new ArrayList<Class<?>>();
		for(Class<?> cls : CLASS_SET){
			if(superClass.isAssignableFrom(cls) && !superClass.equals(cls)){
				classSet.add(cls);
			}
		}
		
		return classSet;
	}
	
	/**
	 * 获取应用包下带有某注解的所有子类
	 */
	public static List<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass){
		List<Class<?>> classSet = new ArrayList<Class<?>>();
		for(Class<?> cls : CLASS_SET){
			if(cls.isAnnotationPresent(annotationClass)){
				classSet.add(cls);
			}
		}
		
		return classSet;
	}
	/**
	 * 获取应用包下的所有类
	 */
	public static Set<Class<?>> getClassSet(){
		return CLASS_SET;
	} 
	
	/**
	 * 获取应用包名下所有Service类
	 */
	public static Set<Class<?>> getServiceClassSet(){
		Set<Class<?>> set = new HashSet<Class<?>>();
		for(Class<?> t : CLASS_SET){
			if(t.isAnnotationPresent(Service.class)){
				set.add(t);
			}
		}
		return set;
	}
	/**
	 * 获取应用包名下所有Controller类
	 */
	public static Set<Class<?>> getControllerClassSet(){
		Set<Class<?>> set = new HashSet<Class<?>>();
		for(Class<?> t : CLASS_SET){
			if(t.isAnnotationPresent(Controller.class)){
				set.add(t);
			}
		}
		return set;
	}
	/**
	 * 获取应用包名下所有Bean类(包含Controller和Service类)
	 */
	public static Set<Class<?>> getAllBeanClassSet(){
		Set<Class<?>> set = new HashSet<Class<?>>();
		set.addAll(getControllerClassSet());
		set.addAll(getServiceClassSet());
		return set;
	}
	/**
	 * 是否存在该类
	 * @param cls
	 * @return
	 */
	public static<T> boolean isContain(Class<T> cls) {
		return CLASS_SET.contains(cls);
	}
}
