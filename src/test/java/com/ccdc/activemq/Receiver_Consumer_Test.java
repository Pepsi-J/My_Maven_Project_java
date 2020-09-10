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
	public static final String USERNAME = ActiveMQConnection.DEFAULT_USER; // 默认的连接用户名
	public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD; // 默认的连接密码
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
		// 1.创建连接工厂factory,传入指定URL
		factory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);// 采用默认用户名、密码
		// 2.创建连接connection
		connection = factory.createConnection();
		// 3.启动连接connection
		connection.start();
		// 4.创建Session
		session = connection.createSession(false, session.AUTO_ACKNOWLEDGE);
		// 5.创建destination 消息的目的地（queue/topic）
		if (c == 'q') {
			queue = session.createQueue(QUEUE_NAME);
			// 6.创建队列生产者
			consumer = session.createConsumer(queue);
		} else if (c == 't') {
			topic = session.createTopic(TOPIC_NAME);
			// 6.创建主题生产者
			consumer = session.createConsumer(topic);
		}
	}

	public static void stop() throws JMSException {
		// 9.关闭资源
		consumer.close();
		session.close();
		connection.close();
	}

	// 异步阻塞
	public static void Recrive_Message() throws JMSException {
		while (true) {
			// 7.接收消息
			TextMessage message = (TextMessage) consumer.receive(5000L);
			// 8.返回消息
			if (null != message) {
				System.out.println("消费者接收到的消息：" + message.getText());
			} else {
				break;
			}
		}
	}

	// 监听 + Lambda 表达式
	public static void setListener2() throws JMSException, IOException {
		consumer.setMessageListener((message) -> {
			if (null != message && message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println("订阅者二接收到的消息：" + textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		System.in.read();
	}

	// 监听 + 匿名内部类
	public static void setListener1() throws JMSException, IOException {
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				if (null != message && message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage) message;
					try {
						System.out.println("订阅者一接收到的消息：" + textMessage.getText());
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
		// 创建MQ并启动
		start('t');
		// 接收消息
		setListener1();
		// 关闭资源
		stop();
	}

	@Test
	public void testListener2() throws JMSException, IOException {
		// 创建MQ并启动
		start('t');
		// 接收消息
		setListener2();
		// 关闭资源
		stop();
	}

	@Test
	public void testNoListener() throws JMSException, IOException {
		// 创建MQ并启动
		start('q');
		// 接收消息
		Recrive_Message();
		// 关闭资源
		stop();
	}
}
