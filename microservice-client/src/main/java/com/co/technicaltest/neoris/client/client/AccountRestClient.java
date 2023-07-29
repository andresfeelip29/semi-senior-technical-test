package com.co.technicaltest.neoris.client.client;

import com.co.technicaltest.neoris.client.models.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "microservice-account" , url = "")
public interface AccountRestClient {

    @GetMapping("/")
    List<Account> listUserByCourse(@RequestParam Iterable<Long> ids);

}
