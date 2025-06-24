package com.example.VehicleService;

public class ServiceRequest {
    private Vehicle vehicle;
    private ServiceAppt serviceAppt;

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
