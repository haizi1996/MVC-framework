package test;

import framework.annotation.Controller;
import framework.annotation.RequestMapping;
import framework.annotation.ResponseBody;
import framework.mvc.bean.FileParam;
import framework.servlet.UploadHelper;

@Controller
public class Upload {
	
	@RequestMapping(value="/upload",method="post",produce="text/html;charset=utf-8")
	@ResponseBody
	public String upload(FileParam upfile) {
		
		upfile.setSaveFileName("/upload");
		upfile.setSaveFileName("zhna.txt");
		UploadHelper.uploadFile(upfile);
		return "index"; 
	}
	
	@RequestMapping(value="/home",method="get")
	public String index() {
		return "index";
		
		
	}
}
