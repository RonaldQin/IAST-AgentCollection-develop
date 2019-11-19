package com.engine.bean;

import java.util.Hashtable;
import java.util.UUID;

public class StringTypeSource {

	public static Hashtable<String, StringTypeSource> table = new Hashtable<String, StringTypeSource>();

	private String uuid;
	private String value;

	public StringTypeSource(String value) {
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
