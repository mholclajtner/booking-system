package com.example.booking.factory.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The {@code PhoneModel} annotation is used to designate methods or types
 * that represent a specific mobile phone model within the mobile phone factory.
 * It allows for dynamic identification and instantiation of mobile phone models
 * based on the specified model identifier.
 * <p>
 * This annotation can be applied to types or methods, depending on the design
 * approach chosen for implementing the mobile phone factory. When applied to a
 * method, the method is expected to create and return an instance of a mobile
 * phone model. When applied to a type, the type is recognized as a factory
 * for a specific mobile phone model.
 * </p>
 * <p>
 * The {@code value} attribute of the annotation should be set to a string that
 * uniquely identifies the mobile phone model, such as "Samsung Galaxy S9" or
 * "Apple iPhone 13". This identifier is used by the {@link
 * com.example.booking.factory.MobilePhoneFactory} to create instances of the
 * corresponding mobile phone model.
 * </p>
 *
 * @see com.example.booking.entity.MobilePhone
 * @see com.example.booking.factory.MobilePhoneFactory
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface PhoneModel {
    /**
     * Specifies the unique model identifier for a mobile phone model. This identifier
     * is used by the {@link com.example.booking.factory.MobilePhoneFactory} to match
     * and instantiate the corresponding mobile phone model.
     *
     * @return The unique model identifier for the annotated mobile phone model.
     */
    String value();
}
