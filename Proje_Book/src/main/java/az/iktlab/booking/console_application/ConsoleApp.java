package az.iktlab.booking.console_application;

import az.iktlab.booking.controller.BookController;
import az.iktlab.booking.controller.FlightController;

import java.util.Scanner;

public class ConsoleApp {
    private static final BookController bookController = new BookController();
    private static final FlightController flightController = new FlightController();
    private static final Scanner in = new Scanner(System.in);



    public static void consoleRun(){
            int command;
            do {
                printCommand();
                System.out.print("Please enter the command: ");
                command = in.nextInt();

                switch (command){
                    case 1:
                        flightController.show();
                        break;
                    case 2:
                        flightController.info();
                        break;
                    case 3:
                        bookController.book();
                        break;
                    case 4:
                        bookController.cancel();
                        break;
                    case 5:
                        flightController.flights();
                        break;
                    case 6:
                        System.out.println("- Stop the console app.\n");
                        break;
                }
            }while (command != 6);
            System.out.println("************** END CONSOLE APP **************");
    }
    public static void printCommand() {
        System.out.println(
                "\nLIST OF COMMAND: \n" +
                        "- 1. ONLINE BOARD\n" +
                        "- 2. VIEW FLIGHT INFORMATION\n" +
                        "- 3. SEARCH FLIGHT AND BOOKING: \n" +
                        "- 4. CANCEL OF BOOKING: \n" +
                        "- 5. SHOW MY FLIGHTS: \n" +
                        "- 6. EXIT FROM BOOKING \n");
    }

}
