package com.viewhigh.analysis.eureka.consumer.vo;

import com.viewhigh.analysis.domain.UserLogin;

public class UserLoginVo {

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
	
	public static UserLoginVo fromDoMain(UserLogin userLogin){
		UserLoginVo vo = new UserLoginVo();
		vo.setId(userLogin.getId());
		vo.setRealName(userLogin.getRealName());
		vo.setLoginCount(userLogin.getLoginCount());
		return vo;
	}
	
	@Override
	public String toString() {
		return "UserLogin [id=" + id + ", realName=" + realName + ", loginCount=" + loginCount + "]";
	}
}
