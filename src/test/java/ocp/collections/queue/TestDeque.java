package ocp.collections.queue;

import org.junit.jupiter.api.Test;

import java.util.Deque;


public abstract class TestDeque<T extends Deque<String>> extends TestQueue<T> {
    @Test
    public abstract void testAddFirst();

    @Test
    public abstract void testAddLast();

    @Test
    public abstract void testOfferFirst();

    @Test
    public abstract void testOfferLast();

    @Test
    public abstract void testRemoveFirst();

    @Test
    public abstract void testRemoveLast();

    @Test
    public abstract void testPollFirst();

    @Test
    public abstract void testPollLast();

    @Test
    public abstract void testGetFirst();

    @Test
    public abstract void testGetLast();

    @Test
    public abstract void testPeekFirst();

    @Test
    public abstract void testPeekLast();

    @Test
    public abstract void testRemoveFirstOccurrence();

    @Test
    public abstract void testRemoveLastOccurrence();

    @Test
    public  void testPush(){
        testAddFirst();
    }

    @Test
    public  void testPop(){
        testRemoveFirst();
    }
}
