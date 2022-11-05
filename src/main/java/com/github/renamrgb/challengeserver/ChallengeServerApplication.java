package com.github.renamrgb.challengeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
public class ChallengeServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChallengeServerApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(4000))
                .setReadTimeout(Duration.ofMillis(4000))
                .build();
    }

}
