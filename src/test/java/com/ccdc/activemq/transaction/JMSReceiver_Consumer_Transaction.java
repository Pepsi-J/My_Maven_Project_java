package com.ccdc.activemq.transaction;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;

class JMSReceiver_Consumer_Transaction {
	public static final String USERNAME = ActiveMQConnection.DEFAULT_USER; // Ĭ�ϵ������û���
	public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD; // Ĭ�ϵ���������
	public static final String BROKERURL = "tcp://192.168.241.110:61616";
	public static final String QUEUE_NAME = "testQueue";
	public static ActiveMQConnectionFactory factory;
	public static Connection connection = null;
	public static Session session;
	public static Queue queue;
	public static MessageConsumer consumer;

	@Test
	public void testConsumer() throws JMSException {

		factory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);

		connection = factory.createConnection();

		connection.start();
		// ������session��������
		session = connection.createSession(true, session.AUTO_ACKNOWLEDGE);

		queue = session.createQueue(QUEUE_NAME);

		consumer = session.createConsumer(queue);

		while (true) {
			// 7.������Ϣ
			TextMessage textMessage = (TextMessage) consumer.receive(5000L);
			// 8.������Ϣ
			if (null != textMessage) {
				System.out.println("�����߽��յ�����Ϣ��" + textMessage.getText());
			} else {
				break;
			}
		}

		try {
			session.commit();// �ֶ��ύ
		} catch (Exception e) {
			session.rollback();// ����ع�
		} finally {
			if (null != consumer) {
				consumer.close();
			}
			if (null != session) {
				session.close();
			}
			if (null != connection) {
				connection.close();
			}
		}
	}
}
