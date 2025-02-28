package com.mygb.user.service;

import java.util.Collections;

import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.consumer.ConsumeResult;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.FilterExpressionType;
import org.apache.rocketmq.client.apis.consumer.PushConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class RocketMQConsumer implements ApplicationRunner {

    @Value("${rocketmq.producer.endpoints}")
    private String endpoints;

    @Value("${rocketmq.topic.test}")
    private String topic;

    @Override
    @SuppressWarnings({ "UseSpecificCatch", "ConvertToTryWithResources" })
    public void run(ApplicationArguments args) throws Exception {
        final ClientServiceProvider provider = ClientServiceProvider.loadService();
        ClientConfiguration config = ClientConfiguration.newBuilder()
                .setEndpoints(endpoints)
                .enableSsl(false).build();
        new Thread(() -> {
            try {
                log.info("启动RocketMQConsumer");

                String tag = "*";
                FilterExpression filterExpression = new FilterExpression(tag, FilterExpressionType.TAG);

                PushConsumer pushConsumer = provider.newPushConsumerBuilder()
                        .setClientConfiguration(config)
                        // Set the consumer group name.
                        .setConsumerGroup("my-consumer-group")
                        // Set the subscription for the consumer.
                        .setSubscriptionExpressions(Collections.singletonMap(topic, filterExpression))
                        .setMessageListener(messageView -> {
                            // Handle the received message and return consume result.
                            log.info("Consume message={}", messageView);
                            return ConsumeResult.SUCCESS;
                        })
                        .build();
                // Block the main thread, no need for production environment.
                Thread.sleep(Long.MAX_VALUE);
                // Close the push consumer when you don't need it anymore.
                // You could close it manually or add this into the JVM shutdown hook.
                pushConsumer.close();
            } catch (Exception e) {
                log.error("启动RocketMQConsumer失败", e);
            }
        }).start();
    }

}