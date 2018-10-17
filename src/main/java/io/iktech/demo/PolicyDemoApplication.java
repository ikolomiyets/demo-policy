package io.iktech.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {
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
