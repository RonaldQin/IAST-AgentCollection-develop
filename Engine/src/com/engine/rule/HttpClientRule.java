package com.engine.rule;

public class HttpClientRule extends AbstractInstrumentRule {

	public HttpClientRule(ClassLoader loader, byte[] classfileBuffer, String className) {
		super(loader, classfileBuffer, className);
	}

	public static HttpClientRule instance = null;

	public static HttpClientRule getInstance(ClassLoader loader, byte[] classfileBuffer, String className) {
		if (instance == null) {
			synchronized (HttpClientRule.class) {
				if (instance == null) {
					instance = new HttpClientRule(loader, classfileBuffer, className);
				}
			}
		}
		return instance;
	}

	public static HttpClientRule getInstance() {
		if (instance == null) {
			System.err.println("Can not get instance of " + HttpClientRule.class.getCanonicalName() + "!");
			return null;
		}
		return instance;
	}

}
