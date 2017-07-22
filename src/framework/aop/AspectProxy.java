package framework.aop;

import java.lang.reflect.Method;


public abstract class AspectProxy implements Proxy {

	@Override
	public final Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result = null;
		Class<?> cls = proxyChain.getTargetClass();
		Method method = proxyChain.getTargetMethod();
		Object[] params = proxyChain.getMethodParams();
		begin();
		try {
			if (intercept(cls, method, params)) {
				beforeAdvice(cls, method, params);
				result = proxyChain.doProxyChain();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throwsAdvice(cls, method, params, e);
			throw e;
		}finally {
			end();
		}
		return result;
	}
	public void begin() {
    }

	/**
	 * 不重写过滤规则，将切入所有的方法
	 * @param cls
	 * @param method
	 * @param params
	 * @return
	 */
	protected boolean intercept(Class<?> cls, Method method, Object[] params) {
		return true;
	}

	
	public void beforeAdvice(Class<?> cls, Method method, Object[] params) {

	}

	public void afterAdvice(Class<?> cls, Method method, Object[] params) {

	}
	
	public void throwsAdvice(Class<?> cls, Method method, Object[] params, Throwable e) {
		
	}
	
	public void end() {
    }
}
