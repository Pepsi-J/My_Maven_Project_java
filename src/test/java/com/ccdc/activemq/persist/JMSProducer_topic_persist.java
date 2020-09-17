package com.ccdc.activemq.persist;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;

class JMSProducer_topic_persist {
	public static final String USERNAME = ActiveMQConnection.DEFAULT_USER; // Ĭ�ϵ������û���
	public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD; // Ĭ�ϵ���������
	public static final String BROKERURL = "tcp://192.168.241.110:61616";
	public static final String TOPIC_NAME = "testTopic";
	public static ActiveMQConnectionFactory factory;
	public static Connection connection = null;
	public static Session session;
	public static Topic topic;
	public static MessageProducer producer;

	@Test
	public void test() throws JMSException {

		factory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);// ����Ĭ���û���������

		connection = factory.createConnection();

		session = connection.createSession(false, session.AUTO_ACKNOWLEDGE);

		topic = session.createTopic(TOPIC_NAME);

		producer = session.createProducer(topic);
		// ��������־û�
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		// ��������connection
		connection.start();

		for (int i = 1; i <= 10; i++) {
			TextMessage message = session.createTextMessage("�����߷����ĳ־û����⣺ topicMessage.No" + i);
			producer.send(message);
		}
		producer.close();
		session.close();
		connection.close();
	}

}
