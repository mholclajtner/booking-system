package com.example.booking.service;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Defines the strategy for booking phones within a booking system.
 * This interface allows for the implementation of different booking strategies
 * depending on specific business rules or requirements.
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
public interface BookingStrategy {

    /**
     * Attempts to book a phone for a user based on the implemented booking strategy.
     *
     * @param manager The booking manager responsible for handling the booking process.
     *                Must not be null.
     * @param phoneId The unique identifier of the phone to be booked.
     *                Must not be null or empty.
     * @param user    The identifier for the user attempting to book the phone.
     *                Must not be null or empty.
     * @return {@code true} if the phone was successfully booked,
     *         {@code false} otherwise.
     */
    boolean bookPhone(@NotNull BookingManager manager, @NotEmpty String phoneId, @NotEmpty String user);


    /**
     * Attempts to return a phone for a user based on the implemented return strategy.
     *
     * @param manager The booking manager responsible for handling the return process.
     *                Must not be null.
     * @param phoneId The unique identifier of the phone to be returned.
     *                Must not be null or empty.
     * @param user    The identifier for the user attempting to return the phone.
     *                Must not be null or empty.
     * @return {@code true} if the phone was successfully returned,
     *         {@code false} otherwise.
     */
    boolean returnPhone(@NotNull BookingManager manager, @NotEmpty String phoneId, @NotEmpty String user);


    /**
     * Default method for validating input parameters.
     * Implementing classes can use this method to ensure parameters meet the required constraints.
     *
     * @param manager The booking manager to validate.
     * @param phoneId The phone ID to validate.
     * @param user    The user ID to validate.
     * @throws IllegalArgumentException if any parameter is null or empty.
     */
    default void validateInput(BookingManager manager, String phoneId, String user) {
        if (manager == null || phoneId == null || phoneId.isEmpty() || user == null || user.isEmpty()) {
            throw new IllegalArgumentException("Parameters must not be null or empty");
        }
    }

}