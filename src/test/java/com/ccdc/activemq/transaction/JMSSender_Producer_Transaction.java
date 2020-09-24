package com.ccdc.activemq.transaction;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;

class JMSSender_Producer_Transaction {
	public static final String USERNAME = ActiveMQConnection.DEFAULT_USER; // 默认的连接用户名
	public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD; // 默认的连接密码
	public static final String BROKERURL = "tcp://192.168.241.110:61616";
	public static final String QUEUE_NAME = "testQueue";
	public static ActiveMQConnectionFactory factory;
	public static Connection connection = null;
	public static Session session;
	public static Queue queue;
	public static MessageProducer producer;

	@Test
	public void testProducer() throws JMSException {

		factory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);// 采用默认用户名、密码

		connection = factory.createConnection();

		connection.start();
		// 生产者session开启事务
		session = connection.createSession(true, session.AUTO_ACKNOWLEDGE);

		queue = session.createQueue(QUEUE_NAME);

		producer = session.createProducer(queue);

		for (int i = 1; i <= 3; i++) {
			TextMessage textMessage = session.createTextMessage("queueMessage.No" + i);
			producer.send(textMessage);
		}

		try {
			session.commit();// 手动提交
			System.out.println("消息发布到MQ完成！");
		} catch (Exception e) {
			session.rollback();// 事务回滚
		} finally {
			if (null != producer) {
				producer.close();
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
