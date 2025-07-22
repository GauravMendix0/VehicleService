package com.example.VehicleService.model;

import jakarta.persistence.*;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vid;

    @Column(unique = true)
    private String number;

    private String model;

    @ManyToOne
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
