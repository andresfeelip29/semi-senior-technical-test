package com.co.technicaltest.neoris.movement.services;

import com.co.technicaltest.neoris.movement.models.dto.MovementDTO;
import com.co.technicaltest.neoris.movement.models.entity.documents.Movement;
import com.co.technicaltest.neoris.movement.repositories.MovementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
@Slf4j
@Service
public class MovementServiceImpl implements MovementService {


    private final MovementRepository movementRepository;

    public MovementServiceImpl(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    @Override
    public Flux<Movement> findAll() {
        return this.movementRepository.findAll();
    }

    @Override
    public Mono<Movement> findById(String id) {
        return null;
    }

    @Override
    public Mono<Movement> saveMovement(MovementDTO movementDTO) {
        return null;
    }

    @Override
    public Mono<Void> deleteMovement(String id) {
        return null;
    }

    @Override
    public Flux<Movement> filterMovementForRageDateAndClientId(LocalDateTime initDate, LocalDateTime endDate, Long clientId) {
        return null;
    }
}
