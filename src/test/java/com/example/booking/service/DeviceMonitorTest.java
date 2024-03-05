package com.example.booking.service;

import com.example.booking.monitoring.DeviceMonitor;
import com.example.booking.monitoring.entity.DeviceContext;
import com.example.booking.monitoring.tasks.MonitoringTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class DeviceMonitorTest {

    @Mock
    private DeviceContext mockContext;
    @Mock
    private MonitoringTask mockTask;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockContext.deviceId()).thenReturn("12345");
        when(mockTask.getStatus()).thenReturn("Mock task status");
    }

    @Test
    public void testDeviceMonitorTaskInitialization() {

        List<MonitoringTask> tasks = new ArrayList<>();
        tasks.add(mockTask);

        DeviceMonitor deviceMonitor = new DeviceMonitor(mockContext, tasks);
        String status = deviceMonitor.getStatus();

        assertTrue(status.contains("Mock task status"), "The status should contain the mock task status");
    }
}