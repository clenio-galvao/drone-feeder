package com.trybe.dronefeeder.controllers;

import com.trybe.dronefeeder.dtos.DeliveryDto;
import com.trybe.dronefeeder.services.DeliveryService;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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
  public ResponseEntity<DeliveryDto> findById(@PathVariable Long id) {
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
  public ResponseEntity<DeliveryDto> update(@PathVariable Long id, DeliveryDto deliveryDto) {
    return ResponseEntity.ok(deliveryService.update(id, deliveryDto));
  }

  /**
   * put finalize delivery route. 
   * []@throws IOException exception
   * */
  @PutMapping(value = "/finalize/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<DeliveryDto> packageDelivered(@PathVariable long id,
      @RequestPart("file") MultipartFile file) throws IOException {
    DeliveryDto deliveryDto = deliveryService.packageDelivered(id, file);
    return ResponseEntity.ok(deliveryDto);
  }

  /**
   * put collect package delivery route. 
   * []@throws IOException exception
   * */
  @PutMapping(value = "/collected/{id}")
  public ResponseEntity<DeliveryDto> collectedPackage(@PathVariable long id) {
    DeliveryDto deliveryDto = deliveryService.collectedPackage(id);
    return ResponseEntity.ok(deliveryDto);
  }

  /** download a video. */
  @GetMapping("/videos/download/{id}")
  public ResponseEntity<ByteArrayResource> download(@PathVariable Long id) throws IOException {
    byte[] data = deliveryService.download(id);
    ByteArrayResource resource = new ByteArrayResource(data);

    return ResponseEntity
      .ok()
      .contentLength(data.length)
      .header("Content-type", "application/octet-stream")
      .header("Content-disposition", "attachment; filename=\"" + "delivery_video_" + id + "\"")
      .body(resource);
  }

  @GetMapping("/videos")
  public ResponseEntity<List<String>> findAllVideos() {
    List<String> list = deliveryService.findAllVideos();
    return ResponseEntity.ok(list);
  }
  
  /** delete route. */
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(Long id) {
    deliveryService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
