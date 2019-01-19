package ocp.classdesign.nestedclass;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLocalInner {
    int a = 1;
    static int b = 5;

    @Test
    public void testLocalInner() {
        int y = 5;
        class A {
            //static int x; compilation error
            private static final int x = 5;//ok

            private void m1() {
                assertEquals(5, y);
                assertEquals(1, a);
                assertEquals(5, b);
            }
        }
        //y=6; compilation error must be effectively final
        class B {
            private A m2() {
                return new A();
            }
        }
        B b = new B();
        A a = b.m2();
        a.m1();
    }

    class B {
        public String m1() {
            return "a";
        }
    }

    @Test
    public void testInner() {
        B b = new B();
        assertEquals("a", b.m1());
        class B {
            public String m1() {
                return "b";
            }
        }
        B x = new B();
        assertEquals("b", x.m1());
    }


}

abstract class T {
    abstract int m1();

    public T() {
    }
}

class W {

    class S extends T {
        int m1() {
            return 0;
        }
    }

    static class S2 {
    }

    private static void m2() {
        //class J extends S{ compilation error S cannot be accessed from static context
        //int m1(){return 1;}
        //}
        S s = new W().new S();
        class J extends S2 {
        }
        S2 s2 = new J();
    }

    private void m3() {
        class J extends S {
            int m1() {
                return 1;
            }
        }
        class K extends S2 {
        }
        S s = new J();
    }
}