package com.example.VehicleService.repo;

import com.example.VehicleService.model.ServiceAppt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoServiceApt extends JpaRepository<ServiceAppt, Integer> {
    // Repository ServiceAppt
}
