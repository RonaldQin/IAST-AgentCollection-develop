package com.engine.rule;

public class Log4JRule extends AbstractInstrumentRule {

	public Log4JRule(ClassLoader loader, byte[] classfileBuffer, String className) {
		super(loader, classfileBuffer, className);
	}

	private static Log4JRule instance = null;

	public static Log4JRule getInstance(ClassLoader loader, byte[] classfileBuffer, String className) {
		if (instance == null) {
			synchronized (Log4JRule.class) {
				if (instance == null) {
					instance = new Log4JRule(loader, classfileBuffer, className);
				}
			}
		}
		return instance;
	}

	public static Log4JRule getInstance() {
		if (instance == null) {
			System.err.println("Can not get instance of " + Log4JRule.class.getCanonicalName() + "!");
			return null;
		}
		return instance;
	}

	public String insert_GetLogMessage() {
		StringBuffer code_buffer = new StringBuffer("");
		try {
			code_buffer.append("System.out.println(\"Log4J output message: \" + $1);");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code_buffer.toString();
	}
}
