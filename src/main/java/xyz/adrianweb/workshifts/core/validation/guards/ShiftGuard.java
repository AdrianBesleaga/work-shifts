package xyz.adrianweb.workshifts.core.validation.guards;

import xyz.adrianweb.workshifts.core.domain.WorkShift;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ShiftGuard extends BaseGuard<WorkShift> {
    public ShiftGuard(WorkShift workShift) {
        super(workShift);
    }

    public void againstMoreThanOneShiftPerDay(List<WorkShift> workShiftList, String message) {
        against(() -> isInvalidShiftForWorker(workShiftList), message);
    }

    public void againstNonExistingShift(List<WorkShift> workShifts, String message) {
        against(() -> againstNonExistingShifts(workShifts), message);
    }

    public void againstInvalidShift(String message) {
        against(this::againstInvalidShift, message);
    }

    private boolean againstInvalidShift() {
        return !this.value.getShiftEnd().minusHours(8)
                .isEqual(this.value.getShiftStart());
    }

    private boolean isInvalidShiftForWorker(List<WorkShift> workerShifts) {
        WorkShift newWorkShift = this.value;
        Optional<WorkShift> sameDayShift = workerShifts
                .stream()
                .filter(shift -> {
                    LocalDateTime shiftStart = shift.getShiftStart();
                    int dayOfYear = shiftStart.getDayOfYear();
                    int newWorkDay = newWorkShift.getShiftStart().getDayOfYear();
                    return dayOfYear == newWorkDay;
                })
                .findFirst();
        return sameDayShift.isPresent();
    }

    private boolean againstNonExistingShifts(List<WorkShift> workShifts) {
        return !workShifts.contains(this.value);
    }
}
