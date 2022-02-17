package az.iktlab.booking.model;

import lombok.Data;

@Data
public class Book {
    private Integer bookId;
    private Person person;
    private Flight flight;

    public Book() {
    }

    public Book(Person person, Flight flight) {
        this.person = person;
        this.flight = flight;
    }

    public Book(Integer bookId, Person person, Flight flight) {
        this.bookId = bookId;
        this.person = person;
        this.flight = flight;
    }

}
