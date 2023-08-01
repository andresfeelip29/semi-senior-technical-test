package com.co.technicaltest.neoris.movement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Value("${config.base.url}")
    private String baseUrl;

    @Bean
    public WebClient.Builder registerWebClient() {
        return WebClient.builder().baseUrl(baseUrl);
    }


}
