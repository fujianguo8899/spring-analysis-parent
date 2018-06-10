package com.viewhigh.analysis.eureka.provider.domain;

public class User {

	private String name;
	private String password;
	private String realName;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + ", password=" + password + ", realName=" + realName + "]";
	}
	
	
}
