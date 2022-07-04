package xyz.adrianweb.workshifts.presentation.api.mock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import xyz.adrianweb.workshifts.core.domain.WorkShift;
import xyz.adrianweb.workshifts.core.domain.Worker;
import xyz.adrianweb.workshifts.core.usecases.WorkerShiftsUsecase;
import xyz.adrianweb.workshifts.infrastructure.database.IWorkerShiftsRepo;
import xyz.adrianweb.workshifts.presentation.api.WorkerShiftsController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@WebFluxTest(controllers = WorkerShiftsController.class)
@ExtendWith(SpringExtension.class)
@Import({WorkerShiftsUsecase.class})
class WorkerShiftsControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    IWorkerShiftsRepo iWorkerShiftsRepo;

    @Test
    void shouldReturnShiftsForWorker() {
        String workerName = "test";
        Worker worker = Worker.builder().name(workerName).build();

        Mockito
                .when(iWorkerShiftsRepo.getWorkShiftsByWorker(worker))
                .thenReturn(List.of(new WorkShift()));

        webTestClient
                .get()
                .uri("/api/shifts/{workerName}", workerName)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1);
    }

    @Test
    void shouldCreateWorker() {
        String workerName = "test";
        Worker worker = Worker.builder().name(workerName).build();

        Mockito
                .when(iWorkerShiftsRepo.upsert(worker))
                .thenReturn(Mono.just(worker));

        webTestClient
                .post()
                .uri("/api/workers")
                .body(BodyInserters.fromValue(worker))
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.name").isEqualTo(workerName);
    }

    @Test
    void shouldAssignWorkerToShift() {
        //Given
        String workerName = "test";
        Worker worker = Worker.builder().name(workerName).build();
        LocalDateTime shiftStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime shiftEnd = shiftStart.plusHours(8);
        WorkShift workShift = WorkShift.builder()
                .shiftStart(shiftStart)
                .shiftEnd(shiftEnd)
                .build();

        //When
        //The shift exists
        Mockito
                .when(iWorkerShiftsRepo.getWorkShifts())
                .thenReturn(List.of(workShift));

        //And the worker is added to shift
        Mockito
                .when(iWorkerShiftsRepo.addWorkerToShift(worker, workShift))
                .thenReturn(List.of(workShift));

        //Then
        webTestClient
                .put()
                .uri("/api/shifts/{workerName}", workerName)
                .body(BodyInserters.fromValue(workShift))
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.[0].shiftStart").isEqualTo(shiftStart.format(DateTimeFormatter.ISO_DATE_TIME))
                .jsonPath("$.[0].shiftEnd").isEqualTo(shiftEnd.format(DateTimeFormatter.ISO_DATE_TIME));
    }
}