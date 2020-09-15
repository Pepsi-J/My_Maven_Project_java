package com.ccdc.activemq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;

class JMSProducer_topic_persist {
	public static final String USERNAME = ActiveMQConnection.DEFAULT_USER; // Ĭ�ϵ������û���
	public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD; // Ĭ�ϵ���������
	public static final String BROKERURL = "tcp://192.168.43.69:61616";
	public static final String TOPIC_NAME = "testTopic";
	public static ActiveMQConnectionFactory factory;
	public static Connection connection = null;
	public static Session session;
	public static Topic topic;
	public static MessageProducer producer;

	@Test
	void test() throws JMSException {
		// 1.�������ӹ���factory,����ָ��URL
		factory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);// ����Ĭ���û���������
		// 2.��������connection
		connection = factory.createConnection();
		// 3.����Session
		session = connection.createSession(false, session.AUTO_ACKNOWLEDGE);
		// 4.����destination ��Ϣ��Ŀ�ĵأ�topic��
		topic = session.createTopic(TOPIC_NAME);
		// 5.�������ⷢ����
		producer = session.createProducer(topic);
		// 6.��������־û�
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		// 7.��������connection
		connection.start();
		for (int i = 1; i <= 10; i++) {
			// 7.������Ϣ
			TextMessage message = session.createTextMessage("�����߷��������⣺ topicMessage.No" + i);
			// 8.������Ϣ
			producer.send(message);
		}
		// 9.�ر���Դ
		producer.close();
		session.close();
		connection.close();
	}

}
