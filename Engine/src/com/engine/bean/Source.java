package com.engine.bean;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Source {

	public static final String LABEL_SOURCE = "source";
	public static final String LABEL_TAINTED = "tainted";

	public static ConcurrentHashMap<String, Source> table = new ConcurrentHashMap<String, Source>();

	public static String isTainted(Object o) {
		for (Map.Entry<String, Source> entry : table.entrySet()) {
			if (o == entry.getValue().getValue()
					&& o.getClass().getCanonicalName().equals(entry.getValue().getType())) {
				return entry.getKey();
			}
		}
		return null;
	}

	private String uuid;
	private Object value;
	private String label; // "source" - 源、"tainted" - 污染
	private String[] pre;
	private String type;
	private TransmitStackTrace transmitStackTrace;

	public static void dumpStackTrace(String sourceKey) {
		Source source = table.get(sourceKey);
		if (source == null)
			return;
		System.out.println("   Transmit from method: " + 
				source.getTransmitStackTrace().getMethod() + "; Input: " + source.getTransmitStackTrace().getInput()
				+ "; Output: " + source.getTransmitStackTrace().getOutput());
		String[] pres = source.getPre();
		if (pres == null || pres.length == 0) {
			return;
		}
		for (String pre : pres)
			dumpStackTrace(pre);
	}

	public Source(Object value, String label, String type, String[] pre) {
		this.uuid = UUID.randomUUID().toString().toLowerCase();
		this.value = value;
		this.label = label;
		this.type = type;
		this.pre = pre;
		table.put(uuid, this);
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TransmitStackTrace getTransmitStackTrace() {
		return transmitStackTrace;
	}

	public void setTransmitStackTrace(TransmitStackTrace transmitStackTrace) {
		this.transmitStackTrace = transmitStackTrace;
	}

	@Override
	public String toString() {
		return "Source [uuid=" + uuid + ", value=" + value + ", label=" + label + ", pre=" + Arrays.toString(pre)
				+ ", type=" + type + ", transmitStackTrace=" + transmitStackTrace + "]";
	}

}
