package com.ccdc.activemq.spring_boot_mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Main_Producer {

	public static void main(String[] args) {
		SpringApplication.run(Main_Producer.class, args);
	}

}
