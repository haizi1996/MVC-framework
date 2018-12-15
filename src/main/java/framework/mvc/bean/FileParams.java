package framework.mvc.bean;

import java.util.ArrayList;
import java.util.List;

public class FileParams {
	
	List<FileParam> fileParams = new ArrayList<FileParam>();

	public FileParams(List<FileParam> fileParams) {
		this.fileParams = fileParams;
	}
	
	public int size() {
		return fileParams.size();
	}
	
	public List<FileParam> getAll(){
		return this.fileParams;
	}
	
	public FileParam getOne() {
		return size() == 1 ? fileParams.get(0) : null;
	}

}
