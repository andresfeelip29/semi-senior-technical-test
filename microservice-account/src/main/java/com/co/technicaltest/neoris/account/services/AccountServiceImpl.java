package com.co.technicaltest.neoris.account.services;

import com.co.technicaltest.neoris.account.client.ClientRestClient;
import com.co.technicaltest.neoris.account.exceptions.AccountNotFoundException;
import com.co.technicaltest.neoris.account.mappers.AccountMapper;
import com.co.technicaltest.neoris.account.models.Client;
import com.co.technicaltest.neoris.account.models.dto.AccountDTO;
import com.co.technicaltest.neoris.account.models.dto.AccountQueryDTO;
import com.co.technicaltest.neoris.account.models.dto.AccountResponseDTO;
import com.co.technicaltest.neoris.account.models.entity.Account;
import com.co.technicaltest.neoris.account.models.entity.AccountClient;
import com.co.technicaltest.neoris.account.repositories.AccountRepository;
import domain.exception.client.ClientAccountNotFoundException;
import domain.exception.client.ClientNotFoundException;
import domain.models.enums.ExceptionMessage;
import domain.utils.GenerateRamdomAccountNumber;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    private final ClientRestClient clientRestClient;


    public AccountServiceImpl(AccountRepository accountRepository,
                              AccountMapper accountMapper,
                              ClientRestClient clientRestClient) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.clientRestClient = clientRestClient;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponseDTO> getAllAccounts() {
        log.info("Se realiza consulta de cuentas");
        return this.accountRepository.findAll()
                .stream()
                .map(this.accountMapper::accountToAccountResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountQueryDTO> getAllAccountsFromMicroserviceClient(Iterable<Long> accountsIds) {
        log.info("Se realiza consulta de cuentas {}, desde microservicio de cleintes", accountsIds);
        return this.accountRepository.findAllById(accountsIds)
                .stream()
                .map(this.accountMapper::accountToAccountQueryDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountResponseDTO> findAccountClientDetail(Long accountId) {
        log.info("Se realiza consulta con detalle de cliente, con cuenta id: {}", accountId);
        Account account = this.accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(String.format(ExceptionMessage.ACCOUNT_NOT_FOUND.getMessage(), accountId)));
        try {
            log.info("Se realiza consulta a microservicio de clientes, a cliente con id : {}", account.getAccountClient().getClientId());
            Client clientFromMicroserviceClient = this.clientRestClient.findClientFromMicroserviceClient(account.getAccountClient().getClientId());
            account.setClient(clientFromMicroserviceClient);
            return Optional.ofNullable(this.accountMapper.accountToAccountResponseDto(account));
        } catch (FeignException e) {
            log.info("No se encontro cuenta con id: {}, asociada algun usuario en consulta a microservicio clientes", accountId);
            throw new ClientAccountNotFoundException(String.format(ExceptionMessage.ACCOUNT_ASSOCIATED_TO_CLIENT_NO_FOUND.getMessage(), accountId));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AccountResponseDTO> findAccountById(Long accountId) {
        log.info("Se realiza consulta de cuenta con id: {}", accountId);
        return Optional.ofNullable(this.accountRepository.findById(accountId)
                .map(this.accountMapper::accountToAccountResponseDto)
                .orElseThrow(() -> new AccountNotFoundException(String.format(ExceptionMessage.ACCOUNT_NOT_FOUND.getMessage(), accountId))));
    }

    @Override
    @Transactional
    public AccountResponseDTO saveAccount(AccountDTO accountDTO) {
        AccountResponseDTO response = null;
        log.info("Se realiza proceso de almacenado de informacion de cuenta: {}", accountDTO);
        if (Objects.isNull(accountDTO)) return null;
        log.info("Se realiza consulta a microservicio de clientes para creacion de cuenta, a cliente con id : {}", accountDTO.clientId());
        try {
            Client clientFromMicroserviceClient = this.clientRestClient.findClientFromMicroserviceClient(accountDTO.clientId());
            Account account = this.accountMapper.accountDtoToAccount(accountDTO);
            account.setAccountClient(new AccountClient(clientFromMicroserviceClient.getId()));
            account.setAccountNumber(GenerateRamdomAccountNumber.generateBankAccountNumber());
            response = this.accountMapper.accountToAccountResponseDto(this.accountRepository.save(account));
        } catch (FeignException e) {
            log.info("No se encontro cliente con id: {} en consulta a microservicio clientes", accountDTO.clientId());
            throw new ClientNotFoundException(String.format(ExceptionMessage.CLIENT_NOT_FOUND.getMessage(), accountDTO.clientId()));
        }
        return response;
    }

    @Override
    @Transactional
    public AccountResponseDTO updateAccount(AccountDTO accountDTO, Long accountId) {
        AccountResponseDTO response = null;
        log.info("Se realiza proceso de actualizacion de informacion de cuenta: {}", accountDTO);
        if (Objects.isNull(accountDTO)) return null;
        Account account = this.accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(String.format(ExceptionMessage.ACCOUNT_NOT_FOUND.getMessage(), accountId)));
        account = this.accountMapper.updateAccountToAccountDto(account, accountDTO);
        response = this.accountMapper.accountToAccountResponseDto(this.accountRepository.save(account));
        return response;
    }

    @Override
    public Boolean deleteAccount(Long accountId) {
        log.info("Se incia proceso de eliminacion de cuenta: {}", accountId);
        Account account = this.accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(String.format(ExceptionMessage.ACCOUNT_NOT_FOUND.getMessage(), accountId)));
        try {
            this.accountRepository.delete(account);
            this.clientRestClient.deleteAccountClientFromMicroserviceClient(accountId);
            log.info("Se elimina cuenta con id: {}", accountId);
            return true;
        } catch (FeignException e) {
            log.info("No se encontro cuenta con id: {}, asociada algun usuario en consulta a microservicio clientes", accountId);
            throw new ClientAccountNotFoundException(String.format(ExceptionMessage.ACCOUNT_ASSOCIATED_TO_CLIENT_NO_FOUND.getMessage(), accountId));
        }
    }
}
