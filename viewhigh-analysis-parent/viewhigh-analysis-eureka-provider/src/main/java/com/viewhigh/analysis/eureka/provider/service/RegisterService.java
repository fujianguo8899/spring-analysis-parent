package com.viewhigh.analysis.eureka.provider.service;

import com.viewhigh.analysis.api.IRegisterService;
import com.viewhigh.analysis.domain.User;
import com.viewhigh.analysis.eureka.provider.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
public class RegisterService implements IRegisterService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public Integer register(@RequestBody User user) {
		int result = userMapper.insertUser(user);
		return result;
	}

    
}
