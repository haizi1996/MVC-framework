package framework.utils;

import java.lang.reflect.Field;
import java.util.Map;

import framework.annotation.AutoWare;

/**
 *依赖注入工具包
 */
public class IocUtil {
	
	private static Map<Class<?>,Object> beanMap;
	
	static {
		//获取所有的Bean类与Bean实例之间的映射关系
		beanMap = BeanUtil.getBeanMap();
		if(beanMap != null && beanMap.size() > 0){
			for(Map.Entry<Class<?>, Object> map : beanMap.entrySet()){
				Class<?> clazz = map.getKey();
				Object obj = map.getValue();
				Field[] fields = clazz.getDeclaredFields();
				if(fields != null && fields.length >0){
					for(Field field : fields){
						if(field.isAnnotationPresent(AutoWare.class)){
							Class<?> cls = field.getType();
							Object value = beanMap.get(cls);
							if(value != null){
								ReflectionUtil.setField(obj, field, value);
							}
						}
					}
				}
				
			}
		}
	}
	public static Map<Class<?>,Object> getBeanMap() {
		return beanMap;
	}
}
