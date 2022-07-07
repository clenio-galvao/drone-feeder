package com.trybe.dronefeeder.dtos;

import com.trybe.dronefeeder.models.Delivery;
import com.trybe.dronefeeder.models.Drone;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.trybe.dronefeeder.utils.Messages.*;

/** dto class delivery. */
public class DeliveryResponseDto implements Serializable {

  private static final long serialVersionUID = -5319810718389636837L;
  @ApiModelProperty(notes = "Delivery", example = "7")
  private Long id;

  @ApiModelProperty(notes = "latitude pacage withdrawal", example = "41.40338", required = true)
  private String latitudeWithdrawal;

  @ApiModelProperty(notes = "longitude pacage withdrawal", example = "2.17403", required = true)
  private String longitudeWithdrawal;
  private String dateWithdrawal;

  @ApiModelProperty(notes = "latitude pacage delivery", example = "41.40338", required = true)
  private String latitudeDelivery;

  @ApiModelProperty(notes = "longitude pacage delivery", example = "2.17403", required = true)
  private String longitudeDelivery;
  private String dateDelivery;

  private String videoNameDelivery;

  @ApiModelProperty(notes = "drone id", example = "2", required = true)
  private Drone drone;

  public DeliveryResponseDto() {}

  /** constructor method. */
  public DeliveryResponseDto(Delivery entity) {
    this.id = entity.getId();
    this.latitudeWithdrawal = entity.getLatitudeWithdrawal();
    this.longitudeWithdrawal = entity.getLongitudeWithdrawal();
    this.dateWithdrawal = Objects.isNull(entity.getDateWithdrawal()) ? null : entity.getDateWithdrawal().toString();
    this.latitudeDelivery = entity.getLatitudeDelivery();
    this.longitudeDelivery = entity.getLongitudeDelivery();
    this.dateDelivery = Objects.isNull(entity.getDateDelivery()) ? null : entity.getDateDelivery().toString();
    this.videoNameDelivery = entity.getVideoNameDelivery();
    System.out.println(entity.getDrone().getBrand());
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


  public String getLongitudeWithdrawal() {
    return longitudeWithdrawal;
  }


  public String getDateWithdrawal() {
    return dateWithdrawal;
  }


  public String getLatitudeDelivery() {
    return latitudeDelivery;
  }


  public String getLongitudeDelivery() {
    return longitudeDelivery;
  }


  public String getDateDelivery() {
    return dateDelivery;
  }


  public String getVideoNameDelivery() {
    return videoNameDelivery;
  }


  public Drone getDrone() {
    return drone;
  }

  public void setDrone(Drone drone) {
    this.drone = drone;
  }

}
