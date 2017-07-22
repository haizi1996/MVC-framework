package framework.servlet;

import java.util.List;
import java.util.regex.Pattern;

public class ConcreteResourceHandler extends AbstractResourceHandler {
	
	private String mapping ;
	
	private String location;
	

	public ConcreteResourceHandler(List<String> mapping,List<String> location,int index) {
		
		this.mapping = (mapping.get(index)).replaceAll("\\*\\*", ".*");
		this.location = location.get(index);
		if(++index < mapping.size()) {
			this.successor = new ConcreteResourceHandler(mapping, location, index);
		}
	
	}

	@Override
	public String handleRequest(String uri) {
		String path = null;
		if(getSuccessor() != null) {
			path = this.successor.handleRequest(uri);
		}else {
			path = getLocation(uri);
		}
		return path;
	}
	
	public String  getLocation(String uri) {
		if(isMapping(uri)) {
			String sufferPath = uri.substring(mapping.lastIndexOf("/"));
			return location + sufferPath;
		}
		return null;
	}
	
	public boolean isMapping(String uri) {
		return Pattern.matches(mapping, uri);
	}

}
