package com.co.technicaltest.neoris.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MicroserviceClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceClientApplication.class, args);
    }

}
