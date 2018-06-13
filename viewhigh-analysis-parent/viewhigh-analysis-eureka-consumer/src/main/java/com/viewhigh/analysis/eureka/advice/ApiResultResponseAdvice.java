package com.viewhigh.analysis.eureka.advice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.viewhigh.analysis.domain.ApiResult;


public class ApiResultResponseAdvice implements ResponseBodyAdvice<Object> {
	
	// ResponseBodyAdvice 其作用是在响应体写出之前做一些处理；比如，修改返回值、加密等。
	
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		// null not instanceof ApiResult
		if (body instanceof ApiResult) {
			return body;
		}
		return new ApiResult<Object>(body);
	}
}
