package com.example.booking.monitoring.entity;

import jakarta.persistence.Embeddable;

/**
 * Represents the key metrics of a device's current state.
 * <p>
 * This record holds information about a device's battery level, available free memory,
 * and the system load. These metrics are crucial for monitoring the health and performance
 * of a device, facilitating decisions regarding resource management and potential maintenance needs.
 * </p>
 *
 * @param batteryLevel The current battery level of the device, represented as a percentage (0.0 to 100.0).
 * @param totalMemory The total amount of memory in the device, measured in bytes.
 * @param freeMemory The amount of free memory available on the device, measured in bytes.
 * @param systemLoad The current system load average for the device, which can be interpreted as a measure of system activity.
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
@Embeddable
public record DeviceMetrics(double batteryLevel, long totalMemory, long freeMemory, double systemLoad) {}