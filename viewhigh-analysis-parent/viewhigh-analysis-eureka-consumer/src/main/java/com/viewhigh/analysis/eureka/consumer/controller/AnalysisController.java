package com.viewhigh.analysis.eureka.consumer.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viewhigh.analysis.domain.RedisKey;
import com.viewhigh.analysis.domain.User;
import com.viewhigh.analysis.eureka.consumer.service.AnalysisFeignClient;
import com.viewhigh.analysis.eureka.consumer.vo.UserLoginTimeVo;
import com.viewhigh.analysis.eureka.consumer.vo.UserLoginVo;

@RestController
@RequestMapping(value = "${jwt.validate.path}")
public class AnalysisController {

	@Autowired
	private AnalysisFeignClient analysisFeignClient;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@GetMapping(value = "/analysis/logincount")
	public List<UserLoginVo> countUserLogin(){
//		List<UserLogin> countUserLogins = analysisFeignClient.countUserLogin();
//		List<UserLoginVo> list = countUserLogins.stream().map(UserLoginVo::fromDoMain).collect(Collectors.toList());
		
		List<User> users = analysisFeignClient.users();
		List<UserLoginVo> list = new ArrayList<>();
		users.forEach(user -> {
			String count = (String) redisTemplate.opsForHash().get(RedisKey.USER_LONGIN.getKey() + user.getId().toString(), RedisKey.LOGIN_COUNT.getKey());
			UserLoginVo vo = new UserLoginVo();
			vo.setId(user.getId());
			vo.setRealName(user.getRealName());
			vo.setLoginCount(Long.valueOf(count == null ? "0" : count));
			list.add(vo);
		});

		return list;
	}
	
	@GetMapping(value = "/analysis/logintime")
	public List<UserLoginTimeVo> userLoginTime(){
		List<User> users = analysisFeignClient.users();
		List<UserLoginTimeVo> list = new ArrayList<>();
		users.forEach(user -> {
			String sum = (String) redisTemplate.opsForHash().get(RedisKey.USER_LONGIN.getKey() + user.getId().toString(), RedisKey.LOGIN_SUM.getKey());
			UserLoginTimeVo vo = new UserLoginTimeVo();
			vo.setId(user.getId());
			vo.setRealName(user.getRealName());
			vo.setLoginTime(Long.valueOf(sum == null ? "0" : sum));
			list.add(vo);
		});

		return list;
	}
	
	
	
	
	
	
	
	
	
	
}
