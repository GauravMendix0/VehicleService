package com.example.VehicleService.repo;

import com.example.VehicleService.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoVehicle extends JpaRepository<Vehicle, Integer> {
    // Repository Vehicle
}
