package ocp.classdesign.overriding;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestOverriding {

    static class A {
        static String m1() {
            return "a";
        }
    }

    static class B extends A {
        static String m1() {
            return "b";
        }
    }


    static class C extends A {
        //String m1(){return "b";} Compilation error m1 must be static
        String m2() {
            return "c";
        }
    }

    static class D {
        String m1() {
            return "d";
        }
    }

    static class E extends D {
        //static String m1() {return "d";} Compilation error m1 cannot be static
    }

    interface F {
        default String m1() {
            return "F";
        }
    }

    interface G extends F {
        default String m1() {
            return "G";
        }
    }

    interface I {
        default String m1() {
            return "I";
        }
    }

    class H implements G,I {
        public String m1() {
            return G.super.m1();
        }
    }

    @Test
    public void testDefaultMethodCall(){
        H h=new H();
        assertEquals("G",h.m1());
    }

    @Test
    public void testStaticOverride() {
        A a = new B();
        assertEquals("a", a.m1());
        B b = new B();
        assertEquals("b", b.m1());
    }
}
