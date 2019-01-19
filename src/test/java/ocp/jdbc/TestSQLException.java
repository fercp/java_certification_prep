package ocp.jdbc;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSQLException {
    @Test
    public void testSQLException(){
        try (
                Connection connection =
                        DriverManager.getConnection("jdbc:oracle:thin:@dburl", "user", "password")
        ) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            assertEquals(1, statement.executeUpdate("insert into x values ('a','b')"));

        } catch (SQLException e) {
            assertTrue(e.getMessage().startsWith("ORA-00913"));
            // sql states
            //https://docs.oracle.com/cd/E15817_01/appdev.111/b31228/appd.htm
            assertEquals("42000",e.getSQLState());
            assertEquals(913,e.getErrorCode());
        }
    }
}
