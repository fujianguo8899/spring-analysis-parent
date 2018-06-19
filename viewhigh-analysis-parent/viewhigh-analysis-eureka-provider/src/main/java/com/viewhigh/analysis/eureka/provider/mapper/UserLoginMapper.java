package com.viewhigh.analysis.eureka.provider.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.viewhigh.analysis.domain.User;
import com.viewhigh.analysis.domain.UserLogin;
import com.viewhigh.analysis.domain.UserPower;

public interface UserLoginMapper {
	
	// 记录用户登录信息
	Integer recordUserLogin(@Param("user") User user);
	
	// 统计用户登录次数
	List<UserLogin> countUserLogin();
	
	// 获取用户权限
	List<UserPower> listUserPower(@Param("id") Long id);
	
}
