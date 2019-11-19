package com.engine.instrument;

public class StringBuilderTaintRule extends AbstractInstrumentRule {

	private static StringBuilderTaintRule instance = null;

	public StringBuilderTaintRule(ClassLoader loader, byte[] classfileBuffer, String className) {
		super(loader, classfileBuffer, className);
	}

	public static StringBuilderTaintRule getInstance(ClassLoader loader, byte[] classfileBuffer, String className) {
		if (instance == null) {
			synchronized (StringBuilderTaintRule.class) {
				if (instance == null) {
					instance = new StringBuilderTaintRule(loader, classfileBuffer, className);
				}
			}
		}
		return instance;
	}



}
