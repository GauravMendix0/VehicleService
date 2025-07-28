package com.example.VehicleService.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vid;

    @Column(unique = true)
    @NotBlank(message = "Vehicle number cannot be blank")
    @Size(min = 3, max = 20, message = "Vehicle number must be between 3 and 20 characters")
    private String number;

    @NotBlank(message = "Model cannot be blank")
    @Size(min = 2, max = 30, message = "Model must be between 2 and 30 characters")
    private String model;

    @ManyToOne
    @NotNull(message = "Owner must be provided")
    private Owner owner;

    public Vehicle() {}

    public Vehicle(String number, String model, Owner owner) {
        this.number = number;
        this.model = model;
        this.owner = owner;
    }

    // --- Getters and Setters ---

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
