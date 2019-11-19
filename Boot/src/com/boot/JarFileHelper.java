package com.boot;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.net.URLDecoder;
import java.util.jar.JarFile;

public class JarFileHelper {

	public static void addJarToBootstrap(Instrumentation inst, String jarFilePath) throws IOException {
		inst.appendToBootstrapClassLoaderSearch(new JarFile(jarFilePath));
	}

	/* 获取本地 jar 包的文件路径 */
	public static String getLocalJarPath() {
		URL localUrl = Agent.class.getProtectionDomain().getCodeSource().getLocation();
		String path = null;
		try {
			path = URLDecoder.decode(localUrl.getFile().replace("+", "%2B"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return path; // 取配置文件中 BootJarFileName 的值
	}

	/* 获取本地 jar 包的父级目录的路径 */
	public static String getLocalJarParentPath() {
		String jarPath = getLocalJarPath();
		return jarPath.substring(0, jarPath.lastIndexOf("/"));
	}
}
