package com.viewhigh.analysis.eureka.consumer.service;

import com.viewhigh.analysis.api.IAnalysisService;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "analysisProvider")
public interface AnalysisFeignClient extends IAnalysisService {
}
