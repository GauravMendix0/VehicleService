package com.example.VehicleService.model;

import jakarta.persistence.*;

@Entity
public class ServiceAppt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sid;

    private String date;
    private String service;

    @ManyToOne
    private Vehicle vehicle;

    public ServiceAppt() {}

    public ServiceAppt(Vehicle vehicle, String date, String serviceType) {
        this.vehicle = vehicle;
        this.date = date;
        this.service = serviceType;
    }


    // --- Getters and Setters ---

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
