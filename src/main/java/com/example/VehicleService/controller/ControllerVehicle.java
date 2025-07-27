package com.example.VehicleService.controller;

import com.example.VehicleService.model.Vehicle;
import com.example.VehicleService.model.VehicleRequest;
import com.example.VehicleService.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class ControllerVehicle {

    private final VehicleService vehicleService;

    public ControllerVehicle(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable int id) {
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody VehicleRequest request) {
        return ResponseEntity.ok(vehicleService.createVehicle(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> replaceVehicle(@PathVariable int id, @RequestBody VehicleRequest request) {
        return ResponseEntity.ok(vehicleService.replaceVehicle(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable int id, @RequestBody VehicleRequest request) {
        return ResponseEntity.ok(vehicleService.updateVehicle(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable int id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.ok().build();
    }
}
