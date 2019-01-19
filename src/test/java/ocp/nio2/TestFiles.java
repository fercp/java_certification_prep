package ocp.nio2;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class TestFiles {
    private static final String SEPARATOR = File.separator;


    @Test
    public void testExists() {
        Path path = Paths.get("");
        assertTrue(Files.exists(path));
        assertTrue(Files.exists(path, LinkOption.NOFOLLOW_LINKS));
        path = Paths.get("Invalid");
        assertFalse(Files.exists(path));
    }

    @Test
    public void testIsSameFile() {
        Path path1 = Paths.get("..");
        Path path2 = Paths.get(".." + SEPARATOR + ".");
        try {
            assertTrue(Files.isSameFile(path1, path2));
        } catch (IOException e) {
            fail();
        }
        Path path3 = Paths.get("Invalid");
        assertThrows(IOException.class, () -> Files.isSameFile(path1, path3));
    }

    @Test
    public void testCreateDirectory() {
        Path path = null;
        try {
            path = Paths.get("delete");
            Files.createDirectory(path);
        } catch (IOException e) {
            fail();
        } finally {
            deleteFile(path);
        }
    }

    @Test
    public void testCreateFile() throws IOException {
        Path path = null;
        try {
            path = Paths.get("delete");
            assertEquals(path, Files.createFile(path));
        } catch (IOException e) {
            fail();
        } finally {
            deleteFile(path);
        }

            try (InputStream is = new FileInputStream(""); OutputStream os = new FileOutputStream("");) {
                byte[] buffer = new byte[1024];
                int bytesRead = 0;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            } catch (java.io.InvalidClassException e) {
                e.printStackTrace();
            }

    }


    @Test
    public void testCreateDirectories() {
        try {
            Path path = Paths.get("delete" + SEPARATOR + "delete" + SEPARATOR + "delete");
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } finally {
            try {
                Files.deleteIfExists(Paths.get("delete" + SEPARATOR + "delete" + SEPARATOR + "delete"));
                Files.deleteIfExists(Paths.get("delete" + SEPARATOR + "delete"));
                Files.deleteIfExists(Paths.get("delete"));
            } catch (IOException e) {
                e.printStackTrace();
                fail();
            }
        }
    }

    @Test
    public void testCopy() {
        Path path = createInputFile();
        Path path2 = Paths.get("test2");
        try {
            Path path3 = Files.copy(path, path2, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES, LinkOption.NOFOLLOW_LINKS);
            assertEquals(path2, path3);
            readInputFile(path2.toString());
            Path dir = Paths.get("dir");
            Files.deleteIfExists(dir);
            Files.createDirectory(dir);
            assertThrows(IOException.class, () -> Files.copy(path, dir));
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } finally {
            deleteFile(path2);
        }
    }

    @Test
    public void testCopyWithStreams() {
        Path path = createInputFile();
        try (InputStream is = new FileInputStream(path.toString());
             OutputStream out = new FileOutputStream("testOut")) {
            Files.copy(is, Paths.get("test2"));
            Files.copy(Paths.get("test"), out);
            readInputFile("test2");
            readInputFile("testOut");
        } catch (IOException e) {
            fail();
        } finally {
            try {
                Files.deleteIfExists(Paths.get("test"));
                Files.deleteIfExists(Paths.get("test2"));
                Files.deleteIfExists(Paths.get("testOut"));
            } catch (IOException e) {
                fail();
            }
        }
    }


    @Test
    public void testMove() {
        Path source = createInputFile();
        Path target = Paths.get("targetdel");
        try {
            assertEquals(target, Files.move(source, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE));
        } catch (IOException e) {
            fail();
        } finally {
            deleteFile(target);
        }
    }

    @Test
    public void testDelete() {
        assertThrows(NoSuchFileException.class, () -> Files.delete(Paths.get("test")));
    }

    @Test
    public void testDeleteIfExists() throws IOException {
        assertFalse(Files.deleteIfExists(Paths.get("test")));
        Path path = createInputFile();
        assertTrue(Files.deleteIfExists(path));
    }

    private void readInputFile(String file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        assertEquals("a", bufferedReader.readLine());
        assertEquals("b", bufferedReader.readLine());
        assertEquals("c", bufferedReader.readLine());
        bufferedReader.close();
    }

    private Path createInputFile() {
        Path path = Paths.get("test");
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new BufferedOutputStream(new FileOutputStream(path.toFile())));
        } catch (FileNotFoundException e) {
            fail();
        }
        printWriter.println("a");
        printWriter.println("b");
        printWriter.println("c");
        printWriter.close();
        return path;
    }

    @Test
    public void testNewBufferedReader() {
        Path path = createInputFile();

        try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("US-ASCII"))) {
            assertEquals("a", reader.readLine());
            assertEquals("b", reader.readLine());
            assertEquals("c", reader.readLine());
        } catch (IOException e) {
            fail();
        }

        assertThrows(NoSuchFileException.class, () -> Files.newBufferedReader(Paths.get("ddss"), Charset.forName("US-ASCII")));
    }

    @Test
    public void testNewBufferedWriter() {
        Path path = Paths.get("test");

        try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("US-ASCII"))) {
            writer.write("a");
            writer.newLine();
            writer.write("b");
            writer.newLine();
            writer.write("c");
            writer.newLine();
        } catch (IOException e) {
            fail();
        }
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("US-ASCII"))) {
            assertEquals("a", reader.readLine());
            assertEquals("b", reader.readLine());
            assertEquals("c", reader.readLine());
        } catch (IOException e) {
            fail();
        }
        deleteFile(path);
    }

    @Test
    public void testReadAllLines() {
        Path path = createInputFile();
        try {
            final List<String> lines = Files.readAllLines(path);
            assertEquals("a", lines.get(0));
            assertEquals("b", lines.get(1));
            assertEquals("c", lines.get(2));
        } catch (IOException e) {
            fail();
        } finally {
            deleteFile(path);
        }
    }

    @Test
    public void testFileAttributes() {
        Path path = createInputFile();
        assertFalse(Files.isDirectory(path));
        assertTrue(Files.isRegularFile(path));
        assertFalse(Files.isSymbolicLink(path));
        assertTrue(Files.isReadable(path));
        assertTrue(Files.isWritable(path));
        //assertTrue(Files.isExecutable(path));//Interesting  in windows true in macos false
        try {
            UserPrincipal owner = Files.getOwner(path);
            assertEquals("KFS" + SEPARATOR + "U005509", owner.getName());
            owner = path.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName("U005509");
            assertEquals("KFS" + SEPARATOR + "U005509", owner.getName());
            Path path2 = Files.setOwner(path, owner);
            assertEquals(path, path2);
        } catch (IOException e) {
            fail();
        }
        FileTime time = FileTime.fromMillis(System.currentTimeMillis());
        try {
            Files.setLastModifiedTime(path, time);
        } catch (IOException e) {
            fail();
        }
        try {
            assertEquals(time, Files.getLastModifiedTime(path));
        } catch (IOException e) {
            fail();
        }
        try {
            assertTrue(Files.getLastModifiedTime(path).toMillis() > 0);
        } catch (IOException e) {
            fail();
        }

        try {
            assertTrue(Files.size(path) > 0);
        } catch (IOException e) {
            fail();
        }
        try {
            assertFalse(Files.isHidden(path));
        } catch (IOException e) {
            fail();
        }
        deleteFile(path);

        //Do not throw exception if file not exists
        assertFalse(Files.isDirectory(path));
        assertFalse(Files.isRegularFile(path));
        assertFalse(Files.isSymbolicLink(path));
        assertFalse(Files.isReadable(path));
        assertFalse(Files.isExecutable(path));

        //Throws exception
        assertThrows(NoSuchFileException.class, () -> Files.isHidden(path));
        assertThrows(NoSuchFileException.class, () -> Files.size(path));
        assertThrows(NoSuchFileException.class, () -> Files.getLastModifiedTime(path));
        assertThrows(NoSuchFileException.class, () -> Files.getOwner(path));
    }

    @Test
    public void testReadAttributes() {
        Path path = createInputFile();
        BasicFileAttributes data = null;
        try {
            data = Files.readAttributes(path, BasicFileAttributes.class);
        } catch (IOException e) {
            fail();
        }
        assertFalse(data.isDirectory());
        assertTrue(data.isRegularFile());
        assertFalse(data.isSymbolicLink());
        assertFalse(data.isOther());
        assertTrue(data.size() > 0);
        assertNotNull(data.creationTime());
        assertNotNull(data.lastAccessTime());
        try {
            assertEquals(Files.getLastModifiedTime(path), data.lastModifiedTime());
        } catch (IOException e) {
            fail();
        }
        assertNull(data.fileKey());
        deleteFile(path);
    }

    private void deleteFile(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testReadAttributeView() {
        Path path = createInputFile();
        BasicFileAttributeView view = Files.getFileAttributeView(path, BasicFileAttributeView.class);
        BasicFileAttributes data = null;
        try {
            data = view.readAttributes();
            assertNotNull(data);
        } catch (IOException e) {
            fail();
        }
        FileTime lastModifiedTime = FileTime.fromMillis(data.lastModifiedTime().toMillis() + 10_000);
        try {
            FileTime lastAccessTime = null, createTime = null;
            view.setTimes(lastModifiedTime, lastAccessTime, createTime);
            assertEquals(lastModifiedTime, view.readAttributes().lastModifiedTime());
        } catch (IOException e) {
            fail();
        }
        deleteFile(path);
    }

    @Test
    public void testWalk() {
        try {
            Files.walk(Paths.get(""), 10).forEach(System.out::println);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testFind() {
        try {
            Files.find(Paths.get(""), 10, (p, a) -> p.toString().endsWith("class") && a.isRegularFile()).forEach(System.out::println);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testList() {
        try {
            Files.list(Paths.get("")).forEach(System.out::println);
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testLines() {
        try {
            Path path = createInputFile();
            Files.lines(path).filter(l -> l.equals("a")).forEach(System.out::println);
        } catch (IOException e) {
            fail();
        }
        final Path path = Paths.get(".").normalize();  // h1
        System.out.println("-" + path + "-");
        int count = 0;
        for (int i = 0; i < path.getNameCount(); ++i) {
            System.out.println("-" + path.getName(i) + "-");
            count++;
        }
        System.out.println(count);
        List<Integer> l = IntStream.rangeClosed(1, 5)
                .mapToObj(i -> i).collect(Collectors.toList());
        l.forEach(System.out::print);
    }

    @Test
    public void testDel() {
        java.util.List<? extends java.sql.Statement> list5 =
                new java.util.ArrayList();
    }


}
