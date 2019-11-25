package com.engine.rule;

import java.util.Arrays;

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

	public String insert_GetExecCommand() {
		pool.importPackage(CommandExecRule.class.getCanonicalName());
		StringBuffer code_buffer = new StringBuffer("");
		try {
			dealMethod.addLocalVariable("_$commandExecRuleInstance",
					pool.get(CommandExecRule.class.getCanonicalName()));
			code_buffer.append("_$commandExecRuleInstance = CommandExecRule.getInstance();");
			code_buffer.append("if (_$commandExecRuleInstance != null) {");
			code_buffer.append("_$commandExecRuleInstance.execCommand = $1;");
			code_buffer.append("System.out.println(\"执行命令： \" + _$commandExecRuleInstance.execCommand);");
			code_buffer.append("}");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code_buffer.toString();
	}

	public String insert_GetExecCommands() {
		pool.importPackage(Arrays.class.getCanonicalName());
		StringBuffer code_buffer = new StringBuffer("");
		try {
			code_buffer.append("System.out.println(\"执行命令： \" + Arrays.toString($$));");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code_buffer.toString();
	}
}
