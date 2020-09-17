package com.ccdc.activemq.spring_mq;

import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class SpringMq_Producer {
	@Autowired
	private JmsTemplate jmsTemplate;

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		SpringMq_Producer producer = (SpringMq_Producer) ctx.getBean("springMq_Producer");
		// lambda
		producer.jmsTemplate.send((session) -> {
			TextMessage textMessage = session.createTextMessage("********spring + activemq ���� case........");
			return textMessage;
		});

		System.out.println("�����߷�����Ϣ��MQ�ɹ�!");
	}
}
