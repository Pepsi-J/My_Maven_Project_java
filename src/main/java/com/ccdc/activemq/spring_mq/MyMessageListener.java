package com.ccdc.activemq.spring_mq;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;

/**
 * 	ʹ���������mq����������ҪԤ�����������߼���������������������
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
				System.out.println("********�������յ���Ϣ��" + textmessage.getText());
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
