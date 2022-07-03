package com.trybe.dronefeeder.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trybe.dronefeeder.dtos.DeliveryDto;
import com.trybe.dronefeeder.models.Drone;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DeliveryControllerTest {

  // @MockBean
  // private DeliveryRepository deliveryRepository;

  // @MockBean
  // private DeliveryService deliveryService;

  /* Distribution center Localization data */
  String dcLatitude = "-23.011001377319435";
  String dcLongitude = "-43.30900399390854";

  /* Date Formatter */
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  List<DeliveryDto> inputList = new ArrayList<>();

  @Autowired
  private MockMvc mockMvc;

  @Test
  @Order(1)
  @DisplayName("Insert 3 deliveries throught '/deliveries' route successfully")
  void createDeliveryTest() throws Exception {

    DeliveryDto dto1 = new DeliveryDto();
    dto1.setLatitudeWithdrawal(dcLatitude);
    dto1.setLongitudeWithdrawal(dcLongitude);
    LocalDateTime now = LocalDateTime.now();
    dto1.setDateWithdrawal(now.minusHours(3).format(formatter));
    dto1.setLatitudeDelivery("-22.971439736450783");
    dto1.setLongitudeDelivery("-43.18896160204596");
    dto1.setDateDelivery(now.minusHours(2).format(formatter));
    dto1.setVideoNameDelivery("CopacabanaRuaRaimundoCorrea");
    Drone drone1 = new Drone(1L, "Samsung", "88D");
    dto1.setDrone(drone1);

    mockMvc.perform(
            post("/deliveries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto1)))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.latitudeDelivery").value("-22.971439736450783"));

    DeliveryDto dto2 = new DeliveryDto();
    dto2.setLatitudeWithdrawal(dcLatitude);
    dto2.setLongitudeWithdrawal(dcLongitude);
    dto2.setDateWithdrawal(now.minusHours(2).format(formatter));
    dto2.setLatitudeDelivery("-23.00921607503666");
    dto2.setLongitudeDelivery("-43.3281119980874");
    dto2.setDateDelivery(now.minusHours(1).format(formatter));
    dto2.setVideoNameDelivery("BarraDaTijucaCondBarramares");
    Drone drone2 = new Drone(2L, "LG", "22D");
    dto2.setDrone(drone2);

    mockMvc.perform(
            post("/deliveries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto2)))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.latitudeDelivery").value("-23.00921607503666"));

    DeliveryDto dto3 = new DeliveryDto();
    dto3.setLatitudeWithdrawal(dcLatitude);
    dto3.setLongitudeWithdrawal(dcLongitude);
    dto3.setDateWithdrawal(now.minusHours(1).format(formatter));
    dto3.setLatitudeDelivery("-23.012743278589884");
    dto3.setLongitudeDelivery("-43.306103656637845");
    dto3.setDateDelivery(now.minusMinutes(30).format(formatter));
    dto3.setVideoNameDelivery("BarraDaTijucaAvGeneralGuedesDaFontoura");
    Drone drone3 = new Drone(3L, "LogiTech", "33T");
    dto3.setDrone(drone3);

    mockMvc.perform(
            post("/deliveries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto3)))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.latitudeDelivery").value("-23.012743278589884"));

    inputList.add(dto1);
    inputList.add(dto2);
    inputList.add(dto3);
  }

  @Test
  @Order(2)
  @DisplayName("Returns a Delivery list with size equals 3")
  void findAllDeliveryTest() throws Exception {

    mockMvc.perform(get("/deliveries"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(3)));
  }

  @Test
  @Order(3)
  @DisplayName("Should return a Drone entity as response in successful case")
  void findByIdDeliveryTest() throws Exception {

    mockMvc.perform(get("/deliveries/{id}", 3L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.latitudeDelivery").value("-23.012743278589884"))
        .andExpect(jsonPath("$.longitudeDelivery").value("-43.306103656637845"));

  }

  @Test
  @Order(4)
  @DisplayName("Updating a delivery in database successfully")
  void updateDeliveryTest() throws Exception {

    DeliveryDto dtoUpdated = new DeliveryDto();
    dtoUpdated.setLatitudeWithdrawal(dcLatitude);
    dtoUpdated.setLongitudeWithdrawal(dcLongitude);
    dtoUpdated.setDateWithdrawal(inputList.get(1).getDateWithdrawal());
    dtoUpdated.setLatitudeDelivery("-23.99999999999999");
    dtoUpdated.setLongitudeDelivery("-43.9999999999999");
    dtoUpdated.setDateDelivery(inputList.get(1).getDateDelivery());
    dtoUpdated.setVideoNameDelivery("BarraDaTijucaOlegarioMaciel");
    Drone drone2 = new Drone(2L, "LG", "22D");
    dtoUpdated.setDrone(drone2);

    mockMvc.perform(
            put("/deliveries/{id}", 2L)
                .content(new ObjectMapper().writeValueAsString(dtoUpdated))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(2L))
        .andExpect(jsonPath("$.latitudeWithdrawal").value(dcLatitude))
        .andExpect(jsonPath("$.longitudeWithdrawal").value(dcLongitude))
        .andExpect(jsonPath("$.dateWithdrawal").value(inputList.get(1).getDateWithdrawal()))
        .andExpect(jsonPath("$.latitudeDelivery").value("-23.99999999999999"))
        .andExpect(jsonPath("$.longitudeDelivery").value("-43.9999999999999"))
        .andExpect(jsonPath("$.dateDelivery").value(inputList.get(1).getDateDelivery()))
        .andExpect(jsonPath("$.videoNameDelivery").value("BarraDaTijucaOlegarioMaciel"))
        .andExpect(jsonPath("$.drone_id").value(new ObjectMapper().writeValueAsString(drone2)));
  }

  @Test
  @Order(5)
  @DisplayName("Deleting a delivery from database successfully")
  void deleteDeliveryTest() throws Exception {

    mockMvc.perform(
            delete("/deliveries/{id}", 1L))
        .andExpect(status().isAccepted());

  }
}