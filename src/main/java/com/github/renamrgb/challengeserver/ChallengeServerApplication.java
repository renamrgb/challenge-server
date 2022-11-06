package com.github.renamrgb.challengeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeServerApplication {

    private static final Long DURATION = 3L;

    public static void main(String[] args) {
        SpringApplication.run(ChallengeServerApplication.class, args);
    }
}
