package framework.mvc.bean;

import java.io.InputStream;
/**
 * 文件属性
 * @author haizi
 *
 */
public class FileParam {
	//表单的name属性值
	private String fieldName;
	//客户端传过来的文件名
	private String fileName;
	//文件的大小
	private long filesize;
	//文件类型
	private String contentype;
	private InputStream inputStream;
	//文件存储的子目录
	private String saveFilePath;
	//文件存储的文件名
	private String saveFileName;
	
	public String getSaveFilePath() {
		return saveFilePath==null?"/":saveFilePath;
	}
	public void setSaveFilePath(String saveFilePath) {
		this.saveFilePath = saveFilePath;
	}
	public String getSaveFileName() {
		return saveFileName;
	}
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
	public FileParam(String fieldName, String fileName, long fileSize, String contentype, InputStream inputStream) {
		super();
		this.fieldName = fieldName;
		this.fileName = fileName;
		this.filesize = fileSize;
		this.contentype = contentype;
		this.inputStream = inputStream;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getFilesize() {
		return filesize;
	}
	public String getContentype() {
		return contentype;
	}
	public void setContentype(String contentype) {
		this.contentype = contentype;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
