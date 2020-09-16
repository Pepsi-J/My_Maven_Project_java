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
	public static final String USERNAME = ActiveMQConnection.DEFAULT_USER; // 默认的连接用户名
	public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD; // 默认的连接密码
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
	 * 	非事务情况下，手动签收模式必须和acknowledge()对应。
	 */
	@Test
	public void testConsumer() throws JMSException {

		factory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);

		connection = factory.createConnection();

		connection.start();
		// 非事务，手动签收
		session = connection.createSession(false, session.CLIENT_ACKNOWLEDGE);

		queue = session.createQueue(QUEUE_NAME);

		consumer = session.createConsumer(queue);

		while (true) {
			TextMessage textMessage = (TextMessage) consumer.receive(5000L);
			if (null != textMessage) {
				System.out.println("消费者接收到的消息：" + textMessage.getText());
				textMessage.acknowledge();// 手动签收
			} else {
				break;
			}
		}
		consumer.close();
		session.close();
		connection.close();
	}
	
	/**
	 * 	开启事务后，无论是否设置手动签收，都会默认自动签收
	 *	范围大小：事务>签收
	 */
	@Test
	public void testConsumer1() throws JMSException {

		factory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);

		connection = factory.createConnection();

		connection.start();
		// 事务，手动签收
		session = connection.createSession(true, session.CLIENT_ACKNOWLEDGE);

		queue = session.createQueue(QUEUE_NAME);

		consumer = session.createConsumer(queue);

		while (true) {
			TextMessage textMessage = (TextMessage) consumer.receive(5000L);
			if (null != textMessage) {
				System.out.println("消费者接收到的消息：" + textMessage.getText());
			} else {
				break;
			}
		}
		consumer.close();
		session.commit();//提交事务
		session.close();
		connection.close();
	}
}
