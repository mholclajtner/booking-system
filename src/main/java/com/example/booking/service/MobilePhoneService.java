package com.example.booking.service;

import com.example.booking.entity.MobilePhone;
import com.example.booking.entity.event.MobilePhoneAddedEvent;
import com.example.booking.factory.MobilePhoneFactory;
import com.example.booking.repository.MobilePhoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Service class for managing mobile phone operations.
 * This service handles the creation and management of mobile phones,
 * leveraging a mobile phone repository for persistence.
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
@Service
@AllArgsConstructor
public class MobilePhoneService {

    private final MobilePhoneRepository mobilePhoneRepository;

    private final ApplicationEventPublisher eventPublisher;


    /**
     * Creates a new mobile phone with the specified model.
     * This method utilizes the MobilePhoneFactory for creating a mobile phone instance,
     * sets the phone's availability to true, and saves it to the repository.
     *
     * @param model the model of the mobile phone to be created.
     * @return the newly created and saved MobilePhone object.
     */
    public MobilePhone createMobilePhone(String model) {
        MobilePhone newPhone = MobilePhoneFactory.createPhone(model);
        assert newPhone != null;
        newPhone.setAvailable(true);
        MobilePhone result = mobilePhoneRepository.save(newPhone);
        eventPublisher.publishEvent(new MobilePhoneAddedEvent(this, result));
        return result;
    }
}