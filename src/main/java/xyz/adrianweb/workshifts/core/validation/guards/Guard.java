package xyz.adrianweb.workshifts.core.validation.guards;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Guard {
    public static TextGuard guard(Text value) {
        return new TextGuard(value);
    }
}
