package com.engine.rule;

public class JSTLRule extends AbstractInstrumentRule {

	public JSTLRule(ClassLoader loader, byte[] classfileBuffer, String className) {
		super(loader, classfileBuffer, className);
	}

	private static JSTLRule instance = null;

	public static JSTLRule getInstance(ClassLoader loader, byte[] classfileBuffer, String className) {
		if (instance == null) {
			synchronized (JSTLRule.class) {
				if (instance == null) {
					instance = new JSTLRule(loader, classfileBuffer, className);
				}
			}
		}
		return instance;
	}

	public static JSTLRule getInstance() {
		if (instance == null) {
			System.out.println("Can not get instance of " + JSTLRule.class.getCanonicalName() + "!");
			return null;
		}
		return instance;
	}

	public String insert_GetImportURL() {
		StringBuffer code_buffer = new StringBuffer("");
		try {
			code_buffer.append("System.out.println(\"JSTL import tag's url is: \" + $_);");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code_buffer.toString();
	}

}
