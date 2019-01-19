package ocp.collections.queue;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TestLinkedListWDeque extends TestDeque<LinkedList<String>> {
    @Override
    public LinkedList<String> createCollection() {
        return new LinkedList<>();
    }

    @Override
    @Test
    public void testAddFirst() {
        collection.addFirst("d");//addfirst void return
        collection.addFirst("d");
    }

    @Override
    @Test
    public void testAddLast() {
        collection.addLast("d");//addfirst void return
        collection.addLast("d");
    }

    @Override
    @Test
    public void testOfferFirst() {
        assertTrue(collection.offerFirst("d"));
        assertTrue(collection.offerFirst("d"));
        assertTrue(collection.offerFirst("d"));
    }

    @Override
    @Test
    public void testOfferLast() {
        assertTrue(collection.offerLast("d"));
        assertTrue(collection.offerLast("d"));
        assertTrue(collection.offerLast("d"));
    }

    @Override
    @Test
    public void testRemoveFirst() {
        assertEquals("a", collection.removeFirst());
        assertEquals("b", collection.removeFirst());
        assertEquals("c", collection.removeFirst());
        assertThrows(NoSuchElementException.class, () -> collection.removeFirst());
    }

    @Override
    @Test
    public void testRemoveLast() {
        assertEquals("c", collection.removeLast());
        assertEquals("b", collection.removeLast());
        assertEquals("a", collection.removeLast());
        assertThrows(NoSuchElementException.class, () -> collection.removeFirst());
    }

    @Test
    public void testOffer() {
        assertTrue(collection.offer("d"));
        assertTrue(collection.offer("d"));
        assertTrue(collection.remove("d"));
        assertTrue(collection.contains("d"));
    }

    @Override
    @Test
    public void testAllowNull() {
        assertTrue(collection.add(null));
        assertTrue(collection.offer(null));
    }

    @Override
    @Test
    public void testAllowMultipleNulls() {
        assertTrue(collection.add(null));
        assertTrue(collection.add(null));
        assertTrue(collection.offer(null));
    }


    @Override
    @Test
    public void testPollFirst() {
        assertEquals("a", collection.pollFirst());
        assertEquals("b", collection.pollFirst());
        assertEquals("c", collection.pollFirst());
        assertNull(collection.pollFirst());
    }

    @Override
    @Test
    public void testPollLast() {
        assertEquals("c", collection.pollLast());
        assertEquals("b", collection.pollLast());
        assertEquals("a", collection.pollLast());
        assertNull(collection.pollLast());
    }

    @Override
    @Test
    public void testGetFirst() {
        assertEquals("a", collection.getFirst());
        collection.clear();
        assertThrows(NoSuchElementException.class, () -> collection.getFirst());
    }

    @Override
    @Test
    public void testGetLast() {
        assertEquals("c", collection.getLast());
        collection.clear();
        assertThrows(NoSuchElementException.class, () -> collection.getLast());
    }

    @Override
    @Test
    public void testPeekFirst() {
        assertEquals("a", collection.peekFirst());
        assertEquals("a", collection.peekFirst());
        collection.clear();
        assertNull(collection.peekFirst());
    }

    @Override
    @Test
    public void testPeekLast() {
        assertEquals("c", collection.peekLast());
        assertEquals("c", collection.peekLast());
        collection.clear();
        assertNull(collection.peekLast());
    }

    @Override
    @Test
    public void testRemoveFirstOccurrence() {
        assertTrue(collection.removeFirstOccurrence("a"));
        assertFalse(collection.removeFirstOccurrence("a"));
        assertFalse(collection.removeLastOccurrence(null));
    }

    @Override
    @Test
    public void testRemoveLastOccurrence() {
        assertTrue(collection.removeLastOccurrence("a"));
        assertFalse(collection.removeLastOccurrence("a"));
        assertFalse(collection.removeLastOccurrence(null));
    }

}
