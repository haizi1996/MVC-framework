package framework.tx;

import java.lang.reflect.Method;

import framework.annotation.Transaction;
import framework.aop.Proxy;
import framework.aop.ProxyChain;
import framework.helper.DatabaseHelper;

public class TranscationProxy implements Proxy{

	private static ThreadLocal<Boolean> thread = new ThreadLocal<Boolean>() {
		@Override
		protected Boolean initialValue() {
			return false;
		}
		
	};

	@Override
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result;
        // 判断当前线程是否进行了事务处理
        boolean flag = thread.get();
        // 获取目标方法
        Method method = proxyChain.getTargetMethod();
        if(!flag && method.isAnnotationPresent(Transaction.class)) {
        	try {
        		thread.set(true);
        		 // 开启事务
                DatabaseHelper.startTransaction();
                result = proxyChain.doProxyChain();
             // 提交事务
                DatabaseHelper.commitTransaction();
        	}catch (Exception e) {
        		DatabaseHelper.rollbackTransaction();
                throw e;
			}finally {
				thread.remove();
			}
        }else {
        	result = proxyChain.doProxyChain();
        }
		return result;
	}

}
