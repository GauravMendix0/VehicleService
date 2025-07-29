package com.example.VehicleService.controller;

import com.example.VehicleService.dto.VehicleRequestDTO;
import com.example.VehicleService.dto.VehicleResponseDTO;
import com.example.VehicleService.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class ControllerVehicle {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponseDTO> createVehicle(@Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        VehicleResponseDTO created = vehicleService.addVehicle(vehicleRequestDTO);
        return ResponseEntity.status(200).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> getVehicle(@PathVariable int id) {
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> partialUpdateVehicle(
            @PathVariable int id,
            @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        VehicleResponseDTO updated = vehicleService.partialUpdateVehicle(id, vehicleRequestDTO);
        return ResponseEntity.ok(updated);
    }


    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> updateVehicle(
            @PathVariable int id,
            @Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        VehicleResponseDTO updated = vehicleService.updateVehicle(id, vehicleRequestDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable int id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.ok("Vehicle deleted successfully");
    }
}
