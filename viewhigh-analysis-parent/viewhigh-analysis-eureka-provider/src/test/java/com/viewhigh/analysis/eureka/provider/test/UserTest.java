package com.viewhigh.analysis.eureka.provider.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.viewhigh.analysis.eureka.provider.service.LoginService;

public class UserTest {

	@Autowired
	private LoginService loginService;
	
	@Test
	public void testListUser(){
		loginService.login();
	}
}
