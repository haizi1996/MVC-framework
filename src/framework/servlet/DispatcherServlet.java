package framework.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import framework.core.LoadFrame;
import framework.core.ObjectFactory;
import framework.mvc.Handler;
import framework.mvc.bean.Model;
import framework.utils.WebUtil;

@WebServlet(urlPatterns = "/", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HandlerMapping handlerMaping = ObjectFactory.getObject(HandlerMapping.class);
	private HandlerInvoker handlerInvoker = ObjectFactory.getObject(HandlerInvoker.class);
	private AbstractResourceHandler resourceHandler = ObjectFactory.getResourceHandler();

	/**
	 * 初始化框架
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		ServletContext servletContext = config.getServletContext();
		MimeType.init(servletContext.getRealPath(""));
		UploadHelper.init(servletContext);
		LoadFrame.init();
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uri = request.getRequestURI();
		String contextpath = request.getContextPath();
		String requestPath = uri.substring(contextpath.length());
		if (requestPath.endsWith("/")) {
			requestPath = requestPath.substring(0, requestPath.length() - 1);
		}
		
		// 静态资源处理
		if (resourceHandler != null) {
			String responsePath = resourceHandler.handleRequest(requestPath);
			if (responsePath != null) {
				WebUtil.sendResources(request,response,responsePath);
				return;
			}
		}

		// 获取 Handler
		Handler handler = handlerMaping.getHandler(requestPath, request.getMethod());
		// 若未找到 Action，则跳转到 404 页面
		if (handler == null) {
			WebUtil.sendError(HttpServletResponse.SC_NOT_FOUND, "", response);
			return;
		}
		try {
			// 初始化 DataContext
			Model.init(request, response);

			if (handler != null) {
				// 调用 Handler
				handlerInvoker.invokeHandler(request, response, handler);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("handler 处理异常");
		} finally {
			// 销毁 DataContext
			Model.destroy();
		}
	}
}
