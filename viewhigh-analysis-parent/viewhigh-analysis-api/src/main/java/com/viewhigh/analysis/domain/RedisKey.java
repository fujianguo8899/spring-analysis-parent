package com.viewhigh.analysis.domain;

public enum RedisKey {

	USER_LONGIN("userLogin:"),
	LOGIN_NAME("loginName"),
	LOGIN_TIME("loginTime"),
	LOGIN_COUNT("loginCount"),
	LOGIN_SUM("loginSum");
	
	private String key;

	private RedisKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
