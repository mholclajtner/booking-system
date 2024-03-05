package com.example.booking.factory.configuration;

import com.example.booking.factory.annotation.PhoneModel;
import com.example.booking.entity.MobilePhone;
import com.example.booking.monitoring.entity.DeviceMetrics;

import java.util.Map;

/**
 * Configures and creates instances of various mobile phone models with model-specific default metrics.
 * Utilizes the {@link PhoneModel} annotation for specifying the model created by each method.
 * This class acts as a factory for {@link MobilePhone} instances, with each model pre-configured
 * based on realistic performance and hardware metrics.
 *
 * @see com.example.booking.factory.MobilePhoneFactory
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
public class PhoneModelConfig {

    private static final Map<String, DeviceMetrics> modelSpecificMetrics = Map.of(
            "Samsung Galaxy S9", new DeviceMetrics(120.0, 4096, 4096, 0.01),
            "Samsung Galaxy S8", new DeviceMetrics(110.0, 4096, 4096,0.02),
            "Motorola Nexus 6", new DeviceMetrics(80.0, 3072,3072, 0.05),
            "Oneplus 9", new DeviceMetrics(130.0, 6144, 6144,0.01),
            "Apple iPhone 13", new DeviceMetrics(140.0, 4096, 4096, 0.01),
            "Apple iPhone 12", new DeviceMetrics(130.0, 4096, 4096,0.01),
            "Apple iPhone 11", new DeviceMetrics(120.0, 4096, 4096,0.02),
            "iPhone X", new DeviceMetrics(110.0, 3072, 3072,0.03),
            "Nokia 3310", new DeviceMetrics(50.0, 512, 512, 0.1) // Example of a simplistic model with lower performance metrics
    );

    /**
     * Creates an instance of a mobile phone with specified model and default metrics.
     * Metrics are defined based on realistic assessments of each model's capabilities.
     *
     * @param id    The unique identifier for the mobile phone instance. Must be unique
     *              across all instances created by this factory.
     * @param model The model name of the mobile phone to be created.
     * @return An instance of {@link MobilePhone} configured for the specified model.
     */
    private static MobilePhone createPhoneModel(String id, String model) {
        DeviceMetrics metrics = modelSpecificMetrics.getOrDefault(model, new DeviceMetrics(100.0, 2048, 2048,0.05));
        return new MobilePhone(id, model, metrics);
    }

    /**
     * Creates an instance of a Samsung Galaxy S9 mobile phone. This model is equipped with default metrics
     * that reflect its high-end CPU and memory capabilities, suitable for advanced gaming and multitasking.
     * The battery efficiency metric ensures a balance between performance and power consumption, making it
     * ideal for users who demand both speed and durability.
     *
     * @param id The unique identifier for the mobile phone instance. It is crucial for the ID
     *           to be unique to facilitate effective tracking and identification of the mobile phone
     *           within a management system or inventory.
     * @return A {@link MobilePhone} instance specifically configured for the Samsung Galaxy S9, with metrics
     *         that highlight its strengths in processing power and energy efficiency.
     */
    @PhoneModel("Samsung Galaxy S9")
    public static MobilePhone createSamsungGalaxyS9(String id) {
        return createPhoneModel(id, "Samsung Galaxy S9");
    }

    /**
     * Creates an instance of a Samsung Galaxy S8 mobile phone with default metrics.
     * The Galaxy S8 features a high-performance processor and memory configuration,
     * but being an older model, it has slightly less processing power and battery efficiency
     * compared to newer models. The chosen metrics reflect its balance between performance
     * and energy consumption typical for its generation.
     *
     * @param id The unique identifier for the mobile phone instance. It is crucial for the ID
     *           to be unique to facilitate effective tracking and identification of the mobile phone
     *           within a management system or inventory.
     * @return An instance of {@link MobilePhone} specifically configured for the Samsung Galaxy S8 model,
     *         including default performance and hardware metrics.
     */
    @PhoneModel("Samsung Galaxy S8")
    public static MobilePhone createSamsungGalaxyS8(String id) {
        return createPhoneModel(id, "Samsung Galaxy S8");
    }

    /**
     * Creates an instance of a Motorola Nexus 6 mobile phone with default metrics.
     * The Nexus 6, known for its large screen and robust performance for its time,
     * has metrics that represent its capabilities well. The device's processing power
     * and memory are set to reflect its mid-range status during its market period,
     * with a focus on providing a reliable user experience.
     *
     * @param id The unique identifier for the mobile phone instance. It is crucial for the ID
     *           to be unique to facilitate effective tracking and identification of the mobile phone
     *           within a management system or inventory.
     * @return An instance of {@link MobilePhone} specifically configured for the Motorola Nexus 6 model,
     *         including default performance and hardware metrics.
     */
    @PhoneModel("Motorola Nexus 6")
    public static MobilePhone createMotorolaNexus6(String id) {
        return createPhoneModel(id, "Motorola Nexus 6");
    }

    /**
     * Creates an instance of a Oneplus 9 mobile phone with default metrics.
     * The Oneplus 9 is designed for high performance, featuring top-tier processing capabilities
     * and memory. The metrics chosen reflect its status as a flagship model, capable of handling
     * intensive applications and multitasking with minimal battery impact, aligning with its
     * reputation for speed and efficiency.
     *
     * @param id The unique identifier for the mobile phone instance. It is crucial for the ID
     *           to be unique to facilitate effective tracking and identification of the mobile phone
     *           within a management system or inventory.
     * @return An instance of {@link MobilePhone} specifically configured for the Oneplus 9 model,
     *         including default performance and hardware metrics.
     */
    @PhoneModel("Oneplus 9")
    public static MobilePhone createOneplus9(String id) {
        return createPhoneModel(id, "Oneplus 9");
    }

    /**
     * Creates an instance of an Apple iPhone 13 mobile phone with default metrics.
     * As a recent flagship from Apple, the iPhone 13's metrics are set to represent its advanced
     * processor efficiency and high memory capacity. These metrics underscore its ability to
     * execute demanding applications smoothly while maintaining energy efficiency, reflecting
     * Apple's emphasis on performance and user experience.
     *
     * @param id The unique identifier for the mobile phone instance. It is crucial for the ID
     *           to be unique to facilitate effective tracking and identification of the mobile phone
     *           within a management system or inventory.
     * @return An instance of {@link MobilePhone} specifically configured for the Apple iPhone 13 model,
     *         including default performance and hardware metrics.
     */
    @PhoneModel("Apple iPhone 13")
    public static MobilePhone createAppleiPhone13(String id) {
        return createPhoneModel(id, "Apple iPhone 13");
    }

    /**
     * Creates an instance of an Apple iPhone 12 mobile phone with default metrics.
     * The iPhone 12 combines a powerful processor with efficient energy use, thanks to Apple's
     * hardware optimization. The selected metrics reflect its capability for high performance
     * in both processing and graphics, suitable for both everyday and intensive tasks, while
     * maintaining longer battery life.
     *
     * @param id The unique identifier for the mobile phone instance. It is crucial for the ID
     *           to be unique to facilitate effective tracking and identification of the mobile phone
     *           within a management system or inventory.
     * @return An instance of {@link MobilePhone} specifically configured for the Apple iPhone 12 model,
     *         including default performance and hardware metrics.
     */
    @PhoneModel("Apple iPhone 12")
    public static MobilePhone createAppleiPhone12(String id) {
        return createPhoneModel(id, "Apple iPhone 12");
    }

    /**
     * Creates an instance of an Apple iPhone 11 mobile phone with default metrics.
     * The iPhone 11 is designed to offer a balance between high performance and energy efficiency,
     * suitable for a wide range of applications from gaming to productivity. The metrics reflect
     * its ability to handle demanding tasks with ease, while ensuring that battery life is preserved,
     * aligning with its role as a versatile and reliable device.
     *
     * @param id The unique identifier for the mobile phone instance. It is crucial for the ID
     *           to be unique to facilitate effective tracking and identification of the mobile phone
     *           within a management system or inventory.
     * @return An instance of {@link MobilePhone} specifically configured for the Apple iPhone 11 model,
     *         including default performance and hardware metrics.
     */
    @PhoneModel("Apple iPhone 11")
    public static MobilePhone createAppleiPhone11(String id) {
        return createPhoneModel(id, "Apple iPhone 11");
    }

    /**
     * Creates an instance of an iPhone X mobile phone with default metrics.
     * The iPhone X marked a significant advancement in Apple's phone technology, introducing
     * facial recognition and a new design. The metrics chosen reflect its status as a groundbreaking
     * model at the time, with sufficient processing power and memory to support its innovative features
     * while maintaining efficient battery usage.
     *
     * @param id The unique identifier for the mobile phone instance. It is crucial for the ID
     *           to be unique to facilitate effective tracking and identification of the mobile phone
     *           within a management system or inventory.
     * @return An instance of {@link MobilePhone} specifically configured for the iPhone X model,
     *         including default performance and hardware metrics.
     */
    @PhoneModel("iPhone X")
    public static MobilePhone createiPhoneX(String id) {
        return createPhoneModel(id, "iPhone X");
    }

    /**
     * Creates an instance of a Nokia 3310 mobile phone with default metrics.
     * This model is renowned for its exceptional durability and extensive battery life,
     * making it a popular choice for users seeking reliability and simplicity.
     *
     * @param id The unique identifier for the mobile phone instance. It is crucial for the ID
     *           to be unique to facilitate effective tracking and identification of the mobile phone
     *           within a management system or inventory.
     * @return An instance of {@link MobilePhone} tailored for the Nokia 3310 model, equipped
     *         with default metrics that highlight its durability and battery efficiency.
     */
    @PhoneModel("Nokia 3310")
    public static MobilePhone createNokia3310(String id) {
        return createPhoneModel(id, "Nokia 3310");
    }

}