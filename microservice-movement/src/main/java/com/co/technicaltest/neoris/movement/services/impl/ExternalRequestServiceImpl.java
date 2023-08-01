package com.co.technicaltest.neoris.movement.services.impl;

import com.co.technicaltest.neoris.movement.exceptions.BalanceUpdateException;
import com.co.technicaltest.neoris.movement.models.Account;
import com.co.technicaltest.neoris.movement.models.Client;
import com.co.technicaltest.neoris.movement.services.ExternalRequestService;
import domain.exception.account.AccountNotFoundException;
import domain.exception.client.ClientNotFoundException;
import domain.models.enums.ExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Collections;

@Slf4j
@Service
public class ExternalRequestServiceImpl implements ExternalRequestService {


    private final WebClient.Builder webClient;

    @Value("${microservices.client.url}")
    private String urlMicroserviceClient;

    @Value("${microservices.account.url}")
    private String urlMicroserviceAccount;

    public ExternalRequestServiceImpl(WebClient.Builder webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<Client> findClientFromMicroserviceClient(Long clientId) {
        log.info("Se realiza solicitud de consulta a microservcios cliente con id: {}", clientId);
        return this.webClient.build().get()
                .uri(urlMicroserviceClient.concat("/external/{clientId}"),
                        Collections.singletonMap("clientId", clientId))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Client.class)
                .switchIfEmpty(Mono.error(new ClientNotFoundException(
                        String.format(ExceptionMessage.CLIENT_NOT_FOUND_IN_MICROSERVICE_CLIENT.getMessage(), clientId))));
    }

    @Override
    public Mono<Account> findAccountFromMicroserviceAccount(Long accountId) {
        log.info("Se realiza solicitud de consulta a microservcios cuentas con id: {}", accountId);
        return this.webClient.build().get()
                .uri(urlMicroserviceAccount.concat("/detail/{accountId}"),
                        Collections.singletonMap("accountId", accountId))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Account.class)
                .switchIfEmpty(Mono.error(new AccountNotFoundException(
                        String.format(ExceptionMessage.CLIENT_NOT_FOUND_IN_MICROSERVICE_ACCOUNT.getMessage(), accountId))));
    }

    @Override
    public Mono<Account> updateBalanceToAccountFromMicroserviciosAccount(Long accountId, BigDecimal newBalance) {
        log.info("Se realiza solicitud de consulta a microservcios cuentas para actualizar balance de cuenta con id: {}", accountId);
        return this.webClient.build().put()
                .uri(urlMicroserviceAccount.concat("/external/"), uriBuilder -> uriBuilder
                        .queryParam("accountId", accountId)
                        .queryParam("newBalance", newBalance)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Account.class)
                .switchIfEmpty(Mono.error(new BalanceUpdateException(
                        String.format(ExceptionMessage.BALANCE_UPDATE_ERROR.getMessage(), accountId))));
    }
}
