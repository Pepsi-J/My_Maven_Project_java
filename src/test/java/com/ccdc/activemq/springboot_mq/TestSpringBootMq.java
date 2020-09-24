package com.ccdc.activemq.springboot_mq;

import static org.junit.jupiter.api.Assertions.fail;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ccdc.activemq.spring_boot_mq.Main_Producer;
import com.ccdc.activemq.spring_boot_mq.SpringBootMq_Producer;



@SpringBootTest(classes = Main_Producer.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestSpringBootMq {

	@Resource
	private SpringBootMq_Producer springBootMq_Producer;

	@Test
	public void testsend() throws Exception {
		springBootMq_Producer.producMessage();
	}

}
