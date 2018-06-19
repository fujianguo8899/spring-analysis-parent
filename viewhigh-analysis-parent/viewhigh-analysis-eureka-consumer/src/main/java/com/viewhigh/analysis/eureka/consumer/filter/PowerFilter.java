package com.viewhigh.analysis.eureka.consumer.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viewhigh.analysis.domain.ApiResult;
import com.viewhigh.analysis.domain.ErrorInfo;
import com.viewhigh.analysis.eureka.consumer.utils.TokenUtil;
import com.viewhigh.analysis.exception.CheckedException;

public class PowerFilter extends OncePerRequestFilter {
	
	@Autowired
	private TokenUtil tokenUtil;
	
	@Autowired
	private StringRedisTemplate strRedisTemplate;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String url = request.getRequestURL().toString();
		if (url.contains("/api")) {
			String authToken = tokenUtil.getToken(request);
			Long uid = tokenUtil.getUidFromToken(authToken);
			String roleId = strRedisTemplate.opsForValue().get("userRole:" + uid);
			if (!"1".equals(roleId) && url.contains("/analysis")) {
				HttpServletResponse httpResponse = response;
				httpResponse.setCharacterEncoding("UTF-8");
				httpResponse.setContentType("application/json; charset=utf-8");
				httpResponse.setStatus(HttpServletResponse.SC_OK);

				ObjectMapper mapper = new ObjectMapper();
				ApiResult<Void> result = ApiResult.errorResult(ErrorInfo.USER_NOT_HAVE_POWER);
				httpResponse.getWriter().write(mapper.writeValueAsString(result));
				return;
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
