package framework.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;


/**
 * 代理链
 *
 */
public class ProxyChain {
	private Class<?> targetClass;
	
	private Object targetObject;
	private Method targetMethod;
	private MethodProxy methodProxy;
	
	private Object[] methodParams;
	private int proxyIndex = 0;
	
	private List<Proxy> proxyList = new  ArrayList<Proxy>();

	public ProxyChain(Class<?> targetClass, Object targetObject,
			Method targetMethod, MethodProxy methodProxy,
			Object[] methodParams, List<Proxy> proxyList) {
		this.targetClass = targetClass;
		this.targetObject = targetObject;
		this.targetMethod = targetMethod;
		this.methodProxy = methodProxy;
		this.methodParams = methodParams;
		this.proxyList = proxyList;
	}
	
	public Object doProxyChain() throws Throwable{
		Object methodResult = null;
		try{
			if(proxyIndex < proxyList.size()){
				
				methodResult = proxyList.get(proxyIndex++).doProxy(this); 
			}else{
				methodResult = methodProxy.invokeSuper(targetObject,methodParams);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return methodResult;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public Method getTargetMethod() {
		return targetMethod;
	}

	public MethodProxy getMethodProxy() {
		return methodProxy;
	}

	public Object[] getMethodParams() {
		return methodParams;
	}
	
}
