package az.iktlab.booking.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnPostgresSQL {

    private static ConnPostgresSQL inst;
    private Connection con;


    public static ConnPostgresSQL getInstance() {
        try {
            if(inst == null || inst.getConnection().isClosed() ) {
                inst = new ConnPostgresSQL();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inst;
    }

    public ConnPostgresSQL() {
        try{
            String url = ("jdbc:postgresql://localhost:5432/book");
            Class.forName("org.postgresql.Driver");
            con = DriverManager
                    .getConnection(
                            url,
                            "postgres",
                            "root"
                    );
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return con;
    }
}

