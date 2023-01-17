package com.example.demo.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaProducerController {

	@Autowired
	@Qualifier("kafkaTemplate")
	KafkaTemplate<String, String> kt;
	
//	@PostMapping("create")
//	public String produce(@RequestBody String value)
//	{
//		kt.send("mgmplat-comp-example", value, value);
//		return "message sent";
//	}
	
	@Autowired
	@Qualifier("producerChannel")
	DirectChannel dc;
	
	@Autowired
	KafkaListenerEndpointRegistry registry;
	
	@PostMapping("create")
	public String produce(@RequestBody String value)
	{
		System.out.println("containers :"+registry.getListenerContainers());
		Map<String, String> headers = Collections.singletonMap(KafkaHeaders.TOPIC, "mgmplat-comp-example");
        dc.send(new GenericMessage(value, headers));
		return "message sent";
	}
}
