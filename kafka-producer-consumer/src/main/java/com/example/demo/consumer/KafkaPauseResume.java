package com.example.demo.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class KafkaPauseResume implements CommandLineRunner{

	@Autowired
	KafkaTemplate<String, String> template;
	
	@Autowired
	KafkaListenerEndpointRegistry registry;
	@Override
	public void run(String... args) throws Exception {
		System.out.println("----------------------------------------------------");
		System.out.println("containers :"+registry.getListenerContainers());
		
//		template.send("mgmplat-comp-example", "thing1");
//        Thread.sleep(5000);
//        System.out.println("pausing");
//        registry.getListenerContainer("kafkaListenerContainer").pause();
//        Thread.sleep(5000);
//        template.send("mgmplat-comp-example", "thing2");
//        Thread.sleep(5000);
//        System.out.println("resuming");
//        registry.getListenerContainer("kafkaListenerContainer").resume();
//        Thread.sleep(5000);
		
	}

}
