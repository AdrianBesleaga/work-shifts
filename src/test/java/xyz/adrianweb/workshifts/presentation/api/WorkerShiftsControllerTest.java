package xyz.adrianweb.workshifts.presentation.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import xyz.adrianweb.workshifts.core.domain.model.WorkShift;
import xyz.adrianweb.workshifts.core.domain.usecases.WorkerShiftsImpl;
import xyz.adrianweb.workshifts.infrastructure.database.IWorkerShiftsRepo;

import java.util.List;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@WebFluxTest(controllers = WorkerShiftsController.class)
@ExtendWith(SpringExtension.class)
@Import(WorkerShiftsImpl.class)
class WorkerShiftsControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    IWorkerShiftsRepo iWorkerShiftsRepo;

    @Test
    void shouldReturnShiftsForWorker() {
        String workerName = "test";

        Mockito.when(iWorkerShiftsRepo.findByWorker(workerName)).thenReturn(List.of(new WorkShift()));

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
}