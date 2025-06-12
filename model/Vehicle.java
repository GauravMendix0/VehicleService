package model;

public class Vehicle 
{
    private String number;
    private String model;
    private Owner owner;

    public Vehicle(String number, String model, Owner owner) 
    {
        this.number = number;
        this.model = model;
        this.owner = owner;
    }

    public String getNumber() 
    { 
        return number; 
    }

    public String getModel() 
    { 
        return model; 
    }

    public Owner getOwner() 
    { 
        return owner; 
    }
}
    