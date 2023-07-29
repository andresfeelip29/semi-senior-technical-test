package com.co.technicaltest.neoris.client.mapper;

import com.co.technicaltest.neoris.client.models.dto.ClientDTO;
import com.co.technicaltest.neoris.client.models.dto.ClientResponseDTO;
import com.co.technicaltest.neoris.client.models.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    Client clientDtoToClient(ClientDTO clientDTO);
    ClientResponseDTO clientToClientResponseDto(Client client);

    Client updateClientDtoToClient(@MappingTarget Client client, ClientDTO clientDTO);

}
