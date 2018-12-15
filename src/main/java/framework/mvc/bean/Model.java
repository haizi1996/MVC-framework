package framework.mvc.bean;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;

import framework.utils.CastUtil;
import framework.utils.CodeUtil;


/**
 * 数据上下文
 * @author haizi
 *
 */

public class Model {
	/*
	 * 每个线程都拥有自己的Model
	 */
	private static ThreadLocal<Model> dataContextContainer = new ThreadLocal<Model>();

	private HttpServletRequest request;
    private HttpServletResponse response;
	
    private Model() {
    	
    }
    /**
     * 初始化
     * @param request
     * @param response
     */
	public static void init(HttpServletRequest request, HttpServletResponse response) {
		Model model = new Model();
		model.request = request;
		model.response = response;
		dataContextContainer.set(model);
	}
	/**
	 * 获取model实例
	 * @return
	 */
	public static Model getInstance() {
		return dataContextContainer.get();
	}
	 /**
     * 销毁
     */
    public static void destroy() {
        dataContextContainer.remove();
    }
    /**
     * 获取 Request 对象
     */
    public HttpServletRequest getRequest() {
        return getInstance().request;
    }

    /**
     * 获取 Response 对象
     */
    public static HttpServletResponse getResponse() {
        return getInstance().response;
    }

    /**
     * 获取 Session 对象
     */
    public  HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取 Servlet Context 对象
     */
    public javax.servlet.ServletContext getServletContext() {
        return getRequest().getServletContext();
    }
    
    /**
     * 将数据放入 Request 中
     */
    public void putRequest(String key, Object value) {
        getRequest().setAttribute(key, value);
    }

    /**
     * 从 Request 中获取数据
     */
    @SuppressWarnings("unchecked")
	public <T> T getRequest(String key) {
        return (T) getRequest().getAttribute(key);
    }
    
    /**
     * 移除 Request 中的数据
     */
    public void removeRequest(String key) {
        getRequest().removeAttribute(key);
    }

    /**
     * 从 Request 中获取所有数据
     */
    public  Map<String, Object> getAllRequest() {
        Map<String, Object> map = new HashMap<String, Object>();
        Enumeration<String> names = getRequest().getAttributeNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            map.put(name, getRequest().getAttribute(name));
        }
        return map;
    }
    
    /**
     * 将数据放入 Response 中
     */
    public  void putResponse(String key, Object value) {
        getResponse().setHeader(key, CastUtil.castString(value));
    }

    /**
     * 从 Response 中获取数据
     */
    @SuppressWarnings("unchecked")
    public <T> T getResponse(String key) {
        return (T) getResponse().getHeader(key);
    }

    /**
     * 从 Response 中获取所有数据
     */
    public Map<String, Object> getAllResponse() {
        Map<String, Object> map = new HashMap<String, Object>();
        for (String name : getResponse().getHeaderNames()) {
            map.put(name, getResponse().getHeader(name));
        }
        return map;
    }
    /**
     * 将数据放入 Session 中
     */
    public  void putSession(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * 从 Session 中获取数据
     */
    @SuppressWarnings("unchecked")
    public  <T> T getSession(String key) {
        return (T) getSession().getAttribute(key);
    }

    /**
     * 移除 Session 中的数据
     */
    public  void removeSession(String key) {
        getSession().removeAttribute(key);
    }

    /**
     * 从 Session 中获取所有数据
     */
    public  Map<String, Object> getAllSession() {
        Map<String, Object> map = new HashMap<String, Object>();
        Enumeration<String> names = getSession().getAttributeNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            map.put(name, getSession().getAttribute(name));
        }
        return map;
    }

    /**
     * 移除 Session 中所有的数据
     */
    public void removeAllSession() {
        getSession().invalidate();
    }
    /**
     * 将数据放入 Cookie 中
     */
    public static void put(String key, Object value) {
        String strValue = CodeUtil.encodeURL(CastUtil.castString(value));
        javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie(key, strValue);
        getResponse().addCookie(cookie);
    }

    /**
     * 从 Cookie 中获取数据
     */
    @SuppressWarnings("unchecked")
    public  <T> T getCookie(String key) {
        T value = null;
        javax.servlet.http.Cookie[] cookieArray = getRequest().getCookies();
        if (ArrayUtils.isNotEmpty(cookieArray)) {
            for (javax.servlet.http.Cookie cookie : cookieArray) {
                if (key.equals(cookie.getName())) {
                    value = (T) CodeUtil.decodeURL(cookie.getValue());
                    break;
                }
            }
        }
        return value;
    }

    /**
     * 从 Cookie 中获取所有数据
     */
    public  Map<String, Object> getAllCookie() {
        Map<String, Object> map = new HashMap<String, Object>();
        javax.servlet.http.Cookie[] cookieArray = getRequest().getCookies();
        if (ArrayUtils.isNotEmpty(cookieArray)) {
            for (javax.servlet.http.Cookie cookie : cookieArray) {
                map.put(cookie.getName(), cookie.getValue());
            }
        }
        return map;
    }


    /**
     * 将数据放入 ServletContext 中
     */
    public void putServletContext(String key, Object value) {
        getServletContext().setAttribute(key, value);
    }

    /**
     * 从 ServletContext 中获取数据
     */
    @SuppressWarnings("unchecked")
    public  <T> T getServletContext(String key) {
        return (T) getServletContext().getAttribute(key);
    }

    /**
     * 移除 ServletContext 中的数据
     */
    public  void removeServletContext(String key) {
        getServletContext().removeAttribute(key);
    }

    /**
     * 从 ServletContext 中获取所有数据
     */
    public  Map<String, Object> getAllServletContext() {
        Map<String, Object> map = new HashMap<String, Object>();
        Enumeration<String> names = getServletContext().getAttributeNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            map.put(name, getServletContext().getAttribute(name));
        }
        return map;
    }
}
