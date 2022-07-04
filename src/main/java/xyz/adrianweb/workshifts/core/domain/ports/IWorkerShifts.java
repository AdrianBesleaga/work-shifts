package xyz.adrianweb.workshifts.core.domain.ports;

import xyz.adrianweb.workshifts.core.domain.model.WorkShift;

import java.util.List;

public interface IWorkerShifts {
    List<WorkShift> getWorkShiftsByWorker(String workerName);
}
