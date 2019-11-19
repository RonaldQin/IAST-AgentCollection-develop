package com.boot;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.instrument.Instrumentation;
import java.net.URLDecoder;

public class ModuleLoader {

	// IAST-AgentEngine jar 包的文件名
	public static final String IASTAGENT_ENGINE_JAR = ConfigurationPropertiesHelper.getEngineJarFileName();

	private static ModuleContainer engineContainer;
	private static ModuleLoader instance;
	
	public static ClassLoader moduleClassLoader; // Ext ClassLoader
	public static String baseDirectory;
	
	static {
		Class clazz = ModuleLoader.class;
		String path = clazz.getResource("/" + clazz.getName().replace(".", "/") + ".class").getPath();
		if (path.startsWith("file:")) {
			path = path.substring(5);
		}
		if (path.contains("!")) {
			path = path.substring(0, path.indexOf("!"));
		}
		try {
			baseDirectory = URLDecoder.decode(new File(path).getParent(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			baseDirectory = new File(path).getParent();
		}

		// 定位到 ExtClassLoader 类加载器
		ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
		while (systemClassLoader.getParent() != null
				&& !systemClassLoader.getClass().getName().equals("sun.misc.Launcher$ExtClassLoader")) {
			systemClassLoader = systemClassLoader.getParent();
		}
		moduleClassLoader = systemClassLoader;
//		moduleClassLoader = Agent.class.getClassLoader();
	}
	
	private ModuleLoader(Instrumentation inst) {
		engineContainer = new ModuleContainer(IASTAGENT_ENGINE_JAR);
		engineContainer.start(inst);
	}

	public static synchronized void load(Instrumentation inst) {
		if (instance == null) {
			instance = new ModuleLoader(inst);
		}
	}

}
































