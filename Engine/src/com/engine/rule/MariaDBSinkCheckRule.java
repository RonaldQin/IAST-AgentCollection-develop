package com.engine.rule;

import com.engine.bean.Source;

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
		pool.importPackage(Source.class.getCanonicalName());
		pool.importPackage(StringBuilderTaintRule.class.getCanonicalName());
		StringBuffer code_buffer = new StringBuffer("");
		try {
			code_buffer.append("if (StringBuilderTaintRule.isSwitchOn()) { StringBuilderTaintRule.switchOff();");
			dealMethod.addLocalVariable("_$taintedKey", pool.get(String.class.getCanonicalName()));
			code_buffer.append("_$taintedKey = Source.isTainted($1);");
			code_buffer.append("if (_$taintedKey != null) {");
			code_buffer.append(
					"System.out.println(\"Trigger Sink: org.mariadb.jdbc.MariaDbStatement.executeQuery(sql) with Source: \" + $1);");
			code_buffer.append("Source.dumpStackTrace(_$taintedKey);");
			code_buffer.append("}");
			code_buffer.append("StringBuilderTaintRule.switchOn(); }");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code_buffer.toString();
	}

}
