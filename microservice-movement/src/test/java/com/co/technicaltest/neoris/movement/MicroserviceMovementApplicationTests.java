package com.co.technicaltest.neoris.movement;

import com.co.technicaltest.neoris.movement.models.dto.MovementResponseDTO;
import com.co.technicaltest.neoris.movement.services.impl.MovementServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureWebTestClient
class MicroserviceMovementApplicationTests {

    private final WebTestClient webTestClient;

    private final MovementServiceImpl movementService;

    private static final Long MOVEMENT_ID = 2L;


    private final String BASE_URL = "/api/v1/movimientos";

    @Autowired
    public MicroserviceMovementApplicationTests(WebTestClient webTestClient, MovementServiceImpl movementService) {
        this.webTestClient = webTestClient;
        this.movementService = movementService;
    }

    @Test
    void test_list_all_movements() {
        webTestClient.get()
                .uri(BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(MovementResponseDTO.class)
                .consumeWith(response -> {
                    List<MovementResponseDTO> responseDTOS = response.getResponseBody();
                    Assertions.assertTrue(responseDTOS.size() > 0);
                });

    }

    @Test
    void test_find_by_id_movement() {

        MovementResponseDTO responseDTO = this.movementService.findAll().blockFirst();

        assert responseDTO != null;

        webTestClient.get()
                .uri(BASE_URL.concat("/{id}"), Collections.singletonMap("id", responseDTO.id()))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isNotEmpty();
    }


}
