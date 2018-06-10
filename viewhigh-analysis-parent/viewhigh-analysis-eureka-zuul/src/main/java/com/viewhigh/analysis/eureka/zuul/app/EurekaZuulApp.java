package com.viewhigh.analysis.eureka.zuul.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class EurekaZuulApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaZuulApp.class).web(true).run(args);
    }
}
