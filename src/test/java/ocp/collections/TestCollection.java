package ocp.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public abstract class TestCollection<T extends Collection<String>> {
    protected T collection;

    @BeforeEach
    public void init() {
        collection = createCollection();
        collection.add("a");
        collection.add("b");
        collection.add("c");
    }

    public abstract T createCollection();

    @Test
    public void testSize() {
        assertEquals(3, collection.size());
    }


    @Test
    public void testIsEmpty() {
        assertFalse(collection.isEmpty());
    }

    @Test
    public void testContains() {
        assertTrue(collection.contains("a"));
    }

    @Test
    public void testToArray() {
        assertEquals(3, collection.toArray().length);
    }

    @Test
    public void testAdd() {
        assertTrue(collection.add("d"));
        assertTrue(collection.contains("d"));
    }

    @Test
    public void testAddAll() {
        assertTrue(collection.addAll(Arrays.asList("d", "e", "f")));
    }


    @Test
    public void testRemove() {
        assertTrue(collection.remove("a"));
    }

    @Test
    public void testRemoveAll() {
        assertTrue(collection.removeAll(Collections.singletonList("a")));
    }

    @Test
    public void testRemoveIf() {
        assertTrue(collection.removeIf(s -> s.equals("a")));
    }

    @Test
    public void testContainsAll() {
        assertTrue(collection.containsAll(Arrays.asList("a", "b", "c")));
    }

    @Test
    public void testRetainAll() {
        assertTrue(collection.retainAll(Arrays.asList("a", "b")));
        assertEquals("[a, b]",collection.toString());
    }

    @Test
    public abstract void testAllowNull();

    @Test
    public abstract void testAllowMultipleNulls();


    @Test
    public abstract void testContainsItself();

    @Test
    public abstract void testAllowDuplicates();


}
