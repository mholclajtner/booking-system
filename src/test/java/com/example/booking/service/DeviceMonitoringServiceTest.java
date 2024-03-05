package com.example.booking.service;

import com.example.booking.entity.Booking;
import com.example.booking.entity.MobilePhone;
import com.example.booking.monitoring.DeviceMonitor;
import com.example.booking.monitoring.entity.DeviceContext;
import com.example.booking.monitoring.entity.DeviceMetrics;
import com.example.booking.repository.MobilePhoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

class DeviceMonitoringServiceTest {

    @Mock
    private MobilePhoneRepository mobilePhoneRepository;
    @Mock
    private BookingManager bookingManager;
    @Mock
    private DeviceMonitor deviceMonitor;
    @Mock
    private Booking booking;

    private DeviceMonitoringService deviceMonitoringService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deviceMonitoringService = new DeviceMonitoringService(mobilePhoneRepository, bookingManager);
    }

    @Test
    void getAllPhoneStatuses_ReturnsCorrectJson() throws NoSuchFieldException, IllegalAccessException {

        DeviceContext context = new DeviceContext("123", new DeviceMetrics(100, 2048, 1024, 0.1));
        when(deviceMonitor.getContext()).thenReturn(context);
        when(deviceMonitor.getStatus()).thenReturn("OK");
        when(bookingManager.findBookingByDeviceId("123")).thenReturn(Optional.of(booking));

        ConcurrentHashMap<String, DeviceMonitor> deviceMonitorsMap = new ConcurrentHashMap<>();
        deviceMonitorsMap.put("123", deviceMonitor);

        Field mapField = DeviceMonitoringService.class.getDeclaredField("deviceMonitorsMap");
        mapField.setAccessible(true);
        mapField.set(deviceMonitoringService, deviceMonitorsMap);

        String jsonStatuses = deviceMonitoringService.getAllPhoneStatuses();

        assertNotNull(jsonStatuses);
        assertFalse(jsonStatuses.isEmpty());

    }

    @Test
    void updateDeviceMetricsIfNeeded_UpdatesMetricsWhenConditionMet() {

        MobilePhone phone = mock(MobilePhone.class);
        DeviceMetrics originalMetrics = new DeviceMetrics(100, 2048, 499, 0.1); // Below the threshold
        when(phone.getDeviceMetrics()).thenReturn(originalMetrics);
        when(phone.isAvailable()).thenReturn(false);

        deviceMonitoringService.updateDeviceMetricsIfNeeded(phone);

        verify(phone).setDeviceMetrics(any(DeviceMetrics.class));
        verify(mobilePhoneRepository).save(phone);

    }
}