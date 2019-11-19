package com.engine.bean;

import java.util.Arrays;

public class Instrumentation {
	public static final String POSITION_BEFORE = "before";
	public static final String POSITION_AFTER = "after";
	public static final int METHOD_TYPE_CONSTRUCTOR = 0;
	public static final int METHOD_TYPE_NORMAL = 1;
	public static final int METHOD_TYPE_STATIC_BLOCK = 2;

	private String methodName;
	private String[] params;
	private String insertPosition;
	private String insertLogic;
	private int methodType;

	public Instrumentation() {
		super();
	}

	public Instrumentation(String methodName, String[] params, String insertPosition, String insertLogic,
			int methodType) {
		super();
		this.methodName = methodName;
		this.params = params;
		this.insertPosition = insertPosition;
		this.insertLogic = insertLogic;
		this.methodType = methodType;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String[] getParams() {
		return params;
	}

	public void setParams(String[] params) {
		this.params = params;
	}

	public String getInsertPosition() {
		return insertPosition;
	}

	public void setInsertPosition(String insertPosition) {
		this.insertPosition = insertPosition;
	}

	public String getInsertLogic() {
		return insertLogic;
	}

	public void setInsertLogic(String insertLogic) {
		this.insertLogic = insertLogic;
	}

	public int getMethodType() {
		return methodType;
	}

	public void setMethodType(int methodType) {
		this.methodType = methodType;
	}

	@Override
	public String toString() {
		return "Instrumentation [methodName=" + methodName + ", params=" + Arrays.toString(params) + ", insertPosition="
				+ insertPosition + ", insertLogic=" + insertLogic + ", methodType=" + methodType + "]";
	}


}
