package ocp.collections.set;

import org.junit.jupiter.api.Test;

import java.util.NavigableSet;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TestNavigableSet<T extends NavigableSet<String>> extends TestSortedSet<T> {

    @Test
    public void testLower() {
        assertEquals("a", collection.lower("b"));
        assertNull(collection.lower("a"));
        assertThrows(NullPointerException.class, () -> collection.lower(null));
    }

    @Test
    public void testFloor() {
        assertEquals("b", collection.floor("b"));
        assertEquals("a",collection.floor("a"));
        assertThrows(NullPointerException.class, () -> collection.lower(null));
    }


    @Test
    public void testCeiling() {
        assertEquals("b", collection.ceiling("b"));
        assertNull(collection.ceiling("d"));
        assertThrows(NullPointerException.class, () -> collection.ceiling(null));
    }

    @Test
    public void testHigher() {
        assertEquals("c", collection.higher("b"));
        assertNull(collection.higher("c"));
        assertThrows(NullPointerException.class, () -> collection.higher(null));
    }

    @Test
    public void testPoolFirst() {
        assertEquals("a", collection.pollFirst());
        assertEquals("b",collection.first());
        collection.clear();
        assertNull(collection.pollFirst());
    }

    @Test
    public void testPoolLast() {
        assertEquals("c", collection.pollLast());
        assertEquals("b",collection.last());
        collection.clear();
        assertNull(collection.pollLast());
    }

    @Test
    public void testDescendingSet() {
        NavigableSet<String> set=collection.descendingSet();
        assertEquals("a",set.last());
        assertEquals("c",set.first());
    }

    @Test
    public void testSubSetWithRange() {
        collection.add("f");
        NavigableSet<String> set=collection.subSet("a",true,"f",true);
        assertEquals("a",set.first());
        assertEquals("f",set.last());
        assertTrue(set.add("d"));
        assertThrows(IllegalArgumentException.class,()->set.add("g"));
    }


    @Test
    public void testHeadSetWithRange() {
        collection.add("f");
        NavigableSet<String> set=collection.headSet("a",true);
        assertEquals("a",set.first());
    }


    @Test
    public void testTailSetWithRange() {
        collection.add("f");
        NavigableSet<String> set=collection.tailSet("f",true);
        assertEquals("f",set.last());
    }

}
