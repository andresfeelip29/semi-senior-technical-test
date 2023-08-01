package com.co.technicaltest.neoris.movement;

import com.co.technicaltest.neoris.movement.handler.MovementHandler;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class RouterFunctionConfig {

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

    

    @Bean
    public RouterFunction<ServerResponse> routes(MovementHandler handler) {
        return RouterFunctions.route(RequestPredicates.GET("/api/v1/movimientos"), handler::findAll)
                .andRoute(RequestPredicates.GET("/api/v1/movimientos/{id}"), handler::findById)
                .andRoute(RequestPredicates.POST("/api/v1/movimientos/"), handler::saveMovement)
                .andRoute(RequestPredicates.DELETE("/api/v1/movimientos/{id}"), handler::deleteMovement)
                .andRoute(RequestPredicates.GET("/api/v1/movimientos/"), handler::filterMovementForRageDateAndClientId);
    }


}
