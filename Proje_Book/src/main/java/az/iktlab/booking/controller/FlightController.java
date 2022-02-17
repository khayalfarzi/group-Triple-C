package az.iktlab.booking.controller;

import az.iktlab.booking.model.Flight;
import az.iktlab.booking.service.impl.FlightServiceImpl;
import az.iktlab.booking.service.inter.FlightService;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class FlightController {

    private static final Scanner in = new Scanner(System.in);
    private final FlightService flightService;

    public FlightController(FlightService flightService){
        this.flightService = flightService;
    }

    public FlightController(){
        this.flightService = new FlightServiceImpl();
    }

    public List<Flight> show(){
        List<Flight> flights = flightService.show();
        System.out.println(flights.isEmpty() ? "There are not any flights next 24 hours" : flights);
        return flights;
    }

    public Flight info() {
        System.out.print("Please enter flight id: ");
        int flight_id = in.nextInt();

        Flight flight = flightService.info(flight_id);
        System.out.println(flight.getFlightId() != null ? flight :
                "Not any flight matching with entered flight number \nPlease check again" );
        return flight;
    }

    public List<Flight> flights(){
        System.out.print("Please enter name and surname");
        System.out.print("Name: ");
        String name = in.nextLine();
        System.out.print("Surname: ");
        String surname = in.nextLine();
        List<Flight> flights = flightService.getFlightsByNameAndSurname(name,surname);
        System.out.println(flights.isEmpty() ? "There is not any flight with your name\nPlease try again" :
                flights);
        return flights;
    }

    public List<Flight> search(){
        System.out.print("Please enter destination,date and number of people: \ndestination: ");
        String destination = in.nextLine();
        System.out.print("date: ");
        String date = in.nextLine();
        System.out.print("Number of people: ");
        int numberOfPeople = in.nextInt();

        return flightService.search(destination,Date.valueOf(date),numberOfPeople);
    }
}
