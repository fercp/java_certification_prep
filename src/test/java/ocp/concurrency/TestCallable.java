package ocp.concurrency;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TestCallable {
    @Test
    public void testCallable() {
        useCallable(() -> {
            throw new IOException();
        });
        // useSupplier(() -> {throw new IOException();}); // DOES NOT COMPILE
        //  use(() -> {throw new IOException();}); // DOES NOT COMPILE
        use((Callable<Integer>) () -> {
            throw new IOException("");
        });
        use2(()-> Integer.valueOf(5));
        use3(()->Integer.valueOf(5));
       // use3(()->m1()); compilation error
        use4(this::m1);
        use5(this::m2);
        use6(this::m2);
        use(this::m3);
        use(()->m3());
        use(()->System.out.println());
        //use(System.out::println); //Compilation error interesting!!!!
    }

    private String m1() throws Exception{
        if(true) throw new Exception();
        return "";
    }


    private String m2() {
        return "";
    }

    private void m3() {
    }


    private void use(Runnable expression) {
    }


    private void use(Supplier<Integer> expression) {
    }

    private void use(Callable<Integer> expression) {
    }


    private void useCallable(Callable<Integer> expression) {
    }

    private void useSupplier(Supplier<Integer> expression) {
    }

    private void use2(Runnable expression) {
        fail();
    }

    private void use2(Callable<Integer> expression) {
    }

    private void use3(Runnable expression) {
        assertTrue(true);
    }

    private void use4(Runnable expression) {
        fail();
    }

    private void use4(Callable<String> expression) {
        try {
            expression.call();
            fail();
        } catch (Exception e) {
        }
    }

    private void use5(Runnable expression) {
        fail();
    }

    private void use5(Callable<String> expression) {
        assertTrue(true);
    }

    private void use6(Runnable expression) {
        assertTrue(true);
    }



}
