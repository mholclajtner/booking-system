package com.example.booking.service;

import com.example.booking.service.impl.SimpleBookingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link SimpleBookingStrategy} methods to ensure they correctly validate inputs.
 * Uses mock objects for dependencies to isolate testing to the strategy implementation.
 */
public class BookingStrategyTest {

    private SimpleBookingStrategy bookingStrategy;
    private BookingManager mockBookingManager;

    /**
     * Setup before each test case.
     * Initializes a {@link SimpleBookingStrategy} and mocks a {@link BookingManager}.
     */
    @BeforeEach
    public void setUp() {
        bookingStrategy = new SimpleBookingStrategy();
        mockBookingManager = Mockito.mock(BookingManager.class);
    }

    /**
     * Validates that no exception is thrown for valid inputs.
     */
    @Test
    public void validateInput_ValidInput_NoExceptionThrown() {
        String phoneId = "12345";
        String user = "user1";

        assertDoesNotThrow(() -> bookingStrategy.validateInput(mockBookingManager, phoneId, user));
    }

    /**
     * Validates that an exception is thrown when the booking manager is null.
     */
    @Test
    public void validateInput_NullBookingManager_ExceptionThrown() {
        String phoneId = "12345";
        String user = "user1";

        assertThrows(IllegalArgumentException.class, () -> bookingStrategy.validateInput(null, phoneId, user));
    }

    /**
     * Validates that an exception is thrown for an empty phone ID.
     */
    @Test
    public void validateInput_EmptyPhoneId_ExceptionThrown() {
        String user = "user1";

        assertThrows(IllegalArgumentException.class, () -> bookingStrategy.validateInput(mockBookingManager, "", user));
    }

    /**
     * Validates that an exception is thrown for a null user identifier.
     */
    @Test
    public void validateInput_NullUser_ExceptionThrown() {
        String phoneId = "12345";

        assertThrows(IllegalArgumentException.class, () -> bookingStrategy.validateInput(mockBookingManager, phoneId, null));
    }

    /**
     * Validates that an exception is thrown for an empty user identifier.
     */
    @Test
    public void validateInput_EmptyUser_ExceptionThrown() {
        String phoneId = "12345";

        assertThrows(IllegalArgumentException.class, () -> bookingStrategy.validateInput(mockBookingManager, phoneId, ""));
    }
}