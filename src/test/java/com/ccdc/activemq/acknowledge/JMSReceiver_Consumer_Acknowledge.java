package com.ccdc.activemq.acknowledge;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;

class JMSReceiver_Consumer_Acknowledge {
	public static final String USERNAME = ActiveMQConnection.DEFAULT_USER; // Ĭ�ϵ������û���
	public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD; // Ĭ�ϵ���������
	public static final String BROKERURL = "tcp://192.168.241.110:61616";
	public static final String QUEUE_NAME = "testQueue";
	public static final String TOPIC_NAME = "testTopic";
	public static ActiveMQConnectionFactory factory;
	public static Connection connection = null;
	public static Session session;
	public static Queue queue;
	public static Topic topic;
	public static MessageConsumer consumer;
	
	/**
	 * 	����������£��ֶ�ǩ��ģʽ�����acknowledge()��Ӧ��
	 */
	@Test
	public void testConsumer() throws JMSException {

		factory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);

		connection = factory.createConnection();

		connection.start();
		// �������ֶ�ǩ��
		session = connection.createSession(false, session.CLIENT_ACKNOWLEDGE);

		queue = session.createQueue(QUEUE_NAME);

		consumer = session.createConsumer(queue);

		while (true) {
			TextMessage textMessage = (TextMessage) consumer.receive(5000L);
			if (null != textMessage) {
				System.out.println("�����߽��յ�����Ϣ��" + textMessage.getText());
				textMessage.acknowledge();// �ֶ�ǩ��
			} else {
				break;
			}
		}
		consumer.close();
		session.close();
		connection.close();
	}
	
	/**
	 * 	��������������Ƿ������ֶ�ǩ�գ�����Ĭ���Զ�ǩ��
	 *	��Χ��С������>ǩ��
	 */
	@Test
	public void testConsumer1() throws JMSException {

		factory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);

		connection = factory.createConnection();

		connection.start();
		// �����ֶ�ǩ��
		session = connection.createSession(true, session.CLIENT_ACKNOWLEDGE);

		queue = session.createQueue(QUEUE_NAME);

		consumer = session.createConsumer(queue);

		while (true) {
			TextMessage textMessage = (TextMessage) consumer.receive(5000L);
			if (null != textMessage) {
				System.out.println("�����߽��յ�����Ϣ��" + textMessage.getText());
			} else {
				break;
			}
		}
		consumer.close();
		session.commit();//�ύ����
		session.close();
		connection.close();
	}
}
