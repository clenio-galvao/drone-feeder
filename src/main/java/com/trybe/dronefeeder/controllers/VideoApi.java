package com.trybe.dronefeeder.controllers;

import com.trybe.dronefeeder.dtos.VideoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/** Video API interface. */
@Api(value = "Video", tags = "Video")
public interface VideoApi {

  @ApiOperation(value = "Get a paginated list of videos", tags = "Video")
  public ResponseEntity<Page<VideoDto>> findAll(Pageable pageable);

  @ApiOperation(value = "Upload a new video", tags = "Video")
  public ResponseEntity<VideoDto> upload(@RequestBody VideoDto videoDto);

  @ApiOperation(value = "Download a new video", tags = "Video")
  public ResponseEntity<VideoDto> download(@PathVariable Long id);
}
