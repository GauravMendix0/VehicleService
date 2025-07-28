// Service appointment entity. It contains fields as service id, service date, vehicle
package com.example.VehicleService.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class ServiceAppt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sid;

    @NotNull(message = "Service date is required")
    @Future(message = "Service date must be in the future")
    private LocalDateTime serviceDate;

    @NotBlank(message = "Service type is required")
    private String service;

    @ManyToOne
    @JoinColumn(name = "vid")
    private Vehicle vehicle;

    public ServiceAppt() {}

    public ServiceAppt(Vehicle vehicle, LocalDateTime serviceDate, String service) {
        this.vehicle = vehicle;
        this.serviceDate = serviceDate;
        this.service = service;
    }

    // --- Getters and Setters ---

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
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

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
