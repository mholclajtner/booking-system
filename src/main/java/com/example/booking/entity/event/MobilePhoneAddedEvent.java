package com.example.booking.entity.event;

import com.example.booking.entity.MobilePhone;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * A custom application event that signifies a new mobile phone has been added to the application.
 * It extends the {@link ApplicationEvent} class, making it suitable for use within the Spring application context
 * to communicate between components in an event-driven manner.
 *
 * This event can be published using the {@link org.springframework.context.ApplicationEventPublisher} interface
 * whenever a new mobile phone is added to the system, enabling other components to respond to this addition,
 * such as updating inventories, notifying services, or refreshing UI components.
 *
 * Usage example (within a Spring service method):
 * <pre>{@code
 *  public void addMobilePhone(MobilePhone mobilePhone) {
 *      // Logic to add the mobile phone to the system...
 *      MobilePhoneAddedEvent event = new MobilePhoneAddedEvent(this, mobilePhone);
 *      applicationEventPublisher.publishEvent(event);
 *  }
 * }</pre>
 *
 * Listeners can then handle this event to perform various operations in response to the new mobile phone addition.
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
@Getter
public class MobilePhoneAddedEvent extends ApplicationEvent {

    /**
     * The MobilePhone instance added to the system.
     */
    private final MobilePhone mobilePhone;

    /**
     * Creates a new {@code MobilePhoneAddedEvent}.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     * @param mobilePhone the {@code MobilePhone} that has been added to the system
     */
    public MobilePhoneAddedEvent(Object source, MobilePhone mobilePhone) {
        super(source);
        this.mobilePhone = mobilePhone;
    }

}