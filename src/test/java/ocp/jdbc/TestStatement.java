package ocp.jdbc;

import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestStatement {
    @Test
    public void testStatement() {
        try (
                Connection connection =
                        DriverManager.getConnection("jdbc:oracle:thin:@dburl", "user", "password")
        ) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            assertEquals(1, statement.executeUpdate("insert into x values ('a')"));

            ResultSet rs = statement.executeQuery("select y from x");
            assertTrue(rs.next());
            assertEquals("a", rs.getString("y"));
            assertEquals("a", rs.getString(1));
            assertThrows(SQLException.class,()->rs.getString(0));

            statement.executeUpdate("delete from x");
            assertThrows(SQLException.class, () -> rs.getString(1)); //resultset closed
            assertEquals(1, statement.executeUpdate("insert into x values ('a')"));

            assertTrue(statement.execute("select y from x"));
            ResultSet rs2 = statement.getResultSet();
            assertTrue(rs2.next());
            assertEquals("a", rs2.getString("y"));
            assertEquals(-1, statement.getUpdateCount());

          /*
           Should throw exception bu oracle jdbc driver works unexpectedly
           assertEquals(1,statement.executeUpdate("select y from x"));
            rs2=statement.getResultSet();
            assertTrue(rs2.next());
            assertEquals("a",rs2.getString("y"));
*/
            assertFalse(statement.execute("delete from x"));
            assertEquals(1, statement.getUpdateCount());
            assertNull(statement.getResultSet());
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }
    }
}
