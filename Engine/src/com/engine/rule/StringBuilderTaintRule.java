package com.engine.rule;

import com.engine.bean.Source;
import com.engine.bean.TransmitStackTrace;

public class StringBuilderTaintRule extends AbstractInstrumentRule {

	public StringBuilderTaintRule(ClassLoader loader, byte[] classfileBuffer, String className) {
		super(loader, classfileBuffer, className);
	}

	private static StringBuilderTaintRule instance = null;

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

	public static StringBuilder _appendThisValue = null;

	public String insert_GetAppendThisValue() {
		pool.importPackage(StringBuilderTaintRule.class.getCanonicalName());
		StringBuffer code_buffer = new StringBuffer("");
		code_buffer.append(StringBuilderTaintRule.class.getCanonicalName() + "._appendThisValue = $0;");
		return code_buffer.toString();
	}

	public String insert_AppendTransmitTaint() {
		pool.importPackage(Source.class.getCanonicalName());
		pool.importPackage(StringBuilderTaintRule.class.getCanonicalName());
		pool.importPackage(TransmitStackTrace.class.getCanonicalName());
		String methodName = dealClass.getName() + "." + dealMethod.getName();
		StringBuffer code_buffer = new StringBuffer("");
		try {
			dealMethod.addLocalVariable("_thisIsTainted", pool.get(String.class.getCanonicalName()));
			dealMethod.addLocalVariable("_param0IsTainted", pool.get(String.class.getCanonicalName()));
			dealMethod.addLocalVariable("_taint", pool.get(Source.class.getCanonicalName()));
			dealMethod.addLocalVariable("_transmitStackTrace", pool.get(TransmitStackTrace.class.getCanonicalName()));

			code_buffer.append("_transmitStackTrace = new TransmitStackTrace(\"" + methodName
					+ "\", StringBuilderTaintRule._appendThisValue, $_);");
			code_buffer.append("_thisIsTainted = Source.isTainted(StringBuilderTaintRule._appendThisValue);");
			code_buffer.append("_param0IsTainted = Source.isTainted($1);");
			code_buffer.append("if (_thisIsTainted != null && _param0IsTainted != null) {");
			code_buffer.append(
					"_taint = new Source($_, Source.LABEL_TAINTED, $_.getClass().getCanonicalName(), new String[] {_thisIsTainted, _param0IsTainted});");
			code_buffer.append("_taint.setTransmitStackTrace(_transmitStackTrace);");
			code_buffer.append("} else if (_param0IsTainted != null) {");
			code_buffer.append(
					"_taint = new Source($_, Source.LABEL_TAINTED, $_.getClass().getCanonicalName(), new String[] { _param0IsTainted });");
			code_buffer.append("_taint.setTransmitStackTrace(_transmitStackTrace);");
			code_buffer.append("} else if (_thisIsTainted != null) {");
			code_buffer.append(
					"_taint = new Source($_, Source.LABEL_TAINTED, $_.getClass().getCanonicalName(), new String[] {_thisIsTainted});");
			code_buffer.append("_taint.setTransmitStackTrace(_transmitStackTrace);");
			code_buffer.append("}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code_buffer.toString();
	}

}
