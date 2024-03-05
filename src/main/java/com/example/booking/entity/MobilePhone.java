package com.example.booking.entity;

import com.example.booking.monitoring.entity.DeviceMetrics;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.*;

import java.util.Random;

/**
 * The {@code MobilePhone} class represents a mobile phone entity within a booking system.
 * It encapsulates the details necessary for managing the inventory and booking status of mobile phones.
 * <p>
 * This class is part of the model layer and is used to represent the state and characteristics of a mobile phone,
 * including its identification, model, availability for booking, and metrics related to the device's physical and performance attributes.
 * The associated device metrics provide insights into the health and performance of the mobile phone,
 * which can be used for monitoring and managing the device effectively in the booking system.
 * </p>
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class MobilePhone {

    /**
     * The unique identifier for the mobile phone. This ID is used to uniquely identify each mobile phone
     * within the booking system. It is expected to be non-null and unique across all mobile phones.
     */
    @Id @NonNull private String id;

    /**
     * The model name of the mobile phone. This field represents the manufacturer's name or designation
     * for the mobile phone model. It provides a human-readable way to identify the type of mobile phone.
     */
    @NonNull private String model;

    /**
     * Indicates whether the mobile phone is available for booking. This boolean flag is used to manage
     * the booking status of the mobile phone. When {@code true}, the mobile phone is available for new bookings;
     * when {@code false}, it is currently booked or otherwise unavailable for booking.
     */
    private boolean isAvailable = true;

    /**
     * The device metrics associated with the mobile phone. This {@link DeviceMetrics} object encapsulates
     * various performance and health metrics for the mobile phone, such as battery level, memory usage, and system load.
     * These metrics are useful for monitoring the condition and performance of the mobile phone within the booking system.
     */
    @Getter @NonNull private DeviceMetrics deviceMetrics;

    @Transient
    private Random random = new Random();
    public void updateDeviceMetrics() {
        // Adjust these values as necessary for your simulation
        double batteryChange = (random.nextDouble() - 0.5) * 10; // Random change between -5 and +5
        long memoryChange = (long) ((random.nextDouble() - 0.5) * 512); // Random change between -256 and +256 MB
        double loadChange = (random.nextDouble() - 0.5) * 0.2; // Random change between -0.1 and +0.1

        double newBatteryLevel = Math.max(0, Math.min(100, this.deviceMetrics.batteryLevel() + batteryChange));
        long newFreeMemory = Math.max(0, Math.min(this.deviceMetrics.totalMemory(), this.deviceMetrics.freeMemory() + memoryChange));
        double newSystemLoad = Math.max(0, Math.min(1, this.deviceMetrics.systemLoad() + loadChange));

        // Since DeviceMetrics is a record (and thus immutable), create a new instance with the updated values
        this.deviceMetrics = new DeviceMetrics(
                newBatteryLevel,
                this.deviceMetrics.totalMemory(), // assuming total memory doesn't change
                newFreeMemory,
                newSystemLoad
        );
    }

    // Getters for the device metrics which simply return the current value of each metric
    public double getBatteryLevel() {
        return this.deviceMetrics.batteryLevel();
    }

    public long getTotalMemory() {
        return this.deviceMetrics.totalMemory();
    }

    public long getFreeMemory() {
        return this.deviceMetrics.freeMemory();
    }

    public double getSystemLoad() {
        return this.deviceMetrics.systemLoad();
    }

}