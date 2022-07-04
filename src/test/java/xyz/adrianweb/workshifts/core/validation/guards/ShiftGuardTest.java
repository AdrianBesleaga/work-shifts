package xyz.adrianweb.workshifts.core.validation.guards;

import org.junit.jupiter.api.Test;
import xyz.adrianweb.workshifts.core.domain.WorkShift;
import xyz.adrianweb.workshifts.core.validation.exceptions.ValidationException;
import xyz.adrianweb.workshifts.core.validation.exceptions.ValidationMessages;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShiftGuardTest {

    @Test
    void againstMoreThanOneShiftPerDay() {
        LocalDateTime now = LocalDateTime.now();
        int workHours = 10;
        WorkShift workShift = WorkShift.builder()
                .shiftStart(now)
                .shiftEnd(now.plusHours(workHours))
                .build();
        ShiftGuard shiftGuard = new ShiftGuard(workShift);

        Exception expectedEx = assertThrows(ValidationException.class, () ->
                shiftGuard.againstMoreThanOneShiftPerDay(List.of(workShift),
                        ValidationMessages.SHIFTS_MORE_THAN_1_PER_DAY)
        );
        assertEquals(expectedEx.getMessage(), ValidationMessages.SHIFTS_MORE_THAN_1_PER_DAY);
    }

    @Test
    void againstNonExistingShift() {
        LocalDateTime now = LocalDateTime.now();
        int workHours = 8;
        WorkShift workShift = WorkShift.builder()
                .shiftStart(now)
                .shiftEnd(now.plusHours(workHours))
                .build();
        ShiftGuard shiftGuard = new ShiftGuard(workShift);

        Exception expectedEx = assertThrows(ValidationException.class, () ->
                shiftGuard.againstNonExistingShift(List.of(),
                        ValidationMessages.SHIFT_IS_OUTSIDE_OF_KNOWN_WORKING_DAY_HOURS)
        );
        assertEquals(expectedEx.getMessage(), ValidationMessages.SHIFT_IS_OUTSIDE_OF_KNOWN_WORKING_DAY_HOURS);
    }

    @Test
    void againstInvalidShift() {
        LocalDateTime now = LocalDateTime.now();
        int workHours = 10;
        WorkShift workShift = WorkShift.builder()
                .shiftStart(now)
                .shiftEnd(now.plusHours(workHours))
                .build();
        ShiftGuard shiftGuard = new ShiftGuard(workShift);

        Exception expectedEx = assertThrows(ValidationException.class, () ->
                shiftGuard.againstInvalidShift(ValidationMessages.SHIFT_PERIOD_DOES_NOT_MATCH)
        );
        assertEquals(expectedEx.getMessage(), ValidationMessages.SHIFT_PERIOD_DOES_NOT_MATCH);
    }
}