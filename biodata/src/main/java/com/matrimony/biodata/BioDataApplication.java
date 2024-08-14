package com.matrimony.biodata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BioDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(BioDataApplication.class, args);
	}

}
