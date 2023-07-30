package com.co.technicaltest.neoris.account.client;

import com.co.technicaltest.neoris.account.models.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservice-client", url = "${microservices.client.url}/clientes")
public interface ClientRestClient {

    @GetMapping("/external/{clientId}")
    Client findClientFromMicroserviceClient(@PathVariable Long clientId);

    @DeleteMapping("/external/{accountId}")
    void deleteAccountClientFromMicroserviceClient(@PathVariable Long accountId);

}
