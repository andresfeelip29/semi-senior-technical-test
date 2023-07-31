package com.co.technicaltest.neoris.client.services;

import com.co.technicaltest.neoris.client.client.AccountRestClient;
import com.co.technicaltest.neoris.client.exceptions.ClientNotFoundException;
import com.co.technicaltest.neoris.client.mappers.ClientMapper;
import com.co.technicaltest.neoris.client.models.dto.ClientDTO;
import com.co.technicaltest.neoris.client.models.dto.ClientQueryDTO;
import com.co.technicaltest.neoris.client.models.dto.ClientResponseDTO;
import com.co.technicaltest.neoris.client.models.entity.Client;
import com.co.technicaltest.neoris.client.models.entity.ClientAccount;
import com.co.technicaltest.neoris.client.repositories.ClientAccountRepository;
import com.co.technicaltest.neoris.client.repositories.ClientRepository;
import domain.exception.client.ClientAccountNotFoundException;
import domain.models.ClientAccountQueryDTO;
import domain.models.enums.ExceptionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    private final AccountRestClient accountRestClient;

    private final ClientAccountRepository clientAccountRepository;

    public ClientServiceImpl(ClientRepository clientRepository,
                             ClientMapper clientMapper,
                             AccountRestClient accountRestClient,
                             ClientAccountRepository clientAccountRepository) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.accountRestClient = accountRestClient;
        this.clientAccountRepository = clientAccountRepository;
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
                    .map(ClientAccount::getAccountId)
                    .toList();
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
                .orElseThrow(() -> new ClientNotFoundException(String.format(ExceptionMessage.CLIENT_NOT_FOUND.getMessage(), clientId))));
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
    public void saveClientAccountFromMicroserviceClient(ClientAccountQueryDTO clientAccountQueryDTO) {
        log.info("Se realiza consulta desde microservicio de cuentas, para asociar cuenta a usuario : {}", clientAccountQueryDTO);
        ClientAccount clientAccount = null;
        if (!Objects.isNull(clientAccountQueryDTO)) {
            Client client = this.clientRepository.findById(clientAccountQueryDTO.clientId()).
                    orElseThrow(() -> new ClientNotFoundException(String.format(ExceptionMessage.CLIENT_NOT_FOUND.getMessage(),
                            clientAccountQueryDTO.clientId())));
            clientAccount = new ClientAccount();
            clientAccount.setAccountId(clientAccountQueryDTO.accountId());
            client.addClientUser(clientAccount);
            this.clientRepository.save(client);
            log.info("Se realiza asignacion de cuenta con id: {} a usuario con id : {}", clientAccountQueryDTO.accountId(), clientAccountQueryDTO.clientId());
        }
    }

    @Override
    @Transactional
    public ClientResponseDTO updateClient(ClientDTO clientDTO, Long clientId) {
        Client client = this.clientRepository.findById(clientId).
                orElseThrow(() -> new ClientNotFoundException(String.format(ExceptionMessage.CLIENT_NOT_FOUND.getMessage(),
                        clientId)));
        client = this.clientRepository.save(this.clientMapper.updateClientDtoToClient(client, clientDTO));
        log.info("Se actualiza usuario con id: {}", client.getId());
        return this.clientMapper.clientToClientResponseDto(client);
    }

    @Override
    @Transactional
    public Boolean deleteClient(Long clientId) {
        Client client = this.clientRepository.findById(clientId).
                orElseThrow(() -> new ClientNotFoundException(String.format(ExceptionMessage.CLIENT_NOT_FOUND.getMessage(),
                        clientId)));
        this.clientRepository.delete(client);
        log.info("Se elimina usuario con id: {}", clientId);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClientQueryDTO> findClientByIdFromMicroserviceAccount(Long clientId) {
        log.info("Se realiza consulta desde microservicio de cuentas, a cliente con id: {}", clientId);
        return Optional.ofNullable(this.clientRepository.findById(clientId)
                .map(this.clientMapper::clientToClientQueryDto)
                .orElseThrow(() -> new ClientNotFoundException(String.format(ExceptionMessage.CLIENT_NOT_FOUND.getMessage(), clientId))));
    }

    @Override
    @Transactional
    public void deleteAccountClientFromMicroserviceAccount(Long accountId) {
        log.info("Se inicia proceso de eliminar de cuenta con id: {}, desde microservicio de cuentas", accountId);
        ClientAccount clientAccount = this.clientAccountRepository.findById(accountId).
                orElseThrow(() -> new ClientAccountNotFoundException(String.format(ExceptionMessage.ACCOUNT_ASSOCIATED_TO_CLIENT_NO_FOUND.getMessage(),
                        accountId)));
        this.clientAccountRepository.delete(clientAccount);
        log.info("Se elimina cuenta con id: {}, asociada al usuario con id: {}", accountId, clientAccount.getId());
    }
}
