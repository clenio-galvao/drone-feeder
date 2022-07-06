package com.trybe.dronefeeder.repositories;

import com.trybe.dronefeeder.models.Delivery;
import com.trybe.dronefeeder.models.Drone;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/** delivery repository interface. */
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
	
  public List<Delivery> findAllByDrone(Drone drone);
}
