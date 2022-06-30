package com.trybe.dronefeeder.services;

import com.trybe.dronefeeder.dtos.DroneDto;
import com.trybe.dronefeeder.models.Drone;
import com.trybe.dronefeeder.repositories.DroneRepository;
import com.trybe.dronefeeder.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static com.trybe.dronefeeder.utils.Messages.*;
import static java.util.Objects.isNull;

@Service
public class DroneService {
    
    @Autowired
    private DroneRepository droneRepository;

    @Transactional(readOnly = true)
    public Page<DroneDto> findAllPaged(Pageable pageable) {
        Page<Drone> drones = droneRepository.findAll(pageable);
        return drones.map(DroneDto::new);
    }

    @Transactional(readOnly = true)
    public DroneDto findById(Long id) {
        Optional<Drone> drone = droneRepository.findById(id);
        Drone entity = drone.orElseThrow(() -> new ResourceNotFoundException(EXCEPTION_ENTITY_NOT_FOUND));
        return new DroneDto(entity);
    }

    @Transactional
    public DroneDto create(DroneDto dto) {
        Drone entity = new Drone();
        copyDtoToEntity(dto, entity);
        entity = droneRepository.save(entity);
        return new DroneDto(entity);
    }

    @Transactional
    public DroneDto update(Long id, DroneDto dto) {
        try {
            Drone entity = droneRepository.getById(id);
            copyDtoToEntityUpdate(dto, entity);
            entity = droneRepository.save(entity);
            return new DroneDto(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(EXCEPTION_ID_NOT_FOUND + id + ".");
        }
    }

    public void delete(Long id) {
        try {
            droneRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(EXCEPTION_ID_NOT_FOUND + id + ".");
        }
    }

    private void copyDtoToEntity(DroneDto dto, Drone entity) {
        validationFields(dto);
        entity.setBrand(dto.getBrand());
        entity.setModel(dto.getModel());
    }


    private void copyDtoToEntityUpdate(DroneDto dto, Drone entity) {
        if (!isNull(dto.getBrand())) {
            validBrand(dto.getBrand());
            entity.setBrand(dto.getBrand());
        }
        if (!isNull(dto.getModel())) {
            validModel(dto.getModel());
            entity.setModel(dto.getModel());
        }
    }

    private void validBrand(String field) {
        if (field.trim().isEmpty()) {
            throw new ResourceNotFoundException(VALIDATION_BRAND_IS_EMPTY);
        }
        if (field.trim().length() < 3 || field.trim().length() > 100) {
            throw new ResourceNotFoundException(VALIDATION_BRAND_SIZE);
        }
    }

    private void validModel(String field) {
        if (field.trim().isEmpty()) {
            throw new ResourceNotFoundException(VALIDATION_MODEL_IS_EMPTY);
        }
        if (field.trim().length() < 3 || field.trim().length() > 240) {
            throw new ResourceNotFoundException(VALIDATION_MODEL_SIZE);
        }
    }

    private void validFieldsIsNotNull(DroneDto dto) {
        if (isNull(dto.getBrand())) {
            throw new ResourceNotFoundException(VALIDATION_BRAND_IS_REQUIRED);
        }
        if (isNull(dto.getModel())) {
            throw new ResourceNotFoundException(VALIDATION_MODEL_IS_REQUIRED);
        }
    }

    private void validationFields(DroneDto dto) {
        validFieldsIsNotNull(dto);
        validBrand(dto.getBrand());
        validModel(dto.getModel());
    }
}
