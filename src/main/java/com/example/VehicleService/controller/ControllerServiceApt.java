package com.example.VehicleService.controller;

import com.example.VehicleService.dto.ServiceApptRequestDTO;
import com.example.VehicleService.dto.ServiceApptResponseDTO;
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

    @GetMapping
    public ResponseEntity<List<ServiceApptResponseDTO>> getAllAppointments() {
        return ResponseEntity.ok(serviceServiceAppt.getAllAppointments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceApptResponseDTO> getAppointmentById(@PathVariable int id) {
        return ResponseEntity.ok(serviceServiceAppt.getAppointmentById(id));
    }

    @PostMapping
    public ResponseEntity<ServiceApptResponseDTO> createAppointment(@RequestBody ServiceApptRequestDTO request) {
        return ResponseEntity.ok(serviceServiceAppt.createAppointment(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceApptResponseDTO> replaceAppointment(@PathVariable int id,
                                                                     @RequestBody ServiceApptRequestDTO request) {
        return ResponseEntity.ok(serviceServiceAppt.replaceAppointment(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ServiceApptResponseDTO> updateAppointment(@PathVariable int id,
                                                                    @RequestBody ServiceApptRequestDTO request) {
        return ResponseEntity.ok(serviceServiceAppt.updateAppointment(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable int id) {
        serviceServiceAppt.deleteAppointment(id);
        return ResponseEntity.ok().build();
    }
}
