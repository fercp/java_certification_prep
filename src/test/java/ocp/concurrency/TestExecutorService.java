package ocp.concurrency;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class TestExecutorService {
    @RepeatedTest(3)
    public void testExecute() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        Runnable runnable1 = atomicInteger::incrementAndGet;
        Runnable runnable2 = () -> atomicInteger.set(atomicInteger.get() * 2);
        Runnable runnable3 = atomicInteger::incrementAndGet;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(runnable1);
        executorService.execute(runnable2);
        executorService.execute(runnable3);
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        assertEquals(3, atomicInteger.get());
    }

    @RepeatedTest(3)
    public void testInvokeAll() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        List<Callable<Integer>> callableList =
                Arrays.asList(
                        atomicInteger::incrementAndGet,
                        () -> {
                            atomicInteger.set(atomicInteger.get() * 2);
                            return atomicInteger.get();
                        },
                        atomicInteger::incrementAndGet);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        List<Future<Integer>> futures = executorService.invokeAll(callableList);
        futures.forEach(f -> assertTrue(f.isDone()));
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        assertEquals(3, atomicInteger.get());
    }

    @RepeatedTest(3)
    public void testInvokeAny() throws InterruptedException, ExecutionException {
        AtomicInteger atomicInteger = new AtomicInteger();
        List<Callable<Integer>> callableList =
                Arrays.asList(
                        atomicInteger::incrementAndGet,
                        () -> {
                            atomicInteger.set(atomicInteger.get() * 2);
                            return atomicInteger.get();
                        },
                        atomicInteger::incrementAndGet);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        assertEquals(new Integer(1), executorService.invokeAny(callableList));
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    public void testShutDownNow() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(System.out::println);
        List<Runnable> runnables = executorService.shutdownNow();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }

    @RepeatedTest(3)
    public void testSubmitWithCallable() throws InterruptedException, ExecutionException {
        AtomicInteger atomicInteger = new AtomicInteger();
        Callable<Integer> callable1 = atomicInteger::incrementAndGet;
        Callable<Integer> callable2 = () -> {
            atomicInteger.set(atomicInteger.get() * 2);
            return atomicInteger.get();
        };
        Callable<Integer> callable3 = atomicInteger::incrementAndGet;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<?> future1 = executorService.submit(callable1);
        Future<?> future2 = executorService.submit(callable2);
        Future<?> future3 = executorService.submit(callable3);
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        assertTrue(future1.isDone());
        assertTrue(future2.isDone());
        assertTrue(future3.isDone());
        assertEquals(1, future1.get());
        assertEquals(2, future2.get());
        assertEquals(3, future3.get());
        assertEquals(3, atomicInteger.get());
    }

    @RepeatedTest(3)
    public void testSubmitWithRunnable() throws InterruptedException, ExecutionException {
        AtomicInteger atomicInteger = new AtomicInteger();
        Runnable runnable1 = atomicInteger::incrementAndGet;
        Runnable runnable2 = () -> atomicInteger.set(atomicInteger.get() * 2);
        Runnable runnable3 = atomicInteger::incrementAndGet;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<?> future1 = executorService.submit(runnable1);
        Future<?> future2 = executorService.submit(runnable2);
        Future<?> future3 = executorService.submit(runnable3);
        Future<Integer> future4=executorService.submit(runnable1,5);
        assertEquals(Integer.valueOf(5),future4.get());
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        assertTrue(future1.isDone());
        assertTrue(future2.isDone());
        assertTrue(future3.isDone());
        assertNull(future1.get());
        assertEquals(4, atomicInteger.get());
    }

    @Test
    public void testExecutorCreations(){
        ExecutorService executorService=Executors.newSingleThreadExecutor();
        executorService=Executors.newCachedThreadPool();
        executorService=Executors.newFixedThreadPool(1);
        executorService=Executors.newWorkStealingPool();
        executorService=Executors.newWorkStealingPool(5);
        ScheduledExecutorService scheduledExecutorService=Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService=Executors.newScheduledThreadPool(1);
    }
}

