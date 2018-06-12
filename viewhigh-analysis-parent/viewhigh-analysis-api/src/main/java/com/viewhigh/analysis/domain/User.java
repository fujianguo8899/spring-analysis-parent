package com.viewhigh.analysis.domain;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = -6874527323607005183L;
	
	private Long id;
	private String name;
	private String password;
	private String realName;
	private String address;
	private String mobile;
	private Integer age;
	private Integer sex;/* 0:女 1：男 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	@Override
	public String toString() {
		return "UserVo [id=" + id + ", name=" + name + ", password=" + password + ", realName=" + realName
				+ ", address=" + address + ", mobile=" + mobile + ", age=" + age + ", sex=" + sex + "]";
	}
	
	
}
