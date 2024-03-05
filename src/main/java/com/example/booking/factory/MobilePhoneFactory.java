package com.example.booking.factory;

import com.example.booking.factory.annotation.PhoneModel;
import com.example.booking.factory.configuration.PhoneModelConfig;
import com.example.booking.entity.MobilePhone;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Factory class for creating instances of MobilePhone.
 * Utilizes reflection to dynamically find and invoke methods in PhoneModelConfig
 * annotated with @PhoneModel, based on the requested model identifier.
 * Ensures that each phone instance created has a unique identifier.
 *
 * Thread safety is guaranteed by using ConcurrentHashMap for storing phone counts.
 *
 * The factory design pattern is used to encapsulate the instantiation logic and keep it separate
 * from the main business logic. It allows for flexibility and scalability when new phone models are added.
 *
 * Usage:
 * MobilePhone newPhone = MobilePhoneFactory.createPhone("GalaxyS21");
 * if (newPhone != null) {
 *     // Use the newly created phone instance
 * }
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
@Slf4j
public class MobilePhoneFactory {
    private static final Map<String, Integer> phoneCount = new ConcurrentHashMap<>();
    private static final Map<String, Method> phoneModels;

    static {
        var configPhoneModels = new HashMap<String, Method>();

        // Reflection-based setup to map model identifiers to creator methods
        Arrays.stream(PhoneModelConfig.class.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(PhoneModel.class))
                .forEach(method -> {
                    var annotation = method.getAnnotation(PhoneModel.class);
                    configPhoneModels.put(annotation.value(), method);
                });
        phoneModels = Map.copyOf(configPhoneModels);
    }

    /**
     * Creates a new instance of MobilePhone based on the specified model identifier.
     * If the model is recognized, invokes the corresponding method annotated with @PhoneModel.
     * Handles and logs any reflection-related exceptions during method invocation.
     *
     * @param model The model identifier for which to create the phone.
     * @return A new instance of MobilePhone for the specified model, or null if the model is not supported.
     */
    public static MobilePhone createPhone(String model) {
        var creatorModel = phoneModels.get(model);
        if (creatorModel == null) {
            log.warn("Model not recognized or supported: {}", model);
            return null;
        }

        var id = generateId(model);
        try {
            MobilePhone phone = (MobilePhone) creatorModel.invoke(null, id);
            log.info("Created new mobile phone instance: {}", phone);
            return phone;
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("Error invoking creator method for model {}: {}", model, e.getMessage(), e);
            Throwable cause = e.getCause();
            if (cause != null) {
                log.error("Underlying cause: ", cause);
            }
        }
        return null;
    }

    /**
     * Generates a unique identifier for a phone model by appending a count to the model's base ID.
     * Synchronizes access to the phone count map to ensure thread-safe incrementing of the count.
     *
     * @param model The base model identifier.
     * @return A unique identifier for the phone, formatted as "{model}-{count}".
     */
    public static String generateId(String model) {
        int count = phoneCount.compute(model, (k, v) -> (v == null) ? 1 : v + 1);
        log.debug("Generated ID for model {}: {}", model, count);
        return STR."\{model}-\{count}";
    }
}