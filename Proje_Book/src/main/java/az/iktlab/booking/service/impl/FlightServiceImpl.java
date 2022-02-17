package az.iktlab.booking.service.impl;

import az.iktlab.booking.dao.impl.FlightDaoImpl;
import az.iktlab.booking.dao.inter.FlightDao;
import az.iktlab.booking.model.Flight;
import az.iktlab.booking.service.inter.FlightService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightServiceImpl implements FlightService {

    private final FlightDao flightDao;

    public FlightServiceImpl(){
        this.flightDao = new FlightDaoImpl();
    }


    @Override
    public List<Flight> search(String destination, Date date, Integer numberOfPeople){
        return flightDao.getFlightByDestinationAndDateAndEmptySeats(destination,date, numberOfPeople);
    }

    @Override
    public List<Flight> show() {
        List<Flight> flights = flightDao.getAll();
        List<Flight> flights1 = new ArrayList<>();
        for (Flight flight : flights) {
            if(flight.getLocalDate().toLocalDate().getDayOfYear()
            == LocalDate.now().getDayOfYear()+1){
                flights1.add(flight);
            }
        }
        return flights1;
    }

    @Override
    public Flight info(int flight_id){
        Optional<Flight> flightOptional = flightDao.getById(flight_id);
        if(flightOptional.isPresent() && flightOptional.get().getFlightId() != null){
            return flightOptional.get();
        }
        return new Flight();
    }

    @Override
    public List<Flight> getFlightsByNameAndSurname(String name,String surname){
        return  flightDao.getFlightsByNameAndSurname(name,surname);
    }

    @Override
    public Flight getFlightByBookId(int bookId){
        return flightDao.getFlightByBookId(bookId).get();
    }

    @Override
    public boolean update(Flight flight) {
        return flightDao.update(flight);
    }
}
