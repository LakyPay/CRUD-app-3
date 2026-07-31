package user.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic userEventsTopic() {
        return TopicBuilder.name("user-events")
            .partitions(2)
            .replicas(1)
            .config(TopicConfig.RETENTION_MS_CONFIG, "86400000")
            .config(TopicConfig.RETENTION_BYTES_CONFIG, "524288000")
            .build();
    }
}
