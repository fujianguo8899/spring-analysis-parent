package com.viewhigh.analysis.eureka.consumer.runnable;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;

import com.viewhigh.analysis.domain.RedisKey;
import com.viewhigh.analysis.domain.User;
import com.viewhigh.analysis.eureka.consumer.service.LoginFeignClient;

public class RecordUserLogin implements Runnable {
	
	private User user;
	
	private LoginFeignClient loginFeignClient;
	
    private RedisTemplate<String, Object> redisTemplate;

	public RecordUserLogin(final User user, 
						   final LoginFeignClient loginFeignClient, 
						   final RedisTemplate<String, Object> redisTemplate) {
		this.user = user;
		this.loginFeignClient = loginFeignClient;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void run() {
		// 1.记录用户登录时间到数据库，统计用户各时间段登录人数
		loginFeignClient.recordUserLogin(user);
		
		synchronized (this) {
			// 2.获取redis中上次登录的信息
			String count = (String) redisTemplate.opsForHash().get(RedisKey.USER_LONGIN.getKey() + user.getId().toString(), RedisKey.LOGIN_COUNT.getKey());
			String sum = (String) redisTemplate.opsForHash().get(RedisKey.USER_LONGIN.getKey() + user.getId().toString(), RedisKey.LOGIN_SUM.getKey());
			// 3.更新redis用户登录的信息
			Long nowCount = Long.valueOf(count == null ? "0" : count);//登录次数
			
			Map<String, String> map = new HashMap<>();
			map.put(RedisKey.LOGIN_NAME.getKey(), user.getRealName());
			map.put(RedisKey.LOGIN_TIME.getKey(), String.valueOf(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"))));
			map.put(RedisKey.LOGIN_COUNT.getKey(), String.valueOf(++nowCount));
			map.put(RedisKey.LOGIN_SUM.getKey(), sum == null ? "0" : sum);
			redisTemplate.opsForHash().putAll(RedisKey.USER_LONGIN.getKey() + user.getId().toString(), map);
		}
	}

	
	

}
