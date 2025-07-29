package com.example.VehicleService.service;

import com.example.VehicleService.dto.ServiceApptRequestDTO;
import com.example.VehicleService.dto.ServiceApptResponseDTO;
import com.example.VehicleService.exception.ResourceNotFoundException;
import com.example.VehicleService.model.ServiceAppt;
import com.example.VehicleService.model.Vehicle;
import com.example.VehicleService.repo.RepoServiceApt;
import com.example.VehicleService.repo.RepoVehicle;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceServiceAppt {

    private final RepoServiceApt repoServiceApt;
    private final RepoVehicle repoVehicle;

    public ServiceServiceAppt(RepoServiceApt repoServiceApt, RepoVehicle repoVehicle) {
        this.repoServiceApt = repoServiceApt;
        this.repoVehicle = repoVehicle;
    }

    public List<ServiceApptResponseDTO> getAllAppointments() {
        return repoServiceApt.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ServiceApptResponseDTO getAppointmentById(int id) {
        return convertToDTO(repoServiceApt.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service with id : " + id + " Not found")));
    }

    public ServiceApptResponseDTO createAppointment(ServiceApptRequestDTO request) {
        Vehicle vehicle = repoVehicle.findById(request.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle with id : " + request.getVehicleId() + " Not found"));

        ServiceAppt appt = new ServiceAppt();
        appt.setVehicle(vehicle);
        appt.setServiceDate(request.getServiceDate());
        appt.setService(request.getService());

        return convertToDTO(repoServiceApt.save(appt));
    }

    public ServiceApptResponseDTO replaceAppointment(int id, ServiceApptRequestDTO request) {
        ServiceAppt existing = repoServiceApt.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service with id : " + id + " Not found"));

        Vehicle vehicle = repoVehicle.findById(request.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle with id : " + request.getVehicleId() + " Not found"));

        existing.setService(request.getService());
        existing.setServiceDate(request.getServiceDate());
        existing.setVehicle(vehicle);

        return convertToDTO(repoServiceApt.save(existing));
    }

    public ServiceApptResponseDTO updateAppointment(int id, ServiceApptRequestDTO request) {
        ServiceAppt existing = repoServiceApt.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service with id : " + id + " Not found"));

        if (request.getServiceDate() != null) existing.setServiceDate(request.getServiceDate());
        if (request.getService() != null) existing.setService(request.getService());
        if (request.getVehicleId() != null) {
            Vehicle vehicle = repoVehicle.findById(request.getVehicleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Vehicle with id : " + request.getVehicleId() + " Not found"));
            existing.setVehicle(vehicle);
        }

        return convertToDTO(repoServiceApt.save(existing));
    }

    public void deleteAppointment(int id) {
        ServiceAppt appt = repoServiceApt.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service with id : " + id + " Not found"));
        repoServiceApt.delete(appt);
    }

    private ServiceApptResponseDTO convertToDTO(ServiceAppt appt) {
        ServiceApptResponseDTO dto = new ServiceApptResponseDTO();
        dto.setId(appt.getSid());
        dto.setServiceDate(appt.getServiceDate());
        dto.setService(appt.getService());
        dto.setVehicleNumber(appt.getVehicle().getNumber());
        dto.setVehicleModel(appt.getVehicle().getModel());
        dto.setOwnerName(appt.getVehicle().getOwner().getName());
        return dto;
    }
}
