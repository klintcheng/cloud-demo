package com.mygb.user.service;

import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.SendReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RocketMQProducer {

    @Autowired
    private Producer producer;

    @Value("${rocketmq.topic.test}")
    private String topic;

    public void sendMessage(String body) throws Exception {
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        Message message = provider.newMessageBuilder()
                .setTopic(topic)
                .setBody(body.getBytes())
                .build();

        SendReceipt sendResult = producer.send(message);
        System.out.println("Send message successfully, messageId=" + sendResult.getMessageId());
    }
}