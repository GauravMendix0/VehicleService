package com.example.VehicleService.repo;

import com.example.VehicleService.ServiceAppt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoServiceApt extends JpaRepository<ServiceAppt, Integer> {
    // Extend with custom findBy...() if needed
}
