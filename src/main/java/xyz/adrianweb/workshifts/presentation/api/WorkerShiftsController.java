package xyz.adrianweb.workshifts.presentation.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.adrianweb.workshifts.core.domain.model.WorkShift;
import xyz.adrianweb.workshifts.core.domain.ports.IWorkerShifts;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WorkerShiftsController {

    private final IWorkerShifts iWorkerShifts;

    @GetMapping("/shifts/{workerName}")
    public List<WorkShift> getWorkerShifts(@PathVariable String workerName) {
        return iWorkerShifts.getWorkShiftsByWorker(workerName);
    }
}
