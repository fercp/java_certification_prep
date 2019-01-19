package ocp.classdesign.interfaces;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestInterfaces {
    interface A {
        static String m1() {
            return "A";
        }

        default void m2() {
        }

        void m3();

        static void m4() {
        }
    }

    //can override static method with default method
    interface B extends A {
        default String m1() {
            return "B";
        }
    }

    interface C {
        default String m1() {
            return "C";
        }
    }


    /* compilation error cannot override default method with static
    interface D extends C{
        static String m1(){}
    }                                                               */

    interface D extends B, C {
        default String m1() {
            return C.super.m1();
        }
    }

    interface F extends B {
        default String m1() {
            B.super.m2();
            return B.super.m1();
        }
    }

    interface G extends D, B, C {
        default String m1() {
            //return B.super.m1(); Compilation error because D extends B
            return D.super.m1();
        }
    }


    class E implements D, B {
        @Override
        public void m3() {

        }
    }

    @Test
    public void testInterface() {
        E e = new E();
        assertEquals("C", e.m1());
    }


    interface H {
        public default String m1() {
            return "H";
        }
    }

    interface O {
        public static String m1() {
            return "O";
        }
    }

    interface P extends O{
        public default String m1() {
            return "P";
        }
    }

    interface HO extends H, O {
    }

    class HOM implements HO {

    }

    @Test
    public void testStaticAndDefault() {
        HOM off = new HOM();
        assertEquals("H",off.m1());
        assertEquals("O",O.m1());
        P p=new P(){};
        assertEquals("P",p.m1());
    }

}
