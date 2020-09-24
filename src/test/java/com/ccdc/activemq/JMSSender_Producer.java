package com.ccdc.activemq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.jupiter.api.Test;

class JMSSender_Producer {
	public static final String USERNAME = ActiveMQConnection.DEFAULT_USER; // 默认的连接用户名
	public static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD; // 默认的连接密码
//	public static final String BROKERURL = "tcp://192.168.241.110:61616";
	public static final String BROKERURL = "tcp://localhost:61616";
	public static final String QUEUE_NAME = "testQueue";
	public static final String TOPIC_NAME = "testTopic";
	public static ActiveMQConnectionFactory factory;
	public static Connection connection = null;
	public static Session session;
	public static Queue queue;
	public static Topic topic;
	public static MessageProducer producer;

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
			producer = session.createProducer(queue);
		} else {
			topic = session.createTopic(TOPIC_NAME);
			// 6.创建主题生产者
			producer = session.createProducer(topic);
		}
	}

	public static void stop() throws JMSException {
		// 9.关闭资源
		producer.close();
		session.close();
		connection.close();
	}

	public static void Send_Message(char c) throws JMSException {
		for (int i = 1; i <= 3; i++) {
			// 7.创建消息--TextMessage
			TextMessage textMessage = session.createTextMessage((c == 'q' ? "queueMessage.No" : "topicMessage.No") + i);
//			textMessage.setJMSMessageID(String.valueOf(i)); //消息ID
//			textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT); //持久化模式，默认持久化
//			textMessage.setJMSDestination(null); //目的地
//			textMessage.setJMSExpiration(0); //过期时间
//			textMessage.setJMSPriority(i); //优先级
			// 8.发送消息
			producer.send(textMessage);

			// 创建消息--MapMessage
//			MapMessage mapMessage = session.createMapMessage();
//			mapMessage.setString("k" + i, "mapMessageV" + i);//设置消息
//			mapMessage.setStringProperty("k2", "vipp");//设置属性
			// 发送消息
//			producer.send(mapMessage);
		}
	}

	@Test
	public void testProducer() throws JMSException {
		char c = 'q';
		// 创建MQ并启动
		start(c);
		// 发送消息
		Send_Message(c);// q:queue / c:topic
		// 关闭资源
		stop();

		System.out.println("消息发布到MQ完成！");
	}
}
