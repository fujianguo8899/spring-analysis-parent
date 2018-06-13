package com.viewhigh.analysis.domain;

import java.io.Serializable;

/**
 * 用户登录次数统计实体
 */
public class UserLogin implements Serializable {
	
	private static final long serialVersionUID = -1481186426941379409L;
	
	private Long id;
	private String realName;
	private Long loginCount;
	
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
	public Long getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(Long loginCount) {
		this.loginCount = loginCount;
	}
	
	@Override
	public String toString() {
		return "UserLogin [id=" + id + ", realName=" + realName + ", loginCount=" + loginCount + "]";
	}
	
	
	
}
