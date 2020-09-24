package com.ccdc.activemq.spring_boot_mq;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.camel.language.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

@Component
@EnableJms
public class ConfigBean {
	
	@Value("${myQueue}")
	private String myQueue;

	@Bean(ref = "")
	public Queue queue() {
		return new ActiveMQQueue(myQueue);
	}
}
