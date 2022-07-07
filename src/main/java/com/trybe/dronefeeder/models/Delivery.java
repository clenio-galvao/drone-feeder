package com.trybe.dronefeeder.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * model class delivery.
 */
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

    public Delivery() {
    }

    public Delivery(Long id, String latitudeWithdrawal, String longitudeWithdrawal, LocalDateTime dateWithdrawal, String latitudeDelivery, String longitudeDelivery, LocalDateTime dateDelivery, String videoNameDelivery, Drone drone) {
        this.id = id;
        this.latitudeWithdrawal = latitudeWithdrawal;
        this.longitudeWithdrawal = longitudeWithdrawal;
        this.dateWithdrawal = dateWithdrawal;
        this.latitudeDelivery = latitudeDelivery;
        this.longitudeDelivery = longitudeDelivery;
        this.dateDelivery = dateDelivery;
        this.videoNameDelivery = videoNameDelivery;
        this.drone = drone;
    }

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
