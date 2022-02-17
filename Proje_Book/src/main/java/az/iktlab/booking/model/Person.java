package az.iktlab.booking.model;

import lombok.Data;

import java.util.List;

@Data
public class Person {
    private Integer personId;
    private String personName;
    private String personSurname;
    private List<Book> bookList;

    public Person() {
    }

    public Person(Integer personId, String personName, String personSurname) {
        this.personId = personId;
        this.personName = personName;
        this.personSurname = personSurname;
    }

    public Person(String name, String surname) {
        this.personName  = name;
        this.personSurname = surname;
    }
    public Person(int person_id) {
    }


}
