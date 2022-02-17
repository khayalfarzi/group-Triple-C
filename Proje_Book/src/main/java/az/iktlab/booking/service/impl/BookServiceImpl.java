package az.iktlab.booking.service.impl;

import az.iktlab.booking.dao.impl.BookDaoImpl;
import az.iktlab.booking.dao.impl.PersonDaoImpl;
import az.iktlab.booking.dao.inter.CrudDao;
import az.iktlab.booking.dao.inter.PersonDao;
import az.iktlab.booking.model.Book;
import az.iktlab.booking.model.Flight;
import az.iktlab.booking.model.Person;
import az.iktlab.booking.service.inter.BookService;
import az.iktlab.booking.service.inter.FlightService;

import java.util.List;
import java.util.Scanner;


public class BookServiceImpl implements BookService {

    private static final Scanner scanner = new Scanner(System.in);

    private final CrudDao<Book> bookDao;
    private final PersonDao personDao;
    private final FlightService flightService;

    public BookServiceImpl() {
        this.bookDao = new BookDaoImpl();
        this.personDao = new PersonDaoImpl();
        this.flightService = new FlightServiceImpl();
    }
    @Override
    public boolean cancel(int book_id) {
        if(bookDao.getById(book_id).isPresent() && bookDao.getById(book_id).get().getBookId() != null ){
            boolean result = bookDao.remove(book_id);
            Flight flight = flightService.getFlightByBookId(book_id);
            return result;
        }
        else
            return false;
    }

    @Override
    public boolean book(int option,int peopleOfNumber){
        boolean result = false;
        for (int i = 1; i <= peopleOfNumber; i++) {
            System.out.print("Name: ");
            String name  = scanner.nextLine();
            System.out.print("Surname: ");
            String surname  = scanner.nextLine();

            Person person = personDao.getPersonByNameAndSurname(name,surname).get();
            Flight flight = flightService.info(option);
            Book book;
            if (person.getPersonId() == null) {
                new PersonDaoImpl().add(new Person(name, surname));
                person = personDao.getPersonByNameAndSurname(name, surname).get();
            }
            book = new Book(1,person,flight);
            List<Flight> flights = flightService.getFlightsByNameAndSurname(name,surname);
            if(!flights.isEmpty() && flights.contains(flight)){
                return result;
            }
            result = bookDao.add(book);
        }
        return result;
    }
}
