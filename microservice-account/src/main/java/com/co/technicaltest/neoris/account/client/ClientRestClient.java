package com.co.technicaltest.neoris.account.client;

import com.co.technicaltest.neoris.account.models.Client;
import domain.models.ClientAccountQueryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "microservice-client", url = "${microservices.client.url}/clientes")
public interface ClientRestClient {

    @GetMapping("/external/{clientId}")
    Client findClientFromMicroserviceClient(@PathVariable Long clientId);

    @DeleteMapping("/external/")
    void deleteAccountClientFromMicroserviceClient(@RequestParam Long clientId ,@RequestParam Long accountId);

    @PostMapping("/external/")
    void saveClientAccountFromMicroserviceClient(@RequestBody ClientAccountQueryDTO clientAccountQueryDTO);

}
