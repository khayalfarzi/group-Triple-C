package az.iktlab.booking.model;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Data
public class Flight {
    private Integer flightId;
    private Date localDate;
    private Time localTime;
    private String destination;
    private int seats;
    private int fullSeats;
    private List<Book> bookList;

    public Flight() {
    }

    public Flight(Integer flightId,Date localDate, Time localTime,
                  String destination,int seats, int fullSeats) {
        this.flightId = flightId;
        this.localDate = localDate;
        this.localTime = localTime;
        this.destination = destination;
        this.seats = seats;
        this.fullSeats = fullSeats;
    }

    public Flight(int flightId) {
    }

    public Flight(int flightId, Date date, String destination, int seats, int fullSeats) {
        this.flightId = flightId;
        this.localDate = date;
        this.destination = destination;
        this.seats = seats;
        this.fullSeats = fullSeats;
    }


}
