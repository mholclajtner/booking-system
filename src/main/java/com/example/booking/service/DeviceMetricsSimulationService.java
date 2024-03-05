package com.example.booking.service;

import com.example.booking.monitoring.DeviceMonitor;
import com.example.booking.monitoring.entity.DeviceContext;
import com.example.booking.monitoring.entity.DeviceMetrics;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Random;

/**
 * Service responsible for simulating changes in device metrics.
 * This service is scheduled to run periodically to simulate metrics changes for devices being monitored.
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
@AllArgsConstructor
public class DeviceMetricsSimulationService {

    private final DeviceMonitoringService deviceMonitoringService;
    private final BookingManager bookingManager;

    /**
     * Scheduled task to simulate changes in device metrics.
     * This method runs periodically to simulate changes in device metrics for devices that are currently booked.
     * It randomly updates the battery level and memory usage for each device being monitored.
     */
    @Scheduled(fixedRate = 10000) // Simulate metrics change every 10 seconds
    public void simulateMetricsChange() {
        Collection<DeviceMonitor> monitors = deviceMonitoringService.getDeviceMonitorsMap().values();
        monitors.stream()
                .filter(device -> bookingManager.isDeviceBooked(device.getContext().deviceId()))
                .forEach(this::randomlyUpdateDeviceMetrics);
    }

    /**
     * Simulates random updates to device metrics.
     * This method simulates random changes in battery level and memory usage for a given device.
     *
     * @param monitor The DeviceMonitor representing the device for which metrics are being updated.
     */
    private void randomlyUpdateDeviceMetrics(DeviceMonitor monitor) {

        DeviceMetrics currentMetrics = monitor.getContext().metrics();
        Random random = new Random();

        long maxMemoryDecrease = (long) (currentMetrics.totalMemory() * 0.05); // 5% of total memory
        long memoryDecrease = (maxMemoryDecrease > 0) ? (random.nextLong(maxMemoryDecrease) + 1) : 0;
        long newFreeMemory = Math.max(currentMetrics.freeMemory() - memoryDecrease, 0);

        double maxBatteryDecrease = currentMetrics.batteryLevel() * 0.02; // 2% of current battery level
        double batteryDecrease = (random.nextDouble() * maxBatteryDecrease);
        double newBatteryLevel = Math.max(currentMetrics.batteryLevel() - batteryDecrease, 0);

        // Ensure newBatteryLevel do not fall below 0
        newBatteryLevel = Math.max(newBatteryLevel, 0);

        // Since DeviceMetrics is immutable, create a new instance with the updated values
        DeviceMetrics newMetrics = new DeviceMetrics(
                newBatteryLevel,
                currentMetrics.totalMemory(),
                newFreeMemory,
                currentMetrics.systemLoad()
        );

        // Since DeviceContext is also immutable, create a new instance with the updated metrics
        DeviceContext newContext = new DeviceContext(
                monitor.getContext().deviceId(),
                newMetrics
        );

        deviceMonitoringService.updateDeviceMonitorContext(monitor, newContext);

        log.info(STR."Updated metrics for device: \{newContext.deviceId()}. New Free Memory: \{newFreeMemory} MB, New Battery Level: \{newBatteryLevel}%");

    }
}