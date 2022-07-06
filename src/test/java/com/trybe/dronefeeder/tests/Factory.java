package com.trybe.dronefeeder.tests;

import com.trybe.dronefeeder.dtos.DroneDto;
import com.trybe.dronefeeder.models.Drone;

public class Factory {

    private static final Long idTest = 1L;
    private static final String brandTest = "LG K6D3";
    private static final String modelTest = "12DD";

    public static final String TEXT_LONGER_THAN_60_CHARACTERS = "Lorem ipsum dictumst malesuada quisque himenaeos elementum.";

    public static Drone createDrone() {
        Drone entity = new Drone();
        entity.setBrand(brandTest);
        entity.setModel(modelTest);
        return entity;
    }

    public static Drone createDroneWithId() {
        return new Drone(idTest, brandTest, modelTest);
    }

    public static DroneDto createDroneDto() {
        return new DroneDto(idTest, brandTest, modelTest);
    }
}
