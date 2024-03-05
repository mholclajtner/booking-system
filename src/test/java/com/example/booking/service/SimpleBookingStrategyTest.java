package com.example.booking.service;

import com.example.booking.entity.Booking;
import com.example.booking.entity.MobilePhone;
import com.example.booking.service.impl.SimpleBookingStrategy;
import com.example.booking.monitoring.entity.DeviceMetrics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link SimpleBookingStrategy} focusing on the booking process.
 * Uses Mockito to mock dependencies for isolation.
 */
@ExtendWith(MockitoExtension.class)
public class SimpleBookingStrategyTest {

    @Mock
    private BookingManager mockBookingManager;

    private BookingStrategy bookingStrategy;

    /**
     * Setup before each test.
     * Initializes a new instance of {@link SimpleBookingStrategy}.
     */
    @BeforeEach
    void setUp() {
        bookingStrategy = new SimpleBookingStrategy();
    }

    /**
     * Tests that a successful booking returns true.
     * Mocks a successful booking scenario using a {@link BookingManager} and verifies the outcome.
     */
    @Test
    void bookPhone_SuccessfulBooking_ReturnsTrue() {
        DeviceMetrics expectedMetrics = new DeviceMetrics(100, 2048, 2048, 0.1);

        String phoneId = "12345";
        String user = "user1";

        when(mockBookingManager.bookPhone(phoneId, user))
                .thenReturn(Optional.of(new Booking(
                        "some-booking-id",
                        new MobilePhone("phone-id", "phone-model", expectedMetrics),
                        LocalDateTime.now(),
                        "some-user"
                )));

        boolean result = bookingStrategy.bookPhone(mockBookingManager, phoneId, user);

        assertTrue(result);

        verify(mockBookingManager).bookPhone(phoneId, user);
    }

    /**
     * Tests that a failed booking attempt returns false.
     * Simulates a booking failure and checks the method's return value.
     */
    @Test
    void bookPhone_FailedBooking_ReturnsFalse() {
        String phoneId = "67890";
        String user = "user2";

        when(mockBookingManager.bookPhone(phoneId, user)).thenReturn(Optional.empty());

        boolean result = bookingStrategy.bookPhone(mockBookingManager, phoneId, user);

        assertFalse(result);

        verify(mockBookingManager).bookPhone(phoneId, user);
    }
}