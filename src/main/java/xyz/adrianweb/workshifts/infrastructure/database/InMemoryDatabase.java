package xyz.adrianweb.workshifts.infrastructure.database;

import org.springframework.stereotype.Component;
import xyz.adrianweb.workshifts.core.domain.model.WorkShift;
import xyz.adrianweb.workshifts.core.domain.model.Worker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryDatabase implements IWorkerShiftsRepo {

    private final Map<Worker, List<WorkShift>> memoryDb = new HashMap<>();

    @Override
    public Worker save(Worker worker) {
        return null;
    }

    @Override
    public WorkShift save(WorkShift workShift) {
        return null;
    }

    @Override
    public Worker findById(String id) {
        return null;
    }

    @Override
    public List<WorkShift> findByWorker(String workerName) {
        return null;
    }
}
