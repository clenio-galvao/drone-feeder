package com.trybe.dronefeeder.controllers.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class StandardError {
    private static final long serialVersionUID = 4386240649033540624L;

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String  path;
    private String message;



    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
