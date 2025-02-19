package com.jmyy.gateway.startup;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupMessagePrinter implements ApplicationRunner {

    @Value("${custom.message}")
    private String message;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("启动配置消息: " + message);

    }

}
