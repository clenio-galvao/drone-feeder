package com.trybe.dronefeeder.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
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
import com.trybe.dronefeeder.repositories.DeliveryRepository;
import com.trybe.dronefeeder.services.DeliveryService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DeliveryControllerTest {

  Logger logger = Logger.getLogger(DeliveryControllerTest.class.getSimpleName());
  /* Distribution center Localization data */
  String dcLatitude = "23.0110";
  String dcLongitude = "43.3090";
  /* Date Formatter */
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

  @MockBean
  private DeliveryRepository deliveryRepository;
  private final List<DeliveryDto> inputList = new ArrayList<>();

  @MockBean
  private DeliveryService deliveryService;
  @Autowired
  private MockMvc mockMvc;

  @Test
  @Order(1)
  @DisplayName("Insert 3 deliveries through '/deliveries' route successfully")
  void createDeliveryTest() throws Exception {

    DeliveryDto dto1 = new DeliveryDto();
    dto1.setId(1L);
    dto1.setLatitudeWithdrawal(dcLatitude);
    dto1.setLongitudeWithdrawal(dcLongitude);
    LocalDateTime now = LocalDateTime.now();
    dto1.setDateWithdrawal(now.minusHours(3));
    dto1.setLatitudeDelivery("22.9714");
    dto1.setLongitudeDelivery("43.1889");
    dto1.setDateDelivery(now.minusHours(2));
    dto1.setVideoNameDelivery("CopacabanaRuaRaimundoCorrea");
    Drone drone1 = new Drone(1L, "Samsung", "88D");
    dto1.setDrone(drone1);

    when(deliveryService.create(Mockito.any(DeliveryDto.class))).thenReturn(dto1);

    mockMvc.perform(
            post("/deliveries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto1)))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.latitudeDelivery").value("22.9714"));

    DeliveryDto dto2 = new DeliveryDto();
    dto2.setId(2L);
    dto2.setLatitudeWithdrawal(dcLatitude);
    dto2.setLongitudeWithdrawal(dcLongitude);
    dto2.setDateWithdrawal(now.minusHours(2));
    dto2.setLatitudeDelivery("23.0092");
    dto2.setLongitudeDelivery("43.3281");
    dto2.setDateDelivery(now.minusHours(1));
    dto2.setVideoNameDelivery("BarraDaTijucaCondBarramares");
    Drone drone2 = new Drone(2L, "LG", "22D");
    dto2.setDrone(drone2);

    when(deliveryService.create(Mockito.any(DeliveryDto.class))).thenReturn(dto2);

    mockMvc.perform(
            post("/deliveries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto2)))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.latitudeDelivery").value("23.0092"));

    DeliveryDto dto3 = new DeliveryDto();
    dto3.setId(3L);
    dto3.setLatitudeWithdrawal(dcLatitude);
    dto3.setLongitudeWithdrawal(dcLongitude);
    dto3.setDateWithdrawal(now.minusHours(1));
    dto3.setLatitudeDelivery("23.0127");
    dto3.setLongitudeDelivery("43.3061");
    dto3.setDateDelivery(now.minusMinutes(30));
    dto3.setVideoNameDelivery("BarraDaTijucaAvGeneralGuedesDaFontoura");
    Drone drone3 = new Drone(3L, "LogiTech", "33T");
    dto3.setDrone(drone3);

    when(deliveryService.create(Mockito.any(DeliveryDto.class))).thenReturn(dto3);

    mockMvc.perform(
            post("/deliveries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto3)))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.latitudeDelivery").value("23.0127"));

    inputList.add(dto1);
    inputList.add(dto2);
    inputList.add(dto3);

  }

  @Test
  @Order(2)
  @DisplayName("Returns a Delivery list with size equals 3")
  void findAllDeliveryTest() throws Exception {

    DeliveryDto dtoResponseInPage = new DeliveryDto();
    dtoResponseInPage.setId(2L);
    dtoResponseInPage.setLatitudeWithdrawal(dcLatitude);
    dtoResponseInPage.setLongitudeWithdrawal(dcLongitude);
    LocalDateTime now = LocalDateTime.now();
    dtoResponseInPage.setDateWithdrawal(now.minusHours(2));
    dtoResponseInPage.setLatitudeDelivery("23.0092");
    dtoResponseInPage.setLongitudeDelivery("43.3281");
    dtoResponseInPage.setDateDelivery(now.minusHours(1));
    dtoResponseInPage.setVideoNameDelivery("BarraDaTijucaCondBarramares");
    Drone drone2 = new Drone(2L, "LG", "22D");
    dtoResponseInPage.setDrone(drone2);

    inputList.add(dtoResponseInPage);

    Page<DeliveryDto> pageResponseMock = new PageImpl(inputList);

    when(deliveryService.findAllPaged(Mockito.any())).thenReturn(pageResponseMock);

    mockMvc.perform(get("/deliveries"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.content", hasSize(1)));
  }

  @Test
  @Order(3)
  @DisplayName("Should return a Drone entity as response in successful case")
  void findByIdDeliveryTest() throws Exception {

    DeliveryDto dtoToResponse = new DeliveryDto();
    dtoToResponse.setId(3L);
    dtoToResponse.setLatitudeWithdrawal(dcLatitude);
    dtoToResponse.setLongitudeWithdrawal(dcLongitude);
    LocalDateTime now = LocalDateTime.now();
    dtoToResponse.setDateWithdrawal(now.minusHours(1));
    dtoToResponse.setLatitudeDelivery("23.0127");
    dtoToResponse.setLongitudeDelivery("43.3061");
    dtoToResponse.setDateDelivery(now.minusMinutes(30));
    dtoToResponse.setVideoNameDelivery("BarraDaTijucaAvGeneralGuedesDaFontoura");
    Drone drone3 = new Drone(3L, "LogiTech", "33T");
    dtoToResponse.setDrone(drone3);

    when(deliveryService.findById(3L)).thenReturn(dtoToResponse);

    mockMvc.perform(get("/deliveries/{id}", 3L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.latitudeDelivery").value("23.0127"))
        .andExpect(jsonPath("$.longitudeDelivery").value("43.3061"));

  }

  @Test
  @Order(4)
  @DisplayName("Updating a delivery in database successfully")
  void updateDeliveryTest() throws Exception {

    DeliveryDto dtoUpdated = new DeliveryDto();
    dtoUpdated.setId(2L);
    dtoUpdated.setLatitudeWithdrawal(dcLatitude);
    dtoUpdated.setLongitudeWithdrawal(dcLongitude);
    LocalDateTime now = LocalDateTime.now();
    dtoUpdated.setDateWithdrawal(now.minusHours(5));
    dtoUpdated.setLatitudeDelivery("23.9999");
    dtoUpdated.setLongitudeDelivery("43.9999");
    dtoUpdated.setDateDelivery(now.minusHours(4));
    dtoUpdated.setVideoNameDelivery("BarraDaTijucaOlegarioMaciel");
    Drone drone2 = new Drone(2L, "LG", "22D");
    dtoUpdated.setDrone(drone2);

    when(deliveryService.update(Mockito.anyLong(), Mockito.any(DeliveryDto.class))).thenReturn(
        dtoUpdated);

    mockMvc.perform(
            put("/deliveries/{id}", 2L)
                .content(new ObjectMapper().writeValueAsString(dtoUpdated))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(2L))
        .andExpect(jsonPath("$.latitudeWithdrawal").value(dcLatitude))
        .andExpect(jsonPath("$.longitudeWithdrawal").value(dcLongitude))
        .andExpect(jsonPath("$.dateWithdrawal").value(dtoUpdated.getDateWithdrawal()))
        .andExpect(jsonPath("$.latitudeDelivery").value("23.9999"))
        .andExpect(jsonPath("$.longitudeDelivery").value("43.9999"))
        .andExpect(jsonPath("$.dateDelivery").value(dtoUpdated.getDateDelivery()))
        .andExpect(jsonPath("$.videoNameDelivery").value("BarraDaTijucaOlegarioMaciel"))
        .andExpect(jsonPath("$.drone.id").value(2L))
        .andExpect(jsonPath("$.drone.brand").value("LG"))
        .andExpect(jsonPath("$.drone.model").value("22D"));
  }

  @Test
  @Order(5)
  @DisplayName("Deleting a delivery from database successfully")
  void deleteDeliveryTest() throws Exception {

    DeliveryDto dtoUpdated = new DeliveryDto();
    dtoUpdated.setId(2L);
    dtoUpdated.setLatitudeWithdrawal(dcLatitude);
    dtoUpdated.setLongitudeWithdrawal(dcLongitude);
    LocalDateTime now = LocalDateTime.now();
    dtoUpdated.setDateWithdrawal(now.minusHours(5));
    dtoUpdated.setLatitudeDelivery("23.9999");
    dtoUpdated.setLongitudeDelivery("43.9999");
    dtoUpdated.setDateDelivery(now.minusHours(4));
    dtoUpdated.setVideoNameDelivery("BarraDaTijucaOlegarioMaciel");
    Drone drone2 = new Drone(2L, "LG", "22D");
    dtoUpdated.setDrone(drone2);

    Mockito.doAnswer((Answer<Void>) invocation -> null).when(deliveryService).delete(2L);

    mockMvc.perform(
            delete("/deliveries/{id}", 2L))
        .andExpect(status().is(204));

  }
}