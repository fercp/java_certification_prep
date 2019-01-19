package ocp.io;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TestFile {
    private static final String SEPARATOR=File.separator;

    @Test
    public void testFile() {
        File currentPath = new File("");
        File file = new File("a/b/c/dummy");
        assertFalse(file.exists());
        assertEquals("dummy", file.getName());
        assertEquals(currentPath.getAbsolutePath() + ""+SEPARATOR+"a"+SEPARATOR+"b"+SEPARATOR+"c"+SEPARATOR+"dummy", file.getAbsolutePath());
        assertFalse(file.isDirectory());
        assertFalse(file.isFile());
        assertEquals(0, file.length());
        assertEquals(0, file.lastModified());
        assertFalse(file.delete());
        assertFalse(file.renameTo(new File("a/b/c/dummy2")));

        File dir = new File("dir");

        assertTrue(dir.mkdir());
        assertTrue(dir.delete());
        dir = new File("a/b/c/dir");
        assertEquals("a"+SEPARATOR+"b"+SEPARATOR+"c",dir.getParent());
        assertTrue(dir.mkdirs());
        Arrays.asList("a/b/c/dir", "a/b/c", "a/b", "a").forEach(f ->
                assertTrue(new File(f).delete()));
        File files[]=dir.listFiles();
        assertNull(files);
        String[] filesStr=dir.list();
        assertNull(filesStr);
        assertNull(dir.listFiles(File::isFile));
        assertNull(dir.listFiles((d,s)->d.isDirectory()&&s.startsWith("f")));
        File del=new File("del");
        assertFalse(del.exists());
        try {
            assertTrue(del.createNewFile());
        } catch (IOException e) {
            fail();
        }
        assertTrue(del.exists());
        assertTrue(del.canRead());
        assertTrue(del.canWrite());
        assertFalse(del.canExecute());
        assertTrue(del.exists());
        try {
            assertEquals(del.getAbsolutePath(),del.getCanonicalPath());
        } catch (IOException e) {
            fail();
        }

        assertTrue(del.delete());
    }
}
