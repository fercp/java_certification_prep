package ocp.classdesign.overloading;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestOverLoading {
    static class A {
        public String m1(int x) {
            return "int";
        }

        public String m1(long x) {
            return "long";
        }

        public String m1(Integer x) {
            return "Integer";
        }

        public String m1(Long x) {
            return "Long";
        }

        public String m1(Number x) {
            return "Number";
        }

        public String m1(int... x) {
            return "varargs";
        }
    }

    static class B {
        public String m1(int x) {
            return "int";
        }

        public String m1(long x) {
            return "long";
        }

        public String m1(Long x) {
            return "Long";
        }

        public String m1(Number x) {
            return "Number";
        }

        public String m1(int... x) {
            return "varargs";
        }
    }

    static class B2 {
        public String m1(Long x) {
            return "Long";
        }

        public String m1(Number x) {
            return "Number";
        }

        public String m1(int... x) {
            return "varargs";
        }
    }

    static class C {
        public String m1(long x) {
            return "long";
        }

        public String m1(Integer x) {
            return "Integer";
        }

        public String m1(Long x) {
            return "Long";
        }

        public String m1(Number x) {
            return "Number";
        }

        public String m1(int... x) {
            return "varargs";
        }
    }

    static class D {
        public String m1(Integer x) {
            return "Integer";
        }

        public String m1(Long x) {
            return "Long";
        }

        public String m1(Number x) {
            return "Number";
        }

        public String m1(int... x) {
            return "varargs";
        }
    }


    static class E {
        public String m1(Long x) {
            return "Long";
        }

        public String m1(int... x) {
            return "varargs";
        }
    }


    static class F {
        public String m1(long x) {
            return "long";
        }

        public String m1(int... x) {
            return "varargs";
        }
    }

    static class G {
        public String m1(Long x) {
            return "Long";
        }

        public String m1(int... x) {
            return "varargs";
        }
    }

    static class I {
        public String m1(Long x) {
            return "Long";
        }
    }

    static class H {
        public String m1(int a, int b) {
            return "int-int";
        }

        public String m1(Integer a, int b) {
            return "Integer-int";
        }

        public String m1(Integer a, Integer b) {
            return "Integer-Integer";
        }

        public String m1(Integer a, Object b) {
            return "Integer-Object";
        }

        public String m1(Object a, Object b) {
            return "Object-Object";
        }
    }

    static class J {
        public String m1(Integer x) {
            return "Integer";
        }
    }

    static class K {
        public String m1(Integer x) {
            return "Integer";
        }

        public String m1(Object x) {
            return "Object";
        }
    }

    static class L {
        public String m1(int... x) {
            return "varargs";
        }

        public String m1(Object x) {
            return "Object";
        }
    }

    @Test
    public void testExactMatch() {
        A a = new A();
        assertEquals("int", a.m1(5));
        assertEquals("Integer", a.m1(Integer.valueOf("5")));
    }

    @Test
    public void testSuperClass() {
        B b = new B();
        assertEquals("Number", b.m1(Integer.valueOf("5")));
    }

    @Test
    public void testUpcast() {
        C c = new C();
        assertEquals("long", c.m1(1));
    }

    @Test
    public void testSuperClass2() {
        B2 b2 = new B2();
        assertEquals("Number", b2.m1(5));
    }

    @Test
    public void testAutoBox() {
        D d = new D();
        assertEquals("Integer", d.m1(5));
        assertEquals("Integer", d.m1(Integer.valueOf("5")));
    }

    @Test
    public void testVarArgs() {
        E e = new E();
        assertEquals("varargs", e.m1(5));
    }

    @Test
    public void testUnboxThenUpcast() {
        F f = new F();
        assertEquals("long", f.m1(Integer.valueOf("5")));
    }

    @Test
    public void testUpcastThenBox() {
        G g = new G();
        assertEquals("varargs", g.m1(5));
        J j = new J();
        // j.m1('c');  Compilation error
        I i = new I();
        // i.m1(5) ;      Compilation error
    }

    @Test
    public void testNull() {
        K k = new K();
        assertEquals("Integer", k.m1(null));

        L l = new L();
        assertEquals("varargs", l.m1(null));

        A a = new A();
        //assertEquals("",a.m1(null)); ambigious compilation error
    }

    @Test
    public void testAmbiguousCall() {
        H h = new H();
        assertEquals("int-int", h.m1(5, 6));
        // h.m1(5,Integer.valueOf(5));    Compilation error
        assertEquals("Integer-int", h.m1(Integer.valueOf(5), 6));
        assertEquals("Integer-Integer", h.m1(Integer.valueOf(5), Integer.valueOf(6)));
        assertEquals("Integer-Object", h.m1(5, 6.0));
        assertEquals("Object-Object", h.m1(6.0, 6.0));
        assertEquals("int-int", h.m1((short) 5, (short) 6));
    }

    static class N {
        protected int var;
    }

    interface O {
        int var = 0;
    }

    class P extends N implements O {
        void m1() {
            int x = ((N) this).var;
        }
    }

    @Test
    public void testAmbigious() {
        P p = new P();
        //p.var=5; Compilation error
        assertEquals(0, ((O) p).var);
        ((N) p).var = 5;
        assertEquals(5, ((N) p).var);
    }
}

