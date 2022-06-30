package com.trybe.dronefeeder.controllers;

import com.trybe.dronefeeder.dtos.DroneDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@Api(value = "Drone", tags = "Drone")
public interface DroneApi {

    @ApiOperation(value = "Get a paginated list of registered drones", tags = "Drone")
    public ResponseEntity<Page<DroneDto>> findAll(Pageable pageable);

    @ApiOperation(value = "Get a registered drone by providing ID", tags = "Drone")
    public ResponseEntity<DroneDto> findById(@PathVariable Long id);

    @ApiOperation(value = "Create a new drone", tags = "Drone")
    public ResponseEntity<DroneDto> create(@RequestBody DroneDto droneDto);

    @ApiOperation(value = "Update a drone by providing the ID", tags = "Drone")
    public ResponseEntity<DroneDto> update(@PathVariable Long id, @RequestBody DroneDto droneDto);

    @ApiOperation(value = "Delete a registered drone by providing the ID", tags = "Drone")
    public ResponseEntity<Void> delete(@PathVariable Long id);
}
