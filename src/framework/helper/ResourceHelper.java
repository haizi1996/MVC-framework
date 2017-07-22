package framework.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import framework.utils.StringUtil;

/**
 * 加载静态资源映射
 * @author haizi
 *
 */
public class ResourceHelper {
	private static List<String> mapping = new ArrayList<String>();
	private static List<String> location = new ArrayList<String>();
	static {
		String resourceLocation = ConfigHelper.getAppAssetPath();
		String resourceMapping = ConfigHelper.getAppAssetMapping();
		if(StringUtil.isNotEmpty(resourceLocation) && StringUtil.isNotEmpty(resourceMapping)) {
			String[] resourceLocations = resourceLocation.split(",");
			String[] resourceMappings = resourceMapping.split(",");
			mapping.addAll(Arrays.asList(resourceMappings));
			location.addAll(Arrays.asList(resourceLocations));
		}
	}
	public static List<String> getMapping() {
		return mapping;
	}
	public static List<String> getLocation() {
		return location;
	}
	
	

}
