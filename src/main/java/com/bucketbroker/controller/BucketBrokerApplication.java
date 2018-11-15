package com.bucketbroker.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.bucketbroker.controller","com.bucketbroker.service.impl"})
public class BucketBrokerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BucketBrokerApplication.class, args);
	}
}
