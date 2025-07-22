package com.example.VehicleService.controller;

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
        Optional<Owner> owner = repoOwner.findById(id);
        return owner.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST - create new owner
    @PostMapping
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner) {
        Owner savedOwner = repoOwner.save(owner);
        return ResponseEntity.ok(savedOwner);
    }

    // PUT - full update of existing owner
    @PutMapping("/{id}")
    public ResponseEntity<Owner> updateOwner(@PathVariable int id, @RequestBody Owner owner) {
        if (!repoOwner.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        owner.setOid(id); // Ensure correct ID is used
        Owner updatedOwner = repoOwner.save(owner);
        return ResponseEntity.ok(updatedOwner);
    }

    // PATCH - partial update (currently acts like full update)
    @PatchMapping("/{id}")
    public ResponseEntity<Owner> patchOwner(@PathVariable int id, @RequestBody Owner partialOwner) {
        Optional<Owner> existingOwnerOpt = repoOwner.findById(id);
        if (existingOwnerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Owner existingOwner = existingOwnerOpt.get();
        if (partialOwner.getName() != null) {
            existingOwner.setName(partialOwner.getName());
        }
        if (partialOwner.getContact() != null) {
            existingOwner.setContact(partialOwner.getContact());
        }

        Owner updatedOwner = repoOwner.save(existingOwner);
        return ResponseEntity.ok(updatedOwner);
    }

    // DELETE owner by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable int id) {
        if (!repoOwner.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repoOwner.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
