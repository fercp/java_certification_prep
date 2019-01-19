package ocp.io;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestReaderWriter {
    @Test
    public void testReaderWriter() throws IOException {
        try (Writer fileWriter = new FileWriter("test")) {
            fileWriter.write(new char[]{'a', 'b', 'c', 'd'});
            fileWriter.write(127);
            fileWriter.write("test");
            fileWriter.write("test", 1, 2);
            fileWriter.flush();
            fileWriter.close();

            try (Reader fileReader = new FileReader("test")) {
                assertEquals('a', fileReader.read());
                char[] buf = new char[3];
                assertEquals(3, fileReader.read(buf));
                assertEquals('b', buf[0]);
                assertEquals('c', buf[1]);
            }
        } finally {
            new File("test").delete();
        }
    }

    @Test
    public void testBufferedReaderWriter() throws IOException {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter("test"))) {
            fileWriter.write(new char[]{'a', 'b', 'c', 'd'});
            fileWriter.write(127);
            fileWriter.write("test");
            fileWriter.write("test", 1, 2);
            fileWriter.newLine();
            fileWriter.flush();
            fileWriter.close();


            try (BufferedReader fileReader = new BufferedReader(new FileReader("test"))) {
                assertEquals('a', fileReader.read());
                char[] buf = new char[3];
                assertEquals(3, fileReader.read(buf));
                assertEquals('b', buf[0]);
                assertEquals('c', buf[1]);
                assertEquals(((char) 127) + "testes", fileReader.readLine());
            }
        } finally {
            new File("test").delete();
        }
    }

    @Test
    public void testReaderWriterWithEncoding() throws IOException {
        try (Writer fileWriter = new OutputStreamWriter(new FileOutputStream("test"), Charset.forName("UTF-8"))) {
            fileWriter.write(new char[]{'a', 'b', 'c', 'd'});
            fileWriter.write(127);
            fileWriter.write("test");
            fileWriter.write("test", 1, 2);
            fileWriter.flush();
            fileWriter.close();

            try (Reader fileReader = new InputStreamReader(new FileInputStream("test"), Charset.forName("UTF-8"))) {
                assertEquals('a', fileReader.read());
                char[] buf = new char[3];
                assertEquals(3, fileReader.read(buf));
                assertEquals('b', buf[0]);
                assertEquals('c', buf[1]);
            }
        } finally {
            new File("test").delete();
        }
    }
}
