package ocp.nio2;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TestPath {
    private static final String SEPARATOR = File.separator;

    @Test
    public void testPathCreation() {
        Path path = Paths.get("test");
        assertNotNull(path);
        try {
            URI uri = new URI("file:////pandas/cuddly.png");
            Path path2 = Paths.get(uri);
            assertEquals(uri, path2.toUri());
        } catch (URISyntaxException e) {
            fail();
        }
        Path path3 = FileSystems.getDefault().getPath("test");
        assertEquals(path, path3);

        File file = path3.toFile();
        assertEquals(file.toPath(), path3);


        Path d1 = Paths.get("/works");
        Path d2 = d1.resolve("ocpjp/code"); //1
        d1.resolve("ocpjp/code/sample");  //2
        d1.toAbsolutePath(); //3
        System.out.println(d1);
        System.out.println(d2);

        Path p1 = Paths.get("/temp/records");
        Path p2 = p1.resolve("clients.dat");
        System.out.println(p2);
        assertTrue(p2.startsWith("/temp/records"));


    }

    @Test
    public void testPathMethods() {
        Path path = Paths.get("c:/a/b/c", "d/");
        assertEquals("c:" + SEPARATOR + "a" + SEPARATOR + "b" + SEPARATOR + "c" + SEPARATOR + "d", path.toString());
        for (int i = 0; i < path.getNameCount(); i++) {
            assertEquals(Paths.get(Arrays.asList("a", "b", "c", "d").get(i)), path.getName(i));
        }
        assertEquals(Paths.get("d"), path.getFileName());
        assertEquals(Paths.get("c:/a/b/c"), path.getParent());
        assertEquals(Paths.get("c:/"), path.getRoot());
        assertTrue(path.isAbsolute());

        Path root = Paths.get("");
        Path path2 = Paths.get("a/b/c", "d/");
        assertEquals(Paths.get(root.toAbsolutePath().toString(), path2.toString()), path2.toAbsolutePath());
        assertTrue(Paths.get("c:/a/b/c").isAbsolute());
        assertEquals("c:\\a\\.\\b\\.\\c", Paths.get("c:/a/./b/./c").toAbsolutePath().toString());


        assertEquals(Paths.get("b" + SEPARATOR + "c"), path.subpath(1, 3));
        assertEquals(Paths.get("a" + SEPARATOR + "b"), path.subpath(0, 2));
        assertThrows(IllegalArgumentException.class, () -> path.subpath(1, 1));
        assertThrows(IllegalArgumentException.class, () -> path.subpath(1, 5));

        Path path3 = Paths.get("c:" + SEPARATOR + "Users" + SEPARATOR + "mark");
        assertEquals("Users", path3.getName(0).toString());
        assertEquals(2, path3.getNameCount());
        assertThrows(IllegalArgumentException.class, () -> path3.getName(3));
    }

    @Test
    public void testRelativize() {
        Path p1 = Paths.get("/personal/./photos/../readme.txt");
        Path p2 = Paths.get("/personal/index.html");
        Path p3 = p1.relativize(p2);
        System.out.println(p3);


        p1 = Paths.get("/photos/vacation");
        p2 = Paths.get("/yellowstone");
        System.out.println(p1.resolve(p2) + "  " + p1.relativize(p2));
        Path path1 = Paths.get("fish.txt");
        Path path2 = Paths.get("birds.txt");
        assertEquals(Paths.get(".." + SEPARATOR + "birds.txt"), path1.relativize(path2));
        assertEquals(Paths.get(".." + SEPARATOR + "fish.txt"), path2.relativize(path1));

        Path path3 = Paths.get("c:" + SEPARATOR + "fish.txt");
        Path path4 = Paths.get("birds.txt");
        assertThrows(IllegalArgumentException.class, () -> path3.relativize(path4));
        assertThrows(IllegalArgumentException.class, () -> path4.relativize(path3));

        Path path5 = Paths.get("c:" + SEPARATOR + "fish.txt");
        Path path6 = Paths.get("d:" + SEPARATOR + "birds.txt");
        assertThrows(IllegalArgumentException.class, () -> path5.relativize(path6));
        assertThrows(IllegalArgumentException.class, () -> path6.relativize(path5));

        Path path7 = Paths.get("c:" + SEPARATOR + "fish.txt");
        Path path8 = Paths.get("c:" + SEPARATOR + "birds.txt");
        assertEquals(Paths.get(".." + SEPARATOR + "birds.txt"), path7.relativize(path8));
        assertEquals(Paths.get(".." + SEPARATOR + "fish.txt"), path8.relativize(path7));

        Path path9 = Paths.get("c:" + SEPARATOR + "a" + SEPARATOR + "b" + SEPARATOR + "fish.txt");
        Path path10 = Paths.get("c:" + SEPARATOR + "a" + SEPARATOR + "c" + SEPARATOR + "birds.txt");
        assertEquals(Paths.get(".." + SEPARATOR + ".." + SEPARATOR + "c" + SEPARATOR + "birds.txt"), path9.relativize(path10));
        assertEquals(Paths.get(".." + SEPARATOR + ".." + SEPARATOR + "b" + SEPARATOR + "fish.txt"), path10.relativize(path9));

        path9 = Paths.get("c:" + SEPARATOR + "a" + SEPARATOR + "b" + SEPARATOR + "c" + SEPARATOR + "d" + SEPARATOR + "fish.txt");
        path10 = Paths.get("c:" + SEPARATOR + "a" + SEPARATOR + "b" + SEPARATOR + "e" + SEPARATOR + "f" + SEPARATOR + "birds.txt");
        assertEquals(Paths.get(".." + SEPARATOR + ".." + SEPARATOR + ".." + SEPARATOR + "e" + SEPARATOR + "f" + SEPARATOR + "birds.txt"), path9.relativize(path10));
        assertEquals(Paths.get(".." + SEPARATOR + ".." + SEPARATOR + ".." + SEPARATOR + "c" + SEPARATOR + "" + SEPARATOR + "d" + SEPARATOR + "fish.txt"), path10.relativize(path9));


    }

    @Test
    public void testResolve() {
        Path path1 = Paths.get("fish.txt");
        Path path2 = Paths.get("birds.txt");
        assertEquals(Paths.get("fish.txt" + SEPARATOR + "birds.txt"), path1.resolve(path2));
        assertEquals(Paths.get("birds.txt" + SEPARATOR + "fish.txt"), path2.resolve(path1));
        assertEquals("fish.txt/birds.txt", path1.resolve("birds.txt").toString());

        Path path3 = Paths.get("c:" + SEPARATOR + "fish.txt");
        Path path4 = Paths.get("birds.txt");
        assertEquals(Paths.get("c:" + SEPARATOR + "fish.txt" + SEPARATOR + "birds.txt"), path3.resolve(path4));
        assertEquals(Paths.get("c:" + SEPARATOR + "fish.txt"), path4.resolve(path3));

        Path path5 = Paths.get("c:" + SEPARATOR + "fish.txt");
        Path path6 = Paths.get("d:" + SEPARATOR + "birds.txt");
        assertEquals(Paths.get("d:" + SEPARATOR + "birds.txt"), path5.resolve(path6));
        assertEquals(Paths.get("c:" + SEPARATOR + "fish.txt"), path6.resolve(path5));

        Path path9 = Paths.get("c:" + SEPARATOR + "a" + SEPARATOR + "b" + SEPARATOR + "fish.txt");
        Path path10 = Paths.get("c:" + SEPARATOR + "a" + SEPARATOR + "c" + SEPARATOR + "birds.txt");
        assertEquals(Paths.get("c:" + SEPARATOR + "a" + SEPARATOR + "c" + SEPARATOR + "birds.txt"), path9.resolve(path10));
        assertEquals(Paths.get("c:" + SEPARATOR + "a" + SEPARATOR + "b" + SEPARATOR + "fish.txt"), path10.resolve(path9));

        path9 = Paths.get("c:" + SEPARATOR + "a" + SEPARATOR + "b" + SEPARATOR + "c" + SEPARATOR + "d" + SEPARATOR + "fish.txt");
        path10 = Paths.get("c:" + SEPARATOR + "a" + SEPARATOR + "b" + SEPARATOR + "e" + SEPARATOR + "f" + SEPARATOR + "birds.txt");
        assertEquals(Paths.get("c:" + SEPARATOR + "a" + SEPARATOR + "b" + SEPARATOR + "e" + SEPARATOR + "f" + SEPARATOR + "birds.txt"), path9.resolve(path10));
        assertEquals(Paths.get("c:" + SEPARATOR + "a" + SEPARATOR + "b" + SEPARATOR + "c" + SEPARATOR + "d" + SEPARATOR + "fish.txt"), path10.resolve(path9));
    }

    @Test
    public void testNormalize() {
        Path path3 = Paths.get("E:" + SEPARATOR + "data");
        Path path4 = Paths.get("E:" + SEPARATOR + "user" + SEPARATOR + "home");
        Path relativePath = path3.relativize(path4);
        assertEquals(Paths.get(".." + SEPARATOR + "user" + SEPARATOR + "home"), relativePath);
        Path resolve = path3.resolve(relativePath);
        assertEquals(Paths.get("E:" + SEPARATOR + "data" + SEPARATOR + ".." + SEPARATOR + "user" + SEPARATOR + "home"), resolve);
        assertEquals(Paths.get("E:" + SEPARATOR + "user" + SEPARATOR + "home"), resolve.normalize());
    }

    @Test
    public void testToRealPath() {
        Path home = Paths.get("");
        try {
            Path realPath = home.toRealPath(LinkOption.NOFOLLOW_LINKS);
            assertEquals(home.toAbsolutePath(), realPath);
        } catch (IOException e) {
            fail();
        }
        assertThrows(IOException.class, () -> Paths.get("InvalidPath").toRealPath());
    }

    @Test
    public void testGokhan() {
        assertEquals("dddd", "dddd||ddd;rerr;rrr".split("||")[0]);
    }
}
