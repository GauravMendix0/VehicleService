package com.example.VehicleService.controller;

import com.example.VehicleService.ServiceAppt;
import com.example.VehicleService.ServiceRequest;
import com.example.VehicleService.Vehicle;
import com.example.VehicleService.repo.RepoServiceApt;
import com.example.VehicleService.repo.RepoVehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/services")
public class ControllerServiceApt {

    @Autowired
    private RepoServiceApt repoServiceApt;

    @Autowired
    private RepoVehicle repoVehicle;

    // GET all
    @GetMapping("/")
    public ResponseEntity<List<ServiceAppt>> getAll() {
        return ResponseEntity.ok(repoServiceApt.findAll());
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<ServiceAppt> getById(@PathVariable int id) {
        Optional<ServiceAppt> optional = repoServiceApt.findById(id);
        return optional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST
    @PostMapping("/")
    public ResponseEntity<ServiceAppt> create(@RequestBody ServiceRequest request ) {

        Vehicle vehicle = request.getVehicle();
        ServiceAppt serviceApt = request.getServiceAppt();

        if(repoVehicle.existsById(vehicle.getVid()))
        {
            serviceApt.setVehicle(vehicle);
            ServiceAppt saved =repoServiceApt.save(serviceApt);
            return ResponseEntity.ok(saved);
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<ServiceAppt> replace(@RequestBody ServiceRequest request, @PathVariable int id) {
        Optional<ServiceAppt> optional = repoServiceApt.findById(id);
        Vehicle vehicle = request.getVehicle();
        ServiceAppt newAppt = request.getServiceAppt();

        if (optional.isPresent()) {
            if (!repoVehicle.existsById(vehicle.getVid())) {
                return ResponseEntity.notFound().build();
            }

            ServiceAppt existing = optional.get();
            existing.setDate(newAppt.getDate());
            existing.setService(newAppt.getService());
            existing.setVehicle(vehicle);

            ServiceAppt updated = repoServiceApt.save(existing);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<ServiceAppt> update(@RequestBody ServiceRequest request, @PathVariable int id) {
        Optional<ServiceAppt> optional = repoServiceApt.findById(id);

        if (optional.isPresent()) {
            ServiceAppt existing = optional.get();
            ServiceAppt patch = request.getServiceAppt();
            Vehicle vehicle = request.getVehicle();

            if (patch.getDate() != null) {
                existing.setDate(patch.getDate());
            }

            if (patch.getService() != null) {
                existing.setService(patch.getService());
            }

            if (vehicle != null && repoVehicle.existsById(vehicle.getVid())) {
                existing.setVehicle(vehicle);
            }

            ServiceAppt updated = repoServiceApt.save(existing);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (repoServiceApt.existsById(id)) {
            repoServiceApt.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
