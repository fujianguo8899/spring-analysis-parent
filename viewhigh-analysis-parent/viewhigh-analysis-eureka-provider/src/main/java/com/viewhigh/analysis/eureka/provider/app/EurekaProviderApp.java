package com.viewhigh.analysis.eureka.provider.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = {"com.viewhigh"})
@MapperScan({"com.viewhigh.analysis.eureka.provider.mapper"})
public class EurekaProviderApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaProviderApp.class).web(true).run(args);
    }
}
