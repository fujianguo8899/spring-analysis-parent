package com.viewhigh.analysis.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.viewhigh.analysis.domain.User;

public interface ILoginService {
	
	/**
	 * 用户登录
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	User login(@RequestParam(value = "userName") String userName,
			   @RequestParam(value = "password") String password);
	
	/**
	 * 记录用户登录记录
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/recordUserLogin", method = RequestMethod.POST)
	Integer recordUserLogin(User user);
}
