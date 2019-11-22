package com.engine.rule;

public class StringTaintRule extends AbstractInstrumentRule {

	public StringTaintRule(ClassLoader loader, byte[] classfileBuffer, String className) {
		super(loader, classfileBuffer, className);
	}

	private static StringTaintRule instance = null;

	public static StringTaintRule getInstance(ClassLoader loader, byte[] classfileBuffer, String className) {
		if (instance == null) {
			synchronized (StringTaintRule.class) {
				if (instance == null) {
					instance = new StringTaintRule(loader, classfileBuffer, className);
				}
			}
		}
		return instance;
	}

}
