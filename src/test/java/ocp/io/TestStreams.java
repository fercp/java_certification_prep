package ocp.io;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStreams {
    @Test
    public void testFileStreams() throws IOException {
        try (OutputStream outputStream = new FileOutputStream("test")) {
            write(outputStream);
            try (InputStream inputStream = new FileInputStream("test")) {
                read(inputStream);
            }
        } finally {
            File file = new File("test");
            file.delete();
        }
    }

    @Test
    public void testBufferedFileStreams() throws IOException {
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream("test"))) {
            write(outputStream);

            try (InputStream inputStream = new BufferedInputStream(new FileInputStream("test"))) {
                read(inputStream);
            }
        } finally {
            File file = new File("test");
            file.delete();
        }
    }

    private void write(OutputStream outputStream) throws IOException {
        outputStream.write(127);
        outputStream.write(new byte[]{1, 2, 3, 4, 5, 6});
        outputStream.write(new byte[]{1, 2, 3, 4, 5, 6}, 2, 3);
        outputStream.write(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        outputStream.close();
    }

    private void read(InputStream inputStream) throws IOException {
        int x = inputStream.read();
        assertEquals(127, x);
        assertEquals(1, inputStream.read());
        assertEquals(7, inputStream.skip(7));
        assertEquals(5, inputStream.read());
        if (inputStream.markSupported()) {
            inputStream.mark(5);
            for (int i = 1; i < 6; i++)
                assertEquals(i, inputStream.read());
            inputStream.reset();
            inputStream.mark(3);
            for (int i = 1; i < 6; i++)
                assertEquals(i, inputStream.read());
            //assertThrows(IOException.class, inputStream::reset);   in windows exception in mac os no exception
        }
    }
}
