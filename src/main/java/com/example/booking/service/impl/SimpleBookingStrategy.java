package com.example.booking.service.impl;

import com.example.booking.entity.Booking;
import com.example.booking.service.BookingManager;
import com.example.booking.service.BookingStrategy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implements a simple booking strategy using functional programming concepts.
 * This strategy assumes that all users are equally eligible to book a phone.
 * It is marked as the primary strategy to be used when no other strategy is specified.
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
@Service("simpleStrategy")
@Primary
public class SimpleBookingStrategy implements BookingStrategy {

    /**
     * Attempts to book a phone based on a simple booking strategy.
     * It delegates the booking operation to the BookingManager and returns the result.
     *
     * @param manager The booking manager handling phone bookings.
     * @param phoneId The unique identifier of the phone to book.
     * @param user    The user attempting to book the phone.
     * @return true if the booking is successful, false otherwise.
     */
    @Override
    public boolean bookPhone(BookingManager manager, String phoneId, String user) {
        Optional<Booking> booking = manager.bookPhone(phoneId, user);
        return booking.isPresent();
    }

    /**
     * Attempts to return a booked phone. The operation is successful if the phone is currently booked
     * by the user and the phone is then marked as available again.
     *
     * @param manager The booking manager handling phone returns.
     * @param phoneId The unique identifier of the phone to return.
     * @param user    The user attempting to return the phone.
     * @return true if the phone return is successful, false otherwise.
     */
    @Override
    public boolean returnPhone(BookingManager manager, String phoneId, String user) {
        return manager.returnPhone(phoneId, user)
                .isPresent();
    }

}