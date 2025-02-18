package com.jmyy.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope // 自动刷新配置
public class PingController {

    @Value("${custom.message}")
    private String message;

    @GetMapping("/ping")
    public String ping() {
        return message;
    }
}
