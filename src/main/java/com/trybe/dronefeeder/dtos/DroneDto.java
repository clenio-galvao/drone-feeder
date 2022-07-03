package com.trybe.dronefeeder.dtos;


import com.trybe.dronefeeder.models.Drone;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static com.trybe.dronefeeder.utils.Messages.*;


public class DroneDto implements Serializable {
    private static final long serialVersionUID = 7193684038002132400L;

    @ApiModelProperty(notes = "Drone ID", example = "7")
    private Long id;

    @NotEmpty(message = VALIDATION_BRAND_IS_EMPTY)
    @NotNull(message = VALIDATION_BRAND_IS_REQUIRED)
    @Size(min = 3, max = 50, message = VALIDATION_BRAND_SIZE)
    @ApiModelProperty(notes = "Drone brand", example = "LG K6D3", required = true)
    private String brand;

    @NotEmpty(message = VALIDATION_MODEL_IS_EMPTY)
    @NotNull(message = VALIDATION_MODEL_IS_REQUIRED)
    @Size(min = 3, max = 50, message = VALIDATION_MODEL_SIZE)
    @ApiModelProperty(notes = "Drone model", example = "12DD", required = true)
    private String model;

    public DroneDto() {
    }

    public DroneDto(Long id, String brand, String model) {
        this.id = id;
        this.brand = brand;
        this.model = model;
    }

    public DroneDto(Drone entity) {
        this.id = entity.getId();
        this.brand = entity.getBrand();
        this.model = entity.getModel();
    }

    public Long getId() {
        return id;
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
}
