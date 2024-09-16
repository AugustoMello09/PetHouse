package io.gitHub.AugustoMello09.PetHouse.infra.message.configs;

import java.util.HashMap;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class KafkaAdminConfig {
	
	public final KafkaProperties properties;
	
	@Bean
	KafkaAdmin kafkaAdmin() {
		var configs = new HashMap<String, Object>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
		return new KafkaAdmin(configs);
	}
	
	@Bean
	KafkaAdmin.NewTopics topics(){
		return new KafkaAdmin.NewTopics(
				TopicBuilder.name("bemVindo").partitions(1).replicas(1).build(),
				TopicBuilder.name("Carrinho").partitions(1).replicas(1).build(),
				TopicBuilder.name("Pagamento").partitions(1).replicas(1).build()
				);
	}
	

}
