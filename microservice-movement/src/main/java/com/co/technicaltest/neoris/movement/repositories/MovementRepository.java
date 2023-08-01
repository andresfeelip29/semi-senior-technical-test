package com.co.technicaltest.neoris.movement.repositories;

import com.co.technicaltest.neoris.movement.models.entity.documents.Movement;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface MovementRepository extends ReactiveMongoRepository<Movement, String> {

   @Query("{'clientId': clientId, 'createAt': {$gte: initDate , $lte: endDate}}")
   Flux<Movement> filterMovementForRageDateAndClientId(@Param("initDate") LocalDateTime initDate,
                                                       @Param("endDate") LocalDateTime endDate,
                                                       @Param("clientId") Long clientId );
}
