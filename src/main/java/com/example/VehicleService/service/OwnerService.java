package com.example.VehicleService.service;

import com.example.VehicleService.exception.ResourceNotFoundException;
import com.example.VehicleService.model.Owner;
import com.example.VehicleService.repo.RepoOwner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    private final RepoOwner repoOwner;

    public OwnerService(RepoOwner repoOwner) {
        this.repoOwner = repoOwner;
    }

    public List<Owner> getAll() {
        return repoOwner.findAll();
    }

    public Owner getById(int id) {
        return repoOwner.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner with ID " + id + " not found"));
    }

    public Owner saveOwner(Owner owner) {
        return repoOwner.save(owner);
    }

    public Owner updateOwner(int id, Owner owner) {
        if (!repoOwner.existsById(id)) {
            throw new ResourceNotFoundException("Owner with ID " + id + " not found");
        }
        owner.setOid(id);
        return repoOwner.save(owner);
    }

    public Owner patchOwner(int id, Owner partialOwner) {
        Owner existingOwner = repoOwner.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Owner with ID " + id + " not found"));

        if (partialOwner.getName() != null) {
            existingOwner.setName(partialOwner.getName());
        }
        if (partialOwner.getContact() != null) {
            existingOwner.setContact(partialOwner.getContact());
        }

        return repoOwner.save(existingOwner);
    }

    public void deleteOwner(int id) {
        if (!repoOwner.existsById(id)) {
            throw new ResourceNotFoundException("Owner with ID " + id + " not found");
        }
        repoOwner.deleteById(id);
    }
}
