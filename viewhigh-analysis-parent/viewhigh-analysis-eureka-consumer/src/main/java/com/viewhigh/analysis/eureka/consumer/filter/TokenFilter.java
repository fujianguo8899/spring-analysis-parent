package com.viewhigh.analysis.eureka.consumer.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viewhigh.analysis.domain.ApiResult;
import com.viewhigh.analysis.eureka.consumer.utils.TokenUtil;

public class TokenFilter extends OncePerRequestFilter {

	@Autowired
	private TokenUtil tokenUtil;

	@Value("${jwt.header}")
	private String tokenHeader;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authToken = getToken(request);
		if (tokenUtil.isTokenValid(authToken)) {
			// 异步刷新token失效时间
			tokenUtil.refreshToken(authToken);
		}else if (!"OPTIONS".equalsIgnoreCase(request.getMethod())) {
			HttpServletResponse httpResponse = response;
			httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.setContentType("application/json; charset=utf-8");
			httpResponse.setStatus(HttpServletResponse.SC_OK);

			ObjectMapper mapper = new ObjectMapper();
			ApiResult<Void> result = ApiResult.errorResult("1001", "登录过期，请重新登录");
			httpResponse.getWriter().write(mapper.writeValueAsString(result));
			return;
		}

		filterChain.doFilter(request, response);
		
	}
	
	/**
	 * 从request中获取token信息
	 * @param request
	 * @return
	 */
    private String getToken(HttpServletRequest request){
    	String authToken="";
    	
    	//首先从headers中获取token信息
    	authToken=request.getHeader(tokenHeader);
    	if(authToken!=null&&!("".equals(authToken))){
    		return authToken;
    	}
    	
    	//从headers中没有获取到token信息，从cookie中获取
    	Cookie[] cookies=request.getCookies();
		if(cookies!=null){
			for(int i=0;i<cookies.length;i++){
				Cookie cookie=cookies[i];
				if(tokenHeader.equals(cookie.getName())){
					authToken=cookie.getValue();
					break;
				}
			}
		}
		
    	return authToken;
    }

}
