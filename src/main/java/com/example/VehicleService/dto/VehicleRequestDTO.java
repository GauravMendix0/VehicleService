package com.example.VehicleService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VehicleRequestDTO {

    @NotBlank(message = "Vehicle number is required")
    private String number;

    @NotBlank(message = "Model is required")
    private String model;

    @NotNull(message = "Owner ID is required")
    private Integer ownerId;

    // Getters and Setters

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }
}
