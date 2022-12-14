package com.example.instagram_clone_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class InstagramCloneServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstagramCloneServerApplication.class, args);
    }

}
