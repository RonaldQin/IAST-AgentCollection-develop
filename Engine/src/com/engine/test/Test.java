package com.engine.test;

import java.util.HashMap;

public class Test {

	public static HashMap<String, String> map = new HashMap<String, String>();

	public static void main(String[] args) {

		String s = "qin";

		StringBuilder sb = new StringBuilder("qin");
		System.out.println(sb.toString() == "qin");
	}

	public static void f(String s) {
		System.out.println(s == "Source");
	}

	static volatile boolean flag = false;

	public static synchronized void lf() {

	}
}
