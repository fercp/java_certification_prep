package ocp.concurrency;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestWait {
    static String lock1 = new String("lock1");
    static String lock2 = new String("lock2");

    @Test
    public void testWait() throws InterruptedException {
        Runnable r = () -> {
            synchronized (lock1) {
                try {
                    lock1.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
        Thread.sleep(1000);
        synchronized (lock2) {
            assertThrows(IllegalMonitorStateException.class,()->lock1.notify());
        }
        synchronized (lock1) {
            lock1.notify();
        }
        t.join();
    }


}
