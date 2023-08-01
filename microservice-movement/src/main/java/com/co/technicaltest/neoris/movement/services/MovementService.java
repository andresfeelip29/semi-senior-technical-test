package com.co.technicaltest.neoris.movement.services;

import com.co.technicaltest.neoris.movement.models.dto.MovementDTO;
import com.co.technicaltest.neoris.movement.models.dto.MovementResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MovementService {

    Flux<MovementResponseDTO> findAll();

    Mono<MovementResponseDTO> findById(String id);

    Mono<MovementResponseDTO> saveMovement(MovementDTO movementDTO);

    Mono<Void> deleteMovement(String id);

    Flux<MovementResponseDTO> filterMovementForRageDateAndClientId(LocalDateTime initDate,
                                                                   LocalDateTime endDate,
                                                                   Long clientId);

}
