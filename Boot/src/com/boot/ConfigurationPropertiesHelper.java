package com.boot;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationPropertiesHelper {

	private static final String CONFIG_PROPERTIES_FILE_PATH = "/configuration.properties";
	
	private static Properties prop = null;
	
	/* 读取 configuration.properties 配置文件 */
	static {
		prop = new Properties();
		InputStream properties_in = ConfigurationPropertiesHelper.class
				.getResourceAsStream(CONFIG_PROPERTIES_FILE_PATH);
		try {
			prop.load(properties_in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getBootJarFileName() {
		return prop.getProperty("BootJarFileName");
	}

	public static String getEngineJarFileName() {
		return prop.getProperty("EngineJarFileName");
	}

	public static String getInstrumentRulesPath() {
		return prop.getProperty("InstrumentRulesPath");
	}

}
