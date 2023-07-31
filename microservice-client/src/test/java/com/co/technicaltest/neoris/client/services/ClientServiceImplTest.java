package com.co.technicaltest.neoris.client.services;

import com.co.technicaltest.neoris.client.client.AccountRestClient;
import com.co.technicaltest.neoris.client.exceptions.ClientNotFoundException;
import com.co.technicaltest.neoris.client.mappers.ClientMapper;
import com.co.technicaltest.neoris.client.models.dto.ClientDTO;
import com.co.technicaltest.neoris.client.models.dto.ClientResponseDTO;
import com.co.technicaltest.neoris.client.models.entity.Client;
import com.co.technicaltest.neoris.client.repositories.ClientRepository;
import domain.models.enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Spy
    private ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);

    @Mock
    private AccountRestClient accountRestClient;

    @InjectMocks
    private ClientServiceImpl clientService;

    private ClientDTO clientDTO;
    private ClientDTO clientDTOUpdate;
    private Client client;
    private Client clientUpdate;
    private static final Long CLIENT_ID = 3L;
    private static final Long CLIENT_INCORRET_ID = 4L;

    @BeforeEach
    void init_method_test() {
        this.clientDTO = new ClientDTO("Julian", "223@XL", Gender.MALE,
                18, "35464235", "CLL 23 KR 12-645", "+54 23452535", true);

        this.clientDTOUpdate = new ClientDTO("Clara", "@+233", Gender.FEMALE,
                22, "3424532", "CLL 108 KR 34-34", "+14 45352253", true);

        this.client = this.clientMapper.clientDtoToClient(this.clientDTO);
        this.client.setId(CLIENT_ID);


        this.clientUpdate = this.clientMapper.updateClientDtoToClient(this.client, this.clientDTOUpdate);
    }

    @Test
    void find_clien_by_id() {

        when(this.clientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(this.client));
        Optional<ClientResponseDTO> clientOptional = this.clientService.findClientById(CLIENT_ID);
        assertTrue(clientOptional.isPresent());

        ClientResponseDTO result = clientOptional.get();
        assertNotNull(result);
        assertEquals(CLIENT_ID, result.id());

        ClientNotFoundException clientNotFoundException = assertThrows(ClientNotFoundException.class,
                () -> this.clientService.findClientById(CLIENT_INCORRET_ID));
        assertEquals(String.format("No existe cliente registrado en sistema con id: %d!", CLIENT_INCORRET_ID), clientNotFoundException.getMessage());
    }

    @Test
    void save_client() {
        when(this.clientRepository.save(any(Client.class))).thenReturn(this.client);
        assertNotNull(this.clientService.saveClient(this.clientDTO));
    }

    @Test
    void update_client() {

        when(this.clientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(this.client));
        when(this.clientRepository.save(any(Client.class))).thenReturn(this.clientUpdate);

        ClientResponseDTO result = this.clientService.updateClient(this.clientDTOUpdate, this.client.getId());
        assertNotNull(result);

        ClientNotFoundException clientNotFoundException = assertThrows(ClientNotFoundException.class,
                () -> this.clientService.updateClient(this.clientDTOUpdate, CLIENT_INCORRET_ID));
        assertEquals(String.format("No existe cliente registrado en sistema con id: %d!", CLIENT_INCORRET_ID), clientNotFoundException.getMessage());
    }

    @Test
    void delete_client() {
        when(this.clientRepository.findById(CLIENT_ID)).thenReturn(Optional.of(this.client));
        doNothing().when(this.clientRepository).delete(any(Client.class));

        assertTrue(this.clientService.deleteClient(CLIENT_ID));

        ClientNotFoundException clientNotFoundException = assertThrows(ClientNotFoundException.class,
                () -> this.clientService.deleteClient(CLIENT_INCORRET_ID));
        assertEquals(String.format("No existe cliente registrado en sistema con id: %d!", CLIENT_INCORRET_ID), clientNotFoundException.getMessage());

    }
}