package com.viewhigh.analysis.eureka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.viewhigh.analysis.eureka.advice.ApiResultResponseAdvice;


@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@RestControllerAdvice("com.viewhigh.analysis.eureka.consumer.controller")
	static class ResponseAdvice extends ApiResultResponseAdvice {
	}
}
