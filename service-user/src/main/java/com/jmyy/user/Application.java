package com.jmyy.user;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
@EnableDiscoveryClient
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		log.info("hello world");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
