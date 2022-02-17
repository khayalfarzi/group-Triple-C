package az.iktlab.booking.dao.impl;

import az.iktlab.booking.db.ConnPostgresSQL;
import az.iktlab.booking.dao.inter.PersonDao;
import az.iktlab.booking.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonDaoImpl implements PersonDao {

    private Person getPerson(ResultSet rs) {
        try {
            Integer personId = rs.getInt("person_id");
            String personName = rs.getString("person_name");
            String personSurname = rs.getString("person_surname");
            return new Person(personId,personName,personSurname);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Person();
        }
    }

    @Override
    public List<Person> getAll() {
        List<Person> result = new ArrayList<>();
        String query = "SELECT * FROM person";
        try(Connection conn = ConnPostgresSQL.getInstance().getConnection()){
            Statement statement = conn.createStatement();
            statement.execute(query);
            ResultSet rs = statement.getResultSet();
            while (rs.next()){
                result.add(getPerson(rs));
            }
            return result;
        }catch (SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Person> getById(int id) {
        Person person = new Person();
        String query = "SELECT * FROM person p where p.person_id = ?";
        try(Connection conn = ConnPostgresSQL.getInstance().getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1,id);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while ((rs.next())){
                person = getPerson(rs);
            }
            return Optional.of(person);
        }catch (SQLException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Person person) {
        String query = "update person set person_name=?,surname = ? where person_id = ?";
        try(Connection conn = ConnPostgresSQL.getInstance().getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1,person.getPersonName());
            statement.setString(2,person.getPersonSurname());
            statement.setInt(3,person.getPersonId());
            return statement.execute();
        }catch (SQLException e){
            System.out.printf("Update error :%s",e);
            return false;
        }
    }

    @Override
    public boolean add(Person person) {
        String query = "insert into person(person_name,person_surname) values(?,?)";
        try(Connection conn = ConnPostgresSQL.getInstance().getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1,person.getPersonName());
            statement.setString(2,person.getPersonSurname());
            statement.execute();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean remove(int id) {
        try(Connection conn = ConnPostgresSQL.getInstance().getConnection()){
            PreparedStatement statement = conn.prepareStatement("delete from user where id = ?");
            statement.setInt(1,id);
            return statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Person> getPersonByNameAndSurname(String name, String surname) {
        Person person = new Person();
        String query = "select * from person p where p.person_name=? and person_surname=?";
        try(Connection conn = ConnPostgresSQL.getInstance().getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1,name);
            statement.setString(2,surname);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                person = getPerson(rs);
            }
            return Optional.of(person);
        }catch (SQLException e){
            return Optional.empty();
        }
    }
}
