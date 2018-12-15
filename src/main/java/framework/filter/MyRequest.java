package framework.filter;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

class MyRequest extends HttpServletRequestWrapper{


	private HttpServletRequest request;
	public MyRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
	}
	

	@Override
	public String getParameter(String name) {
		// TODO Auto-generated method stub
		String value = request.getParameter(name);
		if(value==null)
			return value;
		try {
			if("get".equalsIgnoreCase(request.getMethod()))
				value = new String(value.getBytes("ISO-8859-1"),request.getCharacterEncoding());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	
	
}