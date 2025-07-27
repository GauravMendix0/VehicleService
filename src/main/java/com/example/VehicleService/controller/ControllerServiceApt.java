package com.example.VehicleService.controller;

import com.example.VehicleService.model.ServiceRequest;
import com.example.VehicleService.model.ServiceAppt;
import com.example.VehicleService.service.ServiceServiceAppt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ControllerServiceApt {

    private final ServiceServiceAppt serviceServiceAppt;

    public ControllerServiceApt(ServiceServiceAppt serviceServiceAppt) {
        this.serviceServiceAppt = serviceServiceAppt;
    }

    // GET all appointments
    @GetMapping
    public ResponseEntity<List<ServiceAppt>> getAllAppointments() {
        List<ServiceAppt> appointments = serviceServiceAppt.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    // GET appointment by ID
    @GetMapping("/{id}")
    public ResponseEntity<ServiceAppt> getAppointmentById(@PathVariable int id) {
        ServiceAppt appointment = serviceServiceAppt.getAppointmentById(id);
        return ResponseEntity.ok(appointment);
    }

    // POST - Create new service appointment
    @PostMapping
    public ResponseEntity<ServiceAppt> createAppointment(@RequestBody ServiceRequest request) {
        ServiceAppt created = serviceServiceAppt.createAppointment(request);
        return ResponseEntity.ok(created);
    }

    // PUT - Full update
    @PutMapping("/{id}")
    public ResponseEntity<ServiceAppt> replaceAppointment(@RequestBody ServiceRequest request, @PathVariable int id) {
        ServiceAppt updated = serviceServiceAppt.replaceAppointment(id, request);
        return ResponseEntity.ok(updated);
    }

    // PATCH - Partial update
    @PatchMapping("/{id}")
    public ResponseEntity<ServiceAppt> updateAppointment(@RequestBody ServiceRequest request, @PathVariable int id) {
        ServiceAppt updated = serviceServiceAppt.updateAppointment(id, request);
        return ResponseEntity.ok(updated);
    }

    // DELETE appointment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable int id) {
        serviceServiceAppt.deleteAppointment(id);
        return ResponseEntity.ok().build();
    }
}
