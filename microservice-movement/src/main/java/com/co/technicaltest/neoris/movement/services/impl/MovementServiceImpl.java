package com.co.technicaltest.neoris.movement.services.impl;

import com.co.technicaltest.neoris.movement.exceptions.AccountAndClientNotValidRelationshipException;
import com.co.technicaltest.neoris.movement.exceptions.MovementNotFoundException;
import com.co.technicaltest.neoris.movement.mappers.MovementMapper;
import com.co.technicaltest.neoris.movement.models.Account;
import com.co.technicaltest.neoris.movement.models.Client;
import com.co.technicaltest.neoris.movement.models.dto.MovementDTO;
import com.co.technicaltest.neoris.movement.models.dto.MovementResponseDTO;
import com.co.technicaltest.neoris.movement.models.entity.documents.Movement;
import com.co.technicaltest.neoris.movement.repositories.MovementRepository;
import com.co.technicaltest.neoris.movement.services.ExternalRequestService;
import com.co.technicaltest.neoris.movement.services.MovementService;
import domain.exception.movement.AccountHasNoBalanceException;
import domain.models.enums.ExceptionMessage;
import domain.models.enums.MovementType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
public class MovementServiceImpl implements MovementService {


    private final MovementRepository movementRepository;

    private final MovementMapper movementMapper;

    private final ExternalRequestService externalRequestService;


    public MovementServiceImpl(MovementRepository movementRepository, MovementMapper movementMapper, ExternalRequestService externalRequestService) {
        this.movementRepository = movementRepository;
        this.movementMapper = movementMapper;
        this.externalRequestService = externalRequestService;
    }

    @Override
    public Flux<MovementResponseDTO> findAll() {
        log.info("Se realiza consulta de movimientos");
        return this.movementRepository.findAll()
                .map(this.movementMapper::movementToMovementResponseDto);
    }

    @Override
    public Mono<MovementResponseDTO> findById(String id) {
        log.info("Se realiza consulta de movimiento con id: {}", id);
        return this.movementRepository.findById(id)
                .map(this.movementMapper::movementToMovementResponseDto)
                .switchIfEmpty(Mono.error(
                        new MovementNotFoundException(String.format(ExceptionMessage.MOVEMENT_NOT_FOUND.getMessage(), id))));

    }

    @Override
    public Mono<MovementResponseDTO> saveMovement(MovementDTO movementDTO) {
        Mono<MovementResponseDTO> result;

        log.info("Se realiza proceso de almacenado de informacion de cuenta: {}", movementDTO);
        if (Objects.isNull(movementDTO)) return Mono.empty();

        log.info("Se realiza consulta a microservicio de clientes para verificar estado de cliente con id : {}", movementDTO.clientId());
        Mono<Client> client = this.externalRequestService.findClientFromMicroserviceClient(movementDTO.clientId());

        log.info("Se realiza consulta a microservicio de cuentas para verificar saldo y estado de la cuenta con id : {}", movementDTO.accountId());
        Mono<Account> account = this.externalRequestService.findAccountFromMicroserviceAccount(movementDTO.accountId());

        result = account.zipWith(client, (a, c) -> {
                    if (isLessThanZero(a.initialBalance())
                            && movementDTO.movementType().getType().equals(MovementType.DEBIT.getType())) {
                        throw new AccountHasNoBalanceException(
                                ExceptionMessage.ACCOUNT_HAS_NOT_BALANCE_FOR_DEBIT_TRANSACTION.getMessage());
                    }

                    if (a.client().id().compareTo(c.id()) != 0) {
                        throw new AccountAndClientNotValidRelationshipException(
                                String.format(ExceptionMessage.ACCOUNT_NOT_ASSIGNED_TO_CLIENT.getMessage(),
                                        movementDTO.accountId(), movementDTO.clientId()));
                    }

                    BigDecimal value = movementDTO.movementType().getType().equals(MovementType.DEBIT.getType())
                            ? movementDTO.value().negate() : movementDTO.value();

                    return Movement.builder()
                            .createAt(LocalDateTime.now())
                            .movementType(movementDTO.movementType())
                            .movementValue(value)
                            .balance(calculateBalance(value, a.initialBalance()))
                            .clientId(c.id())
                            .name(c.name())
                            .accountId(a.id())
                            .accountNumber(a.accountNumber())
                            .accountType(a.accountType().getType())
                            .state(a.status())
                            .build();
                })
                .flatMap(this.movementRepository::save)
                .map(this.movementMapper::movementToMovementResponseDto)
                .doOnNext(movementResponseDTO -> {
                    log.info("Se realiza peiticion a microservicio cuenta para actualizar informacion de cuenta n°: {}", movementResponseDTO.accountNumber());
                    this.externalRequestService.updateBalanceToAccountFromMicroserviciosAccount(
                            movementDTO.accountId(), movementResponseDTO.balance());
                });

        return result;
    }

    @Override
    public Mono<Void> deleteMovement(String id) {
        log.info("Se se realiza proceso de eliminacin de movimiento con id: {}", id);
        return this.movementRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new MovementNotFoundException(String.format(ExceptionMessage.MOVEMENT_NOT_FOUND.getMessage(), id))))
                .doOnNext(this.movementRepository::delete)
                .then();
    }

    @Override
    public Flux<MovementResponseDTO> filterMovementForRageDateAndClientId(LocalDateTime initDate, LocalDateTime endDate, Long clientId) {
        return this.movementRepository.filterMovementForRageDateAndClientId(initDate, endDate, clientId)
                .map(this.movementMapper::movementToMovementResponseDto);
    }

    private Boolean isLessThanZero(BigDecimal mount) {
        return mount.compareTo(BigDecimal.ZERO) < 0;
    }

    private BigDecimal calculateBalance(BigDecimal value, BigDecimal initialBalance) {
        return initialBalance.add(value);
    }

}
