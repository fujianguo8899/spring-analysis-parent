package com.viewhigh.analysis.eureka.consumer.vo;

import java.io.Serializable;

import com.viewhigh.analysis.domain.User;

public class UserVo implements Serializable {

	private static final long serialVersionUID = 1689637826437973806L;
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public static UserVo fromDoMian(User user){
		UserVo vo = new UserVo();
		vo.setId(user.getId());
		vo.setName(user.getName());
		vo.setPassword(user.getPassword());
		vo.setRealName(user.getRealName());
		vo.setAddress(user.getAddress());
		vo.setMobile(user.getMobile());
		vo.setAge(user.getAge());
		vo.setSex(user.getSex());
		return vo;
	}
	
	
	@Override
	public String toString() {
		return "UserVo [id=" + id + ", name=" + name + ", realName=" + realName
				+ ", address=" + address + ", mobile=" + mobile + ", age=" + age + ", sex=" + sex + "]";
	}
	
	public static User toDoMain(UserVo userVo){
		User user = new User();
		user.setId(userVo.getId());
		user.setName(userVo.getName());
		user.setPassword(userVo.getPassword());
		user.setRealName(userVo.getRealName());
		user.setAddress(userVo.getAddress());
		user.setMobile(userVo.getMobile());
		user.setAge(userVo.getAge());
		user.setSex(userVo.getSex());
		return user;
	}
	
}
