package com.example.VehicleService;

import com.example.VehicleService.model.ServiceAppt;
import com.example.VehicleService.model.Vehicle;

public class ServiceRequest {

    private Vehicle vehicle;
    private ServiceAppt serviceAppt;

    public ServiceRequest() {} // Optional default constructor

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ServiceAppt getServiceAppt() {
        return serviceAppt;
    }

    public void setServiceAppt(ServiceAppt serviceAppt) {
        this.serviceAppt = serviceAppt;
    }
}
