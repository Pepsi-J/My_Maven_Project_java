package com.ccdc.activemq.spring_mq;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;

/**
 * 	使用配置完成mq监听，不需要预先启动消费者即可完成主题的生产和消费
 * 
 * @author jhf
 *
 */
@Component
public class MyMessageListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		if (message != null && message instanceof TextMessage) {
			TextMessage textmessage = (TextMessage) message;
			try {
				System.out.println("********监听器收到消息：" + textmessage.getText());
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
