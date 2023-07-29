package com.co.technicaltest.neoris.client.services;

import com.co.technicaltest.neoris.client.models.dto.ClientDTO;
import com.co.technicaltest.neoris.client.models.dto.ClientResponseDTO;


import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<ClientResponseDTO> getAllClient();

    Optional<ClientResponseDTO> findClientById(Long clientId);

    ClientResponseDTO saveClient(ClientDTO clientDTO);

    ClientResponseDTO updateClient(ClientDTO clientDTO, Long clientId);

    Boolean deleteClient(Long clientId);

}