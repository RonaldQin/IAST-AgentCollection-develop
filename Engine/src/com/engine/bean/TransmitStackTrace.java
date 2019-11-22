package com.engine.bean;

public class TransmitStackTrace {

	private String method;
	private Object input;
	private Object output;

	public TransmitStackTrace(String method, Object input, Object output) {
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

	public Object getInput() {
		return input;
	}

	public void setInput(Object input) {
		this.input = input;
	}

	public Object getOutput() {
		return output;
	}

	public void setOutput(Object output) {
		this.output = output;
	}

	@Override
	public String toString() {
		return "TransmitStackTrace [method=" + method + ", input=" + input + ", output=" + output + "]";
	}

}
