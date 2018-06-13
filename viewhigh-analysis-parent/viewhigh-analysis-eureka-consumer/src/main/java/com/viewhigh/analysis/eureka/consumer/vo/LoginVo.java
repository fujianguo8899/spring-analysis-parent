package com.viewhigh.analysis.eureka.consumer.vo;

public class LoginVo {

	private String userName;
	private String password;
	private String captchaCode;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCaptchaCode() {
		return captchaCode;
	}
	public void setCaptchaCode(String captchaCode) {
		this.captchaCode = captchaCode;
	}
	@Override
	public String toString() {
		return "LoginVo [userName=" + userName + ", password=" + password + ", captchaCode=" + captchaCode + "]";
	}
	
	
}
