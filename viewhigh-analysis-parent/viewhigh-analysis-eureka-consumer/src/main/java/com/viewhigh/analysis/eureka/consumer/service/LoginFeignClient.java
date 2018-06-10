package com.viewhigh.analysis.eureka.consumer.service;

import com.viewhigh.analysis.api.ILoginService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "analysisProvider")
public interface LoginFeignClient extends ILoginService {
}
