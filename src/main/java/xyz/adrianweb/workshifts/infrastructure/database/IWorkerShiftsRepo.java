package xyz.adrianweb.workshifts.infrastructure.database;

import reactor.core.publisher.Mono;
import xyz.adrianweb.workshifts.core.domain.WorkShift;
import xyz.adrianweb.workshifts.core.domain.Worker;

import java.util.List;

public interface IWorkerShiftsRepo {
    Mono<Worker> upsert(Worker worker);

    List<WorkShift> getWorkShiftsByWorker(Worker worker);

    List<WorkShift> addWorkerToShift(Worker worker, WorkShift workShift);

    List<WorkShift> getWorkShifts();
}
