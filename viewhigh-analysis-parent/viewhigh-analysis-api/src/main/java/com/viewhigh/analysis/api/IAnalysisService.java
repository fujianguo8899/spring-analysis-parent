package com.viewhigh.analysis.api;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.viewhigh.analysis.domain.User;
import com.viewhigh.analysis.domain.UserLogin;

public interface IAnalysisService {

	/**
	 * 统计用户登录次数
	 */
	@RequestMapping(value = "/countUserLogin", method = RequestMethod.GET)
	List<UserLogin> countUserLogin();
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	List<User> users();
}
