package framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/*")
public class EncodeFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req ; 
		HttpServletResponse res ;
		try{
			req = (HttpServletRequest)arg0;
			res = (HttpServletResponse)arg1;
		}catch(Exception e){
			throw new RuntimeException("non-http request");
		}
		String encoding = "UTF-8";//默认参数
		req.setCharacterEncoding(encoding);//POST请求方式
		res.setContentType("text/html;charset="+encoding);
		MyRequest request = new MyRequest(req);
		arg2.doFilter(request, res);
	}

	
}
