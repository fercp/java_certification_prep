package ocp.jdbc;

import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestResultSet {
    @Test
    public void testResultSet(){
        try (
                Connection connection =
                        DriverManager.getConnection("jdbc:oracle:thin:@dburl", "user", "password")
        ) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            assertEquals(1, statement.executeUpdate("insert into x values ('1')"));
            assertEquals(1, statement.executeUpdate("insert into x values ('2')"));
            assertEquals(1, statement.executeUpdate("insert into x values ('3')"));
            assertEquals(1, statement.executeUpdate("insert into x values ('4')"));
            assertEquals(1, statement.executeUpdate("insert into x values ('5')"));

            ResultSet rs = statement.executeQuery("select y from x");
            assertTrue(rs.absolute(5));
            assertEquals("5", rs.getString("y"));
            assertFalse(rs.absolute(8));
            assertThrows(SQLException.class,()-> rs.getString("y"));
            assertTrue(rs.relative(-1));
            assertEquals("5", rs.getString("y"));

            assertFalse(rs.relative(-10));
            assertThrows(SQLException.class,()-> rs.getString("y"));
            rs.next();
            assertEquals("1", rs.getString("y"));

            rs.beforeFirst();
            rs.next();
            assertEquals("1", rs.getString("y"));

            rs.afterLast();
            assertTrue(rs.isAfterLast());
            assertTrue(rs.relative(-1));
            assertEquals("5", rs.getString("y"));

            statement = connection.createStatement();
            ResultSet rs2 = statement.executeQuery("select y from x");
            assertThrows(SQLException.class,()->rs2.absolute(0));
            assertTrue(rs2.next());
            assertEquals("1",rs.getString(1));
            

            assertFalse(statement.execute("delete from x"));


            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
