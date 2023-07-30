package com.co.technicaltest.neoris.account.repositories;

import com.co.technicaltest.neoris.account.models.entity.AccountType;
import domain.models.enums.BankAccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {

    AccountType findByBankAccountType(@RequestParam BankAccountType bankAccountType);
}
