package com.engine.bean;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class StringTypeSource {
	public static final String LABEL_SOURCE = "source";
	public static final String LABEL_TAINTED = "tainted";

//	public static volatile Hashtable<String, StringTypeSource> table = new Hashtable<String, StringTypeSource>();
	public static ConcurrentHashMap<String, StringTypeSource> table = new ConcurrentHashMap<String, StringTypeSource>();

	private String uuid;
	private String value;
	private String label; // "source" - 源 、"tainted" - 被污染
	private String[] pre; // 如果是"tainted"，记录前面传递来的污点信息的uuid
	private StringTypeStackTraceInfo stringTypeStackTraceInfo;

	// TODO: .....
	public static synchronized void traversalTaintTransmitPath(String taintKey) {
		if (taintKey == null)
			return;
		else {
			StringTypeSource taint = table.get(taintKey);
			if (taint != null) {
				System.out.println("    StackTraceInfo: " + taint.getStringTypeStackTraceInfo().getMethod());
				String[] pres = taint.getPre();
				if (pres != null) {
					for (String pre : pres)
						traversalTaintTransmitPath(pre);
				}
			} else {
				return;
			}
		}
	}

	public static String isStringTypeSource(String val) {
		for (Map.Entry<String, StringTypeSource> entry : table.entrySet()) {
			if (entry.getValue().getValue().equals(val)) {
				return entry.getKey();
			}
		}
		return null;
	}

	public StringTypeSource(String value, String label, String[] pre) {
		this.value = value;
		this.uuid = UUID.randomUUID().toString().toLowerCase();
		this.label = label;
		this.pre = pre;
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String[] getPre() {
		return pre;
	}

	public void setPre(String[] pre) {
		this.pre = pre;
	}

	public StringTypeStackTraceInfo getStringTypeStackTraceInfo() {
		return stringTypeStackTraceInfo;
	}

	public void setStringTypeStackTraceInfo(StringTypeStackTraceInfo stringTypeStackTraceInfo) {
		this.stringTypeStackTraceInfo = stringTypeStackTraceInfo;
	}

	@Override
	public String toString() {
		return "StringTypeSource [uuid=" + uuid + ", value=" + value + ", label=" + label + ", pre="
				+ Arrays.toString(pre) + ", stringTypeStackTraceInfo=" + stringTypeStackTraceInfo + "]";
	}

}
