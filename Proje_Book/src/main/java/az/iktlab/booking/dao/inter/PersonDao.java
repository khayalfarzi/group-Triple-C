package az.iktlab.booking.dao.inter;

import az.iktlab.booking.model.Person;

import java.util.Optional;

public interface PersonDao extends CrudDao<Person>{

    Optional<Person> getPersonByNameAndSurname(String name, String surname);
}
