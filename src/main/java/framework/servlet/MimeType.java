package framework.servlet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class MimeType {

	private static Map<String,String> memiType ;
	
	@SuppressWarnings("unchecked")
	public static void init(String contextPath) {
		
		String webXmlPath = contextPath;
		for(int i =  0 ; i < 2 ; i ++ ){
			webXmlPath = webXmlPath.substring(0, webXmlPath.lastIndexOf("/"));
		}
		webXmlPath += "/conf/web.xml"; 
		memiType = new HashMap<String,String>();
		//dom4j解析xml
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(webXmlPath);
			Element root =  document.getRootElement();
			List<Element> elements = root.elements("mime-mapping");
			for (Element element : elements) {
				List<Element> nodes = element.elements();
				String key = nodes.get(0).getText();
				String value = nodes.get(1).getText();	
				memiType.put(key, value);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	
	public static String getMemiTypeByFileName(String filename) {
		String rest = null;
		if(filename != null && filename.length() > 0) {
			String key = filename.substring(filename.lastIndexOf(".") + 1);
			rest = memiType.get(key);
		}
		return rest;
	}
}
