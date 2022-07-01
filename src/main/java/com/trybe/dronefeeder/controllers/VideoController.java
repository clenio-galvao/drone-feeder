package com.trybe.dronefeeder.controllers;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.trybe.dronefeeder.dtos.VideoDto;
import com.trybe.dronefeeder.services.VideoService;
import java.io.IOException;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


/** class video controller. */
@RestController
@RequestMapping("/videos")
public class VideoController implements VideoApi {

  @Autowired
  VideoService service;

  @GetMapping
  public ResponseEntity<Page<VideoDto>> findAll(Pageable pageable) {
    Page<VideoDto> list = service.findAll(pageable);
    return ResponseEntity.ok(list);
  }

  /** post route to upload a video. */
  @PostMapping
  public ResponseEntity<VideoDto> upload(
      @RequestParam MultipartFile file, @RequestBody VideoDto videoDto)
      throws AmazonServiceException, SdkClientException, IOException {
    videoDto = service.upload(videoDto.getDelivery(), file);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
              .buildAndExpand(videoDto.getId()).toUri();
    return ResponseEntity.created(uri).body(videoDto);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<byte[]> download(@PathVariable Long id) throws IOException {
    return ResponseEntity.ok(service.download(id));
  }

}
