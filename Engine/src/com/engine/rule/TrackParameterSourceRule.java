package com.engine.rule;

import com.engine.bean.Source;
import com.engine.bean.TransmitStackTrace;

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

	/* 标记 HttpRequest.getParameter("xxx") 的返回值是Source */
	public String insert_LabelSource() {
		pool.importPackage(Source.class.getCanonicalName());
		pool.importPackage(TransmitStackTrace.class.getCanonicalName());
		StringBuffer code_buffer = new StringBuffer("");
		try {
			dealMethod.addLocalVariable("_$source", pool.get(Source.class.getCanonicalName()));
			dealMethod.addLocalVariable("_$methodName", pool.get(String.class.getCanonicalName()));
			dealMethod.addLocalVariable("_$input", pool.get(Object.class.getCanonicalName()));
			dealMethod.addLocalVariable("_$output", pool.get(Object.class.getCanonicalName()));
			code_buffer.append("if ($_ != null) {");
			code_buffer.append("_$methodName = \"" + dealClass.getName() + "." + dealMethod.getName() + "\";");
			code_buffer.append("_$input = $1; _$output = $_;");
			code_buffer.append("_$source = new Source($_, " + Source.class.getCanonicalName()
					+ ".LABEL_SOURCE, $_.getClass().getCanonicalName(), null);");
			code_buffer
					.append("_$source.setTransmitStackTrace(new TransmitStackTrace(_$methodName, _$input, _$output));");
			code_buffer.append("}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code_buffer.toString();
	}

}
