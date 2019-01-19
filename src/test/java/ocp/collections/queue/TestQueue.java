package ocp.collections.queue;

import ocp.collections.TestCollection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TestQueue<T extends Queue<String>> extends TestCollection<T> {
    //Capacity restricted queues can throw IllegalStateException
    @Override
    @Test
    public void testAdd() {
        assertTrue(collection.add("d"));
    }


    @Test
    public abstract void testOffer() ;

    @Override
    @Test
    public abstract void testAllowNull() ;

    @Override
    @Test
    public abstract void testAllowMultipleNulls();


    @Override
    @Test
    public void testContainsItself() {
        ArrayDeque deque = new ArrayDeque();
        deque.add(deque);
        assertTrue(deque.contains(deque));
    }

    @Test
    public void testQueueRemove() {
        assertFalse(collection.remove(""));
        assertEquals("a", collection.remove());
        collection.clear();
        assertThrows(NoSuchElementException.class, () -> collection.remove());
    }

    @Test
    public void testPoll() {
        assertEquals("a", collection.poll());
        collection.clear();
        assertNull(null, collection.poll());
    }

    @Test
    public void testElement() {
        assertEquals("a", collection.element());
        collection.clear();
        assertThrows(NoSuchElementException.class, () -> collection.element());
    }

    @Test
    public void testPeek() {
        assertEquals("a", collection.peek());
        collection.clear();
        assertNull(null, collection.peek());
    }

    @Override
    @Test
    public  void testAllowDuplicates(){
        Assertions.assertTrue(collection.add("a"));
    }
}
