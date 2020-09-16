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
	public static final String USERNAME = ActiveMQConnection.DEFAULT_USER; // 默认的连接用户名
	public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD; // 默认的连接密码
	public static final String BROKERURL = "tcp://192.168.43.69:61616";
	public static final String TOPIC_NAME = "testTopic";
	public static ActiveMQConnectionFactory factory;
	public static Connection connection = null;
	public static Session session;
	public static Topic topic;

	@Test
	void test() throws JMSException {
		System.out.println("*****张三订阅");
		// 1.创建连接工厂factory,传入指定URL
		factory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);// 采用默认用户名、密码
		// 2.创建连接connection
		connection = factory.createConnection();
		// 3.设置主题订阅者
		connection.setClientID("张三");
		// 4.创建Session
		session = connection.createSession(false, session.AUTO_ACKNOWLEDGE);
		// 5.创建destination 消息的目的地（queue/topic）
		topic = session.createTopic(TOPIC_NAME);
		// 6.绑定订阅者和主题
		TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "remark****");
		// 7.启动连接connection
		connection.start();
		
		Message message = topicSubscriber.receive();
		while (null != message) {
			// 8.接受主题消息
			TextMessage textMessage = (TextMessage) message;
			// 返回消息
			System.out.println("收到的持久化topic：" + textMessage.getText());
			message = topicSubscriber.receive();
		}
		// 9.关闭资源
		session.close();
		connection.close();
	}

}
