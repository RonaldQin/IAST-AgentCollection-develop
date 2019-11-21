package com.engine.test;

import java.util.HashMap;

public class Test {

	public static HashMap<String, String> map = new HashMap<String, String>();

	public static void main(String[] args) {
		map.put("1", "Source");
		String source = map.get("1");
		f(source);
	}

	public static void f(String s) {
		System.out.println(s == "Source");
	}
}
