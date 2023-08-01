package com.co.technicaltest.neoris.movement.handler;

import com.co.technicaltest.neoris.movement.models.dto.MovementDTO;
import com.co.technicaltest.neoris.movement.models.dto.MovementResponseDTO;
import com.co.technicaltest.neoris.movement.services.MovementService;
import domain.utils.ParseDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
public class MovementHandler {

    private final MovementService movementService;


    public MovementHandler(MovementService movementService) {
        this.movementService = movementService;
    }


    public Mono<ServerResponse> findAll(ServerRequest request) {
        log.info("Se recibe peticion de consulta de cuentas!");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.movementService.findAll(), MovementResponseDTO.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        return this.movementService.findById(id)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(result))
                ).switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON).build());
    }

    public Mono<ServerResponse> saveMovement(ServerRequest request) {
        Mono<MovementDTO> movementDTOMono = request.bodyToMono(MovementDTO.class);
        return movementDTOMono.flatMap(this.movementService::saveMovement)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(result))
                ).switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON).build());
    }

    public Mono<ServerResponse> deleteMovement(ServerRequest request) {
        String id = request.pathVariable("id");
        return this.movementService.findById(id)
                .flatMap(movement -> this.movementService.deleteMovement(id)
                        .then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> filterMovementForRageDateAndClientId(ServerRequest request) {
        Optional<String> paramInitDate = request.queryParam("initDate");
        Optional<String> paramEndDate = request.queryParam("endDate");
        Optional<String> paramClientId = request.queryParam("clientId");
        if (paramInitDate.isPresent() && paramEndDate.isPresent() && paramClientId.isPresent()) {
            LocalDateTime initDate = ParseDate.formattDateTimeToParam(paramInitDate.get());
            LocalDateTime endDate = ParseDate.formattDateTimeToParam(paramEndDate.get());
            Long clientId = Long.parseLong(paramClientId.get());
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(this.movementService.filterMovementForRageDateAndClientId(initDate, endDate, clientId), MovementResponseDTO.class);
        }
        return ServerResponse.badRequest().build();
    }


}
