package com.viewhigh.analysis.eureka.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.viewhigh.analysis.domain.User;
import com.viewhigh.analysis.eureka.consumer.service.RegisterFeignClient;
import com.viewhigh.analysis.eureka.consumer.vo.UserVo;


@RestController
public class RegisterController {
	@Autowired
	private RegisterFeignClient registerFeignClient;
	
	@PostMapping("/register")
    public int register(@RequestBody UserVo userVo ){
		User user = UserVo.toDoMain(userVo);
		int result = registerFeignClient.register(user);
		return result;
	}
	}

