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
	public static final String USERNAME = ActiveMQConnection.DEFAULT_USER; // 默认的连接用户名
	public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD; // 默认的连接密码
	public static final String BROKERURL = "tcp://192.168.43.69:61616";
	public static final String TOPIC_NAME = "testTopic";
	public static ActiveMQConnectionFactory factory;
	public static Connection connection = null;
	public static Session session;
	public static Topic topic;
	public static MessageProducer producer;

	@Test
	void test() throws JMSException {
		// 1.创建连接工厂factory,传入指定URL
		factory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);// 采用默认用户名、密码
		// 2.创建连接connection
		connection = factory.createConnection();
		// 3.创建Session
		session = connection.createSession(false, session.AUTO_ACKNOWLEDGE);
		// 4.创建destination 消息的目的地（topic）
		topic = session.createTopic(TOPIC_NAME);
		// 5.创建主题发布者
		producer = session.createProducer(topic);
		// 6.设置主题持久化
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		// 7.启动连接connection
		connection.start();
		for (int i = 1; i <= 10; i++) {
			// 7.创建消息
			TextMessage message = session.createTextMessage("发布者发布的主题： topicMessage.No" + i);
			// 8.发送消息
			producer.send(message);
		}
		// 9.关闭资源
		producer.close();
		session.close();
		connection.close();
	}

}
