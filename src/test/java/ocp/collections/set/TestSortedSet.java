package ocp.collections.set;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.SortedSet;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TestSortedSet<T extends SortedSet<String>> extends TestSet<T> {
    @Override
    @Test
    public void testAllowNull() {
        assertThrows(NullPointerException.class, () -> collection.add(null));
    }

    @Override
    @Test
    public void testAllowMultipleNulls() {
        assertThrows(NullPointerException.class, () -> collection.add(null));
    }


    //Default natural ordering returns null
    @Test
    public void testComparator() {
        assertNull(collection.comparator());
    }

    @Test
    public void testSubSet() {
        Set<String> subset = collection.subSet("a", "c");
        assertEquals(2, subset.size());
        assertFalse(subset.contains("c"));
        assertTrue(subset.contains("a"));
        subset = collection.subSet("a", "c" + '\0');
        assertEquals(3, subset.size());
        assertTrue(subset.contains("c"));
        assertTrue(subset.contains("a"));
        subset = collection.subSet("a" + '\0', "c");
        assertEquals(1, subset.size());
        assertFalse(subset.contains("c"));
        assertFalse(subset.contains("a"));

        assertTrue(subset.remove("b"));
        assertFalse(collection.contains("b"));

        collection.add("f");
        Set<String> subset2 = collection.subSet("a", "f");
        assertTrue(subset2.add("e"));
        assertFalse(subset2.add("e"));
        assertThrows(IllegalArgumentException.class, () -> subset2.add("f"));
    }

    @Test
    public void testHeadSet() {
        Set<String> headset = collection.headSet("c");
        assertEquals(2, headset.size());
        assertFalse(headset.contains("c"));
        assertTrue(headset.contains("b"));
        assertTrue(headset.contains("a"));
    }

    @Test
    public void testTailSet() {
        Set<String> tailSet = collection.tailSet("b");
        assertEquals(2, tailSet.size());
        assertTrue(tailSet.contains("b"));
        assertTrue(tailSet.contains("c"));
    }


    @Test
    public void testFirst() {
        assertEquals("a", collection.first());
    }

    @Test
    public void testLast() {
        assertEquals("c", collection.last());
    }

}
