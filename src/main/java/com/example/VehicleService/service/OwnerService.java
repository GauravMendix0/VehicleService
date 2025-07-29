// package: com.example.VehicleService.service

package com.example.VehicleService.service;

import com.example.VehicleService.dto.OwnerRequestDTO;
import com.example.VehicleService.dto.OwnerResponseDTO;
import com.example.VehicleService.exception.ResourceNotFoundException;
import com.example.VehicleService.model.Owner;
import com.example.VehicleService.repo.RepoOwner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerService {

    private final RepoOwner repoOwner;

    public OwnerService(RepoOwner repoOwner) {
        this.repoOwner = repoOwner;
    }

    public List<OwnerResponseDTO> getAllOwner() {
        return repoOwner.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public OwnerResponseDTO getById(int id) {
        Owner owner = repoOwner.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner with ID " + id + " not found"));
        return toResponseDTO(owner);
    }

    public OwnerResponseDTO saveOwner(OwnerRequestDTO dto) {
        Owner owner = new Owner();
        owner.setName(dto.getName());
        owner.setContact(dto.getContact());
        Owner saved = repoOwner.save(owner);
        return toResponseDTO(saved);
    }

    public OwnerResponseDTO updateOwner(int id, OwnerRequestDTO dto) {
        if (!repoOwner.existsById(id)) {
            throw new ResourceNotFoundException("Owner with ID " + id + " not found");
        }
        Owner owner = new Owner();
        owner.setOid(id);
        owner.setName(dto.getName());
        owner.setContact(dto.getContact());
        return toResponseDTO(repoOwner.save(owner));
    }

    public OwnerResponseDTO patchOwner(int id, OwnerRequestDTO partialDTO) {
        Owner existingOwner = repoOwner.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner with ID " + id + " not found"));

        if (partialDTO.getName() != null) {
            existingOwner.setName(partialDTO.getName());
        }
        if (partialDTO.getContact() != null) {
            existingOwner.setContact(partialDTO.getContact());
        }

        return toResponseDTO(repoOwner.save(existingOwner));
    }

    public void deleteOwner(int id) {
        if (!repoOwner.existsById(id)) {
            throw new ResourceNotFoundException("Owner with ID " + id + " not found");
        }
        repoOwner.deleteById(id);
    }

    private OwnerResponseDTO toResponseDTO(Owner owner) {
        OwnerResponseDTO dto = new OwnerResponseDTO();
        dto.setId(owner.getOid());
        dto.setName(owner.getName());
        dto.setContact(owner.getContact());
        return dto;
    }
}
