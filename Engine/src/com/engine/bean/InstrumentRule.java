package com.engine.bean;

public class InstrumentRule {

	private String uuid;
	private String className;
	private Instrumentation[] instrumentations;
	private String dealClass;

	public InstrumentRule() {
		super();
	}

	public InstrumentRule(String uuid, String className, Instrumentation[] instrumentations) {
		super();
		this.uuid = uuid;
		this.className = className;
		this.instrumentations = instrumentations;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Instrumentation[] getInstrumentations() {
		return instrumentations;
	}

	public void setInstrumentations(Instrumentation[] instrumentations) {
		this.instrumentations = instrumentations;
	}

	public String getDealClass() {
		return dealClass;
	}

	public void setDealClass(String dealClass) {
		this.dealClass = dealClass;
	}

	@Override
	public String toString() {
		return "InstrumentRule [uuid=" + uuid + ", className=" + className + ", dealClass=" + dealClass
				+ ", instrumentations=" + instrumentations
				+ "]";
	}

}
