package az.iktlab.booking.controller;

import az.iktlab.booking.model.Flight;
import az.iktlab.booking.service.impl.BookServiceImpl;
import az.iktlab.booking.service.inter.BookService;

import java.util.List;
import java.util.Scanner;

public class BookController {

    private static final Scanner in = new Scanner(System.in);
    private final BookService bookService;
    private final FlightController flightController;

    public BookController(BookService bookService,FlightController flightController) {
        this.bookService = bookService;
        this.flightController = flightController;
    }
    public BookController() {
        this.bookService = new BookServiceImpl();
        this.flightController = new FlightController();
    }

    public boolean cancel(){
        System.out.print("Enter ID of booking: ");
        int booking_id = in.nextInt();
        boolean isCancel = bookService.cancel(booking_id);
        System.out.println(isCancel ? "Booking has been canceled" :
                "There isn't matching booking number.\nPlease check again" );
        return isCancel;
    }

    public boolean book(){
        List<Flight> flights = flightController.search();
        System.out.println("0.Back");
        flights.forEach(System.out::print);

        System.out.print("Please enter a flight number (0 to return menu)\n" + "option: ");
        int option = in.nextInt();
        System.out.println("Please enter count of people");
        int numberOfPeople = in.nextInt();
        boolean result = bookService.book(option,numberOfPeople);
        return result;
    }
}
