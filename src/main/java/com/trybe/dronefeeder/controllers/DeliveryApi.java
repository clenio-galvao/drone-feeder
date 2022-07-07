package com.trybe.dronefeeder.controllers;

import com.trybe.dronefeeder.dtos.DeliveryDto;
import com.trybe.dronefeeder.dtos.DeliveryResponseDto;
import com.trybe.dronefeeder.models.Delivery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/** delivery api interface. */
@Api(value = "Delivery", tags = "Delivery")
public interface DeliveryApi {
  @ApiOperation(value = "Get a paginated list of registered drones", tags = "Drone")
  public ResponseEntity<List<DeliveryResponseDto>> findAll(@RequestParam("droneId") Long droneId);

  @ApiOperation(value = "Get a registered drone by providing ID", tags = "Drone")
  public ResponseEntity<DeliveryDto> findById(@PathVariable Long id);

  @ApiOperation(value = "Create a new drone", tags = "Drone")
  public ResponseEntity<DeliveryDto> create(@RequestBody DeliveryDto deliveryDto);

  @ApiOperation(value = "Update a drone by providing the ID", tags = "Drone")
  public ResponseEntity<DeliveryDto> update(
      @PathVariable Long id, @RequestBody DeliveryDto deliveryDto);

  @ApiOperation(value = "Delete a registered drone by providing the ID", tags = "Drone")
  public ResponseEntity<Void> delete(@PathVariable Long id);
}
