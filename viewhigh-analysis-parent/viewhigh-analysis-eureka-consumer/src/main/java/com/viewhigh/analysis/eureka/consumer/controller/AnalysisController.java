package com.viewhigh.analysis.eureka.consumer.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viewhigh.analysis.domain.UserLogin;
import com.viewhigh.analysis.eureka.consumer.service.AnalysisFeignClient;
import com.viewhigh.analysis.eureka.consumer.vo.UserLoginVo;

@RestController
@RequestMapping(value = "${jwt.validate.path}")
public class AnalysisController {

	@Autowired
	private AnalysisFeignClient analysisFeignClient;
	
	@GetMapping(value = "/userlogin")
	public List<UserLoginVo> countUserLogin(){
		List<UserLogin> countUserLogins = analysisFeignClient.countUserLogin();
		List<UserLoginVo> list = countUserLogins.stream().map(UserLoginVo::fromDoMain).collect(Collectors.toList());
		return list;
	}
	
	
	
	
	
	
	
	
	
	
}
