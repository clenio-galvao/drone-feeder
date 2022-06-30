package com.trybe.dronefeeder.dtos;

import static com.trybe.dronefeeder.utils.Messages.VALIDATION_VIDEO_NAME_IS_EMPTY;
import static com.trybe.dronefeeder.utils.Messages.VALIDATION_VIDEO_NAME_IS_REQUIRED;
import static com.trybe.dronefeeder.utils.Messages.VALIDATION_VIDEO_NAME_SIZE;

import com.trybe.dronefeeder.models.Video;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/** Class VideoDto. */
public class VideoDto implements Serializable {
  private static final long serialVersionUID = 7193684038002132402L;
  
  @ApiModelProperty(notes = "Video ID", example = "23")
  private Long id;

  @NotEmpty(message = VALIDATION_VIDEO_NAME_IS_EMPTY)
  @NotNull(message = VALIDATION_VIDEO_NAME_IS_REQUIRED)
  @Size(min = 30, max = 100, message = VALIDATION_VIDEO_NAME_SIZE)
  @ApiModelProperty(
      notes = "Video name",
      example = "2022-03-22T12:35:32-delivery-3",
      required = true
  )
  private String name;
  
  public VideoDto() {}
  
  public VideoDto(Video entity) {
    this.id = entity.getId();
    this.name = entity.getName();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
