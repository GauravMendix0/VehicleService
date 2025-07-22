package com.example.VehicleService;

import com.example.VehicleService.model.Owner;
import com.example.VehicleService.model.Vehicle;

public class VehicleRequest {

    private Vehicle vehicle;
    private Owner owner;

    public VehicleRequest() {}

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
