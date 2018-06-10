package com.viewhigh.analysis.eureka.provider.service;

import com.viewhigh.analysis.api.ILoginService;
import com.viewhigh.analysis.eureka.provider.domain.User;
import com.viewhigh.analysis.eureka.provider.mapper.UserMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
public class LoginService implements ILoginService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public Integer login() {
		List<User> user = userMapper.listUser();
		System.out.println(user);
		return 1;
	}

    
}
