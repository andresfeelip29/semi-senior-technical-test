package com.co.technicaltest.neoris.movement.services;

import com.co.technicaltest.neoris.movement.models.dto.MovementDTO;
import com.co.technicaltest.neoris.movement.models.entity.documents.Movement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MovementService {

    Flux<Movement> findAll();
    Mono<Movement> findById(String id);

    Mono<Movement> saveMovement(MovementDTO movementDTO);

    Mono<Void> deleteMovement(String id);

    Flux<Movement> filterMovementForRageDateAndClientId(LocalDateTime initDate, LocalDateTime endDate, Long clientId);

}
