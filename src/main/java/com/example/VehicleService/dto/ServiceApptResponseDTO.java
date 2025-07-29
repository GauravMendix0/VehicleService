package com.example.VehicleService.dto;

import java.time.LocalDateTime;

public class ServiceApptResponseDTO {

    private int id;
    private LocalDateTime serviceDate;
    private String service;
    private String vehicleNumber;
    private String vehicleModel;
    private String ownerName;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
