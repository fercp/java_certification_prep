package ocp.collections.queue;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingDeque;

import static org.junit.jupiter.api.Assertions.*;

public class TestLinkedBlockinqDeque extends TestDeque<LinkedBlockingDeque<String>> {
    @Override
    @Test
    public LinkedBlockingDeque<String> createCollection() {
        return new LinkedBlockingDeque<>(5);
    }

    @Override
    @Test
    public void testAddFirst() {
        collection.addFirst("d");//addfirst void return
        collection.addFirst("d");
        assertThrows(IllegalStateException.class, () -> collection.addFirst("e"));
    }

    @Override
    @Test
    public void testAddLast() {
        collection.addLast("d");//addlast void return
        collection.addLast("d");
        assertThrows(IllegalStateException.class, () -> collection.addLast("d"));
    }

    @Override
    @Test
    public void testOfferFirst() {
        assertTrue(collection.offerFirst("d"));
        assertTrue(collection.offerFirst("d"));
        assertFalse(collection.offerFirst("d"));
    }

    @Override
    @Test
    public void testOfferLast() {
        assertTrue(collection.offerLast("d"));
        assertTrue(collection.offerLast("d"));
        assertFalse(collection.offerLast("d"));
    }

    @Override
    @Test
    public void testRemoveFirst() {
        assertEquals("a",collection.removeFirst());
        assertEquals("b",collection.removeFirst());
        assertEquals("c",collection.removeFirst());
        assertThrows(NoSuchElementException.class,()->collection.removeFirst());
    }

    @Override
    @Test
    public void testRemoveLast() {
        assertEquals("c",collection.removeLast());
        assertEquals("b",collection.removeLast());
        assertEquals("a",collection.removeLast());
        assertThrows(NoSuchElementException.class,()->collection.removeLast());
    }

    @Override
    @Test
    public void testPollFirst() {
        assertEquals("a", collection.pollFirst());
        assertEquals("b", collection.pollFirst());
        assertEquals("c", collection.pollFirst());
        assertNull( collection.pollFirst());
    }

    @Override
    @Test
    public void testPollLast() {
        assertEquals("c", collection.pollLast());
        assertEquals("b", collection.pollLast());
        assertEquals("a", collection.pollLast());
        assertNull( collection.pollLast());
    }

    @Override
    @Test
    public void testGetFirst() {
        assertEquals("a", collection.getFirst());
        assertEquals("a", collection.getFirst());
        collection.clear();
        assertThrows(NoSuchElementException.class,()->collection.getFirst());
    }

    @Override
    @Test
    public void testGetLast() {
        assertEquals("c", collection.getLast());
        assertEquals("c", collection.getLast());
        collection.clear();
        assertThrows(NoSuchElementException.class,()->collection.getLast());
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
    public void testOffer() {
        assertTrue(collection.offer("d"));
        assertTrue(collection.offer("d"));
        assertFalse(collection.offer("d"));
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


    @Override
    @Test
    public void testAllowNull() {
        assertThrows(NullPointerException.class, () -> collection.add(null));
        assertThrows(NullPointerException.class, () -> collection.offer(null));
    }

    @Override
    @Test
    public void testAllowMultipleNulls() {
        assertThrows(NullPointerException.class, () -> collection.add(null));
        assertThrows(NullPointerException.class, () -> collection.offer(null));
    }
}
