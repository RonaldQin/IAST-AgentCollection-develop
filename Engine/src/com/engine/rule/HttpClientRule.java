package com.engine.rule;

import com.engine.bean.Source;
import com.engine.bean.TransmitStackTrace;

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

	public String insert_taintGetMethod() {
		pool.importPackage(Source.class.getCanonicalName());
		pool.importPackage(TransmitStackTrace.class.getCanonicalName());
		String methodName = dealClass.getName() + "." + dealMethod.getName();
		StringBuffer code_buffer = new StringBuffer("");
		try {
			dealMethod.addLocalVariable("_$param0Tainted", pool.get(String.class.getCanonicalName()));
			dealMethod.addLocalVariable("_$taint", pool.get(Source.class.getCanonicalName()));
			dealMethod.addLocalVariable("_$transmitStackTrace", pool.get(TransmitStackTrace.class.getCanonicalName()));

			code_buffer.append("_$transmitStackTrace = new TransmitStackTrace(\"" + methodName + "\", $1, $0);");

			code_buffer.append("_$param0Tainted = Source.isTainted($1);");
			code_buffer.append("if (_$param0Tainted != null) {");
			code_buffer.append(
					"_$taint = new Source($0, Source.LABEL_TAINTED, $0.getClass().getCanonicalName(), new String[] {_$param0Tainted});");
			code_buffer.append("_$taint.setTransmitStackTrace(_$transmitStackTrace);");
			code_buffer.append("}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code_buffer.toString();
	}

	public String insert_SinkExecuteMethod() {
		pool.importPackage(Source.class.getCanonicalName());
		StringBuffer code_buffer = new StringBuffer("");
		try {
			dealMethod.addLocalVariable("_$taintedKey", pool.get(String.class.getCanonicalName()));
			code_buffer.append("_$taintedKey = Source.isTainted($1);");
			code_buffer.append("if (_$taintedKey != null) {");
			code_buffer.append(
					"System.out.println(\"Trigger Sink: org.apache.commons.httpclient.HttpClient.executeMethod(HttpMethod) with Source: \" + $1);");
			code_buffer.append("Source.dumpStackTrace(_$taintedKey);");
			code_buffer.append("}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code_buffer.toString();
	}

}

