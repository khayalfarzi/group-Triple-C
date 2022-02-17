package az.iktlab.booking.dao.inter;

import az.iktlab.booking.model.Flight;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface FlightDao extends CrudDao<Flight> {

    List<Flight> getFlightsByNameAndSurname(String name,String surname);

    List<Flight> getFlightByDestinationAndDateAndEmptySeats(String destination, Date date, Integer numberOfPeople);

    Optional<Flight> getFlightByBookId(int book_id);
}
