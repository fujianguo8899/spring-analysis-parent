package com.viewhigh.analysis.eureka.consumer.runnable;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;

import com.viewhigh.analysis.domain.RedisKey;

public class RecordUserLoginTime implements Runnable {
	
	private Long uid;
	
    private RedisTemplate<String, Object> redisTemplate;

	public RecordUserLoginTime(final Long uid, 
						   final RedisTemplate<String, Object> redisTemplate) {
		this.uid = uid;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void run() {
//		synchronized (this) {
			// 2.获取redis中上次登录的信息
			String time  = (String) redisTemplate.opsForHash().get(RedisKey.USER_LONGIN.getKey() + uid.toString(), RedisKey.LOGIN_TIME.getKey());
			String sum   = (String) redisTemplate.opsForHash().get(RedisKey.USER_LONGIN.getKey() + uid.toString(), RedisKey.LOGIN_SUM.getKey());
			String name  = (String) redisTemplate.opsForHash().get(RedisKey.USER_LONGIN.getKey() + uid.toString(), RedisKey.LOGIN_NAME.getKey());
			String count = (String) redisTemplate.opsForHash().get(RedisKey.USER_LONGIN.getKey() + uid.toString(), RedisKey.LOGIN_COUNT.getKey());
			// 3.更新redis用户登录的信息
			long nowTime = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
			Long nowSum = nowTime - Long.valueOf(time);
			
			Map<String, String> map = new HashMap<>();
			map.put(RedisKey.LOGIN_NAME.getKey(), name);
			map.put(RedisKey.LOGIN_TIME.getKey(), String.valueOf(nowTime));
			map.put(RedisKey.LOGIN_COUNT.getKey(), count);
			map.put(RedisKey.LOGIN_SUM.getKey(), String.valueOf(nowSum + Long.valueOf(sum)));
			redisTemplate.opsForHash().putAll(RedisKey.USER_LONGIN.getKey() + uid.toString(), map);
//		}
	}

	
	

}
