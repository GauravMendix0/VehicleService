package com.example.VehicleService.service;

import com.example.VehicleService.dto.VehicleRequestDTO;
import com.example.VehicleService.dto.VehicleResponseDTO;
import com.example.VehicleService.exception.ResourceNotFoundException;
import com.example.VehicleService.model.Owner;
import com.example.VehicleService.model.Vehicle;
import com.example.VehicleService.repo.RepoOwner;
import com.example.VehicleService.repo.RepoVehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    private RepoVehicle vehicleRepository;

    @Autowired
    private RepoOwner ownerRepository;

    public VehicleResponseDTO addVehicle(VehicleRequestDTO requestDTO) {
        Owner owner = ownerRepository.findById(requestDTO.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        Vehicle vehicle = new Vehicle();
        vehicle.setNumber(requestDTO.getNumber());
        vehicle.setModel(requestDTO.getModel());
        vehicle.setOwner(owner);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return convertToResponseDTO(savedVehicle);
    }

    public VehicleResponseDTO getVehicleById(int id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
        return convertToResponseDTO(vehicle);
    }

    public List<VehicleResponseDTO> getAllVehicles() {
        return vehicleRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public VehicleResponseDTO partialUpdateVehicle(int id, VehicleRequestDTO requestDTO) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));

        if (requestDTO.getNumber() != null) {
            vehicle.setNumber(requestDTO.getNumber());
        }

        if (requestDTO.getModel() != null) {
            vehicle.setModel(requestDTO.getModel());
        }

        if (requestDTO.getOwnerId() != 0) {
            Owner owner = ownerRepository.findById(requestDTO.getOwnerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
            vehicle.setOwner(owner);
        }

        Vehicle updated = vehicleRepository.save(vehicle);
        return convertToResponseDTO(updated);
    }


    public VehicleResponseDTO updateVehicle(int id, VehicleRequestDTO requestDTO) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));

        Owner owner = ownerRepository.findById(requestDTO.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        vehicle.setNumber(requestDTO.getNumber());
        vehicle.setModel(requestDTO.getModel());
        vehicle.setOwner(owner);

        Vehicle updated = vehicleRepository.save(vehicle);
        return convertToResponseDTO(updated);
    }

    public void deleteVehicle(int id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
        vehicleRepository.delete(vehicle);
    }

    private VehicleResponseDTO convertToResponseDTO(Vehicle vehicle) {
        VehicleResponseDTO dto = new VehicleResponseDTO();
        dto.setId(vehicle.getVid());
        dto.setNumber(vehicle.getNumber());
        dto.setModel(vehicle.getModel());
        dto.setOwnerName(vehicle.getOwner().getName());
        return dto;
    }
}
