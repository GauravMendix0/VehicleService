package com.example.VehicleService.repo;

import com.example.VehicleService.ServiceAppt;
import com.example.VehicleService.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoServiceApt extends JpaRepository<ServiceAppt,Integer> {
}
