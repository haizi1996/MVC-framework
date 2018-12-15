package framework.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import framework.helper.ConfigHelper;
import framework.mvc.bean.FileParam;
import framework.mvc.bean.FileParams;
import framework.utils.Config;
import framework.utils.FileUtil;
import framework.utils.StreamUtil;
import framework.utils.StringUtil;

public class UploadHelper {
	
	 /**
     * FileUpload 对象（用于解析所上传的文件）
     */
    private static ServletFileUpload fileUpload;
    /**
     * 初始化
     */
    @SuppressWarnings("unused")
	public static void init(ServletContext servletContext) {
    	File repository = new File(ConfigHelper.getFileUploadDir());
    	if(repository == null) {
    		// 获取一个临时目录（使用 Tomcat 的 work 目录）
    		repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
    	}
        // 创建 FileUpload 对象
        fileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
        // 设置上传限制
        int uploadLimit = ConfigHelper.getUploadLimit(10);;
        if (uploadLimit != 0) {
            fileUpload.setFileSizeMax(uploadLimit * 1024 * 1024); // 单位为 M
        }
    }
	
    /**
     * 判断请求是否为 multipart 类型
     */
	public static boolean isMultipart(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return ServletFileUpload.isMultipartContent(request);
	}
	/**
     * 创建 multipart 请求参数列表
     */
    public static Map<String,Object> createMultipartParamList(HttpServletRequest request) throws Exception {
        // 定义参数列表
     //   List<Object> paramList = new ArrayList<Object>();
        // 创建两个对象，分别对应 普通字段 与 文件字段
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        // 获取并遍历表单项
        List<FileItem> fileItemList;
        try {
            fileItemList = fileUpload.parseRequest(request);
        } catch (FileUploadBase.FileSizeLimitExceededException e) {
            // 异常转换（抛出自定义异常）
            throw new RuntimeException("异常转换（抛出自定义异常）");
        }
        for (FileItem fileItem : fileItemList) {
            // 分两种情况处理表单项
            String fieldName = fileItem.getFieldName();
            if (fileItem.isFormField()) {
                // 处理普通字段
                String fieldValue = fileItem.getString(Config.ENCODE);
                fieldMap.put(fieldName, fieldValue);
            } else {
                // 处理文件字段
                String fileName = FileUtil.getRealFileName(fileItem.getName());
                if (StringUtil.isNotEmpty(fileName)) {
                    long fileSize = fileItem.getSize();
                    String contentType = fileItem.getContentType();
                    InputStream inputSteam = fileItem.getInputStream();
                    // 创建 FileParam 对象，并将其添加到 multipartList 中
                    FileParam fileParam = new FileParam(fieldName, fileName, fileSize, contentType, inputSteam);
                    fieldMap.put(fieldName,fileParam);
                }
            }
        }
        // 返回参数列表
        return fieldMap;
    }
    /**
     * 
     * @param fileParam
     */
    public static void uploadFile( FileParam fileParam) {
        try {
            if (fileParam != null) {
                // 创建文件路径（绝对路径）
            	
                String filePath = ConfigHelper.getFileUploadDir()+ fileParam.getSaveFilePath()+"/" + fileParam.getFileName();
                filePath=filePath.replaceAll("//", "/");
                FileUtil.createFile(filePath);
                // 执行流复制操作
                InputStream inputStream = new BufferedInputStream(fileParam.getInputStream());
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
                StreamUtil.copyStream(inputStream, outputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("上传文件出错！");
        }
    }

    /**
     * 批量上传文件
     */
    public static void uploadFiles(String basePath, FileParams fileParams) {
        for (FileParam fileParam : fileParams.getAll()) {
            uploadFile(fileParam);
        }
    }

}
