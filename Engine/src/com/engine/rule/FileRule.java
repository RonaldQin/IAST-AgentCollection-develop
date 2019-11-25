package com.engine.rule;

import com.engine.bean.HttpRequestHelperStack;
import com.engine.bean.HttpRequestInfo;

public class FileRule extends AbstractInstrumentRule {

	public FileRule(ClassLoader loader, byte[] classfileBuffer, String className) {
		super(loader, classfileBuffer, className);
	}

	private static FileRule instance = null;

	public static FileRule getInstance(ClassLoader loader, byte[] classfileBuffer, String className) {
		if (instance == null) {
			synchronized (FileRule.class) {
				if (instance == null) {
					instance = new FileRule(loader, classfileBuffer, className);
				}
			}
		}
		return instance;
	}

	public static FileRule getInstance() {
		if (instance == null) {
			System.err.println("Can not get instance of " + FileRule.class.getCanonicalName() + "!");
			return null;
		}
		return instance;
	}

	public String fileListURL = null;

	/* 目录遍历 */
	public String insert_GetFileListPath() {
		pool.importPackage(FileRule.class.getCanonicalName());
		pool.importPackage(HttpRequestInfo.class.getCanonicalName());
		pool.importPackage(HttpRequestHelperStack.class.getCanonicalName());
		StringBuffer code_buffer = new StringBuffer("");
		try {
			dealMethod.addLocalVariable("_$fileRuleInstance", pool.get(FileRule.class.getCanonicalName()));
			code_buffer.append("_$fileRuleInstance = FileRule.getInstance();");
			code_buffer.append("if (_$fileRuleInstance != null && path != null) {");
			code_buffer.append("_$fileRuleInstance.fileListURL = path;");
			code_buffer.append("System.out.println(\"目录遍历路径：\" + _$fileRuleInstance.fileListURL);");
			// 检测传递的路径是否是否属于目录的路径遍历漏洞
//			code_buffer.append("if (_$fileRuleInstance.hasTraversal(_$fileRuleInstance.fileListURL)) {");
//			code_buffer.append("System.out.println(\"File.listFiles遍历目录的路径：\" + _$fileRuleInstance.fileListURL);");
//			code_buffer.append("}");
			code_buffer.append("}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code_buffer.toString();
	}

	public String readFilePath = null;

	/* 读文件 */
	public String insert_GetReadFilePath() {
		pool.importPackage(FileRule.class.getCanonicalName());
		StringBuffer code_buffer = new StringBuffer("");
		try {
			dealMethod.addLocalVariable("_$fileRuleInstance", pool.get(FileRule.class.getCanonicalName()));
			code_buffer.append("_$fileRuleInstance = FileRule.getInstance();");
			code_buffer.append("if (_$fileRuleInstance != null && $1 != null) {");
			code_buffer.append("_$fileRuleInstance.readFilePath = $1;");
			code_buffer.append("System.out.println(\"读取文件路径： \" + _$fileRuleInstance.readFilePath);");
			code_buffer.append("}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code_buffer.toString();
	}

	// 检测传递的路径是否是否属于目录的路径遍历漏洞
	public boolean hasTraversal(String path) {
		String path2 = "/" + path.replaceAll("\\\\", "/") + "/";
		int left = path2.indexOf("/../");
		int right = path2.lastIndexOf("/../");
		if (left != -1 && right != -1 && left != right) {
			return true;
		}
		return false;
	}

	/* 写文件 */
	public String insert_GetFileOutputStreamFilePath() {
		StringBuffer code_buffer = new StringBuffer("");
		code_buffer.append("System.out.println(\"写文件地址： \" + $1);");
		return code_buffer.toString();
	}

}
