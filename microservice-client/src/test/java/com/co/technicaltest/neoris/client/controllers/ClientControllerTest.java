package com.co.technicaltest.neoris.client.controllers;


import com.co.technicaltest.neoris.client.exceptions.handler.ClientGlobalExceptionHandler;
import com.co.technicaltest.neoris.client.mappers.ClientMapper;
import com.co.technicaltest.neoris.client.models.dto.ClientDTO;
import com.co.technicaltest.neoris.client.models.dto.ClientResponseDTO;
import com.co.technicaltest.neoris.client.models.entity.Client;
import com.co.technicaltest.neoris.client.services.ClientServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import domain.models.enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest
@ContextConfiguration(classes = ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientServiceImpl clientService;

    @Spy
    private ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);

    @Autowired
    private ObjectMapper objectMapper;

    private ClientDTO saveClient;

    private ClientResponseDTO clientResponseDTO;

    private Client client;

    private static final Long CLIENT_ID = 3L;


    private static final String BASE_URL = "/api/v1/clientes";

    @BeforeEach
    public void setUp() throws Exception {

        MockitoAnnotations.openMocks(this);

        var clientController = new ClientController(clientService);

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        var converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(clientController)
                .setControllerAdvice(new ClientGlobalExceptionHandler())
                .setMessageConverters(converter)
                .build();

        this.saveClient = new ClientDTO("Julian", "223@XL", Gender.MALE,
                18, "35464235", "CLL 23 KR 12-645", "+54 23452535", true);

        this.client = this.clientMapper.clientDtoToClient(this.saveClient);
        this.client.setId(CLIENT_ID);

        this.clientResponseDTO = this.clientMapper.clientToClientResponseDto(this.client);
    }

    @Test
    void test_save_client() throws Exception {

        when(this.clientService.saveClient(any(ClientDTO.class))).thenReturn(this.clientResponseDTO);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .post(BASE_URL.concat("/"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.saveClient)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();
    }


    @Test
    void find_client_by_id() throws Exception {

        when(this.clientService.findClientById(CLIENT_ID)).thenReturn(Optional.of(this.clientResponseDTO));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get(BASE_URL.concat("/".concat(CLIENT_ID.toString())))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();
    }

    @Test
    void delte_client_by_id() throws Exception {

        when(this.clientService.deleteClient(CLIENT_ID)).thenReturn(true);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .delete(BASE_URL.concat("/".concat(CLIENT_ID.toString())))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();
    }
}
