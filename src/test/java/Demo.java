package test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import framework.mvc.bean.FileParam;


public class Demo {
	
	public static void main(String[] args) {
		
		
		
//		String regex = ".+\\{\\w+\\}.*";
//		String s = "/jj/{16556}/jdd";
//		
//		
//		Pattern ls = Pattern.compile(regex);
//		Matcher mat = ls.matcher(s);
//		StringBuffer buffer = new StringBuffer();
//		while(mat.find()) {
//			System.out.println(mat.end());
//		}
//		mat.appendTail(buffer);
//        String res =  buffer.toString();
//		System.out.println(res);
//		System.out.println(Pattern.matches(regex, s));
		
		
		String regex = "/webjars/**";
		String mapping = "/webjars/jquery/3.1.0/jquery.js";
		regex = regex.replaceAll("\\*\\*", ".*");
		System.out.println(regex);
		
		Pattern pat = Pattern.compile(mapping);
		Matcher mat = pat.matcher(mapping);
		if(mat.find()) {
		
			System.out.println(mat.group());
		}
		
		System.out.println(Pattern.matches(regex, mapping));
		
	}

}
