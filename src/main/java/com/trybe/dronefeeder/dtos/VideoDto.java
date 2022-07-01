package com.trybe.dronefeeder.dtos;

import static com.trybe.dronefeeder.utils.Messages.VALIDATION_VIDEO_DELIVERY_IS_EMPTY;
import static com.trybe.dronefeeder.utils.Messages.VALIDATION_VIDEO_DELIVERY_IS_REQUIRED;

import com.trybe.dronefeeder.models.Delivery;
import com.trybe.dronefeeder.models.Video;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/** Class VideoDto. */
public class VideoDto implements Serializable {
  private static final long serialVersionUID = 7193684038002132402L;
  
  @ApiModelProperty(notes = "Video ID", example = "7")
  private Long id;
  
  @NotEmpty(message = VALIDATION_VIDEO_DELIVERY_IS_EMPTY)
  @NotNull(message = VALIDATION_VIDEO_DELIVERY_IS_REQUIRED)
  @ApiModelProperty(notes = "delivery: id", required = true)
  private Delivery delivery;
  
  private String name;
  
  public VideoDto() {}
  
  public VideoDto(Video entity) {
    this.delivery = entity.getDelivery();
  }

  public Delivery getDelivery() {
    return delivery;
  }

  public void setDelivery(Delivery delivery) {
    this.delivery = delivery;
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
