package com.example.VehicleService.controller;

import com.example.VehicleService.Owner;
import com.example.VehicleService.repo.RepoOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/owners")
public class ControllerOwner {

    @Autowired
    private RepoOwner repoOwner;

    // Get all owners
    @GetMapping("/")
    public ResponseEntity<List<Owner>> getAll() {
        return ResponseEntity.ok(repoOwner.findAll());
    }

    // Get owner by ID
    @GetMapping("/{id}")
    public ResponseEntity<Owner> getById(@PathVariable int id) {
        Optional<Owner> owner = repoOwner.findById(id);
        return owner.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create new owner
    @PostMapping("/")
    public ResponseEntity<Owner> create(@RequestBody Owner owner) {
        Owner savedOwner = repoOwner.save(owner);
        return ResponseEntity.ok(savedOwner);
    }

    // Update existing owner
    @PutMapping("/{id}")
    public ResponseEntity<Owner> update(@PathVariable int id, @RequestBody Owner owner) {
        if (!repoOwner.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Owner updatedOwner = repoOwner.save(owner);
        return ResponseEntity.ok(updatedOwner);
    }

    // Delete owner
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (!repoOwner.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repoOwner.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
