package com.trybe.dronefeeder.services;

import com.trybe.dronefeeder.dtos.DroneDto;
import com.trybe.dronefeeder.models.Drone;
import com.trybe.dronefeeder.repositories.DroneRepository;
import com.trybe.dronefeeder.services.exceptions.ResourceNotFoundException;
import com.trybe.dronefeeder.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.trybe.dronefeeder.tests.Factory.TEXT_LONGER_THAN_60_CHARACTERS;
import static com.trybe.dronefeeder.utils.Messages.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class DroneServiceTest {

    @InjectMocks
    private DroneService droneService;

    @Mock
    private DroneRepository droneRepository;

    private Drone drone;
    private Long existsId;
    private Long nonExistsId;

    private DroneDto droneDto;

    private final String expectedBrand = "LG K6D3";
    private final String expectedModel = "12DD";

    @BeforeEach
    void setUp() {
        existsId = 1L;
        nonExistsId = 2L;
        droneDto = Factory.createDroneDto();
        drone = Factory.createDrone();
        Drone droneWithId = Factory.createDroneWithId();
        PageImpl<Drone> page = new PageImpl<>(List.of(drone));

        Mockito.when(droneRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        Mockito.when(droneRepository.findById(existsId)).thenReturn(Optional.of(drone));
        Mockito.when(droneRepository.findById(nonExistsId)).thenThrow(ResourceNotFoundException.class);

        Mockito.when(droneRepository.save(drone)).thenReturn(droneWithId);

        Mockito.when(droneRepository.getReferenceById(existsId)).thenReturn(drone);
        Mockito.when(droneRepository.getReferenceById(nonExistsId)).thenThrow(ResourceNotFoundException.class);

        doNothing().when(droneRepository).deleteById(existsId);
        doThrow(ResourceNotFoundException.class).when(droneRepository).deleteById(nonExistsId);
    }


    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        // Act
        Assertions.assertThrows(ResourceNotFoundException.class, () -> droneService.delete(nonExistsId));

        // Assert
        verify(droneRepository, times(1)).deleteById(nonExistsId);
    }

    @Test
    void deleteShouldDoNothingWhenIdExists() {
        // Act
        Assertions.assertDoesNotThrow(() -> droneService.delete(existsId));

        // Assert
        verify(droneRepository, times(1)).deleteById(existsId);
    }

    @Test
    void updateShouldReturnDroneDtoWhenIdExists() {
        // Act
        DroneDto result = droneService.update(existsId, droneDto);

        // Assert
        Assertions.assertNotNull(result);
        verify(droneRepository, times(1)).save(drone);
        Assertions.assertEquals(result.getBrand(), expectedBrand);
        Assertions.assertEquals(result.getModel(), expectedModel);
    }

    @Test
    void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> droneService.update(nonExistsId, droneDto));

        // Assert
        verify(droneRepository, times(1)).getReferenceById(nonExistsId);
        Assertions.assertEquals(result.getClass(), ResourceNotFoundException.class);
    }

    @Test
    void updateShouldReturnResourceNotFoundExceptionWhenBrandSizeLessThanThree() {
        // Arrange
        DroneDto dtoBrandTest = droneDto;
        dtoBrandTest.setBrand("LG");

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> droneService.update(existsId, dtoBrandTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_BRAND_SIZE);
    }

    @Test
    void updateShouldReturnResourceNotFoundExceptionWhenBrandIsEmpty() {
        // Arrange
        DroneDto dtoBrandTest = droneDto;
        dtoBrandTest.setBrand(" ");

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> droneService.update(existsId, dtoBrandTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_BRAND_IS_EMPTY);
    }

    @Test
    void updateShouldReturnResourceNotFoundExceptionWhenBrandSizeGreaterThan100() {
        // Arrange
        DroneDto dtoBrandTest = droneDto;
        dtoBrandTest.setBrand("Apetitosa apetitosa apetitosa apetitosa apetitosa apetitosa apetitosa apetitosa apetitosa apetitosa a");

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> droneService.update(existsId, dtoBrandTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_BRAND_SIZE);
    }

    @Test
    void updateShouldReturnResourceNotFoundExceptionWhenModelSizeLessThanThree() {
        // Arrange
        DroneDto dtoModelTest = droneDto;
        dtoModelTest.setModel("12");

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> droneService.update(existsId, dtoModelTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_MODEL_SIZE);
    }

    @Test
    void updateShouldReturnResourceNotFoundExceptionWhenModelSizeGreaterThan240() {
        // Arrange
        DroneDto dtoModelTest = droneDto;
        dtoModelTest.setModel("Lorem ipsum dictumst malesuada quisque himenaeos elementum magna conubia, rutrum vitae ac elit a leo platea fermentum, sed morbi nunc blandit hendrerit sagittis turpis, hac augue mi nunc integer luctus phasellus. scelerisque magna malesuada nullam ornare.");

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> droneService.update(existsId, dtoModelTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_MODEL_SIZE);
    }

    @Test
    void createShouldReturnDroneDto() {
        // Act
        DroneDto result = droneService.create(droneDto);

        // Assert
        Assertions.assertNotNull(result);
        verify(droneRepository, times(1)).save(drone);
        Assertions.assertEquals(result.getBrand(), expectedBrand);
        Assertions.assertEquals(result.getModel(), expectedModel);
    }

    @Test
    void createShouldReturnResourceNotFoundExceptionWhenBrandIsNull() {
        // Arrange
        DroneDto dtoBrandNull = droneDto;
        dtoBrandNull.setBrand(null);

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> droneService.create(dtoBrandNull));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_BRAND_IS_REQUIRED);
    }

    @Test
    void createShouldReturnResourceNotFoundExceptionWhenBrandIsEmpty() {
        // Arrange
        DroneDto dtoBrandTest = droneDto;
        dtoBrandTest.setBrand(" ");

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> droneService.create(dtoBrandTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_BRAND_IS_EMPTY);
    }

    @Test
    void createShouldReturnResourceNotFoundExceptionWhenBrandSizeLessThanThree() {
        // Arrange
        DroneDto dtoBrandTest = droneDto;
        dtoBrandTest.setBrand("Mu");

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> droneService.create(dtoBrandTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_BRAND_SIZE);
    }

    @Test
    void createShouldReturnResourceNotFoundExceptionWhenBrandSizeGreaterThan100() {
        // Arrange
        DroneDto dtoBrandTest = droneDto;
        dtoBrandTest.setBrand(TEXT_LONGER_THAN_60_CHARACTERS);

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> droneService.create(dtoBrandTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_BRAND_SIZE);
    }

    @Test
    void createShouldReturnResourceNotFoundExceptionWhenModelSizeLessThanThree() {
        // Arrange
        DroneDto dtoModelTest = droneDto;
        dtoModelTest.setModel("Ma");

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> droneService.create(dtoModelTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_MODEL_SIZE);
    }

    @Test
    void createShouldReturnResourceNotFoundExceptionWhenModelSizeGreaterThan240() {
        // Arrange
        DroneDto dtoModelTest = droneDto;
        dtoModelTest.setModel(TEXT_LONGER_THAN_60_CHARACTERS);

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> droneService.create(dtoModelTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_MODEL_SIZE);
    }

    @Test
    void createShouldReturnResourceNotFoundExceptionWhenModelIsNull() {
        // Arrange
        DroneDto dtoModelTest = droneDto;
        dtoModelTest.setModel(null);

        // Act
        var result = Assertions.assertThrows(ResourceNotFoundException.class, () -> droneService.create(dtoModelTest));

        // Assert
        Assertions.assertEquals(result.getMessage(), VALIDATION_MODEL_IS_REQUIRED);
    }


    @Test
    public void findAllShouldReturnPage() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<DroneDto> result = droneService.findAllPaged(pageable);

        // Assert
        Assertions.assertNotNull(result);
        verify(droneRepository, times(1)).findAll(pageable);
    }

    @Test
    void findByIdShouldReturnDroneDtoWhenIdExixts() {
        // Act
        DroneDto result = droneService.findById(existsId);

        // Assert
        Assertions.assertNotNull(result);
        verify(droneRepository, times(1)).findById(existsId);
        Assertions.assertEquals(result.getBrand(), expectedBrand);
        Assertions.assertEquals(result.getModel(), expectedModel);
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExixts() {
        // Assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> droneService.findById(nonExistsId));

        verify(droneRepository, times(1)).findById(nonExistsId);
    }

}