package ocp.concurrency;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class TestAtomicClasses {
    static class A {
        private final String a;

        A(String a) {
            this.a = a;
        }

        @Override
        public boolean equals(Object o) {
            return ((A) o).a.equals(this.a);
        }
    }

    @Test
    public void testAtomicReference() {
        AtomicReference<A> atomicReference = new AtomicReference<>();
        A a = new A("a");
        atomicReference.set(a);
        assertFalse(atomicReference.compareAndSet(new A("a"), new A("b")));
        assertEquals("a", atomicReference.get().a);
        assertTrue(atomicReference.compareAndSet(a, new A("b")));
        assertEquals("b", atomicReference.get().a);
    }

    @Test
    public void testAtomicInteger() {
        AtomicInteger atomicInteger = new AtomicInteger();
        assertEquals(4,atomicInteger.addAndGet(4));
        assertEquals(5,atomicInteger.addAndGet(1));
        assertEquals(5,atomicInteger.get());
        assertEquals(5,atomicInteger.getAndAccumulate(2,(a,b)->a-b));
        assertEquals(3,atomicInteger.get());
        assertEquals(4,atomicInteger.incrementAndGet());
        assertEquals(4,atomicInteger.getAndIncrement());
        assertEquals(10,atomicInteger.updateAndGet(a->a*2));
        assertEquals(10,atomicInteger.getAndSet(100));
        assertEquals(100,atomicInteger.get());
        atomicInteger.set(1);
        assertEquals(1,atomicInteger.get());
        assertTrue(atomicInteger.compareAndSet(1,10));
        assertEquals(10,atomicInteger.get());
        assertFalse(atomicInteger.compareAndSet(1,20));
        assertEquals(10,atomicInteger.get());
    }
}
