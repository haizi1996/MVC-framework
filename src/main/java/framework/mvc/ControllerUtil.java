package framework.mvc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import framework.annotation.RequestMapping;
import framework.core.ClassHelper;

/**
 * Controller工具类
 */

public class ControllerUtil {

	/**
	 * 用于存放请求与处理器的映射关系
	 */
	private static Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

	static {
		// 存放所有的Controller类
		Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
		if (controllerClassSet != null && controllerClassSet.size() > 0) {
			for (Class<?> clazz : controllerClassSet) {
				Method[] methods = clazz.getDeclaredMethods();
				if (methods != null && methods.length > 0) {
					for (Method method : methods) {
						RequestMapping map = method.getAnnotation(RequestMapping.class);
						if (map != null) {
							String path = map.value();
							String style = map.method();
							Request request = new Request(style, path);
							Handler handler = new Handler(clazz, method);
							ACTION_MAP.put(request, handler);
						}
					}
				}

			}
		}
	}

	

	public static Map<Request, Handler> getACTION_MAP() {
		return ACTION_MAP;
	}

}
