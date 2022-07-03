package com.trybe.dronefeeder.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
  private Date dateWithdrawal;

  private String latitudeDelivery;
  private String longitudeDelivery;
  private Date dateDelivery;

  private String videoNameDelivery;
  
  @ManyToOne
  @JoinColumn(name = "drone_id")
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

  public Date getDateWithdrawal() {
    return dateWithdrawal;
  }

  public void setDateWithdrawal(Date dateWithdrawal) {
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

  public Date getDateDelivery() {
    return dateDelivery;
  }

  public void setDateDelivery(Date dateDelivery) {
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
