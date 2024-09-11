package io.gitHub.AugustoMello09.email.infra.configs;

import java.util.HashMap;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.JsonMessageConverter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class KafkaConsumerConfig {
	
	private final KafkaProperties properties;
	
	@Bean
    ConsumerFactory<String, Object> jsonConsumerFactory() {
        var configs = new HashMap<String, Object>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(configs);
    }

   @Bean
   ConcurrentKafkaListenerContainerFactory<String, Object> jsonContainerFactory(ConsumerFactory<String, Object> jsonConsumerFactory){
	   var factory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
	   factory.setConsumerFactory(jsonConsumerFactory);
	   factory.setRecordMessageConverter(new JsonMessageConverter());
	   return factory;
   }

}
