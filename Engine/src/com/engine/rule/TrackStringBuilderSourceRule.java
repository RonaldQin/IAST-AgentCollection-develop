package com.engine.rule;

import com.engine.bean.StringTypeSource;
import com.engine.bean.StringTypeStackTraceInfo;

public class TrackStringBuilderSourceRule extends AbstractInstrumentRule {
	// -XX:+TraceClassLoading
	// -Xbootclasspath

	private static TrackStringBuilderSourceRule instance = null;

	public TrackStringBuilderSourceRule(ClassLoader loader, byte[] classfileBuffer, String className) {
		super(loader, classfileBuffer, className);
	}

	public static TrackStringBuilderSourceRule getInstance(ClassLoader loader, byte[] classfileBuffer, String className) {
		if (instance == null) {
			synchronized (TrackStringBuilderSourceRule.class) {
				if (instance == null) {
					instance = new TrackStringBuilderSourceRule(loader, classfileBuffer, className);
				}
			}
		}
		return instance;
	}

	public static String _$thisStringValue = "_$thisStringValue"; // 插桩存放StringBuilder修改自身前的this的value。

	/* 插桩修改StringBuilder自身前存储this的值 */
	public String insert_StoreThisValue() {
		StringBuffer code_buffer = new StringBuffer("");
		try {
			code_buffer.append(this.getClass().getCanonicalName() + "._$thisStringValue = $0.toString();");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code_buffer.toString();
	}

	/* 跟踪append()方法修改StringBuilder过程中污点的传播 */
	public String insert_TrackSourceInAppend() {
		pool.importPackage(StringTypeSource.class.getCanonicalName());
		pool.importPackage(StringTypeStackTraceInfo.class.getCanonicalName());
		StringBuffer code_buffer = new StringBuffer("");
		try {
			dealMethod.addLocalVariable("_$taint", pool.get(StringTypeSource.class.getCanonicalName()));

			dealMethod.addLocalVariable("_$thisMatchedSourceKey", pool.get(String.class.getCanonicalName()));
			dealMethod.addLocalVariable("_$param0MatchedSourceKey", pool.get(String.class.getCanonicalName()));

			dealMethod.addLocalVariable("_$methodName", pool.get(String.class.getCanonicalName()));
			dealMethod.addLocalVariable("_$input", pool.get(String.class.getCanonicalName()));
			dealMethod.addLocalVariable("_$output", pool.get(String.class.getCanonicalName()));

			code_buffer.append("_$taint = null;");
			code_buffer.append("_$methodName = \"" + dealClass.getName() + "." + dealMethod.getName() + "\";");
			code_buffer.append("_$output = $_;");
			code_buffer.append("_$input = " + this.getClass().getCanonicalName() + "._$thisStringValue + \", \" + $1;");

			code_buffer.append("_$thisMatchedSourceKey = StringTypeSource.isStringTypeSource("
					+ this.getClass().getCanonicalName() + "._$thisStringValue);");

//			code_buffer.append("_$thisMatchedSourceKey = StringTypeSource.isStringTypeSource($0.toString());");

			code_buffer.append("_$param0MatchedSourceKey = StringTypeSource.isStringTypeSource($1);");

			code_buffer.append(
					"if (_$thisMatchedSourceKey != null && _$param0MatchedSourceKey != null) { _$taint = new StringTypeSource($_.toString(), StringTypeSource.LABEL_TAINTED, new String[] { _$thisMatchedSourceKey, _$param0MatchedSourceKey }); }");
			code_buffer.append(
					"else if (_$thisMatchedSourceKey != null) { _$taint = new StringTypeSource($_.toString(), StringTypeSource.LABEL_TAINTED, new String[] { _$thisMatchedSourceKey }); }");
			code_buffer.append(
					"else if (_$param0MatchedSourceKey != null) { _$taint = new StringTypeSource($_.toString(), StringTypeSource.LABEL_TAINTED, new String[] { _$param0MatchedSourceKey }); }");

			code_buffer.append(
					"if (_$taint != null) { _$taint.setStringTypeStackTraceInfo(new StringTypeStackTraceInfo(_$methodName, _$input, _$output)); }");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return code_buffer.toString();
	}
}
