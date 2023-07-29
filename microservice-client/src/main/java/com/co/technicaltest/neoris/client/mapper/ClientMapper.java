package com.co.technicaltest.neoris.client.mapper;

import com.co.technicaltest.neoris.client.models.dto.ClientDTO;
import com.co.technicaltest.neoris.client.models.dto.ClientResponseDTO;
import com.co.technicaltest.neoris.client.models.entity.Client;
import com.co.technicaltest.neoris.client.models.entity.ClientAccount;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {
    Client clientDtoToClient(ClientDTO clientDTO);

    @Mapping(source = "clientAccounts", target = "clientAccounts", qualifiedByName = "clientToIdList")
    ClientResponseDTO clientToClientResponseDto(Client client);

    Client updateClientDtoToClient(@MappingTarget Client client, ClientDTO clientDTO);

    @Named("clientToIdList")
    public static List<Long> convertListIds(List<ClientAccount> clientAccountList) {
        if(clientAccountList.isEmpty()) Collections.emptyList();
        return clientAccountList
                .stream()
                .map(ClientAccount::getId)
                .collect(Collectors.toList());
    }

}
