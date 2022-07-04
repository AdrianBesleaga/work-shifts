package xyz.adrianweb.workshifts.infrastructure.database;

import lombok.val;
import org.springframework.stereotype.Component;
import xyz.adrianweb.workshifts.core.domain.model.WorkShift;
import xyz.adrianweb.workshifts.core.domain.model.Worker;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryDatabase implements IWorkerShiftsRepo {

    private static final Map<Integer, Integer> SHIFT_HOURS = Map.of(0, 8, 8, 16, 16, 24);
    private final Map<Worker, List<WorkShift>> workerAssignedShifts = new HashMap<>();
    private final Map<LocalDate, List<WorkShift>> shifts = initializeCurrentYearShifts();

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

    @Override
    /* todo guards - validator
     *   never works more than 8 hours (1 shift x day)
     */
    public WorkShift addWorkerToShift(Worker worker, WorkShift workShift) {
        return null;
    }


    private Map<LocalDate, List<WorkShift>> initializeCurrentYearShifts() {
        Map<LocalDate, List<WorkShift>> shifts = new HashMap<>();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.of(2022, Month.DECEMBER, 31);

        startDate.datesUntil(endDate)
                .forEach(day -> {
                    val dayShifts = new ArrayList<WorkShift>();
                    val startOfDay = day.atStartOfDay();
                    SHIFT_HOURS.forEach((start, end) -> {
                        val workShift = WorkShift.builder()
                                .shiftStart(startOfDay.plusHours(start))
                                .shiftEnd(startOfDay.plusHours(end))
                                .build();
                        dayShifts.add(workShift);
                    });
                    shifts.put(day, dayShifts);
                });
        return shifts;
    }
}
