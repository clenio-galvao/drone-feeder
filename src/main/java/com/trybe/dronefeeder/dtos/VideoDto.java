package com.trybe.dronefeeder.dtos;

import static com.trybe.dronefeeder.utils.Messages.VALIDATION_VIDEO_DELIVERY_IS_EMPTY;
import static com.trybe.dronefeeder.utils.Messages.VALIDATION_VIDEO_DELIVERY_IS_REQUIRED;
import static com.trybe.dronefeeder.utils.Messages.VALIDATION_VIDEO_FILE_IS_EMPTY;
import static com.trybe.dronefeeder.utils.Messages.VALIDATION_VIDEO_FILE_IS_REQUIRED;

import com.trybe.dronefeeder.models.Delivery;
import com.trybe.dronefeeder.models.Video;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

/** Class VideoDto. */
public class VideoDto implements Serializable {
  private static final long serialVersionUID = 7193684038002132402L;
  
  @NotEmpty(message = VALIDATION_VIDEO_FILE_IS_EMPTY)
  @NotNull(message = VALIDATION_VIDEO_FILE_IS_REQUIRED)
  @ApiModelProperty(notes = "Video file", required = true)
  private MultipartFile file;
  
  @NotEmpty(message = VALIDATION_VIDEO_DELIVERY_IS_EMPTY)
  @NotNull(message = VALIDATION_VIDEO_DELIVERY_IS_REQUIRED)
  @ApiModelProperty(notes = "delivery: id", required = true)
  private Delivery delivery;
  
  public VideoDto() {}
  
  public VideoDto(Video entity) {
    this.delivery = entity.getDelivery();
  }

  public MultipartFile getFile() {
    return file;
  }

  public void setFile(MultipartFile file) {
    this.file = file;
  }

  public Delivery getDelivery() {
    return delivery;
  }

  public void setDelivery(Delivery delivery) {
    this.delivery = delivery;
  }

}
