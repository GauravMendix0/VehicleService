package service;

import model.*;
import java.util.*;

public class BookingService 
{
    private List<ServiceAppoint> appointments = new ArrayList<>();

    public void bookService(ServiceAppoint appointment) 
    {
        appointments.add(appointment);
    }

    public List<ServiceAppoint> getAllAppointments() 
    {
        return appointments;
    }

    public void displayBookings() 
    {
        if (appointments.isEmpty()) 
        {
            System.out.println("No booking");
            return;
        }

        for (Bookable b : appointments) 
        {
            System.out.println(b.getBookinfo());
        }
    }
}
