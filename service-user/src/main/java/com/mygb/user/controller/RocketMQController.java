package com.mygb.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mygb.user.service.RocketMQProducer;

@RestController
public class RocketMQController {

    @Autowired
    private RocketMQProducer rocketMQProducer;

    @GetMapping("/send")
    public String sendMessage(@RequestParam String message) {
        try {
            rocketMQProducer.sendMessage(message);
        } catch (Exception e) {
            return "Failed to send message: " + e.getMessage();
        }

        return "Message sent: " + message;
    }
}
