package xyz.adrianweb.workshifts.core.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.adrianweb.workshifts.core.domain.WorkShift;
import xyz.adrianweb.workshifts.core.domain.Worker;
import xyz.adrianweb.workshifts.core.ports.IWorkerShiftsUsecase;
import xyz.adrianweb.workshifts.core.validation.exceptions.ValidationMessages;
import xyz.adrianweb.workshifts.core.validation.guards.Text;
import xyz.adrianweb.workshifts.infrastructure.database.IWorkerShiftsRepo;

import java.util.List;

import static xyz.adrianweb.workshifts.core.validation.guards.Guard.guard;

@Service
@RequiredArgsConstructor
public class WorkerShiftsUsecase implements IWorkerShiftsUsecase {

    private final IWorkerShiftsRepo iWorkerShiftsRepo;

    @Override
    public Flux<WorkShift> getWorkShiftsByWorker(Worker worker) {
        guard(Text.of(worker.getName())).againstNullOrWhitespace(ValidationMessages.WORKER_NAME_EMPTY);
        return Flux.fromIterable(iWorkerShiftsRepo.getWorkShiftsByWorker(worker));
    }

    @Override
    public Flux<WorkShift> addWorkerToShift(Worker worker, WorkShift workShift) {
        guard(Text.of(worker.getName())).againstNullOrWhitespace(ValidationMessages.WORKER_NAME_EMPTY);
        guard(workShift).againstNull(ValidationMessages.SHIFT_IS_NULL);
        guard(workShift).againstInvalidShift(ValidationMessages.SHIFT_PERIOD_DOES_NOT_MATCH);

        List<WorkShift> workShifts = iWorkerShiftsRepo.getWorkShifts();
        guard(workShift).againstNonExistingShift(workShifts, ValidationMessages.SHIFT_IS_OUTSIDE_OF_KNOWN_WORKING_DAY_HOURS);

        List<WorkShift> workShiftsByWorker = iWorkerShiftsRepo.getWorkShiftsByWorker(worker);
        guard(workShift).againstMoreThanOneShiftPerDay(workShiftsByWorker, ValidationMessages.SHIFTS_MORE_THAN_1_PER_DAY);

        return Flux.fromIterable(iWorkerShiftsRepo.addWorkerToShift(worker, workShift));
    }

    @Override
    public Mono<Worker> createWorker(Worker worker) {
        return iWorkerShiftsRepo.upsert(worker);
    }
}
