package framework.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.CollectionUtils;

import framework.annotation.Aspect;
import framework.annotation.Service;
import framework.aop.Proxy;
import framework.aop.ProxyFactory;
import framework.core.ClassHelper;
import framework.tx.TranscationProxy;
import framework.utils.BeanUtil;

public class AopHelper {

	static {
		try {
			// 创建 Proxy Map（用于 存放代理类 与 被代理类列表 的映射关系）
			Map<Class<?>, List<Class<?>>> proxyMap = createProxyMap();
			// 创建 Target Map（用于 被代理类 与 代理类列表 的映射关系）
			Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
			for (Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
				Class<?> targetClass = targetEntry.getKey();
				List<Proxy> proxyList = targetEntry.getValue();
				Object proxy = ProxyFactory.createProxy(targetClass, proxyList);
				BeanUtil.setBean(targetClass, proxy);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Map<Class<?>, List<Class<?>>> createProxyMap() {
		Map<Class<?>, List<Class<?>>> proxyMap = new HashMap<Class<?>, List<Class<?>>>();
		addAspectProxy(proxyMap);
		addTransactionProxy(proxyMap);
		return proxyMap;
	}

	/**
	 * 排序代理类
	 * 
	 * @param targetMap
	 */
	private static void sortProxyList(List<Class<?>> proxyClass) {
		if (CollectionUtils.isEmpty(proxyClass)) {
			Collections.sort(proxyClass, new Comparator<Class<?>>() {
				@Override
				public int compare(Class<?> o1, Class<?> o2) {
					return o1.getAnnotation(Aspect.class).order() - o2.getAnnotation(Aspect.class).order();
				}
			});
		}
	}

	private static void addTransactionProxy(Map<Class<?>, List<Class<?>>> proxyMap) {
		List<Class<?>> serviceClassList = ClassHelper.getClassSetByAnnotation(Service.class);
		proxyMap.put(TranscationProxy.class, serviceClassList);
	}

	private static void addAspectProxy(Map<Class<?>, List<Class<?>>> proxyMap) {
		List<Class<?>> proxyClassSet = ClassHelper.getClassSetByAnnotation(Aspect.class);
		for (Class<?> proxyClass : proxyClassSet) {
			if (proxyClass.isAnnotationPresent(Aspect.class)) {
				Aspect aspect = proxyClass.getAnnotation(Aspect.class);
				List<Class<?>> targetClassSet = Arrays.asList(aspect.targetClass());
				sortProxyList(targetClassSet);
				proxyMap.put(proxyClass, targetClassSet);
			}
		}
	}

	private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, List<Class<?>>> proxyMap) throws Exception {
		Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
		for (Entry<Class<?>, List<Class<?>>> proxyEntry : proxyMap.entrySet()) {
			Class<?> proxyClass = proxyEntry.getKey();
			List<Class<?>> targetClassSet = proxyEntry.getValue();
			for (Class<?> targetClass : targetClassSet) {
				Proxy proxy = (Proxy) proxyClass.newInstance();
				if (targetMap.containsKey(targetClass)) {
					targetMap.get(targetClass).add(proxy);
				} else {
					List<Proxy> proxyList = new ArrayList<Proxy>();
					proxyList.add(proxy);
					targetMap.put(targetClass, proxyList);
				}
			}
		}
		return targetMap;
	}
}
