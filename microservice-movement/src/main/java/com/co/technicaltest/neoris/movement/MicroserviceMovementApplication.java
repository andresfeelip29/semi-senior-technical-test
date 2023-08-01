package com.co.technicaltest.neoris.movement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, WebMvcAutoConfiguration.class  })
@EnableWebFlux
public class MicroserviceMovementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceMovementApplication.class, args);
    }

}
