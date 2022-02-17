package az.iktlab.booking.dao.impl;

import az.iktlab.booking.db.ConnPostgresSQL;
import az.iktlab.booking.dao.inter.FlightDao;
import az.iktlab.booking.model.Flight;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDaoImpl implements FlightDao {

    private Flight getFlight(ResultSet rs){
        try {
            int flightId = rs.getInt("flight_id");
            String destination = rs.getString("destination");
            Date localDate = rs.getDate("local_date");
            Time localTime = rs.getTime("local_time");
            int seats = rs.getInt("seats");
            int fullSeats = rs.getInt("full_seats");

            return new Flight(flightId,localDate,localTime,destination,seats,fullSeats);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Flight();
        }
    }

    @Override
    public List<Flight> getFlightsByNameAndSurname(String name, String surname){
        List<Flight> result = new ArrayList<>();
        String query = String.format("select f.flight_id,f.destination,f.local_date,f.local_time,f.seats,f.full_seats\n" +
                " from book b\n" +
                " left join person p on p.person_id = b.person_id\n" +
                " left join flight f on f.flight_id = b.flight_id where p.person_name='%s' and p.person_surname='%s'",name,surname);
        try(Connection con = ConnPostgresSQL.getInstance().getConnection()){
            Statement statement = con.createStatement();
            statement.execute(query);
            ResultSet rs = statement.getResultSet();
            while (rs.next()){
                result.add(getFlight(rs));
            }
            return result;
        }catch (SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    @Override
    public List<Flight> getFlightByDestinationAndDateAndEmptySeats(String destination, Date date, Integer numberOfPeople){
        List<Flight> result = new ArrayList<>();
        String query = "select * from flight f\n" +
                " where f.destination=? and f.local_date = ? and ? <= f.seats-f.full_seats";
        try(Connection con = ConnPostgresSQL.getInstance().getConnection()){
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1,destination);
            statement.setDate(2,date);
            statement.setInt(3,numberOfPeople);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()){
                result.add(getFlight(rs));
            }
            return result;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public Optional<Flight> getFlightByBookId(int book_id) {
        Flight flight = null;
        try(Connection con = ConnPostgresSQL.getInstance().getConnection()){
            PreparedStatement statement = con.prepareStatement(
                    "select f.* from flight f\n" +
                            " left join book b on f.flight_id = b.flight_id\n" +
                            " where book_id=?;"
            );
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                flight = getFlight(rs);
            }
            return Optional.ofNullable(flight);
        }catch (SQLException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Flight> getAll() {
        List<Flight> result = new ArrayList<>();
        try(Connection con = ConnPostgresSQL.getInstance().getConnection()){
            Statement statement = con.createStatement();
            statement.execute("SELECT * FROM flight");
            ResultSet rs = statement.getResultSet();
            while (rs.next()){
                result.add(getFlight(rs));
            }
            return result;
        }catch (SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Flight> getById(int id) {
        Flight result = null;
        String query = String.format("SELECT * FROM flight f where f.flight_id = %d",id);
        try(Connection con = ConnPostgresSQL.getInstance().getConnection()){
            Statement statement = con.createStatement();
            statement.execute(query);
            ResultSet rs = statement.getResultSet();
            while (rs.next()){
                result = getFlight(rs);
            }
            return Optional.ofNullable(result);
        }catch (SQLException e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Flight flight) {
        String query =
                "update flight set local_date=?,local_time =?,destination=?,seats=?,full_seats=? where flight_id = ?";
        try(Connection con = ConnPostgresSQL.getInstance().getConnection()){
            PreparedStatement statement = con.prepareStatement(query);
            statement.setDate(1, flight.getLocalDate());
            statement.setTime(2, flight.getLocalTime());
            statement.setString(3,flight.getDestination());
            statement.setInt(4,flight.getSeats());
            statement.setInt(5,flight.getFullSeats());
            statement.setInt(6,flight.getFlightId());
            return statement.execute();

        }catch (SQLException e){
            e.printStackTrace();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean add(Flight flight) {
        String query = "insert into flight(local_time,destination,seats,full_seats,local_date) values(?,?,?,?,?)";
        try(Connection con = ConnPostgresSQL.getInstance().getConnection()){
            PreparedStatement statement = con.prepareStatement(query);
            statement.setTime(1, flight.getLocalTime());
            statement.setString(2,flight.getDestination());
            statement.setInt(3,flight.getSeats());
            statement.setInt(4,flight.getFullSeats());
            statement.setDate(5, flight.getLocalDate());
            statement.execute();
            return true;
        }catch (SQLException e){
            System.out.printf("Add error :%s",e);
            return false;
        }
    }

    @Override
    public boolean remove(int id) {
        String query = String.format("delete from flight f where f.flight_id = %d",id);
        try(Connection con = ConnPostgresSQL.getInstance().getConnection()){
            Statement statement = con.createStatement();
            return statement.execute(query);
        }catch (SQLException e){
            System.out.printf("Add error :%s",e);
            return false;
        }
    }
}
