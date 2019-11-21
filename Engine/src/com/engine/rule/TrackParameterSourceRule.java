package com.engine.rule;

import com.engine.bean.StringTypeSource;
import com.engine.bean.StringTypeStackTraceInfo;

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
		pool.importPackage(StringTypeStackTraceInfo.class.getCanonicalName());
		StringBuffer code_buffer = new StringBuffer("");
		try {
			dealMethod.addLocalVariable("_$source", pool.get(StringTypeSource.class.getCanonicalName()));

			dealMethod.addLocalVariable("_$methodName", pool.get(String.class.getCanonicalName()));
			dealMethod.addLocalVariable("_$input", pool.get(String.class.getCanonicalName()));
			dealMethod.addLocalVariable("_$output", pool.get(String.class.getCanonicalName()));
			code_buffer.append("_$methodName = \"" + dealClass.getName() + "." + dealMethod.getName() + "\";");
			code_buffer.append("_$input = $1; _$output = $_;");

			code_buffer.append("_$source = new StringTypeSource($_, StringTypeSource.LABEL_SOURCE, null);");
			code_buffer.append(
					"_$source.setStringTypeStackTraceInfo(new StringTypeStackTraceInfo(_$methodName, _$input, _$output));");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code_buffer.toString();
	}

}
