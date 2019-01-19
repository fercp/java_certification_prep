package ocp.concurrency;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRunnable {
    @Test
    public void testRunnable(){
        Runnable runnable= System.out::println;
        runnable=new Runnable() {
            @Override
            public void run() {
                assertTrue(true);
            }
        };
        new Thread(runnable).start();
        //runnable=()->""; compilation error return value
        //runnable=()->5; compilation error return value
    }

    @Test
    public void testThread(){
        Thread thread=new Thread();
        assertTrue(thread instanceof Runnable);
        Integer i1 = Arrays.asList(1,2,3,4,5).stream().findAny().get();
        synchronized(i1) { // y1
            Integer i2 = Arrays.asList(6,7,8,9,10)
                    .parallelStream()
                    .sorted()  // y2
                    .findAny().get(); // y3
            System.out.println(i1+" "+i2);
        }

    }


}
