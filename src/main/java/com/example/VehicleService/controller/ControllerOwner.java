// package: com.example.VehicleService.controller

package com.example.VehicleService.controller;

import com.example.VehicleService.dto.OwnerRequestDTO;
import com.example.VehicleService.dto.OwnerResponseDTO;
import com.example.VehicleService.service.OwnerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
public class ControllerOwner {

    private final OwnerService ownerService;

    public ControllerOwner(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public ResponseEntity<List<OwnerResponseDTO>> getAllOwners() {
        return ResponseEntity.ok(ownerService.getAllOwner());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerResponseDTO> getOwnerById(@PathVariable int id) {
        return ResponseEntity.ok(ownerService.getById(id));
    }

    @PostMapping
    public ResponseEntity<OwnerResponseDTO> createOwner(@Valid @RequestBody OwnerRequestDTO ownerRequestDTO) {
        return ResponseEntity.ok(ownerService.saveOwner(ownerRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerResponseDTO> updateOwner(@PathVariable int id, @Valid @RequestBody OwnerRequestDTO ownerRequestDTO) {
        return ResponseEntity.ok(ownerService.updateOwner(id, ownerRequestDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OwnerResponseDTO> patchOwner(@PathVariable int id, @RequestBody OwnerRequestDTO ownerRequestDTO) {
        return ResponseEntity.ok(ownerService.patchOwner(id, ownerRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable int id) {
        ownerService.deleteOwner(id);
        return ResponseEntity.ok().build();
    }
}
