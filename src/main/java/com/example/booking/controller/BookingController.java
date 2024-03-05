package com.example.booking.controller;

import com.example.booking.entity.dto.BookingRequest;
import com.example.booking.entity.dto.BookingResponse;
import com.example.booking.service.BookingManager;
import com.example.booking.service.BookingStrategy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller for handling booking operations.
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    @Setter private BookingStrategy bookingStrategy;
    private final BookingManager bookingManager;
    private final ApplicationContext applicationContext; // Used to fetch strategies by their names


    /**
     * Books a phone for a specified user.
     * <p>
     * This endpoint attempts to book a phone based on the provided phone and user identifiers.
     * If the booking is successful, it returns a confirmation response. If the phone cannot be booked
     * (e.g., already booked, unavailable), it returns an error response.
     *
     * @param bookingRequest the request body containing the phone ID and user ID
     * @return a {@link ResponseEntity} containing a {@link BookingResponse} with the outcome
     * @throws IllegalArgumentException if the request parameters are invalid
     */
    @Operation(summary = "Book a phone for a user", description = "Attempts to book a phone for a user based on the booking strategy.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Phone booked successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookingResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Failed to book phone or invalid request",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookingResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "An error occurred while processing the booking",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookingResponse.class)) })
    })
    @PostMapping("/bookPhone")
    public ResponseEntity<BookingResponse> bookPhone(
            @RequestBody BookingRequest bookingRequest) {
        try {
            String phoneId = bookingRequest.getPhoneId();
            String userId = bookingRequest.getUserId();

            if (this.bookingStrategy == null) {
                this.bookingStrategy = applicationContext.getBean("simpleStrategy", BookingStrategy.class);
            }

            bookingStrategy.validateInput(bookingManager, phoneId, userId);

            boolean bookingSuccess = bookingStrategy.bookPhone(bookingManager, phoneId, userId);

            if (bookingSuccess) {
                BookingResponse response = new BookingResponse(STR."Phone booked successfully for user: \{userId}", true);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                BookingResponse response = new BookingResponse("Failed to book phone. It might already be booked or unavailable.", false);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException e) {
            BookingResponse response = new BookingResponse(STR."Invalid request: \{e.getMessage()}", false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            BookingResponse response = new BookingResponse("An error occurred while processing the booking.", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles the return of a previously booked phone.
     * <p>
     * This endpoint processes the return of a phone that a user has booked. It confirms the current booking status of the phone
     * and processes the return if the user matches the booking. The method sends a confirmation response upon successful
     * return or an error response if the user did not book the phone or if another error occurs.
     * </p>
     * @param bookingRequest the request body containing the phone ID and user ID
     * @return a {@link ResponseEntity} containing a {@link BookingResponse} that indicates the outcome
     * @throws IllegalArgumentException if the request parameters are invalid
     */
    @Operation(summary = "Return a phone", description = "Attempts to return a previously booked phone for a user. Handles success or failure scenarios.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Phone returned successfully",
                    content = @Content(schema = @Schema(implementation = BookingResponse.class))),
            @ApiResponse(responseCode = "400", description = "Failed to return phone or invalid request",
                    content = @Content(schema = @Schema(implementation = BookingResponse.class))),
            @ApiResponse(responseCode = "500", description = "An error occurred while processing the return",
                    content = @Content(schema = @Schema(implementation = BookingResponse.class)))
    })
    @PostMapping("/returnPhone")
    public ResponseEntity<BookingResponse> returnPhone(@RequestBody BookingRequest bookingRequest) {
        try {
            String phoneId = bookingRequest.getPhoneId();
            String userId = bookingRequest.getUserId();

            // Validate input; similar validation as in the bookPhone method
            bookingStrategy.validateInput(bookingManager, phoneId, userId);

            // Attempt to return the phone
            boolean returnSuccess = bookingStrategy.returnPhone(bookingManager, phoneId, userId);

            if (returnSuccess) {
                BookingResponse response = new BookingResponse(STR."Phone returned successfully for user: \{userId}", true);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                // This might mean the phone was not booked by the user, or an error occurred
                BookingResponse response = new BookingResponse("Failed to return phone. It might not be booked by this user or an error occurred.", false);
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (IllegalArgumentException e) {
            BookingResponse response = new BookingResponse(STR."Invalid request: \{e.getMessage()}", false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            BookingResponse response = new BookingResponse("An error occurred while processing the return.", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to switch booking strategy at runtime.
     *
     * @param strategyName the name of the strategy bean to switch to
     * @return ResponseEntity indicating the outcome of the operation
     */
    @Operation(
            summary = "Change Booking Strategy",
            description = "Changes the booking strategy used by the booking manager.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Strategy changed successfully",
                            content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "404", description = "Strategy not found",
                            content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @PostMapping("/strategy")
    public ResponseEntity<?> changeStrategy(
            @Parameter(description = "The name of the strategy bean to switch to. Supported values include: 'simpleStrategy', 'priorityStrategy', 'advancedStrategy'",
                    required = true,
                    schema = @Schema(allowableValues = { "simpleStrategy", "priorityStrategy", "advancedStrategy" }))
            @RequestParam String strategyName) {
        try {
            BookingStrategy strategy = applicationContext.getBean(strategyName, BookingStrategy.class);
            this.setBookingStrategy(strategy);
            return ResponseEntity.ok(Map.of("message", STR."Strategy changed to \{strategyName}"));
        } catch (NoSuchBeanDefinitionException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(STR."Strategy not found: \{strategyName}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(STR."Error changing strategy: \{e.getMessage()}");
        }
    }
}