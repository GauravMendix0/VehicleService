package com.example.VehicleService.controller;

import com.example.VehicleService.model.ServiceAppt;
import com.example.VehicleService.ServiceRequest;
import com.example.VehicleService.model.Vehicle;
import com.example.VehicleService.repo.RepoServiceApt;
import com.example.VehicleService.repo.RepoVehicle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/services")
public class ControllerServiceApt {

    private final RepoServiceApt repoServiceApt;
    private final RepoVehicle repoVehicle;

    public ControllerServiceApt(RepoServiceApt repoServiceApt, RepoVehicle repoVehicle) {
        this.repoServiceApt = repoServiceApt;
        this.repoVehicle = repoVehicle;
    }

    // GET all appointments
    @GetMapping
    public ResponseEntity<List<ServiceAppt>> getAllAppointments() {
        return ResponseEntity.ok(repoServiceApt.findAll());
    }

    // GET appointment by ID
    @GetMapping("/{id}")
    public ResponseEntity<ServiceAppt> getAppointmentById(@PathVariable int id) {
        return repoServiceApt.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST - Create new service appointment
    @PostMapping
    public ResponseEntity<ServiceAppt> createAppointment(@RequestBody ServiceRequest request) {
        Vehicle vehicle = request.getVehicle();
        ServiceAppt serviceApt = request.getServiceAppt();

        // Ensure the vehicle exists before assigning
        Optional<Vehicle> existingVehicle = repoVehicle.findById(vehicle.getVid());
        if (existingVehicle.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        serviceApt.setVehicle(existingVehicle.get());
        ServiceAppt saved = repoServiceApt.save(serviceApt);
        return ResponseEntity.ok(saved);
    }

    // PUT - Full update
    @PutMapping("/{id}")
    public ResponseEntity<ServiceAppt> replaceAppointment(@RequestBody ServiceRequest request, @PathVariable int id) {
        Optional<ServiceAppt> optional = repoServiceApt.findById(id);
        Optional<Vehicle> vehicleOpt = repoVehicle.findById(request.getVehicle().getVid());

        if (optional.isEmpty() || vehicleOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ServiceAppt existing = optional.get();
        ServiceAppt newAppt = request.getServiceAppt();

        existing.setDate(newAppt.getDate());
        existing.setService(newAppt.getService());
        existing.setVehicle(vehicleOpt.get());

        ServiceAppt updated = repoServiceApt.save(existing);
        return ResponseEntity.ok(updated);
    }

    // PATCH - Partial update
    @PatchMapping("/{id}")
    public ResponseEntity<ServiceAppt> updateAppointment(@RequestBody ServiceRequest request, @PathVariable int id) {
        Optional<ServiceAppt> optional = repoServiceApt.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ServiceAppt existing = optional.get();
        ServiceAppt patch = request.getServiceAppt();

        if (patch.getDate() != null) {
            existing.setDate(patch.getDate());
        }

        if (patch.getService() != null) {
            existing.setService(patch.getService());
        }

        Vehicle vehicle = request.getVehicle();
        if (vehicle != null && repoVehicle.existsById(vehicle.getVid())) {
            existing.setVehicle(vehicle);
        }

        ServiceAppt updated = repoServiceApt.save(existing);
        return ResponseEntity.ok(updated);
    }

    // DELETE appointment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable int id) {
        if (!repoServiceApt.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repoServiceApt.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
