package com.example.booking.service.impl;

import com.example.booking.service.BookingManager;
import org.springframework.stereotype.Service;


/**
 * Strategy for booking phones with advanced criteria.
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
@Service("advancedStrategy")
public class AdvancedBookingStrategy extends BaseBookingStrategy {

    /**
     * Books a phone for a user if they meet the advanced eligibility criteria.
     *
     * @param manager The booking manager handling phone bookings.
     * @param phoneId The unique identifier of the phone to book.
     * @param user    The identifier of the user attempting to book the phone.
     * @return true if the booking is successful, false otherwise.
     */
    @Override
    public boolean bookPhone(BookingManager manager, String phoneId, String user) {
        return isUserEligibleForBooking(user) &&
                manager.bookPhone(phoneId, user).isPresent();
    }

    /**
     * Checks if the user is eligible for booking a phone. This method can
     * incorporate complex business logic to determine user eligibility.
     *
     * @param user The identifier of the user.
     * @return true if the user is eligible for booking, false otherwise.
     */
    private boolean isUserEligibleForBooking(String user) {
        // Placeholder for advanced eligibility logic
        // In a real-world scenario, this could involve checks such as:
        // - User's history of bookings
        // - Loyalty program status
        // - Compliance with company policies
        // Here we assume all users are eligible
        return true;
    }
}