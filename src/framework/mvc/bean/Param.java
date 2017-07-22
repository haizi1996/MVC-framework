package framework.mvc.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *请求参数对象 
 *
 */

public class Param {
	
	
	private List<FormParam> formParamList;
	private List<FileParam> fileParamList;
	
	private Map<String,Object> paramMap;
	
	
	
	
	
	
	public Param(Map<String, Object> paramMap) {
		super();
		this.paramMap = paramMap;
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	public Param() {
		super();
	}

	public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
		super();
		this.formParamList = formParamList;
		this.fileParamList = fileParamList;
	}

	public Object getObject(String name){
		return this.paramMap.get(name);
	}

	/**
	 * 根据参数名获取String类型的值
	 */
	public String getString(String name){
		Object param = getObject(name);
		return (String) param;
	}


	/**
	 * 根据参数名获取int类型的值
	 */
	public Integer getInt(String name){
		return Integer.parseInt((java.lang.String) getString(name));
	}

	/**
	 * 根据参数名获取String类型的值
	 */
	public Long getLong(String name){
		return Long.parseLong((java.lang.String) getString(name));
	}

	
	/**
	 * 获取请求参数映射
	 * @return
	 */
	public Map<String ,Object> getFieldMap(){
		Map<String,Object> fieldMap = new HashMap<String,Object>();
		if(CollectionUtils.isNotEmpty(formParamList)){
			for(FormParam formParam : formParamList){
				String fieldName =   formParam.getFieldName();
				
				Object fieldValue = formParam.getFieldValue();
				
				if(fieldMap.containsKey(fieldValue)){
					fieldValue = fieldMap.get(fieldName) + StringUtils.SPACE + fieldValue;
				}
				
				fieldMap.put(null, fieldValue);
			}
		}
		return fieldMap;
	}
	/**
	 * 获取上传文件映射
	 * 
	 */
	public Map<String ,List<FileParam>> getFieldMap1(){
		
		
		Map <String ,List<FileParam>> fileMap = new HashMap<String ,List<FileParam>>();
		if(CollectionUtils.isNotEmpty(formParamList)){
			
			for(FileParam fileParam : fileParamList){
				
				String fieldName = fileParam.getFieldName();
				List<FileParam> fileParamList;
				if(fileMap.containsKey(fieldName)){
					fileParamList = fileMap.get(fieldName);
				}else{
					fileParamList = new ArrayList<FileParam>();
				}
				fileParamList.add(fileParam);
				fileMap.put(fieldName, fileParamList);
				
			}
			
		}
		
		
		return fileMap;
	}
	
	/**
	 * 获取所有商场文件
	 */
	public List<FileParam> getFileList(String fieldName){
		return getFieldMap1().get(fieldName);
	}
	
	/**
	 * 获取唯一上传文件
	 */
	public FileParam getFile(String fieldName){
		List<FileParam> fileParamList = getFileList(fieldName);
		if(CollectionUtils.isNotEmpty(fileParamList) && fileParamList.size() == 1){
			return fileParamList.get(0);
		}
		return null;
	}
	
	
	
	
	
}
