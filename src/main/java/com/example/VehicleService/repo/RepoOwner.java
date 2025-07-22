package com.example.VehicleService.repo;

import com.example.VehicleService.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoOwner extends JpaRepository<Owner, Integer> {
    // Repository Owner
}
