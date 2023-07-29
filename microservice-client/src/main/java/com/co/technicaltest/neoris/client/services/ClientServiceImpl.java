package com.co.technicaltest.neoris.client.services;

import com.co.technicaltest.neoris.client.client.AccountRestClient;
import com.co.technicaltest.neoris.client.exceptions.ClientNotFoundException;
import com.co.technicaltest.neoris.client.mapper.ClientMapper;
import com.co.technicaltest.neoris.client.models.dto.ClientDTO;
import com.co.technicaltest.neoris.client.models.dto.ClientResponseDTO;
import com.co.technicaltest.neoris.client.models.entity.Client;
import com.co.technicaltest.neoris.client.models.entity.ClientAccount;
import com.co.technicaltest.neoris.client.repositories.ClientRepository;
import domain.models.enums.ExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    private final AccountRestClient accountRestClient;

    public ClientServiceImpl(ClientRepository clientRepository,
                             ClientMapper clientMapper,
                             AccountRestClient accountRestClient) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.accountRestClient = accountRestClient;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponseDTO> getAllClient() {
        log.info("Se realiza consulta de los clientes");
        return this.clientRepository.findAll()
                .stream()
                .map(this.clientMapper::clientToClientResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClientResponseDTO> findClientAccountDetail(Long clientId) {
        log.info("Se realiza consulta de cliente con id: {}", clientId);
        Client client = this.clientRepository.findById(clientId).
                orElseThrow(() -> new ClientNotFoundException(ExceptionMessage.CLIENT_NOT_FOUND.getMessage()));
        if (!client.getClientAccounts().isEmpty()) {
            List<Long> ids = client.getClientAccounts()
                    .stream()
                    .map(ClientAccount::getAccountId).toList();
            client.setAccounts(this.accountRestClient.getAllAccoutDetail(ids));
            log.info("Se realiza consulta a microservicios cuentas, con los siguientes ids: {}", ids);
        }
        return Optional.ofNullable(this.clientMapper.clientToClientResponseDto(client));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClientResponseDTO> findClientById(Long clientId) {
        log.info("Se realiza consulta de cliente con id: {}", clientId);
        return Optional.ofNullable(this.clientRepository.findById(clientId)
                .map(this.clientMapper::clientToClientResponseDto)
                .orElseThrow(() -> new ClientNotFoundException(ExceptionMessage.CLIENT_NOT_FOUND.getMessage())));
    }

    @Override
    @Transactional
    public ClientResponseDTO saveClient(ClientDTO clientDTO) {
        Client client = this.clientMapper.clientDtoToClient(clientDTO);
        if (Objects.isNull(client)) return null;
        client = this.clientRepository.save(client);
        log.info("Se almacena usuario con id: {}", client.getId());
        return this.clientMapper.clientToClientResponseDto(client);
    }

    @Override
    @Transactional
    public ClientResponseDTO updateClient(ClientDTO clientDTO, Long clientId) {
        Client client = this.clientRepository.findById(clientId).
                orElseThrow(() -> new ClientNotFoundException(ExceptionMessage.CLIENT_NOT_FOUND.getMessage()));
        client = this.clientRepository.save(this.clientMapper.updateClientDtoToClient(client, clientDTO));
        log.info("Se actualiza usuario con id: {}", client.getId());
        return this.clientMapper.clientToClientResponseDto(client);
    }

    @Override
    @Transactional
    public Boolean deleteClient(Long clientId) {
        Client client = this.clientRepository.findById(clientId).
                orElseThrow(() -> new ClientNotFoundException(ExceptionMessage.CLIENT_NOT_FOUND.getMessage()));
        this.clientRepository.delete(client);
        log.info("Se elimina usuario con id: {}", clientId);
        return true;
    }
}
