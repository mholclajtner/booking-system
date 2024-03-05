package com.example.booking.service;

import com.example.booking.controller.MobilePhoneController;
import com.example.booking.entity.MobilePhone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MobilePhoneControllerTest {

    @Mock
    MobilePhoneService mobilePhoneService;

    @Mock
    DeviceMonitoringService deviceMonitoringService;

    @InjectMocks
    MobilePhoneController mobilePhoneController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateMobilePhone_Success() {
        String model = "Samsung Galaxy S9";
        MobilePhone mockPhone = new MobilePhone();
        when(mobilePhoneService.createMobilePhone(model)).thenReturn(mockPhone);

        ResponseEntity<?> responseEntity = mobilePhoneController.createMobilePhone(model);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(mockPhone, responseEntity.getBody());
    }

    @Test
    void testCreateMobilePhone_UnsupportedModel() {
        String model = "Unsupported Model";
        when(mobilePhoneService.createMobilePhone(model)).thenReturn(null);

        ResponseEntity<?> responseEntity = mobilePhoneController.createMobilePhone(model);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("The phone model is not supported.", responseEntity.getBody());
    }

    @Test
    void testGetAllPhoneStatuses_Success() {
        String mockStatuses = "Statuses";
        when(deviceMonitoringService.getAllPhoneStatuses()).thenReturn(mockStatuses);

        ResponseEntity<String> responseEntity = mobilePhoneController.getAllPhoneStatuses();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockStatuses, responseEntity.getBody());
    }
}
