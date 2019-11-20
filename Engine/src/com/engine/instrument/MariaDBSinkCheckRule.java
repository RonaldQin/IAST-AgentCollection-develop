package com.engine.instrument;

import com.engine.bean.StringTypeSource;

public class MariaDBSinkCheckRule extends AbstractInstrumentRule {
	
	public MariaDBSinkCheckRule(ClassLoader loader, byte[] classfileBuffer, String className) {
		super(loader, classfileBuffer, className);
	}
	
	private static MariaDBSinkCheckRule instance = null;
	
	public static MariaDBSinkCheckRule getInstance(ClassLoader loader, byte[] classfileBuffer, String className) {
		if (instance == null) {
			synchronized (MariaDBSinkCheckRule.class) {
				if (instance == null) {
					instance = new MariaDBSinkCheckRule(loader, classfileBuffer, className);
				}
			}
		}
		return instance;
	}

	/* 插桩检测Source是否出发Sink (executeQuery) */
	public String insert_CheckTriggerSink() {
		pool.importPackage(StringTypeSource.class.getCanonicalName());
		StringBuffer code_buffer = new StringBuffer("");
		code_buffer.append("if (StringTypeSource.isStringTypeSource($1)) {"
				+ "System.out.println(\"Trigger Sink: org.mariadb.jdbc.MariaDbStatement.executeQuery with Source: \" + $1);"
				+ "}");
		return code_buffer.toString();
	}

}
