package com.viewhigh.analysis.eureka.provider.service;

import com.viewhigh.analysis.api.ILoginService;
import com.viewhigh.analysis.domain.ErrorInfo;
import com.viewhigh.analysis.domain.User;
import com.viewhigh.analysis.domain.UserPower;
import com.viewhigh.analysis.eureka.provider.mapper.UserLoginMapper;
import com.viewhigh.analysis.eureka.provider.mapper.UserMapper;
import com.viewhigh.analysis.exception.CheckedException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Service
@RestController
public class LoginService implements ILoginService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserLoginMapper userLoginMapper;

	@Override
	public User login(String userName, String password) {
		User user = userMapper.getUserByName(userName);
		if (ObjectUtils.isEmpty(user)) {
			throw new CheckedException(ErrorInfo.USER_NOT_FOUND);
		}
		if (!password.equals(user.getPassword())) {
			throw new CheckedException(ErrorInfo.LOGIN_FAIL);
		}
		return user;
	}

	@Override
	@Transactional
	public Integer recordUserLogin(@RequestBody User user) {
		Integer login = userLoginMapper.recordUserLogin(user);
		return login;
	}

	@Override
	public List<UserPower> ListUserPower(Long id) {
		List<UserPower> list = userLoginMapper.listUserPower(id);
		return list;
	}

    
}
