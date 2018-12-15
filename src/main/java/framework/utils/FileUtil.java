package framework.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class FileUtil {

	/**
     * 获取真实文件名（去掉文件路径）
     */
	public static String getRealFileName(String name) {
		// TODO Auto-generated method stub
		return FilenameUtils.getName(name);
	}
	/**
	 * 创建目录
	 * @param filePath
	 */
	public static File createFile(String dirPath) {
		// TODO Auto-generated method stub
		
		String dirpath = dirPath.substring(0, dirPath.lastIndexOf("/"));
		File dir ;
		
		dir = new File(dirpath);
		if(!dir.exists()) {
			try {
				FileUtils.forceMkdir(dir);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("创建目录出错！");
			}
		}
		return dir;
	}

}
