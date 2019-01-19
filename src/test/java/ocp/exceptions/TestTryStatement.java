package ocp.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

public class TestTryStatement {
    private static final SQLException sqlException = new SQLException("sql");

    @ParameterizedTest
    @ValueSource(classes = {SQLException.class, FileNotFoundException.class})
    public void testMultiCatchException(Class clazz) {
        try {
            throw (Throwable) clazz.newInstance();
        } catch (SQLException | FileNotFoundException ex) {
            assertSame(ex.getClass(), clazz);
        } catch (Throwable e) {
            fail();
        }

        //Invalid catches

        //try(B b=new B();System.out.println("")){}

        /*Sub class
        try {
            throw new IOException();
        } catch (IOException| FileNotFoundException  e) { }
        */

        /*
        catch(Exception1 e | Exception2 e | Exception3 e)
         */

        /*
        catch(Exception1 e1 | Exception2 e2 | Exception3 e3)
         */

        /*
        try {
        throw new IOException();
        } catch(IOException | RuntimeException e) {
        e = new RuntimeException(); // DOES NOT COMPILE
        }
         */
    }


    private void mightThrow() throws FileNotFoundException {
    }

    @Test
    public void testInvalidCatches() {
        try {
            mightThrow();
        } catch (IOException | NumberFormatException | IllegalStateException e) {
            //  } catch (InputMismatchException e |MissingResourceException e){
            //    } catch(SQLException | ArrayIndexOutOfBoundsException e){
            //  } catch(FileNotFoundException | IllegalArgumentException e){
        } catch (Exception e) {
            //} catch(IOException e){
        }
    }

    static class A implements AutoCloseable {
        @Override
        public void close() throws Exception {
            throw new ParseException("a", 1);
        }
    }

    static class B implements Closeable {
        @Override
        public void close() throws IOException {
            throw new FileNotFoundException("b");
        }
    }


    private void m1() {
        try (A a = new A(); B b = new B()) {
        } catch (IOException e) {
            assertEquals("b", e.getMessage());
            assertEquals("a", e.getSuppressed()[0].getMessage());
        } catch (ParseException e) {
            fail();
        } catch (Exception e) {
            fail();
        } finally {
        }
    }

    private void m2() {
        try (A a = new A(); B b = new B()) {
            throw new Exception("e");
        } catch (IOException e) {
            fail();
        } catch (ParseException e) {
            fail();
        } catch (Exception e) {
            assertEquals("e", e.getMessage());
            assertEquals("b", e.getSuppressed()[0].getMessage());
            assertEquals("a", e.getSuppressed()[1].getMessage());
        } finally {
        }
    }

    private void m3() {
        try {
            try (A a = new A(); B b = new B()) {
                throw new Exception("e");
            } catch (IOException e) {
                fail();
            } catch (ParseException e) {
                fail();
            } catch (Exception e) {
                assertEquals("e", e.getMessage());
                assertEquals("b", e.getSuppressed()[0].getMessage());
                assertEquals("a", e.getSuppressed()[1].getMessage());
            } finally {
                throw new Exception("f");
            }
        } catch (Exception e) {
            assertEquals("f", e.getMessage());
            assertEquals(0, e.getSuppressed().length);
        }
    }

    private void m4() {
        try {
            try (A a = new A(); B b = new B()) {
                throw new Exception("e");
            } catch (IOException e) {
                fail();
            } catch (ParseException e) {
                fail();
            } finally {
                throw new RuntimeException("f");
            }
        } catch (Exception e) {
            assertEquals("f", e.getMessage());
            assertEquals(0, e.getSuppressed().length);
        }
    }

    @Test
    public void testTryWithResources() {
        m1();
        m2();
        m3();
        m4();
    }


    static class E extends Exception{}

    private void m5() throws SQLException, ParseException {
    }

    private void m6() throws IOException {
    }

    private void m7() throws E {
    }

    public void rethrowing() throws IOException, SQLException, ParseException {
        try {
            m5();
            m6();
            //m7(); E must be added to rethrowing
        } catch (Exception e) {
            throw e;//!!!!!!!!!!! This compiles fine types can be inferred from method signature
        }
    }
}
