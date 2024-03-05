package com.example.booking.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>
 * Represents a response for a booking operation.
 * Encapsulates the result of a booking action, indicating success or failure along with an appropriate message.
 * The {@code success} field indicates the outcome (true if successful, false otherwise).
 * The {@code message} field provides additional details about the outcome, such as an error message or a confirmation.
 * </p>
 * <p>
 * Lombok annotations are used to automatically generate getters, setters, and constructors without writing boilerplate code.
 * {@code @Data} generates all the boilerplate that is normally associated with simple POJOs (Plain Old Java Objects)
 * such as getters for all fields, setters for all non-final fields, and appropriate toString, equals and hashCode implementations.
 * {@code @AllArgsConstructor} generates a constructor with one parameter for each field in the class.
 * </p>
 * <p>
 * Usage example:
 * {@code BookingResponse response = new BookingResponse("Phone booked successfully", true);}
 * </p>
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class BookingResponse {
    /**
     * A descriptive message about the outcome of the booking operation.
     */
    private String message;

    /**
     * A boolean flag indicating whether the booking operation was successful.
     */
    private boolean success;
}