package ocp.jdbc;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class TestConnection {
    @Test
    public void testGetConnection(){

        //way1 >=jdbc 4.0
        try (
            Connection connection=
                    DriverManager.getConnection("jdbc:oracle:thin:@dburl","user","password")
        ){
        } catch (SQLException e) {
            fail();
        }

        //way 2
        Properties properties=new Properties();
        properties.put("user","user");
        properties.put("password","password");
        try (
                Connection connection=
                        DriverManager.getConnection("jdbc:oracle:thin:@dburl",properties)
        ){
        } catch (SQLException e) {
            fail();
        }

        //way 3
        assertThrows(SQLException.class,()->
                        DriverManager.getConnection("jdbc:oracle:thin:@dburl")
        );

        //way 4 <=jdbc3.0
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (
                    Connection connection=
                            DriverManager.getConnection("jdbc:oracle:thin:@dburl","user","password")
            ){
            } catch (SQLException e) {
                fail();
            }
        } catch (ClassNotFoundException e) {
            fail();
        }


    }
}
