package com.co.technicaltest.neoris.movement.repositories;

import com.co.technicaltest.neoris.movement.models.entity.documents.Movement;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementRepository extends ReactiveMongoRepository<Movement, String> {
}
