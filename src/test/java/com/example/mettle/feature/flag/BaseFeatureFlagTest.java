package com.example.mettle.feature.flag;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootTest
public class BaseFeatureFlagTest {


    @Configuration
    static class ContextConfiguration {

        @Bean
        @Primary
        public SecurityFilterChain filterChainTest(final HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.httpBasic().and().authorizeHttpRequests(requests -> requests
                    .anyRequest()
                    .permitAll());
            return http.build();
        }
    }
}
