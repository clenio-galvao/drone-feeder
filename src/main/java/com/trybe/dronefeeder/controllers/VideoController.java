package com.trybe.dronefeeder.controllers;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.trybe.dronefeeder.dtos.VideoDto;
import com.trybe.dronefeeder.services.VideoService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


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
  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) 
  public ResponseEntity<VideoDto> upload(
      @RequestPart("file") MultipartFile file, @RequestParam("deliveryId") long deliveryId)
      throws AmazonServiceException, SdkClientException, IOException {
    VideoDto videoDto = service.upload(deliveryId, file);
    return ResponseEntity.ok(videoDto);
  }

  /** get route to download a video. */
  @GetMapping(value = "/download/{id}")
  public ResponseEntity<ByteArrayResource> download(@PathVariable Long id) throws IOException {
    byte[] data = service.download(id);
    ByteArrayResource resource = new ByteArrayResource(data);
    
    return ResponseEntity
      .ok()
      .contentLength(data.length)
      .header("Content-type", "application/octet-stream")
      .header("Content-disposition", "attachment; filename=\"" + "\"")
      .body(resource);
  }

}
