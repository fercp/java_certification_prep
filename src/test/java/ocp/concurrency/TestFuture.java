package ocp.concurrency;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestFuture {
    @Test
    public void testFutureWithTimeout() throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> "a");
        assertEquals("a",future.get(5,TimeUnit.MINUTES));
        assertTrue(future.isDone());
        assertFalse(future.isCancelled());
        Future<String> future2 = executorService.submit(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "a";
        });
        assertThrows(TimeoutException.class,()->future2.get(1,TimeUnit.MILLISECONDS));
        executorService.shutdown();
    }
}
