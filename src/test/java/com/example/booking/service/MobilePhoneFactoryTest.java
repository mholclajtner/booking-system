package com.example.booking.service;

import com.example.booking.entity.MobilePhone;
import com.example.booking.factory.MobilePhoneFactory;
import com.example.booking.factory.annotation.PhoneModel;
import com.example.booking.monitoring.entity.DeviceMetrics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MobilePhoneFactoryTest {

    @Test
    void testCreatePhone_Success() {
        MobilePhone phone = MobilePhoneFactory.createPhone("Samsung Galaxy S9");
        assertNotNull(phone);
        assertEquals("Samsung Galaxy S9-1", phone.getId());
    }

    @Test
    void testCreatePhone_UnsupportedModel() {
        MobilePhone phone = MobilePhoneFactory.createPhone("UnknownModel");
        assertNull(phone);
    }

    @Test
    void testGenerateId() {
        String model = "GalaxyS21";
        String id1 = MobilePhoneFactory.generateId(model);
        String id2 = MobilePhoneFactory.generateId(model);

        assertNotEquals(id1, id2);
        assertTrue(id1.startsWith(model + "-"));
        assertTrue(id2.startsWith(model + "-"));
    }

    @PhoneModel("GalaxyS21")
    public static MobilePhone createGalaxyS21(String id) {
        return new MobilePhone(id, "Samsung Galaxy S9", new DeviceMetrics(1, 1, 1, 1));
    }
}