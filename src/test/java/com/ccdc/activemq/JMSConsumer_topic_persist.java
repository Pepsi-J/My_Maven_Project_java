package com.ccdc.activemq;

import static org.junit.jupiter.api.Assertions.*;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
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
	public static final String BROKERURL = "tcp://192.168.43.69:61616";
	public static final String TOPIC_NAME = "testTopic";
	public static ActiveMQConnectionFactory factory;
	public static Connection connection = null;
	public static Session session;
	public static Topic topic;

	@Test
	void test() throws JMSException {
		System.out.println("*****��������");
		// 1.�������ӹ���factory,����ָ��URL
		factory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);// ����Ĭ���û���������
		// 2.��������connection
		connection = factory.createConnection();
		// 3.�������ⶩ����
		connection.setClientID("����");
		// 4.����Session
		session = connection.createSession(false, session.AUTO_ACKNOWLEDGE);
		// 5.����destination ��Ϣ��Ŀ�ĵأ�queue/topic��
		topic = session.createTopic(TOPIC_NAME);
		// 6.�󶨶����ߺ�����
		TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "remark****");
		// 7.��������connection
		connection.start();
		
		Message message = topicSubscriber.receive();
		while (null != message) {
			// 8.����������Ϣ
			TextMessage textMessage = (TextMessage) message;
			// ������Ϣ
			System.out.println("�յ��ĳ־û�topic��" + textMessage.getText());
			message = topicSubscriber.receive();
		}
		// 9.�ر���Դ
		session.close();
		connection.close();
	}

}
