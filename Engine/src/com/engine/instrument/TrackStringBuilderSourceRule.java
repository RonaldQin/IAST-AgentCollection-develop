package com.engine.instrument;

import com.engine.bean.StringTypeSource;

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

	public static String $thisStringValue = "$thisStringValue"; // 插桩存放StringBuilder修改自身前的this的value。

	/* 插桩修改StringBuilder自身前存储this的值 */
	public String insert_StoreThisValue() {
		StringBuffer code_buffer = new StringBuffer("");
		try {
			code_buffer.append(this.getClass().getCanonicalName() + ".$thisStringValue = $0.toString();");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code_buffer.toString();
	}

	/* 跟踪append()方法修改StringBuilder过程中污点的传播 */
	public String insert_TrackSourceInAppend() {
		pool.importPackage(StringTypeSource.class.getCanonicalName());
		StringBuffer code_buffer = new StringBuffer("");
		code_buffer.append(
				"if (StringTypeSource.isStringTypeSource($1) || StringTypeSource.isStringTypeSource("
						+ this.getClass().getCanonicalName()
						+ ".$thisStringValue)) { new StringTypeSource($_.toString()); }");
		return code_buffer.toString();
	}
}
