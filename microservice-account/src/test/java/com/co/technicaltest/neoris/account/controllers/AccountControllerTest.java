package com.co.technicaltest.neoris.account.controllers;

import com.co.technicaltest.neoris.account.exceptions.handler.AccountGlobalExceptionHandler;
import com.co.technicaltest.neoris.account.mappers.AccountMapper;
import com.co.technicaltest.neoris.account.models.Client;
import com.co.technicaltest.neoris.account.models.dto.AccountDTO;
import com.co.technicaltest.neoris.account.models.dto.AccountResponseDTO;
import com.co.technicaltest.neoris.account.models.entity.Account;
import com.co.technicaltest.neoris.account.models.entity.AccountClient;
import com.co.technicaltest.neoris.account.models.entity.AccountType;
import com.co.technicaltest.neoris.account.services.AccountServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import domain.models.enums.BankAccountType;
import domain.models.enums.Gender;
import domain.utils.GenerateRamdomAccountNumber;
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

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest
@ContextConfiguration(classes = AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountServiceImpl accountService;

    @Spy
    private AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);

    @Autowired
    private ObjectMapper objectMapper;

    private AccountDTO saveAccount;

    private AccountResponseDTO accountResponseDTO;

    private Account account;

    private Client client;

    private static final Long ACCAUNT_ID = 3L;
    private static final Long INCORRECT_ACCAUNT_ID = 100L;
    private static final Long CLEINT_ID = 2L;
    private static final Long ACCOUNT_TYPE_ID = 1L;


    private static final String BASE_URL = "/api/v1/cuentas";

    @BeforeEach
    public void setUp() throws Exception {

        MockitoAnnotations.openMocks(this);

        var clientController = new AccountController(accountService);

        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        var converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(clientController)
                .setControllerAdvice(new AccountGlobalExceptionHandler())
                .setMessageConverters(converter)
                .build();


        AccountType accountType = new AccountType(BankAccountType.SAVINGS_ACCOUNT);
        accountType.setId(ACCOUNT_TYPE_ID);

        this.saveAccount = new AccountDTO(BankAccountType.SAVINGS_ACCOUNT, new BigDecimal("4343.6"), true, CLEINT_ID);

        this.account = this.accountMapper.accountDtoToAccount(this.saveAccount);
        this.account.setAccountNumber(GenerateRamdomAccountNumber.generateBankAccountNumber());
        this.account.setAccountType(accountType);
        this.account.setAccountClient(new AccountClient(CLEINT_ID));
        this.account.setId(ACCAUNT_ID);

        this.client = new Client();
        this.client.setId(CLEINT_ID);
        this.client.setAddress("CL 33 - TB 4");
        this.client.setAge(23);
        this.client.setStatus(true);
        this.client.setGender(Gender.MALE);
        this.client.setIdentification("35674565");
        this.client.setName("John Doe");
        this.client.setPhone("+34 343225");

        this.account.setClient(this.client);


        this.accountResponseDTO = this.accountMapper.accountToAccountResponseDto(this.account);
    }


    @Test
    void test_create_account() throws Exception {
        when(this.accountService.saveAccount(any(AccountDTO.class))).thenReturn(this.accountResponseDTO);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .post(BASE_URL.concat("/"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(this.saveAccount)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();
    }

    @Test
    void find_client_by_id() throws Exception {

        when(this.accountService.findAccountById(ACCAUNT_ID)).thenReturn(Optional.of(this.accountResponseDTO));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get(BASE_URL.concat("/".concat(ACCAUNT_ID.toString())))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();
    }

    @Test
    void fail_find_client_by_id() throws Exception {

        when(this.accountService.findAccountById(INCORRECT_ACCAUNT_ID)).thenReturn(Optional.of(this.accountResponseDTO));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .get(BASE_URL.concat("/".concat(ACCAUNT_ID.toString())))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();
    }
}
