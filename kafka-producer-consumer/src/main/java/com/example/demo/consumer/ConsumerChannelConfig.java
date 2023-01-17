package com.example.demo.consumer;
import java.util.HashMap;
import java.util.Map;
 
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.messaging.PollableChannel;
 
@Configuration
public class ConsumerChannelConfig {
 
	@Autowired
	 KafkaProperties kafkaProperties;
 
    @Bean
    public DirectChannel consumerChannel() {
        return new DirectChannel();
    }
 
    @Bean
    public KafkaMessageDrivenChannelAdapter kafkaMessageDrivenChannelAdapter() {
        KafkaMessageDrivenChannelAdapter kafkaMessageDrivenChannelAdapter = new KafkaMessageDrivenChannelAdapter(
                kafkaListenerContainer());
        kafkaMessageDrivenChannelAdapter.setOutputChannel(consumerChannel());
 
       // System.out.println("listener Id: "+ kafkaListenerContainer());
        return kafkaMessageDrivenChannelAdapter;
    }
 
    @SuppressWarnings("unchecked")
    @Bean
    public ConcurrentMessageListenerContainer kafkaListenerContainer() {
        ContainerProperties containerProps = new ContainerProperties("mgmplat-comp-example");
 
        return (ConcurrentMessageListenerContainer) new ConcurrentMessageListenerContainer(
                consumerFactory(), containerProps);
    }
 
    @Bean
    public ConsumerFactory consumerFactory() {
        return new DefaultKafkaConsumerFactory(consumerConfigs());
    }
 
    @Bean
    public Map consumerConfigs() {
        Map properties = new HashMap();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "dummy");
        
        properties.put("buffer.memory", 1024 * 1000);
        properties.putAll(kafkaProperties.getSsl().buildProperties());
        properties.putAll(kafkaProperties.getProperties());
        return properties;
    }
}