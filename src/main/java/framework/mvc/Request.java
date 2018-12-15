package framework.mvc;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 封装请求信息
 */
public class Request {
	
	/**
	 * 请求方法(get / put)
	 */
	private String requestMethod;
	/**
	 * 请求路径
	 */
	private String requestPath;
	
	
	/**
	 * 定义权值
	 */
	private int pathValue = 0;
	
	private Map<Integer,String> path;
	
	public Request(String requestMethod ,String requestPath){
		this.requestMethod = requestMethod;
		init(requestPath);
		path = new HashMap<Integer,String>();
	}

	private void init(String requestPath) {
		// TODO Auto-generated method stub
		if(requestPath != null && requestPath.length() > 0) {
			String lines[] = requestPath.split("/");
			StringBuffer buffer = new StringBuffer("^");
			for(int i = 0 ; i < lines.length ; i++) {
				String line = lines[i];
				if(!line.equals("")) {
					if(line.indexOf(0)=='{' && line.indexOf(line.length()-1)=='}') {
						path.put(i, new String(line));
						buffer.append("/\\w+");
						pathValue ++;
					}else {
						buffer.append("/").append(line);
					}
				}
			}
			this.requestPath = buffer.append("$").toString();
		}
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public String getRequestPath() {
		return requestPath;
	}

	private boolean isMatcher(String str) {
		return Pattern.matches(this.requestPath, str);
	}
	
	public boolean isPathAvariable() {
		return pathValue > 0;
	}

	public Map<Integer, String> getPath() {
		return path;
	}

	public boolean isRequest(String requestMethod ,String requestPath) {
		return isMatcher(requestPath)&&this.requestMethod.equalsIgnoreCase(requestMethod);
	}

	public int getPathValue() {
		return pathValue;
	}
	
}