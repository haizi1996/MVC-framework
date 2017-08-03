package framework.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

import framework.annotation.RequestMapping;
import framework.helper.ConfigHelper;

public class WebUtil {
	/**
	 * 获取请求的参数对Map<key,value>
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getRequestParamMap(HttpServletRequest request) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			paramMap.put(paramName, request.getParameter(paramName));
		}
		return paramMap;
	}

	/**
	 * 将数据写入json
	 * 
	 * @param response
	 * @param data
	 */
	public static void writeJSON(HttpServletResponse response, Object data) {
		response.setCharacterEncoding(ConfigHelper.getEncode()); 
		try {
			PrintWriter writer = response.getWriter();
			writer.write(JsonUtil.toJSON(data));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("在响应中写数据出错！");
		}
	}

	/**
	 * 转发请求
	 * 
	 * @param path
	 * @param request
	 * @param response
	 */
	public static void forwardRequest(String path, HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher(ConfigHelper.getAppJspPath()+path+ConfigHelper.getAppJspSuffix()).forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("转发请求出错！");
		}
	}

	/**
	 * 重定向
	 * 
	 * @param path
	 * @param request
	 * @param response
	 */
	public static void redirectRequest(String path, HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect(request.getContextPath() + path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("重定向请求出错！");
		}
	}

	/**
	 * 发送404错误
	 * 
	 * @param code
	 * @param message
	 * @param response
	 */
	public static void sendError(int code, String message, HttpServletResponse response) {
		try {
			response.sendError(code, message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("发送错误代码出错！");
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param response
	 * @param filePath
	 */
	public static void downloadFile(HttpServletResponse response, String filePath) {
		try {
			String originalFileName = FilenameUtils.getName(filePath);
			String downloadedFileName = new String(originalFileName.getBytes("GBK"), "ISO8859_1");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename=\"" + downloadedFileName + "\"");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("下载文件出错！");
		}
	}

	public static String getCookie(HttpServletRequest request, String name) {
		String value = "";
		try {

			Cookie[] cookieArray = request.getCookies();
			if (cookieArray != null) {
				for (Cookie cookie : cookieArray) {
					if (StringUtil.isNotEmpty(name) && name.equals(cookie.getName())) {
						value = CodeUtil.decodeURL(cookie.getValue());
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取 Cookie 出错！");
		}
		return value;
	}
	/**
	 * 将数据以 HTML 格式写入响应中（在 JS 中获取的是 JSON 字符串，而不是 JSON 对象）
	 * @param response
	 * @param actionMethodResult
	 */
	public static void writeHTML(HttpServletResponse response, Object data) {
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.write(JsonUtil.toJSON(data)); // 转为 JSON 字符串
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
            throw new RuntimeException("在响应中写数据出错！");
		}
	}
	/**
	 * 设置响应的消息头
	 * @param response
	 * @param controllerMethod
	 */
	public static void setContentType(HttpServletResponse response ,Method controllerMethod) {
		RequestMapping mapping = controllerMethod.getClass().getAnnotation(RequestMapping.class);
		String contentType = mapping.produce();
		String encodeType = ConfigHelper.getEncode();
		response.setContentType(contentType + ";charset=" + encodeType);
	}
	
	
	/**
	 * 请求静态资源
	 * @throws IOException 
	 */
	public static void sendResources(HttpServletRequest request, HttpServletResponse response, String responsePath) throws IOException {
		ServletContext context = request.getServletContext();
		String inputPath = context.getRealPath(responsePath);
		InputStream input = context.getResourceAsStream(responsePath);
		Reader reader = new FileReader(inputPath);
		BufferedReader buffer = new BufferedReader(reader);
		response.setCharacterEncoding("UTF-8");
		response.setContentType(MimeType.getMemiTypeByFileName(responsePath));
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			String str = null;
			while((str = buffer.readLine()) != null) {
				writer.println(str);
			}
			writer.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(writer != null) {
				writer.close();
			}
			if(input != null) {
				input.close();
			}
			if(buffer != null) {
				buffer.close();
			}
		}
	}

}
