package com.trybe.dronefeeder.controllers;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.trybe.dronefeeder.dtos.VideoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/** Video API interface. */
@Api(value = "Video", tags = "Video")
public interface VideoApi {

  @ApiOperation(value = "Get a paginated list of videos", tags = "Video")
  public ResponseEntity<Page<VideoDto>> findAll(Pageable pageable);

  @ApiOperation(value = "Upload a new video", tags = "Video")
  public ResponseEntity<VideoDto> upload(
      @RequestPart MultipartFile file, @RequestParam long deliveryId)
      throws AmazonServiceException, SdkClientException, IOException;

  @ApiOperation(value = "Download a new video", tags = "Video")
  public ResponseEntity<ByteArrayResource> download(@PathVariable Long id) throws IOException;
}
