package com.example.VehicleService;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Vehicle {

    @Id
    private int vid;
    private String number;
    private String model;
    //    private int oid;
    @OneToOne
    private Owner owner;

    public Vehicle() {}

    public Vehicle(String number, String model, Owner owner) {
        this.number = number;
        this.model = model;
        this.owner = owner;
    }

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

//    public int getOid() {
//        return oid;
//    }
//
//    public void setOid(int oid) {
//        this.oid = oid;
//    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

}
