package com.ccdc.activemq.acknowledge;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;

class JMSSender_Producer_Acknowledge {
	public static final String USERNAME = ActiveMQConnection.DEFAULT_USER; // Ĭ�ϵ������û���
	public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD; // Ĭ�ϵ���������
	public static final String BROKERURL = "tcp://192.168.241.110:61616";
	public static final String QUEUE_NAME = "testQueue";
	public static ActiveMQConnectionFactory factory;
	public static Connection connection = null;
	public static Session session;
	public static Queue queue;
	public static MessageProducer producer;
	
	@Test
	public void testProducer() throws JMSException {

		factory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);// ����Ĭ���û���������

		connection = factory.createConnection();

		connection.start();
		// �������Զ�ǩ��
		session = connection.createSession(false, session.AUTO_ACKNOWLEDGE);

		queue = session.createQueue(QUEUE_NAME);

		producer = session.createProducer(queue);

		for (int i = 1; i <= 3; i++) {
			TextMessage textMessage = session.createTextMessage("queueMessage.No" + i);
			producer.send(textMessage);
		}
		producer.close();
		session.close();
		connection.close();
	}

	@Test
	public void testProducer1() throws JMSException {

		factory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);// ����Ĭ���û���������

		connection = factory.createConnection();

		connection.start();
		// �������Զ�ǩ��
		session = connection.createSession(true, session.AUTO_ACKNOWLEDGE);

		queue = session.createQueue(QUEUE_NAME);

		producer = session.createProducer(queue);

		for (int i = 1; i <= 3; i++) {
			TextMessage textMessage = session.createTextMessage("queueMessage.No" + i);
			producer.send(textMessage);
		}
		producer.close();
		session.commit();//�ύ����
		session.close();
		connection.close();
	}
}
