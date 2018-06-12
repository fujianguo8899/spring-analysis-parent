package com.viewhigh.analysis.eureka.consumer.config;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.viewhigh.analysis.eureka.advice.ApiResultResponseAdvice;
import com.viewhigh.analysis.eureka.consumer.filter.CorsFilter;
import com.viewhigh.analysis.eureka.consumer.filter.TokenFilter;



@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Value("${jwt.validate.path}")
	private String validatePath;
	
	@RestControllerAdvice("com.viewhigh.analysis.eureka.consumer.controller")
	static class ResponseAdvice extends ApiResultResponseAdvice {
	}
	
	@Bean
	public FilterRegistrationBean corsFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new CorsFilter());
		registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
		registrationBean.setOrder(1);
		return registrationBean;
	}
	
	@Bean
	public FilterRegistrationBean tokenFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(tokenFilter());
		registrationBean.addUrlPatterns(validatePath + "/*");
		registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
		registrationBean.setOrder(4);
		return registrationBean;
	}
	
	@Bean
	public Filter tokenFilter() {
		return new TokenFilter();
	}
}
