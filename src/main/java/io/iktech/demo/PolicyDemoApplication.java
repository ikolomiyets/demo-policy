package io.iktech.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebClientAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {
        WebSocketAutoConfiguration.class,
        MultipartAutoConfiguration.class,
        WebClientAutoConfiguration.class,
        ValidationAutoConfiguration.class
})
@EnableScheduling
public class PolicyDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(PolicyDemoApplication.class, args);
    }
}
