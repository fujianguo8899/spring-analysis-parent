package com.viewhigh.analysis.eureka.consumer.vo;


public class UserLoginTimeVo {

	private Long id;
	private String realName;
	private Long loginTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Long getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}
	@Override
	public String toString() {
		return "UserLoginTimeVo [id=" + id + ", realName=" + realName + ", loginTime=" + loginTime + "]";
	}
	
}
