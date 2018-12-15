package framework.tx;

import java.lang.reflect.Method;

import framework.annotation.Transaction;
import framework.aop.Proxy;
import framework.aop.ProxyChain;
import framework.helper.DatabaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TranscationProxy implements Proxy{


	private static final Logger LOGGER = LoggerFactory.getLogger(TranscationProxy.class);
	/**
	 * 定义一个线程本地变量 保持当前线程是否在事务内  当前线程没有事务则创建一个线程
	 */
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
				LOGGER.debug("{myMVC} begin transaction");
        		 // 开启事务
                DatabaseHelper.startTransaction();
                result = proxyChain.doProxyChain();
                LOGGER.debug("{myMVC} commit transaction");
             // 提交事务
                DatabaseHelper.commitTransaction();
        	}catch (Exception e) {
        		DatabaseHelper.rollbackTransaction();
				LOGGER.debug("{myMVC} rollBack transaction");
                throw e;
			}finally {
        		//移除线程本地变量
				thread.remove();
			}
        }else {
        	//执行目标方法
        	result = proxyChain.doProxyChain();
        }
		return result;
	}

}
