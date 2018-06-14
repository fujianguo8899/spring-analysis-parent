package com.viewhigh.analysis.eureka.provider.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.viewhigh.analysis.api.IAnalysisService;
import com.viewhigh.analysis.domain.User;
import com.viewhigh.analysis.domain.UserLogin;
import com.viewhigh.analysis.eureka.provider.mapper.UserLoginMapper;
import com.viewhigh.analysis.eureka.provider.mapper.UserMapper;

@Service
@RestController
public class AnalysisService implements IAnalysisService {

	@Autowired
	private UserLoginMapper userLoginMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public List<UserLogin> countUserLogin() {
		List<UserLogin> list = userLoginMapper.countUserLogin();
		return list;
	}

	@Override
	public List<User> users() {
		List<User> users = userMapper.getUsers();
		return users;
	}

	

}
