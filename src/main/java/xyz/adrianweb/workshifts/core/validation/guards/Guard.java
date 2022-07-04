package xyz.adrianweb.workshifts.core.validation.guards;

import lombok.experimental.UtilityClass;
import xyz.adrianweb.workshifts.core.domain.WorkShift;

@UtilityClass
public class Guard {
    public static TextGuard guard(Text value) {
        return new TextGuard(value);
    }

    public static ShiftGuard guard(WorkShift workShift) {
        return new ShiftGuard(workShift);
    }
}
