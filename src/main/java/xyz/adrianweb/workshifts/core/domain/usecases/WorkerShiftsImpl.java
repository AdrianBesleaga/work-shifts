package xyz.adrianweb.workshifts.core.domain.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.adrianweb.workshifts.core.domain.model.WorkShift;
import xyz.adrianweb.workshifts.core.domain.ports.IWorkerShifts;
import xyz.adrianweb.workshifts.infrastructure.database.IWorkerShiftsRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkerShiftsImpl implements IWorkerShifts {

    private final IWorkerShiftsRepo iWorkerShiftsRepo;

    @Override
    public List<WorkShift> getWorkShiftsByWorker(String workerName) {
        return iWorkerShiftsRepo.findByWorker(workerName);
    }
}
