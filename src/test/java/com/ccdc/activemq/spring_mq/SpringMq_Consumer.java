package com.ccdc.activemq.spring_mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class SpringMq_Consumer {

	@Autowired
	private JmsTemplate jmsTemplate;

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		SpringMq_Consumer consumer = (SpringMq_Consumer) ctx.getBean("springMq_Consumer");
		String message = (String) consumer.jmsTemplate.receiveAndConvert();
		System.out.println("消费者收到消息：" + message);
	}
}
