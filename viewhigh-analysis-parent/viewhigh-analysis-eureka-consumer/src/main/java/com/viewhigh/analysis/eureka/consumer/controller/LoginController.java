package com.viewhigh.analysis.eureka.consumer.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viewhigh.analysis.eureka.consumer.service.LoginFeignClient;

@RestController 
public class LoginController {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private LoginFeignClient loginFeignClient;

    @GetMapping("/login")
    public Integer login(){
        loginFeignClient.login();
        Map<String, Object> pushCertTemporaryMap = new HashMap<>();
        pushCertTemporaryMap.put("test", "test");
        redisTemplate.opsForHash().putAll("token", pushCertTemporaryMap);
        String test = (String) redisTemplate.opsForHash().get("token", "test");
        System.out.println(test);
        return 1;
    }
}
