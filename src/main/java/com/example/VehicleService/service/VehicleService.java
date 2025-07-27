package com.example.VehicleService.service;

import com.example.VehicleService.exception.ResourceNotFoundException;
import com.example.VehicleService.model.Owner;
import com.example.VehicleService.model.Vehicle;
import com.example.VehicleService.model.VehicleRequest;
import com.example.VehicleService.repo.RepoOwner;
import com.example.VehicleService.repo.RepoVehicle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final RepoVehicle repoVehicle;
    private final RepoOwner repoOwner;

    public VehicleService(RepoVehicle repoVehicle, RepoOwner repoOwner) {
        this.repoVehicle = repoVehicle;
        this.repoOwner = repoOwner;
    }

    public List<Vehicle> getAllVehicles() {
        return repoVehicle.findAll();
    }

    public Vehicle getVehicleById(int id) {
        return repoVehicle.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle with id: " + id + " not found"));
    }

    public Vehicle createVehicle(VehicleRequest request) {
        Vehicle vehicle = request.getVehicle();
        Owner owner = request.getOwner();

        Owner existingOwner = repoOwner.findById(owner.getOid())
                .orElseThrow(() -> new ResourceNotFoundException("Owner with id: " + owner.getOid() + " not found"));

        vehicle.setOwner(existingOwner);
        return repoVehicle.save(vehicle);
    }

    public Vehicle replaceVehicle(int id, VehicleRequest request) {
        Vehicle existingVehicle = repoVehicle.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle with id: " + id + " not found"));

        Owner owner = repoOwner.findById(request.getOwner().getOid())
                .orElseThrow(() -> new ResourceNotFoundException("Owner with id: " + request.getOwner().getOid() + " not found"));

        Vehicle newVehicle = request.getVehicle();

        existingVehicle.setNumber(newVehicle.getNumber());
        existingVehicle.setModel(newVehicle.getModel());
        existingVehicle.setOwner(owner);

        return repoVehicle.save(existingVehicle);
    }

    public Vehicle updateVehicle(int id, VehicleRequest request) {
        Vehicle existing = repoVehicle.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle with id: " + id + " not found"));

        Vehicle partial = request.getVehicle();
        Owner patchOwner = request.getOwner();

        if (patchOwner != null) {
            repoOwner.findById(patchOwner.getOid())
                    .orElseThrow(() -> new ResourceNotFoundException("Owner with id: " + patchOwner.getOid() + " not found"));
            existing.setOwner(patchOwner);
        }

        if (partial.getNumber() != null) {
            existing.setNumber(partial.getNumber());
        }
        if (partial.getModel() != null) {
            existing.setModel(partial.getModel());
        }

        return repoVehicle.save(existing);
    }

    public void deleteVehicle(int id) {
        if (!repoVehicle.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle with id: " + id + " not found");
        }
        repoVehicle.deleteById(id);
    }
}
