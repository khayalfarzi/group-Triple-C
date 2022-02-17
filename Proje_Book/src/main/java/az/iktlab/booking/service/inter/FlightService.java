package az.iktlab.booking.service.inter;

import az.iktlab.booking.model.Flight;

import java.sql.Date;
import java.util.List;

public interface FlightService {

    List<Flight> search(String destination, Date date, Integer numberOfPeople);

    List<Flight> show();

    Flight info(int flight_id);

    List<Flight> getFlightsByNameAndSurname(String name,String surname);

    Flight getFlightByBookId(int book);

    boolean update(Flight flight);
}
