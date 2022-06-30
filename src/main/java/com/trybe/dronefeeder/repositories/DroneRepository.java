package com.trybe.dronefeeder.repositories;


import com.trybe.dronefeeder.models.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepository extends JpaRepository<Drone, Long> {
}
