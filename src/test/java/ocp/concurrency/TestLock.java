package ocp.concurrency;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestLock {
    @Test
    public void testReentrantReadWriteLock(){
        ReentrantReadWriteLock reentrantReadWriteLock=new ReentrantReadWriteLock();
        assertThrows(IllegalMonitorStateException.class,()->reentrantReadWriteLock.readLock().unlock());
        System.out.println(1);
        reentrantReadWriteLock.readLock().lock();
        System.out.println(2);
        reentrantReadWriteLock.readLock().lock();
        System.out.println(3);
    }

    @Test
    public void testLock(){
        Lock lock=new ReentrantLock();
        assertThrows(IllegalMonitorStateException.class,()->lock.unlock());
        System.out.println(1);
        lock.lock();
        System.out.println(2);
        lock.unlock();
        System.out.println(3);
    }
}
