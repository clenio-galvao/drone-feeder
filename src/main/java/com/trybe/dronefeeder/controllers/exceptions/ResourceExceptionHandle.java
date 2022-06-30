package com.trybe.dronefeeder.controllers.exceptions;

import com.trybe.dronefeeder.services.exceptions.ResourceNotFoundException;
import com.trybe.dronefeeder.utils.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandle {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError();
        HttpStatus status = HttpStatus.NOT_FOUND;

        err.setTimestamp(LocalDateTime.now());
        err.setStatus(status.value());
        err.setError(Messages.EXCEPTION_RESOURCE_NOT_FOUND);
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
