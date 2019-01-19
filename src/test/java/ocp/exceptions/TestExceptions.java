package ocp.exceptions;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectStreamException;
import java.nio.file.NoSuchFileException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.DateTimeException;
import java.util.MissingResourceException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestExceptions {
    @Test
    public void testUncheckedExceptions() {
        assertThrows(ArithmeticException.class, () -> {
                    throw new ArithmeticException();
                }
        );
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            throw new ArrayIndexOutOfBoundsException();
        });
        assertThrows(IndexOutOfBoundsException.class, () -> {
            throw new ArrayIndexOutOfBoundsException();
        });

        assertThrows(ClassCastException.class, () -> {
            throw new ClassCastException();
        });

        assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException();
        });

        assertThrows(NullPointerException.class, () -> {
            throw new NullPointerException();
        });

        assertThrows(NumberFormatException.class, () -> {
            throw new NumberFormatException();
        });

        assertThrows(IllegalArgumentException.class, () -> {
            throw new NumberFormatException();
        });


        assertThrows(IllegalStateException.class, () -> {
            throw new IllegalStateException();
        });

        assertThrows(UnsupportedOperationException.class, () -> {
            throw new UnsupportedOperationException();
        });

        assertThrows(ArrayStoreException.class, () -> {
            throw new ArrayStoreException();
        });

        assertThrows(DateTimeException.class, () -> {
            throw new DateTimeException("e");
        });
        assertThrows(MissingResourceException.class, () -> {
            throw new MissingResourceException("a","b","c");
        });
    }

    @Test
    public void testCheckedExceptions() {
        assertThrows(ParseException.class, () -> {
                    throw new ParseException("",1);
                }
        );

        assertThrows(IOException.class, () -> {
                    throw new IOException();
                }
        );

        assertThrows(FileNotFoundException.class, () -> {
                    throw new FileNotFoundException();
                }
        );

        assertThrows(NotSerializableException.class, () -> {
                    throw new NotSerializableException();
                }
        );
        assertThrows(ObjectStreamException.class, () -> {
                    throw new NotSerializableException();
                }
        );

        assertThrows(IOException.class, () -> {
                    throw new NotSerializableException();
                }
        );

        assertThrows(SQLException.class, () -> {
                    throw new SQLException();
                }
        );

        assertThrows(IOException.class,()->{throw new NoSuchFileException("");});
    }
}

