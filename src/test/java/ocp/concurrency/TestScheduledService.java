package ocp.concurrency;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestScheduledService {
    @Test
    public void testScheduledService() throws ExecutionException, InterruptedException {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.schedule(()->assertTrue(true),1,TimeUnit.SECONDS);
        ScheduledFuture<Integer> future = service.schedule(() -> 5, 1, TimeUnit.SECONDS);
        assertEquals(Integer.valueOf(5),future.get());
        ScheduledFuture<?> scheduledFuture = service.scheduleAtFixedRate(System.out::println, 1, 1, TimeUnit.SECONDS);
        ScheduledFuture<?> scheduledFuture1 = service.scheduleWithFixedDelay(System.out::println, 1, 1, TimeUnit.SECONDS);

        AtomicInteger x=new AtomicInteger();
        service=Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
     /*   scheduledFuture=service.scheduleAtFixedRate(()->{
            System.out.println(Instant.now().getEpochSecond()+" "+Thread.currentThread().getName()+" -- > "+x.incrementAndGet());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Instant.now().getEpochSecond()+" "+Thread.currentThread().getName()+" exits");
        },1,4000,TimeUnit.MILLISECONDS);  */
        service.scheduleWithFixedDelay(()->{
            System.out.println(Instant.now().getEpochSecond()+" "+Thread.currentThread().getName()+" -- > "+x.incrementAndGet());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Instant.now().getEpochSecond()+" "+Thread.currentThread().getName()+" exits");
        },1,4000,TimeUnit.MILLISECONDS);
        Thread.sleep(1000000);
        service.shutdown();
        service.awaitTermination(1,TimeUnit.MINUTES);
    }

}
