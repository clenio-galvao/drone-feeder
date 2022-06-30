package com.trybe.dronefeeder.utils;

/** Messages for validations. */
public class Messages {
  public static final String VALIDATION_BRAND_IS_REQUIRED = "Brand is required.";
  public static final String VALIDATION_BRAND_IS_EMPTY = "Brand cannot be empty.";
  public static final String VALIDATION_BRAND_SIZE = 
      "Brand must have a minimum of 3 characters and a maximum of 100.";
  public static final String VALIDATION_MODEL_SIZE =
      "Model must have a minimum of 3 characters and a maximum of 240.";
  public static final String VALIDATION_MODEL_IS_REQUIRED = "Model is required.";
  public static final String VALIDATION_MODEL_IS_EMPTY = "Model cannot be empty.";
  
  public static final String VALIDATION_VIDEO_NAME_IS_EMPTY = "Name cannot be empty.";
  public static final String VALIDATION_VIDEO_NAME_IS_REQUIRED = "Name is required.";
  public static final String VALIDATION_VIDEO_NAME_SIZE =
      "Name must have a minimum of 30 characters and a maximum of 100.";

  public static final String EXCEPTION_RESOURCE_NOT_FOUND = "Resource not found.";
  public static final String EXCEPTION_ID_NOT_FOUND = "Id not found ";
  public static final String EXCEPTION_ENTITY_NOT_FOUND = "Entity not found.";
}
