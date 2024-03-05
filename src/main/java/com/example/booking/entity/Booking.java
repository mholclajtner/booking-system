package com.example.booking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents a booking record for a mobile phone.
 * It holds the details about the booking such as the unique identifier,
 * the mobile phone booked, the time at which the booking was made,
 * and the person who made the booking.
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    /**
     * The unique identifier for the booking.
     */
    private String id;

    /**
     * The mobile phone that has been booked.
     */
    private MobilePhone mobilePhone;

    /**
     * The date and time when the booking was made.
     */
    private LocalDateTime bookedAt;

    /**
     * The name of the person who booked the mobile phone.
     */
    private String bookedBy;

}