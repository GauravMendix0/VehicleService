package model;

public class ServiceAppoint implements Bookable 
{
    private Vehicle vehicle;
    private String date;
    private String service;

    public ServiceAppoint(Vehicle vehicle, String date, String serviceType) 
    {
        this.vehicle = vehicle;
        this.date = date;
        this.service = serviceType;
    }

    @Override
    public String getBookinfo() 
    {
        return "Date :" + date + " Vehicle :" + vehicle.getNumber() + service + "Owner :" + vehicle.getOwner().getName();
    }

    public String getDate() 
    {
        return date;
    }
}
