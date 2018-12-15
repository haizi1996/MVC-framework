package framework.servlet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import framework.core.BeanHelper;
import framework.core.ObjectFactory;
import framework.mvc.Handler;
import framework.mvc.bean.FileParam;
import framework.mvc.bean.Model;
import framework.utils.BeanUtil;
import framework.utils.ClassUtil;
import framework.utils.ParamUtil;
import framework.utils.WebUtil;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

public class HandlerInvoker {
	
	private ViewResolver viewResolver = ObjectFactory.getObject(ViewResolver.class);
	
	public void invokeHandler(HttpServletRequest request, HttpServletResponse response, Handler handler) throws Exception {
		// 获取 Action 相关信息
		Class<?> controllerClass = handler.getControllerClass();
		Method controllerMethod = handler.getControllerMethod();
		// 从 BeanHelper 中创建 Action 实例
		Object controllerInstance = BeanHelper.getBean(controllerClass);
		// 创建 所有的值和域
		Map<String, Object> controllerMethodParamValue = createControllerMethodParamValue(request, handler);
		// 获取Controller方法的形参数列表
		List<String> paramName = getParamNameList(controllerClass, controllerMethod);
		// 创建 Controller 方法的实参数列表
		List<Object> controllerMethodParamList = createActionMethodParamList(controllerMethod,
				controllerMethodParamValue, paramName);
		checkParamList(controllerMethod, controllerMethodParamList);
		Object actionMethodResult = invokeActionMethod(controllerMethod,controllerInstance,controllerMethodParamList);
		// 解析视图
        viewResolver.resolveView(request, response,controllerMethod, actionMethodResult);
	}

	private Object invokeActionMethod(Method controllerMethod, Object controllerInstance, List<Object> paramList)
			throws IllegalAccessException, InvocationTargetException {
		// 通过反射调用 Action 方法
		controllerMethod.setAccessible(true); 
		return controllerMethod.invoke(controllerInstance, paramList.toArray());
	}

	private List<Object> createActionMethodParamList(Method method, Map<String, Object> paramValue,
			List<String> paramName) {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<Object>();
		Class<?>[] fieldClass = method.getParameterTypes();
		String key;
		for (int i = 0; i < fieldClass.length; i++) {
			key = paramName.get(i);
			if (ClassUtil.isInt(fieldClass[i])) {
				params.add(Integer.parseInt((String) paramValue.get(key)));
			} else if (ClassUtil.isLong(fieldClass[i])) {
				params.add(Long.parseLong((String) paramValue.get(key)));
			} else if (ClassUtil.isDouble(fieldClass[i])) {
				params.add(Double.parseDouble((String) paramValue.get(key)));
			} else if (ClassUtil.isString(fieldClass[i])) {
				params.add((String) paramValue.get(key));
			} else if(fieldClass[i].equals(Model.class)){
				params.add(Model.getInstance());
			}else if(fieldClass[i].equals(FileParam.class)){
				params.add(paramValue.get(key));
			}else{
				Object t = BeanUtil.getBean(fieldClass[i]);
				BeanUtil.copyValueFileName(fieldClass[i], t, paramValue);
				params.add(t);
			}
		}
		return params;
	}

	private Map<String, Object> createControllerMethodParamValue(HttpServletRequest request, Handler handler) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			// 获取参数名列表
			if (!MapUtils.isEmpty(handler.getPathVariable())) {
				paramMap.putAll(handler.getPathVariable());
			}

			if (UploadHelper.isMultipart(request)) {
				paramMap.putAll(UploadHelper.createMultipartParamList(request));
			} else {
				// 添加普通请求参数列表（包括 Query String 与 Form Data）
				Map<String, Object> requestParamMap = WebUtil.getRequestParamMap(request);
				if (MapUtils.isNotEmpty(requestParamMap)) {
					ParamUtil.fillParamMap(request, paramMap);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return paramMap;
	}

	/**
	 * 获取方法参数列表名
	 */
	private List<String> getParamNameList(Class<?> clazz, Method method) {
		ClassPool pool = ClassPool.getDefault();
		pool.insertClassPath(new ClassClassPath(clazz));
		CtClass cc;
		try {
			cc = pool.get(clazz.getName());
			CtMethod cm = cc.getDeclaredMethod(method.getName());
			MethodInfo methodInfo = cm.getMethodInfo();
			CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
			LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
					.getAttribute(LocalVariableAttribute.tag);
			if (attr == null) {
				System.out.println("params is null");
			}
			String[] paramNames;
			paramNames = new String[cm.getParameterTypes().length];
			int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
			for (int i = 0; i < paramNames.length; i++)
				paramNames[i] = attr.variableName(i + pos);
			List<String> res = null;
			if (!ArrayUtils.isEmpty(paramNames)) {
				res = Arrays.asList(paramNames);
			}
			return res;
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private void checkParamList(Method actionMethod, List<Object> actionMethodParamList) {
		// 判断 Controller 方法参数的个数是否匹配
		Class<?>[] actionMethodParameterTypes = actionMethod.getParameterTypes();
		if (actionMethodParameterTypes.length != actionMethodParamList.size()) {
			String message = String.format("因为参数个数不匹配，所以无法调用 Action 方法！原始参数个数：%d，实际参数个数：%d",
					actionMethodParameterTypes.length, actionMethodParamList.size());
			throw new RuntimeException(message);
		}
	}
}
