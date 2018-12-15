package framework.core;

import framework.helper.AopHelper;
import framework.helper.DatabaseHelper;
import framework.helper.ResourceHelper;
import framework.ioc.IocHelper;
import framework.mvc.ControllerUtil;
import framework.utils.ClassUtil;

public class LoadFrame {
	public static void init() {
        // 定义需要加载的 Helper 类
        Class<?>[] classList = {
        	ResourceHelper.class,
            DatabaseHelper.class,
            ControllerUtil.class,
            BeanHelper.class,
            AopHelper.class,
            IocHelper.class,
        };
        // 按照顺序加载类
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(),true);
        }
    }

}
