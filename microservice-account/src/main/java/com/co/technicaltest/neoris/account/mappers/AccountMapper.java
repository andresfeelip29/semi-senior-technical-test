package com.co.technicaltest.neoris.account.mappers;

import com.co.technicaltest.neoris.account.models.dto.AccountDTO;
import com.co.technicaltest.neoris.account.models.dto.AccountQueryDTO;
import com.co.technicaltest.neoris.account.models.dto.AccountResponseDTO;
import com.co.technicaltest.neoris.account.models.entity.Account;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AccountMapper {

    Account accountDtoToAccount(AccountDTO accountDTO);

    AccountQueryDTO accountToAccountQueryDto(Account accounts);

    AccountResponseDTO accountToAccountResponseDto(Account account);

    Account updateAccountToAccountDto(@MappingTarget Account account, AccountDTO accountDTO);
}
