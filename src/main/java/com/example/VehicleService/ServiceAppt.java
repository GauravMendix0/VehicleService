package com.example.VehicleService;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Component
@Entity
public class ServiceAppt implements Bookable {

    @Id
    private int sid;

//    private int vid;
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

    @Override
    public String getBookinfo() {
        return "Date: " + date + " Vehicle: " + vehicle + " Service: " + service;
    }

    // --- Getters ---

    public int getSid() {
        return sid;
    }



    public String getDate() {
        return date;
    }

    public String getService() {
        return service;
    }

    // --- Setters ---

    public void setSid(int sid) {
        this.sid = sid;
    }



    public void setDate(String date) {
        this.date = date;
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
