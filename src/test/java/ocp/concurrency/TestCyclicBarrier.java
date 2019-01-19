package ocp.concurrency;

import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestCyclicBarrier {
    @Test
    public void testCyclicBarrier() throws InterruptedException {
        AtomicInteger atomicInteger=new AtomicInteger();
        CyclicBarrier cyclicBarrier=new CyclicBarrier(3,()->assertEquals(3,atomicInteger.get()));
        ExecutorService executorService=Executors.newFixedThreadPool(4);
        for(int i=0;i<3;i++) {
            executorService.execute(() -> {
                atomicInteger.incrementAndGet();
                try {
                    cyclicBarrier.await();
                    atomicInteger.incrementAndGet();
                } catch (InterruptedException |BrokenBarrierException e) {
                   fail();
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1,TimeUnit.MINUTES);
        assertEquals(6,atomicInteger.get());
    }
}
