package ocp.classdesign.nestedclass;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAnonymousInner {
    class A {
        public A(String a) {
            assertEquals("a", a);
        }

        String m1() {
            return "A";
        }
    }

    @Test
    public void testAnonymous() {
        String x = "B";
        A a = new A("a") {
            //  public A(String b){} compilation error
            //public A(){} compilation error
            String m1() {
                return x;
            }

            String y;
            //static int y; compilation error
            static final int z=5;
        };
        //x="A"; //Cause compilation error must be effectively final
        assertEquals("B", a.m1());
    }
}
