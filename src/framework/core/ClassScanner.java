package framework.core;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import framework.utils.ClassUtil;

/**
 *类操作工具 
 *
 */
public class ClassScanner {

	
	/**
	 * 扫描工程下的所有类
	 */

	@SuppressWarnings("static-access")
	public static Set<Class<?>> getClassSet(String packageName){
		if(packageName==null) {
			packageName="";
		}else {
			packageName=packageName.replace(".", "/");
		}
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		try {
			Enumeration<URL> urls = ClassUtil.getClassLoader().getResources("/");
			System.out.println(urls.hasMoreElements());
			while(urls.hasMoreElements()){
				URL url = urls.nextElement();
				if(url != null){
					String protocol = url.getProtocol();
					if(protocol.equalsIgnoreCase("file")){
						String packagePath = url.getPath();
						addClass(classSet,packagePath,packageName);
					}else if(protocol.equalsIgnoreCase("jar")){
						JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
						if(jarURLConnection != null){
							JarFile jarFile = jarURLConnection.getJarFile();
							if(jarFile != null){
								Enumeration<JarEntry> jarEntries = jarFile.entries();
								
								while(jarEntries.hasMoreElements()){
									JarEntry jarEntry = jarEntries.nextElement();
									String jarEntryName = jarEntry.getName();
									if(jarEntryName.endsWith(".class")){
										String className = jarEntryName.substring(0,jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
										doAddClass(classSet , className);
									}
								}
							}
						}
					}
					
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("获取指定包下的所有类异常");
		}
		return classSet;
	}

	private static void doAddClass(Set<Class<?>> classSet, String className) {
		// TODO Auto-generated method stub
		Class<?> cls = ClassUtil.loadClass(className, false);
		classSet.add(cls);
	}

	private static void addClass(Set<Class<?>> classSet, String packagePath,
			String packageName) {
		// TODO Auto-generated method stub
		File[] files = new File(packagePath).listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File file) {
				// TODO Auto-generated method stub
				return file.isDirectory() || (file.isFile()&& file.getName().endsWith(".class"));
			}
		});
		for(File file : files){
			String fileName = file.getName();
			if(file.isFile()){
				String className = fileName.substring(0,fileName.lastIndexOf(".")).replaceAll("/", ".");
				if(className != null && className.length() > 0){
					className = packageName  + className;
				}
				doAddClass(classSet, className);
			}else{
				
				String subPackagePath = fileName;
				if(subPackagePath !=  null && subPackagePath.length() > 0 ){
					subPackagePath = packagePath  + subPackagePath + "/";
				}
				String subPackageName = fileName;
				if(subPackageName !=  null && subPackageName.length() > 0){
					
					subPackageName = packageName  + subPackageName + ".";
				}
				addClass(classSet, subPackagePath, subPackageName);
			}
		}
	}
	
	
	
}
