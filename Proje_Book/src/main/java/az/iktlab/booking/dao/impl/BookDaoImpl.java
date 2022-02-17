package az.iktlab.booking.dao.impl;

import az.iktlab.booking.db.ConnPostgresSQL;
import az.iktlab.booking.dao.inter.CrudDao;
import az.iktlab.booking.model.Book;
import az.iktlab.booking.model.Flight;
import az.iktlab.booking.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class BookDaoImpl implements CrudDao<Book> {

    private Book getBook(ResultSet rs){
        try {
            int flightId = rs.getInt("flight_id");
            int book_id = rs.getInt("book_id");
            int person_id = rs.getInt("person_id");

            return new Book(book_id,new Person(person_id),new Flight(flightId));
        } catch (SQLException e) {
            e.printStackTrace();
            return new Book();
        }

    }

    @Override
    public List<Book> getAll() {
        List<Book> result = new ArrayList<>();
        try(Connection conn = ConnPostgresSQL.getInstance().getConnection()){
            String query = "SELECT * FROM book";
            Statement statement = conn.createStatement();
            statement.execute(query);
            ResultSet rs = statement.getResultSet();
            while (rs.next()){
                result.add(getBook(rs));
            }
            return result;
        }catch (SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Book> getById(int id) {
            Book result = null;
            String query = String.format("SELECT * FROM book b where b.flight_id = %d",id);
            try(Connection conn = ConnPostgresSQL.getInstance().getConnection()){
                Statement statement = conn.createStatement();
                statement.execute(query);
                ResultSet rs = statement.getResultSet();
                while (rs.next()){
                    result = getBook(rs);
                }
                return Optional.ofNullable(result);
            }catch (SQLException e){
                e.printStackTrace();
                return Optional.empty();
            }
    }

    @Override
    public boolean update(Book book) {
        String query =
                "update book set person_id=?,flight_id=? where book_id = ?";
        try(Connection conn = ConnPostgresSQL.getInstance().getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1,book.getPerson().getPersonId());
            statement.setInt(2,book.getFlight().getFlightId());
            statement.setInt(3,book.getBookId());
            return statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean add(Book book) {
        try(Connection conn = ConnPostgresSQL.getInstance().getConnection()){
            String query = "INSERT INTO book(person_id,flight_id) values (?,?)";
            PreparedStatement statement = conn
                    .prepareStatement(query);
            statement.setInt(1,book.getPerson().getPersonId());
            statement.setInt(2,book.getFlight().getFlightId());
            statement.execute();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(int id) {
        try(Connection conn = ConnPostgresSQL.getInstance().getConnection()){
            String query = "delete from book b where b.book_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1,id);
            statement.execute();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
