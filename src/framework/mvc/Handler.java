package framework.mvc;

import java.lang.reflect.Method;
import java.util.Map;

/**
 *封装action信息 
 */
public class Handler {
	
	/**
	 * Controller类
	 */
	private Class<?> ControllerClass;
	
	/**
	 * action方法
	 */
	private Method actionMethod ;
	/**
	 * pathVariable的参数
	 */
	private Map<String,String> pathVariable;
	
	public Handler(Class<?> controllerClass, Method actionMethod) {
		ControllerClass = controllerClass;
		this.actionMethod = actionMethod;
	}

	public Class<?> getControllerClass() {
		return ControllerClass;
	}

	public Method getControllerMethod() {
		return actionMethod;
	}

	public Map<String, String> getPathVariable() {
		return pathVariable;
	}

	public void setPathVariable(Map<String, String> pathVariable) {
		this.pathVariable = pathVariable;
	}
	
	
	
}
