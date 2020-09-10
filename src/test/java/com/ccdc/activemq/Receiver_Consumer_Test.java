package com.ccdc.activemq;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;

class Receiver_Consumer_Test {
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
	public static MessageConsumer consumer;

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
			consumer = session.createConsumer(queue);
		} else if (c == 't') {
			topic = session.createTopic(TOPIC_NAME);
			// 6.��������������
			consumer = session.createConsumer(topic);
		}
	}

	public static void stop() throws JMSException {
		// 9.�ر���Դ
		consumer.close();
		session.close();
		connection.close();
	}

	// �첽����
	public static void Recrive_Message() throws JMSException {
		while (true) {
			// 7.������Ϣ
			TextMessage message = (TextMessage) consumer.receive(5000L);
			// 8.������Ϣ
			if (null != message) {
				System.out.println("�����߽��յ�����Ϣ��" + message.getText());
			} else {
				break;
			}
		}
	}

	// ���� + Lambda ���ʽ
	public static void setListener2() throws JMSException, IOException {
		consumer.setMessageListener((message) -> {
			if (null != message && message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println("�����߶����յ�����Ϣ��" + textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		System.in.read();
	}

	// ���� + �����ڲ���
	public static void setListener1() throws JMSException, IOException {
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				if (null != message && message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					try {
						System.out.println("������һ���յ�����Ϣ��" + textMessage.getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});
		System.in.read();
	}

	@Test
	public void testListener1() throws JMSException, IOException {
		// ����MQ������
		start('t');
		// ������Ϣ
		setListener1();
		// �ر���Դ
		stop();
	}

	@Test
	public void testListener2() throws JMSException, IOException {
		// ����MQ������
		start('t');
		// ������Ϣ
		setListener2();
		// �ر���Դ
		stop();
	}

	@Test
	public void testNoListener() throws JMSException, IOException {
		// ����MQ������
		start('q');
		// ������Ϣ
		Recrive_Message();
		// �ر���Դ
		stop();
	}
}
