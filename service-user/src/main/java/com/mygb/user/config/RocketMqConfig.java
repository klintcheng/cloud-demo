package com.mygb.user.config;

import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientConfigurationBuilder;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMqConfig {

    @Value("${rocketmq.producer.endpoints}")
    private String endpoints;

    @Bean
    public Producer producer() throws Exception {
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        ClientConfigurationBuilder builder = ClientConfiguration.newBuilder()
                .setEndpoints(endpoints)
                .enableSsl(false);

        return provider.newProducerBuilder()
                .setClientConfiguration(builder.build())
                .build();
    }

}
