package com.ccdc.activemq;

import static org.junit.jupiter.api.Assertions.*;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;

class Sender_Producer_Test {
	public static final String USERNAME = ActiveMQConnection.DEFAULT_USER; // Ĭ�ϵ������û���
	public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD; // Ĭ�ϵ���������
	public static final String BROKERURL = "tcp://192.168.43.69:61616";
	public static final String QUEUE_NAME = "testQueue";
	public static final String TOPIC_NAME = "testTopic";
	public static ActiveMQConnectionFactory factory;
	public static Connection connection = null;
	public static Session session;
	public static Queue queue;
	public static Topic topic;
	public static MessageProducer producer;

	public static void start(char c) throws JMSException {
		// 1.�������ӹ���factory,����ָ��URL
		factory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);// ����Ĭ���û���������
		// 2.��������connection
		connection = factory.createConnection();
		// 3.��������connection
		connection.start();
		// 4.����Session
		session = connection.createSession(false, session.AUTO_ACKNOWLEDGE);
		// 5.����destination ��Ϣ��Ŀ�ĵأ�queue/topic��
		if (c == 'q') {
			queue = session.createQueue(QUEUE_NAME);
			// 6.��������������
			producer = session.createProducer(queue);
		} else {
			topic = session.createTopic(TOPIC_NAME);
			// 6.��������������
			producer = session.createProducer(topic);
		}
	}

	public static void stop() throws JMSException {
		// 9.�ر���Դ
		producer.close();
		session.close();
		connection.close();
	}

	public static void Send_Message(char c) throws JMSException {
		for (int i = 1; i <= 10; i++) {
			// 7.������Ϣ
			TextMessage message = session
					.createTextMessage("�����߷��͵���Ϣ��" + (c == 'q' ? "queueMessage.No" : "topicMessage.No") + i);
			message.setJMSMessageID(String.valueOf(i));
			// 8.������Ϣ
			producer.send(message);
		}
	}

	@Test
	public void testProducer() throws JMSException {
		char c = 'q';
		// ����MQ������
		start(c);
		// ������Ϣ
		Send_Message(c);// q:queue / c:topic
		// �ر���Դ
		stop();

		System.out.println("��Ϣ������MQ��ɣ�");
	}
}
