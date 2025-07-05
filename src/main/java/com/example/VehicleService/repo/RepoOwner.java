package com.example.VehicleService.repo;

import com.example.VehicleService.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoOwner extends JpaRepository<Owner, Integer> {
    // Custom query methods can be added here if needed
}
