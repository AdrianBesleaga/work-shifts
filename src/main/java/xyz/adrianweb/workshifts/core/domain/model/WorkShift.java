package xyz.adrianweb.workshifts.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkShift {
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
}
