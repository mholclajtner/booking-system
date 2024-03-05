package com.example.booking.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>
 * Represents a request for booking a mobile phone.
 * Encapsulates the essential information required to process a phone booking request.
 * It contains identifiers for both the phone and the user involved in the booking.
 * </p>
 * <p>
 * The {@code phoneId} field is the unique identifier for the mobile phone that is being requested for booking.
 * The {@code userId} field is the unique identifier for the user who is attempting to book the phone.
 * </p>
 * <p>
 * The class uses Lombok's {@code @Data} annotation to eliminate boilerplate code for simple POJOs.
 * With {@code @Data}, getters and setters for all fields, as well as {@code equals}, {@code hashCode}, and {@code toString} methods,
 * are automatically generated at compile-time.
 * </p>
 * <p>
 * Example usage:
 * {@code BookingRequest request = new BookingRequest();
 *  request.setPhoneId("phone123");
 *  request.setUserId("user456");}
 * </p>
 * <p>
 * This class can be used as part of a RESTful API endpoint to encapsulate incoming booking data.
 * It simplifies the passing of data between client and server and ensures that all necessary information is included with the booking request.
 * </p>
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class BookingRequest {
    /**
     * The unique identifier of the mobile phone to be booked.
     */
    private String phoneId;

    /**
     * The unique identifier of the user attempting to book the mobile phone.
     */
    private String userId;
}