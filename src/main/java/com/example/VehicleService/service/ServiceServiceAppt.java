package com.example.VehicleService.service;

import com.example.VehicleService.exception.ResourceNotFoundException;
import com.example.VehicleService.model.ServiceAppt;
import com.example.VehicleService.model.ServiceRequest;
import com.example.VehicleService.model.Vehicle;
import com.example.VehicleService.repo.RepoServiceApt;
import com.example.VehicleService.repo.RepoVehicle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceServiceAppt {

    private final RepoServiceApt repoServiceApt;
    private final RepoVehicle repoVehicle;

    public ServiceServiceAppt(RepoServiceApt repoServiceApt, RepoVehicle repoVehicle) {
        this.repoServiceApt = repoServiceApt;
        this.repoVehicle = repoVehicle;
    }

    public List<ServiceAppt> getAllAppointments() {
        return repoServiceApt.findAll();
    }

    public ServiceAppt getAppointmentById(int id) {
        return repoServiceApt.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service with id : " + id + " Not found"));
    }

    public ServiceAppt createAppointment(ServiceRequest request) {
        Vehicle vehicle = repoVehicle.findById(request.getVehicle().getVid())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle with id : " + request.getVehicle().getVid() + " Not found"));

        ServiceAppt appt = request.getServiceAppt();
        appt.setVehicle(vehicle);

        return repoServiceApt.save(appt);
    }

    public ServiceAppt replaceAppointment(int id, ServiceRequest request) {
        ServiceAppt existing = repoServiceApt.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service with id : " + id + " Not found"));

        Vehicle vehicle = repoVehicle.findById(request.getVehicle().getVid())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle with id : " + request.getVehicle().getVid() + " Not found"));

        ServiceAppt newAppt = request.getServiceAppt();
        existing.setServiceDate(newAppt.getServiceDate());
        existing.setService(newAppt.getService());
        existing.setVehicle(vehicle);

        return repoServiceApt.save(existing);
    }

    public ServiceAppt updateAppointment(int id, ServiceRequest request) {
        ServiceAppt existing = repoServiceApt.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service with id : " + id + " Not found"));

        ServiceAppt patch = request.getServiceAppt();

        if (patch.getServiceDate() != null) {
            existing.setServiceDate(patch.getServiceDate());
        }

        if (patch.getService() != null) {
            existing.setService(patch.getService());
        }

        if (request.getVehicle() != null) {
            Vehicle vehicle = repoVehicle.findById(request.getVehicle().getVid())
                    .orElseThrow(() -> new ResourceNotFoundException("Vehicle with id : " + request.getVehicle().getVid() + " Not found"));
            existing.setVehicle(vehicle);
        }

        return repoServiceApt.save(existing);
    }

    public void deleteAppointment(int id) {
        ServiceAppt appt = repoServiceApt.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service with id : " + id + " Not found"));
        repoServiceApt.delete(appt);
    }
}
