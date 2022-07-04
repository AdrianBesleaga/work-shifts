package xyz.adrianweb.workshifts.core.validation.exceptions;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationMessages {

    public static final String WORKER_NAME_EMPTY = "Worker name is empty";
    public static final String SHIFTS_MORE_THAN_1_PER_DAY = "The worker is not allowed to have 2 shifts in the same day";
    public static final String SHIFT_IS_NULL = "The work shift is null";
    public static final String SHIFT_PERIOD_DOES_NOT_MATCH = "The shift should be 8 hours";
    public static final String SHIFT_IS_OUTSIDE_OF_KNOWN_WORKING_DAY_HOURS = "This shift does not exist for the current year";
}
