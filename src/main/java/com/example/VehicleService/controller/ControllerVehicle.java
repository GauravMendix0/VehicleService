package com.example.VehicleService.controller;

import com.example.VehicleService.Owner;
import com.example.VehicleService.Vehicle;
import com.example.VehicleService.VehicleRequest;
import com.example.VehicleService.repo.RepoOwner;
import com.example.VehicleService.repo.RepoVehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehicles")
public class ControllerVehicle {

    @Autowired
    private RepoVehicle repoVehicle;

    @Autowired
    private RepoOwner repoOwner;

    // GET all vehicles
    @GetMapping("/")
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        List<Vehicle> vehicles = repoVehicle.findAll();
        return ResponseEntity.ok(vehicles);
    }

    // GET vehicle by ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable int id) {
        Optional<Vehicle> vehicle = repoVehicle.findById(id);
        return vehicle.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST - create new vehicle
//    @PostMapping("/")
//    public ResponseEntity<Vehicle> createVehicle(@RequestBody VehicleRequest request) {
//        Vehicle vehicle = request.getVehicle();
//        Owner owner = request.getOwner();
//        vehicle.setOwner(owner);
//        Vehicle saved = repoVehicle.save(vehicle);
//        return ResponseEntity.ok(saved);
//    }

    @PostMapping("/")
    public ResponseEntity<Vehicle> createVehicle(@RequestBody VehicleRequest request) {
        Vehicle vehicle = request.getVehicle();
        Owner owner = request.getOwner();
        if(repoOwner.findById(owner.getOid()).isPresent()) {
            vehicle.setOwner(owner);
            Vehicle saved = repoVehicle.save(vehicle);
            return ResponseEntity.ok(saved);
        }

        else return ResponseEntity.notFound().build();

    }


//    @PostMapping("/")
//    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
//        Owner owner = new Owner(5,"Gaurav","123456789");
//        vehicle.setOwner(owner);
//        Vehicle saved = repoVehicle.save(vehicle);
//        return ResponseEntity.ok(saved);
//    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> replaceVehicle(@RequestBody VehicleRequest request, @PathVariable int id) {
        Optional<Vehicle> optionalVehicle = repoVehicle.findById(id);
        Vehicle newVehicle = request.getVehicle();
        Owner owner = request.getOwner();

        if (optionalVehicle.isPresent()) {
            if (!repoOwner.existsById(owner.getOid())) {
                return ResponseEntity.notFound().build();
            }

            Vehicle existing = optionalVehicle.get();
            existing.setNumber(newVehicle.getNumber());
            existing.setModel(newVehicle.getModel());
            existing.setOwner(owner);

            Vehicle updated = repoVehicle.save(existing);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody VehicleRequest request, @PathVariable int id) {
        Optional<Vehicle> optionalVehicle = repoVehicle.findById(id);

        if (optionalVehicle.isPresent()) {
            Vehicle existing = optionalVehicle.get();
            Vehicle partial = request.getVehicle();
            Owner patchOwner = request.getOwner();

            if (partial.getNumber() != null) {
                existing.setNumber(partial.getNumber());
            }

            if (partial.getModel() != null) {
                existing.setModel(partial.getModel());
            }

            if (patchOwner != null && repoOwner.existsById(patchOwner.getOid())) {
                existing.setOwner(patchOwner);
            }

            Vehicle updated = repoVehicle.save(existing);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // DELETE vehicle
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable int id) {
        if (repoVehicle.existsById(id)) {
            repoVehicle.deleteById(id);
            return ResponseEntity.ok().build(); // 200 OK
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
