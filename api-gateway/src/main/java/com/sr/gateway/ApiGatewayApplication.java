package com.sr.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Value("${spring.application.name}")
    String applicationName;

    @Bean
    String print() {
        System.out.println("Application name from properties file :: " + applicationName);
		return applicationName;
    }


//    @Async
//    @Bean
//    public  String asyncGetData() throws InterruptedException {
//        System.out.println("Before the thread running");
//        Thread.sleep(4000);
//        System.out.println("=================================");
//        System.out.println("After the thread running");
//        return "";
//    }
//
}
