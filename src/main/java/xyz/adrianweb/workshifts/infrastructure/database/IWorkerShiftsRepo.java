package xyz.adrianweb.workshifts.infrastructure.database;

import xyz.adrianweb.workshifts.core.domain.model.WorkShift;
import xyz.adrianweb.workshifts.core.domain.model.Worker;

import java.util.List;

public interface IWorkerShiftsRepo {
    Worker save(Worker worker);

    WorkShift save(WorkShift workShift);

    Worker findById(String id);

    List<WorkShift> findByWorker(String workerName);

    WorkShift addWorkerToShift(Worker worker, WorkShift workShift);
}
