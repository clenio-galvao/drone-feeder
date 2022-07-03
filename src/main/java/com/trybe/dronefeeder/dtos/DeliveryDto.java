package com.trybe.dronefeeder.dtos;

import static com.trybe.dronefeeder.utils.Messages.VALIDATION_IS_EMPTY;
import static com.trybe.dronefeeder.utils.Messages.VALIDATION_IS_REQUIRED;
import static com.trybe.dronefeeder.utils.Messages.VALIDATION_SIZE;

import com.trybe.dronefeeder.models.Delivery;
import com.trybe.dronefeeder.models.Drone;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/** dto class delivery. */
public class DeliveryDto implements Serializable {

  private static final long serialVersionUID = -5319810718389636837L;
  @ApiModelProperty(notes = "Delivery", example = "7")
  private Long id;
  
  @NotEmpty(message = "latitudeWithdrawal" + VALIDATION_IS_EMPTY)
  @NotNull(message = "latitudeWithdrawal" + VALIDATION_IS_REQUIRED)
  @Size(min = 7, max = 10, message = "latitudeWithdrawal" + VALIDATION_SIZE)
  @ApiModelProperty(notes = "latitude pacage withdrawal", example = "41.40338", required = true)
  private String latitudeWithdrawal;
  
  @NotEmpty(message = "longitudeWithdrawal" + VALIDATION_IS_EMPTY)
  @NotNull(message = "longitudeWithdrawal" + VALIDATION_IS_REQUIRED)
  @Size(min = 7, max = 10, message = "longitudeWithdrawal" + VALIDATION_SIZE)
  @ApiModelProperty(notes = "longitude pacage withdrawal", example = "2.17403", required = true)
  private String longitudeWithdrawal;
  private LocalDateTime dateWithdrawal;

  @NotEmpty(message = "latitudeDelivery" + VALIDATION_IS_EMPTY)
  @NotNull(message = "latitudeDelivery" + VALIDATION_IS_REQUIRED)
  @Size(min = 7, max = 10, message = "latitudeDelivery" + VALIDATION_SIZE)
  @ApiModelProperty(notes = "latitude pacage delivery", example = "41.40338", required = true)
  private String latitudeDelivery;
  
  @NotEmpty(message = "longitudeDelivery" + VALIDATION_IS_EMPTY)
  @NotNull(message = "longitudeDelivery" + VALIDATION_IS_REQUIRED)
  @Size(min = 7, max = 10, message = "longitudeDelivery" + VALIDATION_SIZE)
  @ApiModelProperty(notes = "longitude pacage delivery", example = "2.17403", required = true)
  private String longitudeDelivery;
  private LocalDateTime dateDelivery;

  private String videoNameDelivery;
  
  @NotEmpty(message = "drone" + VALIDATION_IS_EMPTY)
  @NotNull(message = "drone" + VALIDATION_IS_REQUIRED)
  @ApiModelProperty(notes = "drone id", example = "2", required = true)
  private Drone drone;
  
  public DeliveryDto() {}
  
  /** constructor method. */
  public DeliveryDto(Delivery entity) {
    this.id = entity.getId();
    this.latitudeWithdrawal = entity.getLatitudeWithdrawal();
    this.longitudeWithdrawal = entity.getLongitudeWithdrawal();
    this.dateWithdrawal = entity.getDateWithdrawal();
    this.latitudeDelivery = entity.getLatitudeDelivery();
    this.longitudeDelivery = entity.getLongitudeDelivery();
    this.dateDelivery = entity.getDateDelivery();
    this.videoNameDelivery = entity.getVideoNameDelivery();
    this.drone = entity.getDrone();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLatitudeWithdrawal() {
    return latitudeWithdrawal;
  }

  public void setLatitudeWithdrawal(String latitudeWithdrawal) {
    this.latitudeWithdrawal = latitudeWithdrawal;
  }

  public String getLongitudeWithdrawal() {
    return longitudeWithdrawal;
  }

  public void setLongitudeWithdrawal(String longitudeWithdrawal) {
    this.longitudeWithdrawal = longitudeWithdrawal;
  }

  public LocalDateTime getDateWithdrawal() {
    return dateWithdrawal;
  }

  public void setDateWithdrawal(LocalDateTime dateWithdrawal) {
    this.dateWithdrawal = dateWithdrawal;
  }

  public String getLatitudeDelivery() {
    return latitudeDelivery;
  }

  public void setLatitudeDelivery(String latitudeDelivery) {
    this.latitudeDelivery = latitudeDelivery;
  }

  public String getLongitudeDelivery() {
    return longitudeDelivery;
  }

  public void setLongitudeDelivery(String longitudeDelivery) {
    this.longitudeDelivery = longitudeDelivery;
  }

  public LocalDateTime getDateDelivery() {
    return dateDelivery;
  }

  public void setDateDelivery(LocalDateTime dateDelivery) {
    this.dateDelivery = dateDelivery;
  }

  public String getVideoNameDelivery() {
    return videoNameDelivery;
  }

  public void setVideoNameDelivery(String videoNameDelivery) {
    this.videoNameDelivery = videoNameDelivery;
  }

  public Drone getDrone() {
    return drone;
  }

  public void setDrone(Drone drone) {
    this.drone = drone;
  }
}
