package com.co.technicaltest.neoris.account.mappers;

import com.co.technicaltest.neoris.account.models.dto.AccountDTO;
import com.co.technicaltest.neoris.account.models.dto.AccountQueryDTO;
import com.co.technicaltest.neoris.account.models.dto.AccountResponseDTO;
import com.co.technicaltest.neoris.account.models.entity.Account;
import org.mapstruct.*;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AccountMapper {

    @Mapping(target = "accountType", ignore = true)
    Account accountDtoToAccount(AccountDTO accountDTO);

    @Mapping(target = "accountType",source = "accountType.bankAccountType")
    AccountQueryDTO accountToAccountQueryDto(Account accounts);

    @Mapping(target = "accountType",source = "accountType.bankAccountType")
    AccountResponseDTO accountToAccountResponseDto(Account account);

    @Mapping(target = "accountType", ignore = true)
    Account updateAccountToAccountDto(@MappingTarget Account account, AccountDTO accountDTO);
}
