package com.examples.JobSchedulerSystem.config;

import com.examples.JobSchedulerSystem.common.ApplicationConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Profile("dev")
public class KafkaTopicConfiguration
{
  @Bean
  public NewTopic shortLivedHighPriorityJob() {
    return TopicBuilder.name(ApplicationConstants.SHORT_LIVED_HIGH_PRIORITY_JOB)
            .partitions(5)
            .replicas(1)
            .build();
  }

  @Bean
  public NewTopic shortLivedLowPriorityJob() {
    return TopicBuilder.name(ApplicationConstants.SHORT_LIVED_LOW_PRIORITY_JOB)
        .partitions(1)
        .replicas(1)
        .build();
  }

  @Bean
  public NewTopic longLivedJob() {
    return TopicBuilder.name(ApplicationConstants.LONG_RUNNING_JOB)
        .partitions(1)
        .replicas(1)
        .build();
  }
}
