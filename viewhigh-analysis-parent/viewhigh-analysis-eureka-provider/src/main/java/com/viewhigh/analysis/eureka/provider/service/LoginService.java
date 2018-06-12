package com.viewhigh.analysis.eureka.provider.service;

import com.viewhigh.analysis.api.ILoginService;
import com.viewhigh.analysis.domain.User;
import com.viewhigh.analysis.eureka.provider.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
public class LoginService implements ILoginService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public User login(String userName, String password) {
		User user = userMapper.getUserByName(userName);
		if (ObjectUtils.isEmpty(user)) {
			throw new RuntimeException("该用户名不存在！");
		}
		if (!password.equals(user.getPassword())) {
			throw new RuntimeException("用户名或密码错误！");
		}
		return user;
	}

    
}
