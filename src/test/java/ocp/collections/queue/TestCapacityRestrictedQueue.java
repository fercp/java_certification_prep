package ocp.collections.queue;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

public class TestCapacityRestrictedQueue extends TestQueue<ArrayBlockingQueue<String>> {

    @Override
    public ArrayBlockingQueue<String> createCollection() {
        return new ArrayBlockingQueue<>(4);
    }

    @Override
    @Test
    public void testAdd() {
        assertTrue(collection.add("d"));
        assertThrows(IllegalStateException.class,()->collection.add("e"));//Queue is full
    }

    @Override
    @Test
    public void testOffer() {
        assertTrue(collection.offer("d"));
        assertFalse(collection.offer("d"));
    }

    @Override
    @Test
    public void testAllowNull() {
        assertThrows(NullPointerException.class,()->collection.add(null));
        assertThrows(NullPointerException.class,()->collection.offer(null));
    }

    @Override
    @Test
    public void testAllowMultipleNulls() {
        assertThrows(NullPointerException.class,()->collection.add(null));
        assertThrows(NullPointerException.class,()->collection.offer(null));
    }

}
