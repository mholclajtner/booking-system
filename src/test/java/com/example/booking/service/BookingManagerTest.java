package com.example.booking.service;

import com.example.booking.entity.Booking;
import com.example.booking.entity.MobilePhone;
import com.example.booking.repository.MobilePhoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingManagerTest {

    @Mock
    private MobilePhoneRepository mobilePhoneRepository;
    private BookingManager bookingManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookingManager = new BookingManager(mobilePhoneRepository, new ArrayList<>());
    }

    @Test
    void bookPhone_WhenPhoneIsAvailable_ShouldCreateBooking() {

        String phoneId = "123";
        String user = "John Doe";
        MobilePhone phone = new MobilePhone();
        phone.setId(phoneId);
        phone.setAvailable(true);

        when(mobilePhoneRepository.findById(phoneId)).thenReturn(Optional.of(phone));

        Optional<Booking> booking = bookingManager.bookPhone(phoneId, user);

        assertTrue(booking.isPresent());
        assertEquals(user, booking.get().getBookedBy());
        verify(mobilePhoneRepository).save(phone);
        assertFalse(phone.isAvailable());
    }

    @Test
    void returnPhone_WhenBookingExistsAndUserMatches_ShouldMarkPhoneAsAvailable() {

        String bookingId = "booking123";
        String user = "John Doe";
        MobilePhone phone = new MobilePhone();
        phone.setAvailable(false);

        Booking booking = new Booking(bookingId, phone, LocalDateTime.now(), user);
        bookingManager.getBookings().add(booking); // Assuming there is a getter for bookings

        Optional<Booking> returnedBooking = bookingManager.returnPhone(bookingId, user);

        assertTrue(returnedBooking.isPresent());
        assertEquals(bookingId, returnedBooking.get().getId());
        assertTrue(phone.isAvailable());
        assertFalse(bookingManager.getBookings().contains(booking));
    }

    @Test
    void isDeviceBooked_WhenDeviceIsBooked_ShouldReturnTrue() {

        String deviceId = "device123";
        MobilePhone phone = new MobilePhone();
        phone.setId(deviceId);
        phone.setAvailable(false);

        Booking booking = new Booking("booking123", phone, LocalDateTime.now(), "John Doe");
        bookingManager.getBookings().add(booking);

        boolean isBooked = bookingManager.isDeviceBooked(deviceId);

        assertTrue(isBooked);
    }


}