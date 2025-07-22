package com.example.VehicleService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates ID
    private int oid;

    private String name;
    private String contact;

    public Owner() {}

    public Owner(int oid, String name, String contact) {
        this.oid = oid;
        this.name = name;
        this.contact = contact;
    }

    // --- Getters and Setters ---

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
