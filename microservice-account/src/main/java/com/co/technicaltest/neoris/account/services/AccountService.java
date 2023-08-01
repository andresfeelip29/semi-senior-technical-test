package com.co.technicaltest.neoris.account.services;

import com.co.technicaltest.neoris.account.models.dto.AccountDTO;
import com.co.technicaltest.neoris.account.models.dto.AccountQueryDTO;
import com.co.technicaltest.neoris.account.models.dto.AccountResponseDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    List<AccountResponseDTO> getAllAccounts();

    List<AccountQueryDTO> getAllAccountsFromMicroserviceClient(List<Long> accountsIds);

    Optional<AccountResponseDTO> findAccountClientDetail(Long accountId);

    Optional<AccountResponseDTO> findAccountById(Long accountId);

    AccountResponseDTO saveAccount(AccountDTO accountDTO);

    AccountResponseDTO updateAccount(AccountDTO accountDTO, Long accountId);

    Boolean deleteAccount(Long accountId);

    Optional<AccountResponseDTO> updateBalanceAccount(Long accountId, BigDecimal newBalance);
}
