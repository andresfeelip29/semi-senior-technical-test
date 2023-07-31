package com.co.technicaltest.neoris.client.mapper;

import com.co.technicaltest.neoris.client.models.Account;
import com.co.technicaltest.neoris.client.models.dto.ClientDTO;
import com.co.technicaltest.neoris.client.models.dto.ClientQueryDTO;
import com.co.technicaltest.neoris.client.models.dto.ClientResponseDTO;
import com.co.technicaltest.neoris.client.models.entity.Client;
import com.co.technicaltest.neoris.client.models.entity.ClientAccount;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ClientMapper {
    Client clientDtoToClient(ClientDTO clientDTO);

    @Mapping(source = "clientAccounts", target = "clientAccounts", qualifiedByName = "clientToIdList")
    @Mapping(source = "accounts", target = "clientAccountsDetail", qualifiedByName = "accountList")
    ClientResponseDTO clientToClientResponseDto(Client client);

    Client updateClientDtoToClient(@MappingTarget Client client, ClientDTO clientDTO);

    ClientQueryDTO clientToClientQueryDto(Client client);

    @Named("clientToIdList")
    public static List<Long> convertListIds(List<ClientAccount> clientAccountList) {
        if (clientAccountList != null) {
            return clientAccountList
                    .stream()
                    .map(ClientAccount::getAccountId)
                    .toList();
        }
        return Collections.emptyList();
    }

    @Named("accountList")
    public static List<Account> seListAccount(List<Account> accounts) {
        if (accounts == null) {
            return Collections.emptyList();
        }
        return accounts;
    }

}
