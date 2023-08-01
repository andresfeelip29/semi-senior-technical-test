package com.co.technicaltest.neoris.movement.services;

import com.co.technicaltest.neoris.movement.models.Account;
import com.co.technicaltest.neoris.movement.models.Client;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface ExternalRequestService {


    Mono<Client> findClientFromMicroserviceClient(Long clientId);

    Mono<Account> findAccountFromMicroserviceAccount(Long accountId);

    Mono<Void> updateBalanceToAccountFromMicroserviciosAccount(Long accountId, BigDecimal newBalance);
}
