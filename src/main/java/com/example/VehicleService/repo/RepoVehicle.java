package com.example.VehicleService.repo;

import com.example.VehicleService.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoVehicle extends JpaRepository<Vehicle,Integer> {

}
