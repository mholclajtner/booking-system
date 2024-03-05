package com.example.booking.monitoring.entity;

/**
 * Represents the context of a device being monitored.
 * <p>
 * This record encapsulates the unique identifier of a device along with its associated metrics.
 * It is used to hold information relevant to the monitoring of a device's state, such as battery level,
 * memory usage, and more, which are encapsulated within {@link DeviceMetrics}.
 * </p>
 *
 * @param deviceId The unique identifier of the device.
 * @param metrics The metrics associated with the device, detailing aspects like battery level, memory usage, etc.
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
public record DeviceContext(String deviceId, DeviceMetrics metrics) {}