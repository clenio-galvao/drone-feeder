package com.trybe.dronefeeder.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trybe.dronefeeder.dtos.DroneDto;
import com.trybe.dronefeeder.services.DroneService;
import com.trybe.dronefeeder.services.exceptions.ResourceNotFoundException;
import com.trybe.dronefeeder.tests.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DroneContoller.class)
class DroneContollerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DroneService droneService;

    @Autowired
    private ObjectMapper objectMapper;

    private Long existsId;
    private Long nonExistsId;
    private DroneDto droneDto;


    @BeforeEach
    void setUp() {
        existsId = 1L;
        nonExistsId = 100L;
        droneDto = Factory.createDroneDto();
        PageImpl<DroneDto> page = new PageImpl<>(List.of(droneDto));

        Mockito.when(droneService.findAllPaged(Mockito.any())).thenReturn(page);

        Mockito.when(droneService.findById(existsId)).thenReturn(droneDto);
        Mockito.when(droneService.findById(nonExistsId)).thenThrow(ResourceNotFoundException.class);

        Mockito.when(droneService.create(droneDto)).thenReturn(droneDto);

        Mockito.when(droneService.update(existsId, droneDto)).thenReturn(droneDto);
        Mockito.when(droneService.update(nonExistsId, droneDto)).thenThrow(ResourceNotFoundException.class);

        doNothing().when(droneService).delete(existsId);
        Mockito.doThrow(ResourceNotFoundException.class).when(droneService).delete(nonExistsId);
    }

    @Test
    void findAllShouldReturnPage() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/drones")
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void findByIdShouldReturnDroneWhenIdExixts() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/drones/{id}", existsId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.brand").exists());
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExixts() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/drones/{id}", nonExistsId)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

    @Test
    void createShouldReturnDroneDto() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(droneDto);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/drones")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isCreated());
    }

    @Test
    void updateShouldReturnDroneDtoWhenIdExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(droneDto);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/drones/{id}", existsId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    void updateShouldNotFoundWhenIdDoesNotExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(droneDto);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/drones/{id}", nonExistsId)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isNotFound());
    }

    @Test
    void deleteShouldReturnNoContentWhenIdExists() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/drones/{id}", existsId));
        result.andExpect(status().isNoContent());
    }

    @Test
    void deleteShouldNotFoundWhenIdDoesNotExists() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete("/drones/{id}", nonExistsId));
        result.andExpect(status().isNotFound());
    }
}