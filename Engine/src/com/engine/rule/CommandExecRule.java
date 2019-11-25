package com.engine.rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.engine.bean.Source;
import com.engine.bean.TransmitStackTrace;

public class CommandExecRule extends AbstractInstrumentRule {

	public CommandExecRule(ClassLoader loader, byte[] classfileBuffer, String className) {
		super(loader, classfileBuffer, className);
	}

	private static CommandExecRule instance = null;

	public static CommandExecRule getInstance(ClassLoader loader, byte[] classfileBuffer, String className) {
		if (instance == null) {
			synchronized (CommandExecRule.class) {
				if (instance == null) {
					instance = new CommandExecRule(loader, classfileBuffer, className);
				}
			}
		}
		return instance;
	}

	public static CommandExecRule getInstance() {
		if (instance == null) {
			System.err.println("Can not get instance of " + CommandExecRule.class.getCanonicalName() + "!");
			return null;
		}
		return instance;
	}

	public String execCommand = null;

	public String insert_ExecSink() {
		pool.importPackage(CommandExecRule.class.getCanonicalName());
		pool.importPackage(Source.class.getCanonicalName());
		StringBuffer code_buffer = new StringBuffer("");
		try {
			dealMethod.addLocalVariable("_$commandExecRuleInstance",
					pool.get(CommandExecRule.class.getCanonicalName()));
			dealMethod.addLocalVariable("_$taintedKey", pool.get(String.class.getCanonicalName()));
			code_buffer.append("_$commandExecRuleInstance = CommandExecRule.getInstance();");
			code_buffer.append("if (_$commandExecRuleInstance != null) {");
			code_buffer.append("_$commandExecRuleInstance.execCommand = $1;");
//			code_buffer.append("System.out.println(\"执行命令： \" + _$commandExecRuleInstance.execCommand);");
			// 输出污点跟踪信息：
			code_buffer.append("_$taintedKey = Source.isTainted($1);");
			code_buffer.append("if (_$taintedKey != null) {");
			code_buffer.append(
					"System.out.println(\"Trigger Sink: " + dealClass.getName() + "." + dealMethod.getName()
							+ " with Source: \" + $1);");
			code_buffer.append("Source.dumpStackTrace(_$taintedKey);");
			code_buffer.append("}");
			code_buffer.append("}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code_buffer.toString();
	}

	public String insert_NewProcessBuilder() {
		pool.importPackage(Arrays.class.getCanonicalName());
		pool.importPackage(Source.class.getCanonicalName());
		pool.importPackage(StringBuilderTaintRule.class.getCanonicalName());
		pool.importPackage(TransmitStackTrace.class.getCanonicalName());
		pool.importPackage(List.class.getCanonicalName());
		pool.importPackage(ArrayList.class.getCanonicalName());
		String methodName = dealClass.getName() + "." + dealMethod.getName();
		StringBuffer code_buffer = new StringBuffer("");
		try {
			dealMethod.addLocalVariable("_taintedParams", pool.get(List.class.getCanonicalName()));
			dealMethod.addLocalVariable("_taint", pool.get(Source.class.getCanonicalName()));
			dealMethod.addLocalVariable("_transmitStackTrace", pool.get(TransmitStackTrace.class.getCanonicalName()));
			dealMethod.addLocalVariable("_tParams", pool.get(String[].class.getCanonicalName()));

			code_buffer.append("if (StringBuilderTaintRule.isSwitchOn()) { StringBuilderTaintRule.switchOff();");
//			code_buffer.append("System.out.println(\"执行命令： \" + Arrays.toString($$));");
			code_buffer.append("_transmitStackTrace = new TransmitStackTrace(\"" + methodName + "\", $args, $0);");
			code_buffer.append("_taintedParams = new ArrayList();");
			code_buffer.append("for (int i = 0; i < $1.length; i++) {");
			code_buffer.append("if (Source.isTainted($1[i]) != null)");
			code_buffer.append("_taintedParams.add(Source.isTainted($1[i]));");
			code_buffer.append("}");
			code_buffer.append("if (_taintedParams.size() > 0) {");
			code_buffer.append("_tParams = new String[_taintedParams.size()];");
			code_buffer.append("for (int i = 0; i < _tParams.length; i++) {");
			code_buffer.append("_tParams[i] = _taintedParams.get(i).toString();");
			code_buffer.append("}");
			code_buffer.append(
					"_taint = new Source($0, Source.LABEL_TAINTED, $0.getClass().getCanonicalName(), _tParams);");
			code_buffer.append("_taint.setTransmitStackTrace(_transmitStackTrace);");
			code_buffer.append("}");
			code_buffer.append("StringBuilderTaintRule.switchOn(); }");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code_buffer.toString();
	}

	public String insert_SinkStart() {
		pool.importPackage(Source.class.getCanonicalName());
		StringBuffer code_buffer = new StringBuffer("");
		try {
			code_buffer.append("if (StringBuilderTaintRule.isSwitchOn()) { StringBuilderTaintRule.switchOff();");
			dealMethod.addLocalVariable("_$taintedKey", pool.get(String.class.getCanonicalName()));
			code_buffer.append("_$taintedKey = Source.isTainted($0);");
			code_buffer.append("if (_$taintedKey != null) {");
			code_buffer.append(
					"System.out.println(\"Trigger Sink: java.lang.ProcessBuilder.start() with Source: \" + $0);");
			code_buffer.append("Source.dumpStackTrace(_$taintedKey);");
			code_buffer.append("}");
			code_buffer.append("StringBuilderTaintRule.switchOn(); }");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code_buffer.toString();
	}
}
