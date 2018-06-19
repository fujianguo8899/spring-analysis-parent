package com.viewhigh.analysis.eureka.consumer.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viewhigh.analysis.domain.ApiResult;
import com.viewhigh.analysis.domain.ErrorInfo;
import com.viewhigh.analysis.domain.RedisKey;
import com.viewhigh.analysis.eureka.consumer.runnable.RecordUserLoginTime;
import com.viewhigh.analysis.eureka.consumer.utils.TokenUtil;

public class TokenFilter extends OncePerRequestFilter {

	private final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 
			   														   3, 
			   														   2, 
			   														   TimeUnit.MINUTES, 
			   														   new ArrayBlockingQueue<>(100), 
			   														   new ThreadPoolExecutor.DiscardPolicy());
	
	@Autowired
	private TokenUtil tokenUtil;
	
	@Autowired
    private RedisTemplate<String, Object> redisTemplate;

	@Value("${jwt.header}")
	private String tokenHeader;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authToken = tokenUtil.getToken(request);
		if (tokenUtil.isTokenValid(authToken)) {
			// 异步刷新token失效时间
			tokenUtil.refreshToken(authToken);
			// 更新redis中的登录总时长
			Long uid = tokenUtil.getUidFromToken(authToken);
			executor.submit(new RecordUserLoginTime(uid, redisTemplate));
//			updateLoginSum(uid);
		}else if (!"OPTIONS".equalsIgnoreCase(request.getMethod())) {
			HttpServletResponse httpResponse = response;
			httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.setContentType("application/json; charset=utf-8");
			httpResponse.setStatus(HttpServletResponse.SC_OK);

			ObjectMapper mapper = new ObjectMapper();
			ApiResult<Void> result = ApiResult.errorResult(ErrorInfo.USER_TOKEN_OVER_TIME);
			httpResponse.getWriter().write(mapper.writeValueAsString(result));
			return;
		}

		filterChain.doFilter(request, response);
		
	}
    
    /*
    private void updateLoginSum(Long uid) {
		synchronized (this) {
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
		}
	}
	*/

}
