package com.trybe.dronefeeder.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_drone")
public class Drone implements Serializable {
    private static final long serialVersionUID = -8194331057711583877L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;

    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Delivery> deliveries;

    public Drone(Long id, String brand, String model) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.deliveries = new ArrayList<Delivery>();
    }

    public Drone() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drone drone = (Drone) o;
        return Objects.equals(id, drone.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
