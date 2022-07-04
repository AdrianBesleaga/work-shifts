package xyz.adrianweb.workshifts.core.ports;

import reactor.core.publisher.Mono;
import xyz.adrianweb.workshifts.core.domain.WorkShift;
import xyz.adrianweb.workshifts.core.domain.Worker;

import java.util.List;

public interface IWorkerShiftsUsecase {
    List<WorkShift> getWorkShiftsByWorker(Worker worker);

    List<WorkShift> addWorkerToShift(Worker worker, WorkShift workShift);

    Mono<Worker> createWorker(Worker worker);
}
