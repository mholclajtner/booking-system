package com.example.booking.monitoring.tasks;

import com.example.booking.monitoring.entity.DeviceContext;
import com.example.booking.monitoring.entity.DeviceMetrics;
import lombok.extern.slf4j.Slf4j;

/**
 * A monitoring task that checks the memory usage of a device.
 * <p>
 * This class implements the {@link MonitoringTask} interface, focusing on assessing
 * the memory usage of a device. It logs the free memory available, simulating a check
 * that could involve more complex logic such as updating device metrics or alerting
 * when memory usage reaches critical levels.
 * </p>
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class MemoryUsageCheck implements MonitoringTask {

    private double lastKnownFreeMemory = -1; // Initialized to an invalid value to indicate "unknown"
    // Assuming you also want to track last known total memory
    private double lastKnownTotalMemory = -1; // Initialized similarly

    /**
     * Performs a memory usage check on the specified device context.
     * <p>
     * This method logs the current free memory of the device in megabytes. It serves as
     * a placeholder for more sophisticated memory usage monitoring, potentially including
     * updating the device's metrics or triggering warnings based on memory availability.
     * </p>
     *
     * @param context The {@link DeviceContext} for the device being checked, which includes
     *                the device's current memory metrics.
     */
    @Override
    public void performCheck(DeviceContext context) {
        DeviceMetrics metrics = context.metrics();
        // Assuming DeviceMetrics now includes a method to get total memory
        double totalMemory = metrics.totalMemory();
        double freeMemory = metrics.freeMemory();
        log.info("Checking memory usage for device: {}. Total memory: {} MB, Free memory: {} MB",
                context.deviceId(), totalMemory, freeMemory);
        // Add actual check logic and update metrics accordingly
    }

    @Override
    public String getStatus() {
        // Check if lastKnownTotalMemory and lastKnownFreeMemory have been set
        if (lastKnownTotalMemory >= 0 && lastKnownFreeMemory >= 0) {
            return String.format("Total Memory: %s MB, Free Memory: %s MB", lastKnownTotalMemory, lastKnownFreeMemory);
        } else {
            return "Memory Status: Unknown";
        }
    }

    @Override
    public String getName() {
        return "MemoryUsageCheck";
    }
}