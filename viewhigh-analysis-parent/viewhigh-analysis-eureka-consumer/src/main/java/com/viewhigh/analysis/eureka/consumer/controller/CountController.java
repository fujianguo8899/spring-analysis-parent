package com.viewhigh.analysis.eureka.consumer.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.viewhigh.analysis.eureka.consumer.service.CountFeignClient;


@RestController
public class CountController {
	@Autowired
	private CountFeignClient countFeignClient;
	
	@GetMapping("/countSex")
    public Map<Integer, Integer> countSex(){
		Map<Integer, Integer> result = countFeignClient.countSex();
		return result;
	}
	
	@GetMapping("/countAge")
	public Map<Integer, Integer> countAge(){
		Map<Integer, Integer> result = countFeignClient.countAge();
		return result;
	}
	}

