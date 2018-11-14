package com.schoewe.springboothttp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableDiscoveryClient
public class SpringBootHttpApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHttpApplication.class, args);
//		new SpringApplicationBuilder(SpringBootHttpApplication.class).web(true).run(args);
	}
}
