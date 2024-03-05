package com.example.booking.service.impl;

import com.example.booking.entity.Booking;
import com.example.booking.service.BookingManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Implements a priority-based booking strategy where certain users have higher priority for booking phones.
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
@Service("priorityStrategy")
public class PriorityBookingStrategy extends BaseBookingStrategy {

    private final Map<String, Integer> userPriorityMap;

    public PriorityBookingStrategy() {
        this.userPriorityMap = new HashMap<>();
        userPriorityMap.put("admin", 1);    // Admins have the highest priority
        userPriorityMap.put("manager", 1);  // Managers also have the highest priority
        userPriorityMap.put("user", 5);     // Regular users have lower priority
    }

    /**
     * Books a phone based on user priority. Users with higher priority (lower numerical value) are
     * allowed to book phones immediately. Users with lower priority may be placed in a queue or rejected.
     *
     * @param manager The booking manager handling phone bookings.
     * @param phoneId The unique identifier of the phone to book.
     * @param user    The user attempting to book the phone.
     * @return true if the booking is successful based on the user's priority, false otherwise.
     */
    @Override
    public boolean bookPhone(BookingManager manager, String phoneId, String user) {
        // Check if the user has a high enough priority to book immediately
        if (userHasPriority(user)) {
            Optional<Booking> booking = manager.bookPhone(phoneId, user);
            return booking.isPresent();
        } else {
            // Logic for handling users without high priority could be implemented here
            // For example, adding to a waiting list or returning a custom message
            return false;
        }
    }

    /**
     * Determines if a user has priority for booking a phone.
     *
     * @param user The identifier of the user.
     * @return true if the user has high priority, false otherwise.
     */
    private boolean userHasPriority(String user) {
        // Assume that a lower number represents higher priority
        // Users not in the map are given a default lower priority
        // The threshold value 10 is an arbitrary choice for this example
        return this.userPriorityMap.getOrDefault(user, Integer.MAX_VALUE) < 10;
    }
}