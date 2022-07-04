package xyz.adrianweb.workshifts.core.ports;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.adrianweb.workshifts.core.domain.WorkShift;
import xyz.adrianweb.workshifts.core.domain.Worker;

public interface IWorkerShiftsUsecase {
    Flux<WorkShift> getWorkShiftsByWorker(Worker worker);

    Flux<WorkShift> addWorkerToShift(Worker worker, WorkShift workShift);

    Mono<Worker> createWorker(Worker worker);
}
