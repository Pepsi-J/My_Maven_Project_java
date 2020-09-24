package com.ccdc.activemq.broker;

import org.apache.activemq.broker.BrokerService;
import org.junit.jupiter.api.Test;

class EmbedBroker {

	@Test
	void testBroker() throws Exception {
		BrokerService brokerService =new BrokerService();
		brokerService.setUseJmx(true);
		brokerService.addConnector("tcp://localhost:61616");
		brokerService.start();
	}

}
