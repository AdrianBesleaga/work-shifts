package xyz.adrianweb.workshifts.core.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.adrianweb.workshifts.core.domain.model.WorkShift;
import xyz.adrianweb.workshifts.core.domain.ports.IWorkerShifts;
import xyz.adrianweb.workshifts.core.validation.exceptions.ValidationMessages;
import xyz.adrianweb.workshifts.core.validation.guards.Text;
import xyz.adrianweb.workshifts.infrastructure.database.IWorkerShiftsRepo;

import java.util.List;

import static xyz.adrianweb.workshifts.core.validation.guards.Guard.guard;

@Service
@RequiredArgsConstructor
public class WorkerShiftsUsecase implements IWorkerShifts {

    private final IWorkerShiftsRepo iWorkerShiftsRepo;

    @Override
    public List<WorkShift> getWorkShiftsByWorker(String workerName) {
        guard(Text.of(workerName)).againstNullOrWhitespace(ValidationMessages.WORKER_NAME_EMPTY);
        return iWorkerShiftsRepo.findByWorker(workerName);
    }
}
