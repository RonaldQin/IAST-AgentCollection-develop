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
			JarFileHelper.addJarToBootstrap(inst,
					"/home/lace/Documents/workspace-sts-3.9.10.RELEASE/Tomcat7/myAgent2/IAST-AgentEngine.jar");
			JarFileHelper.addJarToBootstrap(inst,
					"/home/lace/Documents/workspace2/IAST-Agent/Engine/lib/jackson-annotations-2.9.9.jar");
			JarFileHelper.addJarToBootstrap(inst,
					"/home/lace/Documents/workspace2/IAST-Agent/Engine/lib/jackson-core-2.9.9.jar");
			JarFileHelper.addJarToBootstrap(inst,
					"/home/lace/Documents/workspace2/IAST-Agent/Engine/lib/jackson-databind-2.9.9.jar");
			JarFileHelper.addJarToBootstrap(inst,
					"/mnt/hgfs/libraries/jboss-javassist-javassist-5a796e1/javassist.jar");
			ModuleLoader.load(inst);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
