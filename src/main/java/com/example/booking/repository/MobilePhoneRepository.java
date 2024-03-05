package com.example.booking.repository;

import com.example.booking.entity.MobilePhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing a collection of MobilePhones.
 * Mobile phones can be added, updated, and queried by ID.
 *
 * @author Milos Holclajtner
 * @version 1.0
 * @since 1.0
 */
@Repository
public interface MobilePhoneRepository extends JpaRepository<MobilePhone, String> { }