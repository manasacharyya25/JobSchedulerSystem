package com.examples.JobSchedulerSystem.config;

import com.examples.JobSchedulerSystem.common.ApplicationConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration
{
  @Bean
  public NewTopic longLivedJob() {
    return TopicBuilder.name(ApplicationConstants.SHORT_LIVED_JOB)
            .partitions(1)
            .replicas(1)
            .build();
  }

  @Bean
  public NewTopic shortLivedJob() {
    return TopicBuilder.name(ApplicationConstants.LONG_RUNNING_JOB)
        .partitions(1)
        .replicas(1)
        .build();
  }
}
