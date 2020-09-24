package com.ccdc.activemq.persist;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;

class JMSConsumer_topic_persist {
	public static final String USERNAME = ActiveMQConnection.DEFAULT_USER; // Ĭ�ϵ������û���
	public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD; // Ĭ�ϵ���������
	public static final String BROKERURL = "tcp://192.168.241.110:61616";
	public static final String TOPIC_NAME = "testTopic";
	public static ActiveMQConnectionFactory factory;
	public static Connection connection = null;
	public static Session session;
	public static Topic topic;

	@Test
	public void test() throws JMSException {
		System.out.println("*****��������");

		factory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);// ����Ĭ���û���������

		connection = factory.createConnection();
		// ���ö�����ID
		connection.setClientID("����");

		session = connection.createSession(false, session.AUTO_ACKNOWLEDGE);

		topic = session.createTopic(TOPIC_NAME);
		// �󶨶����ߺ�����
		TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "remark****");
		// ��������connection
		connection.start();

		Message message = topicSubscriber.receive();
		while (null != message) {
			TextMessage textMessage = (TextMessage) message;
			System.out.println("�յ��ĳ־û�topic��" + textMessage.getText());
			message = topicSubscriber.receive();
		}
		session.close();
		connection.close();
	}

}
