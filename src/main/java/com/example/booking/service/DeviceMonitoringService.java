package com.example.booking.service;

import com.example.booking.entity.Booking;
import com.example.booking.entity.MobilePhone;
import com.example.booking.entity.event.MobilePhoneAddedEvent;
import com.example.booking.repository.MobilePhoneRepository;
import com.example.booking.monitoring.DeviceMonitor;
import com.example.booking.monitoring.entity.DeviceContext;
import com.example.booking.monitoring.entity.DeviceMetrics;
import com.example.booking.monitoring.tasks.BatteryLevelCheck;
import com.example.booking.monitoring.tasks.MemoryUsageCheck;
import com.example.booking.monitoring.tasks.MonitoringTask;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service for monitoring device metrics such as battery level and memory usage.
 * <p>This service initializes monitoring tasks for mobile devices upon application startup,
 * handling device contexts and invoking monitoring tasks to periodically check and update
 * device metrics.</p>
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceMonitoringService {

    private final List<MonitoringTask> monitoringTasks = new ArrayList<>();

    // A thread-safe map to hold the DeviceMonitors keyed by deviceId
    @Getter private final ConcurrentHashMap<String, DeviceMonitor> deviceMonitorsMap = new ConcurrentHashMap<>();

    private final MobilePhoneRepository mobilePhoneRepository;

    private final BookingManager bookingManager;


    /**
     * Converts a MobilePhone entity into a DeviceContext object.
     * This method should be replaced with actual conversion logic.
     *
     * @param phone the mobile phone to convert
     * @return the created DeviceContext
     */
    private DeviceContext createDeviceContextFromMobilePhone(MobilePhone phone) {
        return new DeviceContext(phone.getId(), new DeviceMetrics(phone.getDeviceMetrics().batteryLevel(), phone.getDeviceMetrics().totalMemory(), phone.getDeviceMetrics().freeMemory(), phone.getDeviceMetrics().systemLoad()));
    }

    /**
     * Initializes device contexts and starts monitoring upon application readiness.
     * <p>This method is triggered by the ApplicationReadyEvent, ensuring that the monitoring
     * starts only after the application has been fully initialized.</p>
     * <p>Fetches all mobile phones from the repository, converts them to device contexts,
     * and starts monitoring each device with configured tasks.</p>
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initializeAndStartMonitoring() {
        Collection<MobilePhone> mobilePhones = mobilePhoneRepository.findAll();

        List<DeviceContext> deviceContexts = mobilePhones.stream()
                .map(phone -> {
                    phone.updateDeviceMetrics();
                    return new DeviceContext(
                            phone.getId(),
                            new DeviceMetrics(
                                    phone.getBatteryLevel(),
                                    phone.getTotalMemory(),
                                    phone.getFreeMemory(),
                                    phone.getSystemLoad()
                            ));
                })
                .toList();

        deviceContexts.stream()
                .map(ctx -> new DeviceMonitor(ctx, List.of(new BatteryLevelCheck(), new MemoryUsageCheck())))
                .forEach(DeviceMonitor::startMonitoring);
    }

    /**
     * Retrieves the status of all monitored devices.
     *
     * @return A collection or string representing the status of all devices.
     */
    public String getAllPhoneStatuses() {
        List<Map<String, Object>> allStatuses = new ArrayList<>();

        for (DeviceMonitor monitor : deviceMonitorsMap.values()) {
            Map<String, Object> statusMap = new HashMap<>();
            DeviceContext context = monitor.getContext();


            statusMap.put("deviceId", context.deviceId());
            statusMap.put("status", monitor.getStatus());
            statusMap.put("booking", findBookingByDeviceId(context.deviceId()));
            statusMap.put("metrics", context.metrics());

            allStatuses.add(statusMap);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
        mapper.findAndRegisterModules();

        String jsonResponse;

        try {
            jsonResponse = mapper.writeValueAsString(allStatuses);
        } catch (Exception e) {
            log.error("Error while converting statuses to JSON", e);
            jsonResponse = "{\"error\": \"Failed to convert statuses to JSON\"}";
        }

        return jsonResponse;
    }

    /**
     * Finds the booking details for a specific device by its identifier.
     *
     * @param deviceId The unique identifier of the device for which booking details are requested.
     * @return The Booking associated with the device if found, null otherwise.
     */
    private Booking findBookingByDeviceId(String deviceId) {
        return bookingManager.findBookingByDeviceId(deviceId).orElse(null);
    }

    /**
     * Handles the MobilePhoneAddedEvent to set up monitoring for the new mobile phone.
     * <p>
     * This method responds to the MobilePhoneAddedEvent by creating a new DeviceMonitor
     * for the added phone and starts monitoring its metrics.
     *
     * @param event The event that gets fired when a new mobile phone is added.
     */
    @EventListener
    public void onMobilePhoneAdded(MobilePhoneAddedEvent event) {
        MobilePhone phone = event.getMobilePhone();
        DeviceContext context = createDeviceContextFromMobilePhone(phone);
        DeviceMonitor newMonitor = new DeviceMonitor(context, monitoringTasks);
        deviceMonitorsMap.put(context.deviceId(), newMonitor);
        mobilePhoneRepository.save(phone);

        newMonitor.startMonitoring();
    }

    /**
     * Periodically checks and updates the metrics of booked devices.
     * <p>
     * This scheduled task runs every 10 seconds to update device metrics for devices
     * that are not currently available (assumed to be booked). It checks if the free memory
     * is below a certain threshold and updates metrics accordingly.
     */
    @Scheduled(fixedDelay = 10000) // runs every 10 seconds
    public void monitorAndUpdateDeviceMetrics() {
        mobilePhoneRepository.findAll().stream()
                .filter(mobilePhone -> !mobilePhone.isAvailable())
                .forEach(this::updateDeviceMetricsIfNeeded);
    }

    /**
     * Updates the metrics for a device if certain conditions are met.
     * <p>
     * For example, if the free memory is below a certain threshold, it will update the metrics.
     * This method should contain the actual logic to assess and update the device's metrics.
     *
     * @param device The device for which metrics need to be updated.
     */
    void updateDeviceMetricsIfNeeded(MobilePhone device) {
        DeviceMetrics currentMetrics = device.getDeviceMetrics();

        if (currentMetrics.freeMemory() < 500) { // 500 MB threshold
            log.info("Updating metrics for device: {}", device.getId());

            DeviceMetrics updatedMetrics = new DeviceMetrics(
                    currentMetrics.batteryLevel(),
                    currentMetrics.totalMemory(),
                    currentMetrics.freeMemory() + 100,
                    currentMetrics.systemLoad()
            );
            device.setDeviceMetrics(updatedMetrics);
            mobilePhoneRepository.save(device);
        }
    }

    /**
     * Updates the context of a device monitor for a given device.
     * <p>
     * If the DeviceMonitor for a device needs to be updated with new context information,
     * this method replaces the old DeviceMonitor with a new one initialized with the new context.
     *
     * @param oldMonitor The existing DeviceMonitor that needs to be updated.
     * @param newContext The new DeviceContext information for the DeviceMonitor.
     */
    public void updateDeviceMonitorContext(DeviceMonitor oldMonitor, DeviceContext newContext) {

        // Since DeviceMonitor is immutable, create a new DeviceMonitor with the new context
        DeviceMonitor newMonitor = new DeviceMonitor(newContext, List.of(new BatteryLevelCheck(), new MemoryUsageCheck()));

        deviceMonitorsMap.replace(oldMonitor.getContext().deviceId(), newMonitor);

        for(DeviceMonitor deviceMonitor:deviceMonitorsMap.values()) {
            deviceMonitor.startMonitoring();
        }

    }
}