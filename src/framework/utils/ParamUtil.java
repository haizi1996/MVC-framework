package framework.utils;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class ParamUtil {
	
	public static Map<String,Object> fillParamMap(HttpServletRequest request,Map<String ,Object> paramMap) {
		// TODO Auto-generated method stub
		Enumeration<String> paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()){
			String paramName = paramNames.nextElement();
			paramMap.put(paramName, request.getParameter(paramName));
		}
		return paramMap;
	}

}
