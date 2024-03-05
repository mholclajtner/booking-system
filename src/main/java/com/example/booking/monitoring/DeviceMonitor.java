package com.example.booking.monitoring;

import com.example.booking.monitoring.entity.DeviceContext;
import com.example.booking.monitoring.entity.DeviceMetrics;
import com.example.booking.monitoring.tasks.MonitoringTask;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

/**
 * Manages and executes monitoring tasks for a single device.
 * <p>
 * This class is responsible for orchestrating the execution of a collection of monitoring tasks,
 * each of which performs checks on various aspects of a device's state, such as battery level or memory usage.
 * </p>
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Getter
@AllArgsConstructor
public class DeviceMonitor {
    private DeviceContext context;
    private final List<MonitoringTask> tasks;

    /**
     * Starts the execution of monitoring tasks for the device in parallel.
     * <p>
     * This method utilizes a {@link StructuredTaskScope} to execute each monitoring task in its own thread,
     * allowing for concurrent monitoring activities. It waits for all tasks to complete and handles any
     * exceptions that occur during execution, including interrupting the thread if an interruption occurs.
     * </p>
     */
    public void startMonitoring() {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            tasks.forEach(task -> scope.fork(() -> {
                task.performCheck(context);
                return null;
            }));
            scope.join(); // Waits for all tasks to complete
            scope.throwIfFailed(); // Throws an exception if any tasks failed
        } catch (InterruptedException | ExecutionException e) {
            // Handle exceptions, possibly log them, or rethrow if needed
            Thread.currentThread().interrupt();
            log.error(STR."Monitoring tasks for device \{context.deviceId()} were interrupted or failed: \{e.getMessage()}");
            throw new RuntimeException(STR."Monitoring tasks for device \{context.deviceId()} were interrupted or failed", e);
        }
    }

    public String getStatus() {
        StringBuilder statusBuilder = new StringBuilder();
        statusBuilder.append("Device ID: ").append(context.deviceId()).append("\n");

        for (MonitoringTask task : tasks) {
            try {
                String taskStatus = task.getStatus();
                if (taskStatus == null || taskStatus.isEmpty()) {
                    log.warn(STR."Task returned null or empty status for device: \{context.deviceId()}");
                } else {
                    // Appending the task status and new line character in one go for efficiency
                    statusBuilder.append(taskStatus).append("\n");
                }
            } catch (Exception e) {
                // Log the error with more contextual information, such as device ID and task name
                log.error(STR."Exception while getting status from task: \{task.getName()} for device: \{context.deviceId()}", e);
            }
        }

        return statusBuilder.toString();
    }

    public void updateMetrics(DeviceMetrics newMetrics) {
        // Assuming DeviceContext is mutable. If it's a record, you'll need to replace it entirely.
        this.context = new DeviceContext(this.context.deviceId(), newMetrics);
        // If you have tasks that depend on the latest metrics, you might need to update them as well.
    }
}