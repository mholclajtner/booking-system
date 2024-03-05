package com.example.booking.service.impl;

import com.example.booking.service.BookingManager;
import com.example.booking.service.BookingStrategy;

/**
 * Abstract base class for booking strategies.
 * Provides a common implementation for returning a phone.
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
public abstract class BaseBookingStrategy implements BookingStrategy {

    /**
     * Tries to return a phone based on a booking identified by the phone ID and user.
     * This common method can be used across different booking strategies.
     *
     * @param manager The booking manager handling the bookings.
     * @param phoneId The unique identifier of the phone to be returned.
     * @param user    The identifier of the user attempting to return the phone.
     * @return true if the phone was successfully returned, false otherwise.
     */
    @Override
    public boolean returnPhone(BookingManager manager, String phoneId, String user) {
        return manager.findBookingByDeviceId(phoneId)
                .filter(booking -> booking.getBookedBy().equals(user))
                .flatMap(booking -> manager.returnPhone(booking.getId(), user))
                .isPresent();
    }
}