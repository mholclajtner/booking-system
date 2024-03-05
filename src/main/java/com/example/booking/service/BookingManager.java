package com.example.booking.service;

import com.example.booking.entity.Booking;
import com.example.booking.entity.MobilePhone;
import com.example.booking.repository.MobilePhoneRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The {@code com.example.booking.service.BookingManager} class is responsible for managing the bookings of mobile phones.
 * Implements a singleton pattern to ensure a single instance manages all bookings.
 * Relies on {@link MobilePhoneRepository} to handle the persistence of {@code com.example.booking.model.MobilePhone} objects.
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
@Service
@AllArgsConstructor
public class BookingManager {

    private final MobilePhoneRepository phoneRepository;

    @Getter private List<Booking> bookings;

    /**
     * Attempts to book a mobile phone for a specified user.
     * If the phone is available, it creates a new booking, marks the phone as unavailable,
     * and stores the updated phone status.
     *
     * @param phoneId The unique identifier of the phone to book.
     * @param user    The name of the user booking the phone.
     * @return An {@link Optional} containing the created {@link Booking} if successful, or an empty {@code Optional} if the phone is unavailable.
     */
    public Optional<Booking>  bookPhone(String phoneId, String user) {
        return phoneRepository.findById(phoneId)
                .filter(MobilePhone::isAvailable)
                .map(phone -> {
                    phone.setAvailable(false);
                    var booking = new Booking(phoneId, phone, LocalDateTime.now(), user);
                    bookings.add(booking);
                    phoneRepository.save(phone);
                    return booking;
                });
    }

    /**
     * Returns a booked phone, marking it as available again if the booking exists and the user matches.
     *
     * @param bookingId The unique identifier of the booking to return.
     * @param user The name of the user returning the phone.
     * @return An {@link Optional} containing the {@link Booking} if return was successful, empty otherwise.
     */
    public Optional<Booking> returnPhone(String bookingId, String user) {
        Optional<Booking> bookingOptional = findBookingById(bookingId);

        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            if (booking.getBookedBy().equals(user)) {
                MobilePhone phone = booking.getMobilePhone();
                phone.setAvailable(true);
                bookings.remove(booking);
                phoneRepository.save(phone);
                return Optional.of(booking);
            }
        }

        return Optional.empty();
    }

    /**
     * Finds a booking by its unique identifier using a functional approach.
     * Searches the list of current bookings and returns the first match.
     *
     * @param bookingId The unique identifier of the booking to find.
     * @return An {@link Optional} containing the found {@link Booking} if present, or an empty {@code Optional} if not found.
     */
    private Optional<Booking> findBookingById(String bookingId) {
        return bookings.stream()
                .filter(booking -> booking.getId().equals(bookingId))
                .findFirst();
    }

    /**
     * Finds a booking based on the device ID.
     *
     * @param deviceId the unique identifier of the device
     * @return an {@link Optional} containing the booking if found, otherwise an empty {@link Optional}
     */
    public Optional<Booking> findBookingByDeviceId(String deviceId) {
        return bookings.stream()
                .filter(booking -> booking.getMobilePhone().getId().equals(deviceId) && !booking.getMobilePhone().isAvailable())
                .findFirst();
    }

    /**
     * Checks if a device is currently booked.
     *
     * @param deviceId the unique identifier of the device
     * @return {@code true} if the device is booked, otherwise {@code false}
     */
    public boolean isDeviceBooked(String deviceId) {
        return bookings.stream()
                .anyMatch(booking -> booking.getMobilePhone().getId().equals(deviceId) && !booking.getMobilePhone().isAvailable());
    }

}