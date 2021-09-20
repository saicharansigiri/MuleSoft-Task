import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connectiondb {

    public static Connection connection(){
        Connection res = null;
        try {
            Class.forName("org.sqlite.JDBC");
             res= DriverManager.getConnection("jdbc:sqlite:Movies.db");

        }catch (ClassNotFoundException | SQLException e ) {

            System.out.println(e+"\n"+ e.toString());
        }
        return res;
    }
}
