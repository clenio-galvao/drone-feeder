package com.trybe.dronefeeder.controllers;

import com.trybe.dronefeeder.dtos.DeliveryDto;
import com.trybe.dronefeeder.services.DeliveryService;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** delivery controller class. */
@RestController
@RequestMapping("/deliveries")
public class DeliveryController implements DeliveryApi {

  @Autowired
  private DeliveryService deliveryService;

  @GetMapping
  public ResponseEntity<Page<DeliveryDto>> findAll(Pageable pageable) {
    Page<DeliveryDto> list = deliveryService.findAllPaged(pageable);
    return ResponseEntity.ok(list);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<DeliveryDto> findById(Long id) {
    return ResponseEntity.ok(deliveryService.findById(id));
  }

  /** post route. */
  @PostMapping
  public ResponseEntity<DeliveryDto> create(DeliveryDto deliveryDto) {
    deliveryDto = deliveryService.create(deliveryDto);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
          .buildAndExpand(deliveryDto.getId()).toUri();
    return ResponseEntity.created(uri).body(deliveryDto);
  }

  /** put route. */
  @PutMapping(value = "/{id}")
  public ResponseEntity<DeliveryDto> update(Long id, DeliveryDto deliveryDto) {
    return ResponseEntity.ok(deliveryService.update(id, deliveryDto));
  }

  /** delete route. */
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(Long id) {
    deliveryService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
