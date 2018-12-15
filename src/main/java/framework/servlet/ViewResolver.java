package framework.servlet;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MapUtils;

import framework.annotation.ResponseBody;
import framework.mvc.bean.View;
import framework.utils.WebUtil;

public class ViewResolver {

	public void resolveView(HttpServletRequest request, HttpServletResponse response, Method controllerMethod,
			Object actionMethodResult) {
		if (actionMethodResult != null) {
			if (actionMethodResult instanceof View) {
				View view = (View) actionMethodResult;
				if (view.isRedirect()) {
					WebUtil.redirectRequest(view.getPath(), request, response);
				} else {
					Map<String, Object> model = view.getModel();
					if (MapUtils.isNotEmpty(model)) {
						for (Map.Entry<String, Object> entry : model.entrySet()) {
							request.setAttribute(entry.getKey(), entry.getValue());
						}
					}
					WebUtil.forwardRequest(view.getPath(), request, response);
				}
			} else if (actionMethodResult instanceof String) {
				ResponseBody responsebody = controllerMethod.getAnnotation(ResponseBody.class);
				if(responsebody != null) {
					WebUtil.setContentType(response, controllerMethod);
					WebUtil.writeJSON(response, actionMethodResult);
					return ;
				}
				String path = (String) actionMethodResult;
				if (path.indexOf("redirect:") == 0 || path.startsWith("/")) {
					WebUtil.redirectRequest(path.substring("redirect:".length() - 1), request, response);
				} else {
					WebUtil.forwardRequest(path, request, response);
				}
			} else {
				if (UploadHelper.isMultipart(request)) {
                    // 对于 multipart 类型，说明是文件上传，需要转换为 HTML 格式并写入响应中
					WebUtil.setContentType(response, controllerMethod);
                    WebUtil.writeHTML(response, actionMethodResult);
                } else {
                    // 对于其它类型，统一转换为 JSON 格式并写入响应中
                	WebUtil.setContentType(response, controllerMethod);
                    WebUtil.writeJSON(response, actionMethodResult);
                }

			}
			
		}

	}

}
