package com.engine.test;

public class TestStackInfo {

	public static String transmit(String source) {
		String tmp = source;
		tmp += "xxxxxxxxxxxx";
		Throwable ex = new Throwable();
		StackTraceElement[] stackElements = ex.getStackTrace();
		if (stackElements != null) {
			for (int i = 0; i < stackElements.length; i++) {
				System.out.println(stackElements[i].getMethodName() + " : " + stackElements[i].getLineNumber());
			}
		}
		return tmp;
	}
	
	public static void f() {
		String source = "SOURCE";
		String ret = transmit(source);
		System.out.println(ret);
	}

	public static void testT(String s, String... strs) {
		String[] arr = strs;
		for (String a : arr) {
			System.out.println(a);
		}
	}

	public static void main(String[] args) {
		String[] strs = { "q", "2", "sdf" };
		testT("asa", "a", "as");
	}
}





































































