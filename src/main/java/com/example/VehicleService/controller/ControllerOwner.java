package com.example.VehicleService.controller;

import com.example.VehicleService.model.Owner;
import com.example.VehicleService.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
public class ControllerOwner {

    private final OwnerService ownerService;

    @Autowired
    public ControllerOwner(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public ResponseEntity<List<Owner>> getAllOwners() {
        return ResponseEntity.ok(ownerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Owner> getOwnerById(@PathVariable int id) {
        return ResponseEntity.ok(ownerService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner) {
        return ResponseEntity.ok(ownerService.saveOwner(owner));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Owner> updateOwner(@PathVariable int id, @RequestBody Owner owner) {
        return ResponseEntity.ok(ownerService.updateOwner(id, owner));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Owner> patchOwner(@PathVariable int id, @RequestBody Owner partialOwner) {
        return ResponseEntity.ok(ownerService.patchOwner(id, partialOwner));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable int id) {
        ownerService.deleteOwner(id);
        return ResponseEntity.ok().build();
    }
}
