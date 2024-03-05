package com.example.booking.controller;

import com.example.booking.entity.MobilePhone;
import com.example.booking.service.DeviceMonitoringService;
import com.example.booking.service.MobilePhoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for managing mobile phones within the booking system.
 * Provides endpoints for creating new mobile phones and retrieving the status of all phones.
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/mobilephones")
@AllArgsConstructor
public class MobilePhoneController {

    private final MobilePhoneService mobilePhoneService;
    private final DeviceMonitoringService deviceMonitoringService;

    /**
     * Endpoint to create a new mobile phone instance based on the provided model.
     * This endpoint only supports a predefined set of models. Attempting to create a mobile phone
     * with an unsupported model will result in a BAD REQUEST response.
     *
     * @param model The model of the phone to be created.
     * @return ResponseEntity containing the created MobilePhone or an error message.
     */
    @Operation(summary = "Create a new mobile phone instance",
            description = "Creates a new mobile phone instance based on the provided model. " +
                    "The service currently supports a limited set of models. " +
                    "Providing an unsupported model results in a BAD REQUEST response.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Mobile phone created successfully",
                            content = @Content(schema = @Schema(implementation = MobilePhone.class))),
                    @ApiResponse(responseCode = "400", description = "Unsupported phone model provided",
                            content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content)
            })
    @PostMapping("/")
    public ResponseEntity<?> createMobilePhone(
            @Parameter(description = "Model of the phone to create. Supported models: 'Samsung Galaxy S9', 'Samsung Galaxy S8', 'Motorola Nexus 6', 'Oneplus 9', 'Apple iPhone 13', 'Apple iPhone 12', 'Apple iPhone 11', 'iPhone X', 'Nokia 3310'.",
                    required = true,
                    example = "Samsung Galaxy S9",
                    schema = @Schema(allowableValues = {
                            "Samsung Galaxy S9", "Samsung Galaxy S8", "Motorola Nexus 6",
                            "Oneplus 9", "Apple iPhone 13", "Apple iPhone 12", "Apple iPhone 11",
                            "iPhone X", "Nokia 3310"}))
            @RequestParam String model) {
        try {
            MobilePhone newPhone = mobilePhoneService.createMobilePhone(model);
            if (newPhone == null) {
                return ResponseEntity.badRequest().body("The phone model is not supported.");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(newPhone);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(STR."An error occurred while creating the phone: \{e.getMessage()}");
        }
    }

    /**
     * Retrieves the status of all mobile phones in the system.
     * This endpoint provides a summary of the status for each mobile phone.
     *
     * @return ResponseEntity with the status of all mobile phones.
     */
    @Operation(summary = "Retrieve status of all mobile phones",
            description = "Retrieves the current status of all mobile phones within the system. " +
                    "This can include availability, condition, and any other relevant status indicators.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status retrieved successfully",
                            content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content)
            })
    @GetMapping("/status")
    public ResponseEntity<String> getAllPhoneStatuses() {
        try {
            String statuses = deviceMonitoringService.getAllPhoneStatuses();
            return ResponseEntity.ok(statuses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(STR."An error occurred while retrieving phone statuses: \{e.getMessage()}");
        }
    }
}