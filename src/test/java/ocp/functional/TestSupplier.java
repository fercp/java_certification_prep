package ocp.functional;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSupplier {
    static class A implements Supplier<String>{

        @Override
        public String get() {
            return "a";
        }

        public int get2(){ return 0;}

        public static String get3(){return "b";}
    }

    @Test
    public void testSupplier(){
        Supplier<String> stringSupplier=()->"a";
        assertEquals("a",stringSupplier.get());

        Supplier<String> supplier= new A();
        assertEquals("a",supplier.get());

        A a=new A();
        supplier=a::get;
        assertEquals("a",supplier.get());

        supplier=String::new;
        assertEquals("",supplier.get());

        supplier=A::get3;
        assertEquals("b",supplier.get());
    }
}
