package com.engine.instrument;

import com.engine.bean.StringTypeSource;

public class TrackParameterSourceRule extends AbstractInstrumentRule {

	public TrackParameterSourceRule(ClassLoader loader, byte[] classfileBuffer, String className) {
		super(loader, classfileBuffer, className);
	}

	private static TrackParameterSourceRule instance = null;

	public static TrackParameterSourceRule getInstance(ClassLoader loader, byte[] classfileBuffer,
			String className) {
		if (instance == null) {
			synchronized (TrackParameterSourceRule.class) {
				if (instance == null) {
					instance = new TrackParameterSourceRule(loader, classfileBuffer, className);
				}
			}
		}
		return instance;
	}

	/* 标记HttpRequest.getParameter("")的返回值是Source */
	public String insert_LabelSource() {
		pool.importPackage(StringTypeSource.class.getCanonicalName());
		StringBuffer code_buffer = new StringBuffer("");
		code_buffer.append("new StringTypeSource($_);");
		return code_buffer.toString();
	}

}
