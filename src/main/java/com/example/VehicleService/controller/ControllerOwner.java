package com.example.VehicleService.controller;

import com.example.VehicleService.exception.ResourceNotFoundException;
import com.example.VehicleService.model.Owner;
import com.example.VehicleService.repo.RepoOwner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/owners")
public class ControllerOwner {
    private final RepoOwner repoOwner;

    public ControllerOwner(RepoOwner repoOwner) {
        this.repoOwner = repoOwner;
    }

    // GET all owners
    @GetMapping
    public ResponseEntity<List<Owner>> getAllOwners() {
        return ResponseEntity.ok(repoOwner.findAll());
    }

    // GET owner by ID
    @GetMapping("/{id}")
    public ResponseEntity<Owner> getOwnerById(@PathVariable int id) {
        Owner owner = repoOwner.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner with ID " + id + " not found"));
        return ResponseEntity.ok(owner);
    }
    // POST - create new owner
    @PostMapping
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner) {
        Owner savedOwner = repoOwner.save(owner);
        return ResponseEntity.ok(savedOwner);
    }

    // Put - update all
    @PutMapping("/{id}")
    public ResponseEntity<Owner> updateOwner(@PathVariable int id, @RequestBody Owner owner) {
        if (!repoOwner.existsById(id)) {
            throw new ResourceNotFoundException("Owner with ID " + id + " not found");
        }
        owner.setOid(id);
        Owner updatedOwner = repoOwner.save(owner);
        return ResponseEntity.ok(updatedOwner);
    }

    // Patch - partial update
    @PatchMapping("/{id}")
    public ResponseEntity<Owner> patchOwner(@PathVariable int id, @RequestBody Owner partialOwner) {
        Owner existingOwner = repoOwner.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner with ID " + id + " not found"));

        if (partialOwner.getName() != null) {
            existingOwner.setName(partialOwner.getName());
        }
        if (partialOwner.getContact() != null) {
            existingOwner.setContact(partialOwner.getContact());
        }

        return ResponseEntity.ok(repoOwner.save(existingOwner));
    }

    // Delete - Delete owner
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable int id) {
        if (!repoOwner.existsById(id)) {
            throw new ResourceNotFoundException("Owner with ID " + id + " not found");
        }
        repoOwner.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
