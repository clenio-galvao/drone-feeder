package com.trybe.dronefeeder.controllers;

import com.trybe.dronefeeder.dtos.DroneDto;
import com.trybe.dronefeeder.services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/drones")
public class DroneContoller implements DroneApi {

    @Autowired
    private DroneService droneService;

    @GetMapping
    public ResponseEntity<Page<DroneDto>> findAll(Pageable pageable) {
        Page<DroneDto> list = droneService.findAllPaged(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DroneDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(droneService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DroneDto> create(@RequestBody DroneDto droneDto) {
        droneDto = droneService.create(droneDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(droneDto.getId()).toUri();
        return ResponseEntity.created(uri).body(droneDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DroneDto> update(@PathVariable Long id, @RequestBody DroneDto droneDto) {
        return ResponseEntity.ok(droneService.update(id, droneDto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        droneService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
