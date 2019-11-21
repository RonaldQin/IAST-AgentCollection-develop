package com.engine.bean;

public class StringTypeStackTraceInfo {
	
	private String method;
	private String input;
	private String output;

	public StringTypeStackTraceInfo(String method, String input, String output) {
		super();
		this.method = method;
		this.input = input;
		this.output = output;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	@Override
	public String toString() {
		return "StringTypeStackTraceInfo [method=" + method + ", input=" + input + ", output=" + output + "]";
	}
	
	
}
