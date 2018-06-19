package com.viewhigh.analysis.domain;

import java.io.Serializable;

public class UserPower implements Serializable {

	private static final long serialVersionUID = 8318409977972130L;
	
	private Long id;
	private Long rId;
	private String roleName;
	private String power;
	private String degist;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getDegist() {
		return degist;
	}
	public void setDegist(String degist) {
		this.degist = degist;
	}
	public Long getrId() {
		return rId;
	}
	public void setrId(Long rId) {
		this.rId = rId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	@Override
	public String toString() {
		return "UserPower [id=" + id + ", rId=" + rId + ", roleName=" + roleName + ", power=" + power + ", degist="
				+ degist + "]";
	}
	
	
}
