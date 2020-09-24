package com.ccdc.activemq.spring_boot_mq;

import java.util.UUID;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SpringBootMq_Producer {
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Autowired
	private Queue queue;

	public void producMessage() {
		jmsMessagingTemplate.convertAndSend(queue, "****producMessage****:" + UUID.randomUUID().toString().substring(0, 6));
		System.out.println("****producMessage**** send ok");
	}

	// 间隔3秒定时推送
	@Scheduled(fixedDelay = 3000L)
	public void producMessageScheduled() {
		jmsMessagingTemplate.convertAndSend(queue, "****producMessageScheduled****:" + UUID.randomUUID().toString().substring(0, 6));
		System.out.println("****producMessageScheduled**** send ok");
	}

}
