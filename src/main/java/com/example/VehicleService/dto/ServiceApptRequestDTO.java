package com.example.VehicleService.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ServiceApptRequestDTO {

    @NotNull(message = "Service date is required")
    @Future(message = "Service date must be in the future")
    private LocalDateTime serviceDate;

    @NotBlank(message = "Service type is required")
    private String service;

    @NotNull(message = "Vehicle ID is required")
    private Integer vehicleId;

    // Getters and Setters
    public LocalDateTime getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDateTime serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }
}
