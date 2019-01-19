package ocp.collections.list;


import ocp.collections.TestCollection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.UnaryOperator;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public abstract class TestList<T extends List<String>> extends TestCollection<T> {

    //Allows Single null value
    @Override
    @Test
    public void testAllowNull() {
        assertTrue(collection.add(null));
        assertEquals(Arrays.asList("a", "b", "c", null), collection);
    }

    //Allows multiple null values
    @Override
    @Test
    public void testAllowMultipleNulls() {
        assertTrue(collection.add(null));
        assertTrue(collection.add(null));
        assertEquals(Arrays.asList("a", "b", "c", null, null), collection);
        assertTrue(collection.remove(null));
        assertTrue(collection.remove(null));
        assertFalse(collection.remove(null));
        assertFalse(collection.contains(null));
    }


    @Test
    public void testReplaceAll() {
        UnaryOperator<String> unaryOperator = s -> s + "-";
        collection.replaceAll(unaryOperator);
        assertEquals(Arrays.asList("a-", "b-", "c-"), collection);
        collection.replaceAll(e -> null);
        assertEquals(Arrays.asList(null, null, null), collection);
    }

    @Test
    public void testSort() {
        collection.sort(Comparator.reverseOrder());
        assertEquals(Arrays.asList("c", "b", "a"), collection);
        collection.sort(null);
        assertEquals(Arrays.asList("a", "b", "c"), collection);
        collection.add(1, null);
        assertThrows(NullPointerException.class, () -> collection.sort(Comparator.reverseOrder()));
    }

    @Test
    public void testGet() {
        assertEquals("a", collection.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> collection.get(3));
    }

    @Test
    public void testSet() {
        assertEquals("a", collection.set(0, "b"));
        assertEquals("b", collection.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> collection.set(3, "b"));
    }

    @Test
    public void testAddIndexed() {
        collection.add(1, "d");
        assertEquals(Arrays.asList("a", "d", "b", "c"), collection);
    }

    @Test
    public void testRemoveIndexed() {
        assertEquals("b", collection.remove(1));
        assertEquals(Arrays.asList("a", "c"), collection);
    }

    @Test
    public void testRemoveIf(){
        List<Integer> ints2=new ArrayList<>( Arrays.asList(1, 2, 3, 4, 5));
        assertTrue(ints2.removeIf(i->i%2==0));
        assertEquals("[1, 3, 5]",ints2.toString());
        List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5);
        assertThrows(UnsupportedOperationException.class,()->ints.removeIf(i -> (i % 2 ==0)));
    }

    @Test
    public void testIndexOf() {
        assertEquals(1, collection.indexOf("b"));
        assertEquals(-1, collection.indexOf("d"));
    }

    @Test
    public void testLastIndexOf() {
        assertEquals(1, collection.lastIndexOf("b"));
        assertEquals(-1, collection.lastIndexOf("d"));
        collection.add("b");
        assertEquals(3, collection.lastIndexOf("b"));
    }

    @Test
    public void testContainsItself() {
        List list = new ArrayList();
        assertTrue(list.add(list));
        assertTrue(list.contains(list));
    }

    @Override
    @Test
    public void testAllowDuplicates() {
        Assertions.assertTrue(collection.add("a"));
    }
}

