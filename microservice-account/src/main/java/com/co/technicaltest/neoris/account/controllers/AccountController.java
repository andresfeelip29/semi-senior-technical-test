package com.co.technicaltest.neoris.account.controllers;

import com.co.technicaltest.neoris.account.models.dto.AccountDTO;
import com.co.technicaltest.neoris.account.models.dto.AccountQueryDTO;
import com.co.technicaltest.neoris.account.models.dto.AccountResponseDTO;
import com.co.technicaltest.neoris.account.services.AccountService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/api/v1/cuentas")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/")
    public ResponseEntity<List<AccountResponseDTO>> getAllClients() {
        log.info("Se recibe peticion para consulta de todos los clientes");
        return ResponseEntity.ok(this.accountService.getAllAccounts());
    }

    @GetMapping("/external/")
    public ResponseEntity<List<AccountQueryDTO>> getAllAccountsFromMicroserviceClient(@RequestParam Iterable<Long> accountsIds) {
        log.info("Se recibe peticion para consulta de todos los clientes");
        return ResponseEntity.ok(this.accountService.getAllAccountsFromMicroserviceClient(accountsIds));
    }

    @GetMapping("/detail/{accountId}")
    public ResponseEntity<AccountResponseDTO> findAccountClientDetail(@PathVariable Long accountId) {
        log.info("Se recibe peticion de consulta de cuenta con detalle con id: {}", accountId);
        return this.accountService.findAccountClientDetail(accountId)
                .map(account -> ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(account))
                .orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> findAccountById(@PathVariable Long accountId) {
        log.info("Se recibe peticion de consulta de cuenta con id: {}", accountId);
        return this.accountService.findAccountById(accountId)
                .map(account -> ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(account))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/")
    public ResponseEntity<AccountResponseDTO> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        log.info("Se recibe peticion para crear cuenta {}!", accountDTO);
        AccountResponseDTO result = this.accountService.saveAccount(accountDTO);
        if (!Objects.isNull(result)) {
            log.info("Se crea cuenta con id: {}, y usuario id: {}", result.id(), result.client().getId());
            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(result);
        }
        log.error("Error en solicitud de creacion de cuenta!");
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> updateAccount(
            @Valid @RequestBody AccountDTO accountDTO, @PathVariable Long accountId) {
        log.info("Se recibe peticion para actualiza cuenta {}!", accountDTO);
        AccountResponseDTO result = this.accountService.updateAccount(accountDTO, accountId);
        if (!Objects.isNull(result)) {
            log.info("Se actualiza cuenta con id: {}, y usuario id: {}", result.id(), result.client().getId());
            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(result);
        }
        log.error("Error en solicitud de actualizacion de cuenta!");
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        log.info("Se recibe peticion para eliminacion de cuenta con id: {}!", accountId);
        Boolean result = this.accountService.deleteAccount(accountId);
        if (!Objects.isNull(result)) {
            log.info("Cuenta con id: {} eliminada con exito!", accountId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).contentType(MediaType.APPLICATION_JSON).build();
        }
        log.error("Error en solicitud de eliminacion de cuenta!");
        return ResponseEntity.badRequest().build();
    }

}
