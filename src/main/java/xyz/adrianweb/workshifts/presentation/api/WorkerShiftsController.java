package xyz.adrianweb.workshifts.presentation.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.adrianweb.workshifts.core.domain.WorkShift;
import xyz.adrianweb.workshifts.core.domain.Worker;
import xyz.adrianweb.workshifts.core.ports.IWorkerShiftsUsecase;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WorkerShiftsController {

    private final IWorkerShiftsUsecase iWorkerShiftsUsecase;

    @GetMapping("/shifts/{workerName}")
    public Flux<WorkShift> getWorkerShifts(@PathVariable String workerName) {
        Worker worker = Worker.builder().name(workerName).build();
        return iWorkerShiftsUsecase.getWorkShiftsByWorker(worker);
    }

    @PostMapping("/workers")
    public Mono<Worker> createWorker(@RequestBody Worker worker) {
        return iWorkerShiftsUsecase.createWorker(worker);
    }

    @PutMapping("/shifts/{workerName}")
    public Flux<WorkShift> addWorkerToShift(@PathVariable String workerName, @RequestBody WorkShift workShift) {
        Worker worker = Worker.builder().name(workerName).build();
        return iWorkerShiftsUsecase.addWorkerToShift(worker, workShift);
    }
}
