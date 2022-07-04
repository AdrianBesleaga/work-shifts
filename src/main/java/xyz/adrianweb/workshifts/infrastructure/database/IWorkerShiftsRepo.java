package xyz.adrianweb.workshifts.infrastructure.database;

import xyz.adrianweb.workshifts.core.domain.model.WorkShift;
import xyz.adrianweb.workshifts.core.domain.model.Worker;

import java.util.List;

public interface IWorkerShiftsRepo {
    public Worker save(Worker worker);

    public WorkShift save(WorkShift workShift);

    public Worker findById(String id);

    public List<WorkShift> findByWorker(String workerName);
}
