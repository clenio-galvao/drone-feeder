package com.trybe.dronefeeder.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/** model class delivery. */
@Entity
@Table(name = "tb_delivery")
public class Delivery implements Serializable {

  private static final long serialVersionUID = 4816396557022738733L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String latitudeWithdrawal;
  private String longitudeWithdrawal;
  private LocalDateTime dateWithdrawal;

  private String latitudeDelivery;
  private String longitudeDelivery;
  private LocalDateTime dateDelivery;

  private String videoNameDelivery;
  
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "drone_id")
  @JsonBackReference
  private Drone drone;

  public Drone getDrone() {
    return drone;
  }

  public void setDrone(Drone drone) {
    this.drone = drone;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) { 
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Delivery delivery = (Delivery) o;
    return Objects.equals(id, delivery.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
