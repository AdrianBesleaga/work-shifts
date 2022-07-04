package xyz.adrianweb.workshifts.core.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WorkShift {
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
}
