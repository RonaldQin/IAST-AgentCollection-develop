package com.boot;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

public class Agent {

	public static void premain(String agentArg, Instrumentation inst) {
		init(inst);
	}

	public static void agentmain(String agentArg, Instrumentation inst) {
		init(inst);
	}

	public static synchronized void init(Instrumentation inst) {
		// 将本地 jar 包加到 Bootstrap 类加载器的搜索路径中
		try {
			JarFileHelper.addJarToBootstrap(inst, JarFileHelper.getLocalJarPath());
			ModuleLoader.load(inst);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
