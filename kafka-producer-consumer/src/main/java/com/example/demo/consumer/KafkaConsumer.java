package com.example.demo.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//@Service
public class KafkaConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

	@Autowired
	KafkaTemplate<String, String> kt;

	int count = 0;

//    @RetryableTopic(attempts = "2", backoff = @Backoff(delay = 1000, multiplier = 3.0),autoCreateTopics = "false",retryTopicSuffix = "-retry")
//	@KafkaListener(topics = { "mgmplat-comp-example", "mgmplat-comp-example-retry" }, groupId = "group-id")
//	public void consume(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
//		if (topic.equalsIgnoreCase("mgmplat-comp-example-retry")) {
//			count++;
//
//			LOGGER.info("in retry topic for" + count + "time");
//			if (count == 2) {
//				count = 0;
//				return;
//			}
//
//		}
//
//		LOGGER.info("topic name" + topic);
//		LOGGER.info(String.format("Message received -> %s", message));
//		try {
//			int x = Integer.parseInt(message);
//		} catch (Exception e) {
//			kt.send("mgmplat-comp-example-retry", "key1", message);
//
//		}
//
//	}

	long delay = 1000;
	@KafkaListener(topics = "mgmplat-comp-example", groupId = "group-id")
	public void consume1(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,Acknowledgment acknowledgment) {
	
		LOGGER.info(String.format("Message received -> %s", message));
		try {
			RestTemplate restTemplate = new RestTemplate();
			
			ResponseEntity<String> response
			  = restTemplate.getForEntity("https://eostos5nvmvsu8a.m.pipedream.net",String.class);
			LOGGER.info("status is"+response.getStatusCodeValue());
			
			acknowledgment.acknowledge();
		}catch (Exception e) {
			// TODO: handle exception
			LOGGER.info("in catch block");
			count++;
			if(count <= 5)
			{
				acknowledgment.nack(delay);
				delay = delay*2;
				if(count == 5)
				{
					kt.send("mgmplat-comp-example-dlt","key1", message);
					acknowledgment.acknowledge();
				}
			}
			else
			{
				count =0;
				delay = 1000;
			}
				
			
		}
		
		
		
		
		
	}

//    @DltHandler
//    public void dlt(String msg, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic){
//        System.out.println("Dead Message : "+msg +" from "+topic);
//    }
//	
}
