package com.example.mettle.feature.flag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(securedEnabled = true)
public class FeatureFlagApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeatureFlagApplication.class, args);
    }
}
