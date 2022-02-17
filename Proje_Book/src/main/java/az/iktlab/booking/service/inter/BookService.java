package az.iktlab.booking.service.inter;

public interface BookService {

     boolean cancel(int booking_id);

    boolean book(int option,int peopleOfNumber);
}
