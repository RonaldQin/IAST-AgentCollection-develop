package com.engine.bean;

import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

public class StringTypeSource {

	public static Hashtable<String, StringTypeSource> table = new Hashtable<String, StringTypeSource>();

	private String uuid;
	private String value;

	public static boolean isStringTypeSource(String val) {
		for (Map.Entry<String, StringTypeSource> entry : table.entrySet()) {
			if (entry.getValue().getValue().equals(val)) {
				return true;
			}
		}
		return false;
	}

	public StringTypeSource(String value) {
//		System.out.println("New Source: " + value);
		this.value = value;
		this.uuid = UUID.randomUUID().toString().toLowerCase();
		table.put(uuid, this);
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "StringTypeSource [uuid=" + uuid + ", value=" + value + "]";
	}

}
