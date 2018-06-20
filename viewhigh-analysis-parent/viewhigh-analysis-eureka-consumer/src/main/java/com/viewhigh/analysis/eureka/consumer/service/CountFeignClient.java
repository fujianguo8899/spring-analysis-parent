package com.viewhigh.analysis.eureka.consumer.service;

import com.viewhigh.analysis.api.ICountService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "analysisProvider")
public interface CountFeignClient extends ICountService {
}
