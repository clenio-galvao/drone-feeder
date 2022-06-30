package com.trybe.dronefeeder.repositories;

import com.trybe.dronefeeder.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;

/** Interface video repository. */
public interface VideoRepository extends JpaRepository<Video, Long> {}
