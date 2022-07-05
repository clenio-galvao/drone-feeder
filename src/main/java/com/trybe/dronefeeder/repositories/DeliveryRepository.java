package com.trybe.dronefeeder.repositories;

import com.trybe.dronefeeder.models.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

/** delivery repository interface. */
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {}
