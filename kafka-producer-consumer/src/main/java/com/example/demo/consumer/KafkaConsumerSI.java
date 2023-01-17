package com.example.demo.consumer;

import java.util.Collection;
import java.util.HashSet;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerSI {

	@ServiceActivator(inputChannel = "consumerChannel")
	public void method(Message<String> value,@Header(KafkaHeaders.CONSUMER) Consumer<String, String> consumer) throws Exception 
	{
		//Consumer<String, String> consumer  @Header(KafkaHeaders.ACKNOWLEDGMENT) 
		TopicPartition tp = new TopicPartition("mgmplat-comp-example", 0);
		
		Collection<TopicPartition> col = new HashSet<TopicPartition>();
		col.add(tp);
		System.out.println("*****************************************");
		consumer.pause(col);
		System.out.println("paused");
		Thread.sleep(5000);
		consumer.resume(col);
		System.out.println("resumed");
		System.out.println("spring integration"+value.getPayload());
	}
}
