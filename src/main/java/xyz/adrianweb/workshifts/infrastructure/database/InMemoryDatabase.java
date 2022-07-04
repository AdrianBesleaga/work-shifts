package xyz.adrianweb.workshifts.infrastructure.database;

import lombok.val;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;
import xyz.adrianweb.workshifts.core.domain.WorkShift;
import xyz.adrianweb.workshifts.core.domain.Worker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class InMemoryDatabase implements IWorkerShiftsRepo {

    private static final List<Tuple2<Integer, Integer>> SHIFT_HOURS =
            List.of(Tuples.of(0, 8), Tuples.of(8, 16), Tuples.of(16, 24));
    private final Map<Worker, List<WorkShift>> workerAssignedShifts = new HashMap<>();
    private final List<WorkShift> workShifts = initializeCurrentYearShifts();

    public List<WorkShift> getWorkShifts() {
        return workShifts;
    }

    @Override
    public Mono<Worker> upsert(Worker worker) {
        if (!workerAssignedShifts.containsKey(worker)) {
            workerAssignedShifts.put(worker, new ArrayList<>());
        }
        return Mono.just(worker);
    }

    @Override
    public List<WorkShift> getWorkShiftsByWorker(Worker worker) {
        if (!workerAssignedShifts.containsKey(worker)) {
            workerAssignedShifts.put(worker, new ArrayList<>());
        }
        return workerAssignedShifts.get(worker);
    }

    @Override
    public List<WorkShift> addWorkerToShift(Worker worker, WorkShift workShift) {
        if (!workerAssignedShifts.containsKey(worker)) {
            workerAssignedShifts.put(worker, new ArrayList<>());
        }
        List<WorkShift> workShifts = workerAssignedShifts.get(worker);
        workShifts.add(workShift);
        return workShifts;
    }


    private List<WorkShift> initializeCurrentYearShifts() {
        List<WorkShift> shifts = new ArrayList<>();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(1);
        startDate.datesUntil(endDate).forEach(buildDailyShifts(shifts));
        return shifts;
    }

    private Consumer<LocalDate> buildDailyShifts(List<WorkShift> shifts) {
        return day -> {
            val startOfDay = day.atStartOfDay();
            SHIFT_HOURS.forEach(shiftHours -> {
                val workShift = WorkShift.builder()
                        .shiftStart(startOfDay.plusHours(shiftHours.getT1()))
                        .shiftEnd(startOfDay.plusHours(shiftHours.getT2()))
                        .build();
                shifts.add(workShift);
            });
        };
    }
}
