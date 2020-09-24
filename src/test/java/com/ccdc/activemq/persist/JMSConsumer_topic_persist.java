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
	public static final String USERNAME = ActiveMQConnection.DEFAULT_USER; // 默认的连接用户名
	public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD; // 默认的连接密码
	public static final String BROKERURL = "tcp://192.168.241.110:61616";
	public static final String TOPIC_NAME = "testTopic";
	public static ActiveMQConnectionFactory factory;
	public static Connection connection = null;
	public static Session session;
	public static Topic topic;

	@Test
	public void test() throws JMSException {
		System.out.println("*****张三订阅");

		factory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);// 采用默认用户名、密码

		connection = factory.createConnection();
		// 设置订阅者ID
		connection.setClientID("张三");

		session = connection.createSession(false, session.AUTO_ACKNOWLEDGE);

		topic = session.createTopic(TOPIC_NAME);
		// 绑定订阅者和主题
		TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "remark****");
		// 启动连接connection
		connection.start();

		Message message = topicSubscriber.receive();
		while (null != message) {
			TextMessage textMessage = (TextMessage) message;
			System.out.println("收到的持久化topic：" + textMessage.getText());
			message = topicSubscriber.receive();
		}
		session.close();
		connection.close();
	}

}
