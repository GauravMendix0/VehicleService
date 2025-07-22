package com.example.VehicleService.controller;

import com.example.VehicleService.exception.ResourceNotFoundException;
import com.example.VehicleService.model.Owner;
import com.example.VehicleService.model.Vehicle;
import com.example.VehicleService.VehicleRequest;
import com.example.VehicleService.repo.RepoOwner;
import com.example.VehicleService.repo.RepoVehicle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehicles")
public class ControllerVehicle {

    private final RepoVehicle repoVehicle;
    private final RepoOwner repoOwner;

    public ControllerVehicle(RepoVehicle repoVehicle, RepoOwner repoOwner) {
        this.repoVehicle = repoVehicle;
        this.repoOwner = repoOwner;
    }

    // GET all vehicles
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = repoVehicle.findAll();
        return ResponseEntity.ok(vehicles);
    }

    // GET vehicle by ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable int id) {
        Vehicle vehicle=repoVehicle.findById(id).orElseThrow(()->new ResourceNotFoundException("Vehicle with id : "+ id+" Not found"));

        return ResponseEntity.ok(vehicle);
//        return repoVehicle.findById(id)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST - create new vehicle
    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody VehicleRequest request) {
        Vehicle vehicle = request.getVehicle();
        Owner owner = request.getOwner();

        // Better: fetch owner from DB to ensure it's valid
        Optional<Owner> existingOwner = Optional.ofNullable(repoOwner.findById(owner.getOid()).orElseThrow(() -> new ResourceNotFoundException("Owner with id : " + owner.getOid() + " Not found")));
        if (existingOwner.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        vehicle.setOwner(existingOwner.get());
        Vehicle saved = repoVehicle.save(vehicle);
        return ResponseEntity.ok(saved);
    }

    // PUT - full update
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> replaceVehicle(@RequestBody VehicleRequest request, @PathVariable int id) {
        Optional<Vehicle> optionalVehicle = Optional.ofNullable(repoVehicle.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicle with id : " + id + " Not found")));
        Optional<Owner> ownerOpt = Optional.ofNullable(repoOwner.findById(request.getOwner().getOid()).orElseThrow(() -> new ResourceNotFoundException("Owner with id : " + request.getOwner().getOid() + " Not found")));

        if (optionalVehicle.isEmpty() || ownerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Vehicle existing = optionalVehicle.get();
        Vehicle newVehicle = request.getVehicle();

        existing.setNumber(newVehicle.getNumber());
        existing.setModel(newVehicle.getModel());
        existing.setOwner(ownerOpt.get());

        Vehicle updated = repoVehicle.save(existing);
        return ResponseEntity.ok(updated);
    }

    // PATCH - partial update
    @PatchMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody VehicleRequest request, @PathVariable int id) {
        Optional<Vehicle> optionalVehicle = Optional.ofNullable(repoVehicle.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicle with id : " + id + " Not found")));
        if (optionalVehicle.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Vehicle existing = optionalVehicle.get();
        Vehicle partial = request.getVehicle();
        Owner patchOwner = request.getOwner();

        repoOwner.findById(patchOwner.getOid()).orElseThrow(()->new ResourceNotFoundException("Owner with id : "+ patchOwner.getOid()+" Not found"));

        if (partial.getNumber() != null) {
            existing.setNumber(partial.getNumber());
        }

        if (partial.getModel() != null) {
            existing.setModel(partial.getModel());
        }

        if (patchOwner != null) {
            repoOwner.findById(patchOwner.getOid())
                    .ifPresent(existing::setOwner);
        }

        Vehicle updated = repoVehicle.save(existing);
        return ResponseEntity.ok(updated);
    }

    // DELETE vehicle by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable int id) {

          repoVehicle.findById(id).orElseThrow(()->new ResourceNotFoundException("Vehicle with id : "+ id+" Not found"));

//        if (!repoVehicle.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
        repoVehicle.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
