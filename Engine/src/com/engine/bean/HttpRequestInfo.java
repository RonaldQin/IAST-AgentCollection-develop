package com.engine.bean;

import java.util.Map;

public class HttpRequestInfo {
	String uuid;
	String method;
	String url;
	String remoteAddr;
	String referer;
	Map<String, String> header;
	String body;
	Map<String, String> parameters;

	public HttpRequestInfo(String uuid, String method, String url, String remoteAddr, String referer,
			Map<String, String> header, String body, Map<String, String> parameters) {
		super();
		this.uuid = uuid;
		this.method = method;
		this.url = url;
		this.remoteAddr = remoteAddr;
		this.referer = referer;
		this.header = header;
		this.body = body;
		this.parameters = parameters;
	}

	public HttpRequestInfo() {
		super();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "HttpRequestInfo [uuid=" + uuid + ", method=" + method + ", url=" + url + ", remoteAddr=" + remoteAddr
				+ ", referer=" + referer + ", header=" + header + ", body=" + body + ", parameters=" + parameters + "]";
	}


}
