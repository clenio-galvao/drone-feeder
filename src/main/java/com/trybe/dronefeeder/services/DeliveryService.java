package com.trybe.dronefeeder.services;


import static com.trybe.dronefeeder.utils.Messages.EXCEPTION_ENTITY_NOT_FOUND;
import static com.trybe.dronefeeder.utils.Messages.EXCEPTION_ID_NOT_FOUND;
import static com.trybe.dronefeeder.utils.Messages.VALIDATION_IS_EMPTY;
import static com.trybe.dronefeeder.utils.Messages.VALIDATION_IS_REQUIRED;
import static com.trybe.dronefeeder.utils.Messages.VALIDATION_SIZE;
import static java.util.Objects.isNull;

import com.trybe.dronefeeder.dtos.DeliveryDto;
import com.trybe.dronefeeder.models.Delivery;
import com.trybe.dronefeeder.models.Drone;
import com.trybe.dronefeeder.repositories.DeliveryRepository;
import com.trybe.dronefeeder.repositories.DroneRepository;
import com.trybe.dronefeeder.services.exceptions.ResourceNotFoundException;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** delivery service class. */
@Service
public class DeliveryService {

  @Autowired
  private DeliveryRepository deliveryRepository;
  
  @Autowired
  private DroneRepository droneRepository;
  
  @Transactional(readOnly = true)
  public Page<DeliveryDto> findAllPaged(Pageable pageable) {
    Page<Delivery> delivery = deliveryRepository.findAll(pageable);
    return delivery.map(DeliveryDto::new);
  }
  
  /** find by id method. */
  @Transactional(readOnly = true)
  public DeliveryDto findById(Long id) {
    Optional<Delivery> delivery = deliveryRepository.findById(id);
    Delivery entity = delivery.orElseThrow(() -> new ResourceNotFoundException(
        EXCEPTION_ENTITY_NOT_FOUND));
    return new DeliveryDto(entity);
  }
  
  /** create method. */
  @Transactional
  public DeliveryDto create(DeliveryDto dto) {
    Delivery entity = new Delivery();
    copyDtoToEntity(dto, entity);
    entity = deliveryRepository.save(entity);
    return new DeliveryDto(entity);
  }

  /** update method. */
  @Transactional
  public DeliveryDto update(Long id, DeliveryDto dto) {
    try {
      Delivery entity = deliveryRepository.getById(id);
      copyDtoToEntityUpdate(dto, entity);
      entity = deliveryRepository.save(entity);
      return new DeliveryDto(entity);
    } catch (EntityNotFoundException e) {
      throw new ResourceNotFoundException(EXCEPTION_ID_NOT_FOUND + id + ".");
    }
  }

  /** delete method. */
  public void delete(Long id) {
    try {
      deliveryRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException(EXCEPTION_ID_NOT_FOUND + id + ".");
    }
  }
  
  private void copyDtoToEntity(DeliveryDto dto, Delivery entity) {
    validationFields(dto);

    entity.setLatitudeWithdrawal(dto.getLatitudeWithdrawal());
    entity.setLongitudeWithdrawal(dto.getLongitudeWithdrawal());
    entity.setDateWithdrawal(dto.getDateWithdrawal());
    entity.setLatitudeDelivery(dto.getLatitudeDelivery());
    entity.setLongitudeDelivery(dto.getLongitudeDelivery());
    entity.setDateDelivery(dto.getDateDelivery());
    entity.setVideoNameDelivery(dto.getVideoNameDelivery());
    
    Optional<Drone> droneOp = droneRepository.findById(dto.getDrone().getId());
    Drone drone = droneOp.orElseThrow(() -> new ResourceNotFoundException(
        EXCEPTION_ENTITY_NOT_FOUND));
    entity.setDrone(drone);
  }
  
  private void copyDtoToEntityUpdate(DeliveryDto dto, Delivery entity) {
    if (!isNull(dto.getLatitudeWithdrawal())) {
      validLatitudeWithdrawal(dto.getLatitudeWithdrawal());
      entity.setLatitudeWithdrawal(dto.getLatitudeWithdrawal());
    }
    if (!isNull(dto.getLongitudeWithdrawal())) {
      validLongitudeWithdrawal(dto.getLongitudeWithdrawal());
      entity.setLongitudeWithdrawal(dto.getLongitudeWithdrawal());
    }
    if (!isNull(dto.getLatitudeDelivery())) {
      validLatitudeDelivery(dto.getLatitudeDelivery());
      entity.setLatitudeDelivery(dto.getLatitudeDelivery());
    }
    if (!isNull(dto.getLongitudeDelivery())) {
      validLongitudeDelivery(dto.getLongitudeDelivery());
      entity.setLongitudeDelivery(dto.getLongitudeDelivery());
    }
    if (!isNull(dto.getDrone())) {
      entity.setDrone(dto.getDrone());
    }
    
    entity.setDateWithdrawal(dto.getDateWithdrawal());
    entity.setDateDelivery(dto.getDateDelivery());
    entity.setVideoNameDelivery(dto.getVideoNameDelivery());
  }
  
  private void validationFields(DeliveryDto dto) {
    validFieldsIsNotNull(dto);
    validLatitudeWithdrawal(dto.getLatitudeWithdrawal());
    validLongitudeWithdrawal(dto.getLongitudeWithdrawal());
    validLatitudeDelivery(dto.getLatitudeDelivery());
    validLongitudeDelivery(dto.getLongitudeDelivery());
  }
  
  private void validLatitudeWithdrawal(String field) {
    if (field.trim().isEmpty()) {
      throw new ResourceNotFoundException("LatitudeWithdrawal" + VALIDATION_IS_EMPTY);
    }
    if (field.trim().length() < 7 || field.trim().length() > 10) {
      throw new ResourceNotFoundException("LatitudeWithdrawal" + VALIDATION_SIZE);
    }
  }
  
  private void validLongitudeWithdrawal(String field) {
    if (field.trim().isEmpty()) {
      throw new ResourceNotFoundException("LongitudeWithdrawal" + VALIDATION_IS_EMPTY);
    }
    if (field.trim().length() < 7 || field.trim().length() > 10) {
      throw new ResourceNotFoundException("LongitudeWithdrawal" + VALIDATION_SIZE);
    }
  }
  
  private void validLatitudeDelivery(String field) {
    if (field.trim().isEmpty()) {
      throw new ResourceNotFoundException("LatitudeDelivery" + VALIDATION_IS_EMPTY);
    }
    if (field.trim().length() < 7 || field.trim().length() > 10) {
      throw new ResourceNotFoundException("LatitudeDelivery" + VALIDATION_SIZE);
    }
  }
  
  private void validLongitudeDelivery(String field) {
    if (field.trim().isEmpty()) {
      throw new ResourceNotFoundException("LongitudeDelivery" + VALIDATION_IS_EMPTY);
    }
    if (field.trim().length() < 7 || field.trim().length() > 10) {
      throw new ResourceNotFoundException("LongitudeDelivery" + VALIDATION_SIZE);
    }
  }

  private void validFieldsIsNotNull(DeliveryDto dto) {
    if (isNull(dto.getLatitudeWithdrawal())) {
      throw new ResourceNotFoundException("LatitudeWithdrawal" + VALIDATION_IS_REQUIRED);
    }
    if (isNull(dto.getLongitudeWithdrawal())) {
      throw new ResourceNotFoundException("LongitudeWithdrawal" + VALIDATION_IS_REQUIRED);
    }
    if (isNull(dto.getLatitudeDelivery())) {
      throw new ResourceNotFoundException("LatitudeDelivery" + VALIDATION_IS_REQUIRED);
    }
    if (isNull(dto.getLongitudeDelivery())) {
      throw new ResourceNotFoundException("LongitudeDelivery" + VALIDATION_IS_REQUIRED);
    }
    if (isNull(dto.getDrone())) {
      throw new ResourceNotFoundException("Drone" + VALIDATION_IS_REQUIRED);
    }
  }
}
