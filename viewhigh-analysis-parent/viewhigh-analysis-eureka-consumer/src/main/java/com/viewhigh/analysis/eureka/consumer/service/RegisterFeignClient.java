package com.viewhigh.analysis.eureka.consumer.service;

import com.viewhigh.analysis.api.IRegisterService;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "analysisProvider")
public interface RegisterFeignClient extends IRegisterService {
}
