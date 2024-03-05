package com.example.booking.monitoring.tasks;

import com.example.booking.monitoring.entity.DeviceContext;
import com.example.booking.monitoring.entity.DeviceMetrics;
import lombok.extern.slf4j.Slf4j;

/**
 * A monitoring task that checks the battery level of a device.
 * <p>
 * This class implements the {@link MonitoringTask} interface and provides functionality
 * for checking the battery level of a device. Upon execution, the task logs the current
 * battery level, which simulates an actual monitoring check. This could be extended to
 * include updating device metrics or triggering alerts if the battery level is below
 * a specified threshold.
 * </p>
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class BatteryLevelCheck implements MonitoringTask {

    private double lastKnownBatteryLevel = -1; // Initialized to an invalid value to indicate "unknown"

    /**
     * Performs a battery level check on the given device context.
     * <p>
     * This method logs the device's current battery level as a percentage. In a practical scenario,
     * this could involve more complex logic such as updating the device's metrics in a monitoring
     * system or triggering alerts if the battery level is critically low.
     * </p>
     * <p>
     * The simulated check currently logs the battery level for demonstration purposes.
     * The actual implementation would likely interact with device hardware or a monitoring service.
     * </p>
     *
     * @param context The {@link DeviceContext} representing the device to be checked.
     *                It contains the device's metrics, including its current battery level.
     */
    @Override
    public void performCheck(DeviceContext context) {
        DeviceMetrics metrics = context.metrics();
        lastKnownBatteryLevel = metrics.batteryLevel();
        log.info("Checking battery level for device: {}. Current level: {}%", context.deviceId(), lastKnownBatteryLevel);
        // Add actual check logic and update metrics accordingly
    }

    /**
     * Retrieves the last known battery level of the device.
     * <p>
     * This method returns a string describing the battery level status. If the last known battery
     * level has been set, it formats this value into a human-readable string. Otherwise, it
     * indicates that the battery level is unknown.
     * </p>
     *
     * @return A string describing the battery level status.
     */
    @Override
    public String getStatus() {
        return lastKnownBatteryLevel >= 0 ? String.format("Battery Level: %.2f%%", lastKnownBatteryLevel) : "Battery Level: Unknown";
    }

    /**
     * Provides the name of the monitoring task.
     * <p>
     * This method returns the simple name of the task, which can be used for logging or display purposes
     * within the monitoring system.
     * </p>
     *
     * @return The name of the task.
     */
    @Override
    public String getName() {
        return "BatteryLevelCheck";
    }
}