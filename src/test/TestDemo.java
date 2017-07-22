package test;

import java.util.Properties;

import framework.core.ClassScanner;
import framework.utils.PropsUtil;

public class TestDemo {
	public static void main(String[] args) {
		
		Properties prop = PropsUtil.loadProps("default.properties");
		ClassScanner.getClassSet(prop.getProperty("APP_BASE_PACKAGE"));
		
		Class t = TestDemo.class;
		
		
	}
	
	public void print() {
		
	}

}
