package az.iktlab.booking.dao.inter;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {

    List<T> getAll();

    Optional<T> getById(int id);

    boolean update (T t);

    boolean add(T t);

    boolean remove(int id);
}
