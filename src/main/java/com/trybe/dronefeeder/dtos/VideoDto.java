package com.trybe.dronefeeder.dtos;

import com.trybe.dronefeeder.models.Video;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/** Class VideoDto. */
public class VideoDto implements Serializable {
  private static final long serialVersionUID = 7193684038002132402L;
  
  @ApiModelProperty(notes = "Video ID", example = "7")
  private Long id;
  
  private long deliveryId;
  
  private String name;
  
  public VideoDto() {}
  
  /** constructor method. */
  public VideoDto(Video entity) {
    this.id = entity.getId();
    this.deliveryId = entity.getDelivery().getId();
    this.name = entity.getName();
  }

  public long getDeliveryId() {
    return deliveryId;
  }

  public void setDeliveryId(long deliveryId) {
    this.deliveryId = deliveryId;
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
