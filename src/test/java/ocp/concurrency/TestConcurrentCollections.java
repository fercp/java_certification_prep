package ocp.concurrency;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class TestConcurrentCollections {
    @Test
    public void testConcurrentHashMap() {
        Map<String, Object> foodData = new HashMap<>();
        foodData.put("penguin", 1);
        foodData.put("flamingo", 2);
        assertThrows(ConcurrentModificationException.class, () -> {
            for (String key : foodData.keySet())
                foodData.remove(key);
        });


        Map<String, Object> foodDataConcurrent = new ConcurrentHashMap<>();
        foodDataConcurrent.put("penguin", 1);
        foodDataConcurrent.put("flamingo", 2);
        for (String key : foodDataConcurrent.keySet())
            foodDataConcurrent.remove(key);
        assertEquals(0, foodDataConcurrent.keySet().size());

        foodDataConcurrent = new HashMap<String, Object>();
        foodDataConcurrent.put("penguin", 1);
        foodDataConcurrent.put("flamingo", 2);
        Map<String, Object> synchronizedFoodData = Collections.synchronizedMap(foodDataConcurrent);
        assertThrows(ConcurrentModificationException.class, () -> {
            for (String key : synchronizedFoodData.keySet())
                synchronizedFoodData.remove(key);
        });
    }

    @Test
    public void testConcurrentLinkedDeque() {
        Deque<String> concurrentLinkedDeque = new ArrayDeque<>();
        concurrentLinkedDeque.add("a");
        concurrentLinkedDeque.add("b");
        for (String a : concurrentLinkedDeque)
            concurrentLinkedDeque.remove(a);
    }

    @Test
    public void testBlockingQueue() throws InterruptedException {
        LinkedBlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(2);
        blockingQueue.offer("a");
        blockingQueue.offer("b");
        Thread thread = new Thread(() ->
                assertThrows(InterruptedException.class, () -> blockingQueue.offer("c", 1, TimeUnit.MINUTES)));
        thread.start();
        while (!thread.isAlive()) {
            Thread.sleep(2);
        }
        thread.interrupt();
        thread.join();
        blockingQueue.clear();
        thread = new Thread(() ->
                assertThrows(InterruptedException.class, () -> blockingQueue.poll(1, TimeUnit.MINUTES)));
        thread.start();
        while (!thread.isAlive()) {
            Thread.sleep(2);
        }
        thread.interrupt();
        thread.join();
        assertTrue(blockingQueue.offer("a"));
        assertTrue(blockingQueue.offer("b"));
        blockingQueue.offer("b");

        thread = new Thread(() ->
        {
            try {
                assertFalse(blockingQueue.offer("c", 1, TimeUnit.MICROSECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        thread.join();

    }

    @Test
    public void testBlockingDeque() throws InterruptedException {
        LinkedBlockingDeque<String> blockingDeque = new LinkedBlockingDeque<>(2);
        blockingDeque.offer("a");
        blockingDeque.offer("b");
        Thread thread = new Thread(() ->
                assertThrows(InterruptedException.class, () -> blockingDeque.offerFirst("c", 1, TimeUnit.MINUTES)));
        thread.start();
        while (!thread.isAlive()) {
            Thread.sleep(2);
        }
        thread.interrupt();
        thread.join();
        blockingDeque.clear();
        thread = new Thread(() ->
                assertThrows(InterruptedException.class, () -> blockingDeque.pollLast(1, TimeUnit.MINUTES)));
        thread.start();
        Thread.sleep(2);
        thread.interrupt();
    }
}
