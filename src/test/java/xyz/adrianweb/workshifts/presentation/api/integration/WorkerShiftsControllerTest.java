package xyz.adrianweb.workshifts.presentation.api.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import xyz.adrianweb.workshifts.core.domain.WorkShift;
import xyz.adrianweb.workshifts.core.usecases.WorkerShiftsUsecase;
import xyz.adrianweb.workshifts.infrastructure.database.InMemoryDatabase;
import xyz.adrianweb.workshifts.presentation.api.WorkerShiftsController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@WebFluxTest(controllers = WorkerShiftsController.class)
@ExtendWith(SpringExtension.class)
@Import({WorkerShiftsUsecase.class, InMemoryDatabase.class})
class WorkerShiftsControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void shouldNotAllowMoreThanOneShiftPerDay() {
        String workerName = "test";
        LocalDateTime shiftStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime shiftEnd = shiftStart.plusHours(8);

        WorkShift workShift_1 = WorkShift.builder()
                .shiftStart(shiftStart)
                .shiftEnd(shiftEnd)
                .build();

        WorkShift workShift_2 = WorkShift.builder()
                .shiftStart(shiftStart.plusHours(8))
                .shiftEnd(shiftEnd.plusHours(8))
                .build();

        webTestClient
                .put()
                .uri("/api/shifts/{workerName}", workerName)
                .body(BodyInserters.fromValue(workShift_1))
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.[0].shiftStart").isEqualTo(shiftStart.format(DateTimeFormatter.ISO_DATE_TIME))
                .jsonPath("$.[0].shiftEnd").isEqualTo(shiftEnd.format(DateTimeFormatter.ISO_DATE_TIME));

        webTestClient
                .put()
                .uri("/api/shifts/{workerName}", workerName)
                .body(BodyInserters.fromValue(workShift_2))
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectHeader()
                .contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.error")
                .isEqualTo("The worker is not allowed to have 2 shifts in the same day");
    }

    @Test
    void shouldNotAllowShiftsWithMoreThan8Hours() {
        String workerName = "test";
        LocalDateTime shiftStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        int workHours = 10;
        LocalDateTime shiftEnd = shiftStart.plusHours(workHours);

        WorkShift workShift = WorkShift.builder()
                .shiftStart(shiftStart)
                .shiftEnd(shiftEnd)
                .build();

        webTestClient
                .put()
                .uri("/api/shifts/{workerName}", workerName)
                .body(BodyInserters.fromValue(workShift))
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectHeader()
                .contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.error").isEqualTo("The shift should be 8 hours");
    }
}
