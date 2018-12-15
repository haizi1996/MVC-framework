package framework.utils;

import framework.core.ClassScanner;
import framework.helper.AopHelper;
import framework.mvc.ControllerUtil;

public class HelperLoader {

	public static void init(){
		Class<?>[] classList = {ClassScanner.class,BeanUtil.class,IocUtil.class,ControllerUtil.class,AopHelper.class};
		
		for(Class<?> cls : classList){
			ClassUtil.loadClass(cls.getName(), false);
		}
	}
	
}
