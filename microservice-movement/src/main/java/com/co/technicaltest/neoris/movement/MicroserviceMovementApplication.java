package com.co.technicaltest.neoris.movement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MicroserviceMovementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceMovementApplication.class, args);
    }

}
