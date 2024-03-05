package com.example.booking.monitoring.tasks;

import com.example.booking.monitoring.entity.DeviceContext;

/**
 * Interface for defining monitoring tasks for devices.
 * <p>
 * This interface specifies the structure for monitoring tasks that can be performed on devices.
 * Implementing classes are expected to define specific monitoring actions, such as checking battery level,
 * memory usage, or system load, by overriding the {@code performCheck} method.
 * </p>
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
public interface MonitoringTask {

    /**
     * Performs a specific monitoring check on the given device context.
     * <p>
     * Implementing classes should provide the logic for a specific monitoring action within this method.
     * The method receives a {@link DeviceContext} object, which includes information about the device
     * to be monitored, allowing the implementation to access device metrics and other relevant data.
     * </p>
     *
     * @param context The device context on which the monitoring check is to be performed, encapsulating
     *                details about the device and its current state.
     */
    void performCheck(DeviceContext context);

    /**
     * Returns the status of the last check performed by this task.
     * Implementing classes should return a String representation of the status.
     *
     * @return A string representing the status of the task.
     */
    String getStatus();

    /**
     * Returns the name of the monitoring task.
     * Implementing classes should return a String that uniquely identifies the task.
     *
     * @return A string representing the name of the task.
     */
    String getName();

}