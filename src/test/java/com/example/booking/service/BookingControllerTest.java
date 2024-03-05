package com.example.booking.service;

import com.example.booking.controller.BookingController;
import com.example.booking.entity.dto.BookingRequest;
import com.example.booking.entity.dto.BookingResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookingControllerTest {

    @Mock
    BookingStrategy bookingStrategy;

    @Mock
    BookingManager bookingManager;

    @InjectMocks
    BookingController bookingController;

    @Mock
    ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testChangeStrategy_StrategyNotFound() {
        String strategyName = "invalidStrategy";
        when(applicationContext.getBean(strategyName, BookingStrategy.class)).thenThrow(NoSuchBeanDefinitionException.class);

        ResponseEntity<?> responseEntity = bookingController.changeStrategy(strategyName);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Strategy not found: invalidStrategy", responseEntity.getBody());
        verifyNoInteractions(bookingManager);
    }

    @Test
    void testChangeStrategy_InternalServerError() {
        String strategyName = "simpleStrategy";
        when(applicationContext.getBean(strategyName, BookingStrategy.class)).thenThrow(RuntimeException.class);

        ResponseEntity<?> responseEntity = bookingController.changeStrategy(strategyName);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Error changing strategy: null", responseEntity.getBody());
        verifyNoInteractions(bookingManager);
    }
}