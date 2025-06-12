import model.*;
import service.BookingService;

import java.util.Scanner;

public class Main 
{
    public static void main(String[] args) 
    {
        BookingService service = new BookingService();
        Scanner sc = new Scanner(System.in);

        while (true) 
        {
            System.out.println("\n 1. Book Service \n 2. Show All Bookings\n 3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) 
            {
                case 1 -> 
                {
                    System.out.print("Owner name: ");
                    String name = sc.nextLine();

                    System.out.print("Contact: ");
                    String contact = sc.nextLine();

                    System.out.print("Vehicle number: ");
                    String number = sc.nextLine();

                    System.out.print("Vehicle model: ");
                    String model = sc.nextLine();

                    System.out.print("Service type : ");
                    String serviceType = sc.nextLine();

                    System.out.print("Service date : ");
                    String date = sc.nextLine();

                    Owner owner = new Owner(name, contact);
                    Vehicle vehicle = new Vehicle(number, model, owner);
                    ServiceAppoint appointment = new ServiceAppoint(vehicle, date, serviceType);

                    service.bookService(appointment);
                    System.out.println("Service booked successfully");
                }

                case 2 -> 
                {
                    System.out.println("\n All Service Bookings ");
                    service.displayBookings();
                }

                case 3 -> 
                {
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                }

                default -> System.out.println("Invalid choice");
            }
        }
    }
}
