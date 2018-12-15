package framework.aop;

/**
 *执行链式代理 
 *
 */

public interface Proxy {
	
	Object doProxy(ProxyChain proxyChain) throws Throwable;
	
}
