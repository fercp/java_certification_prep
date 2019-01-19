package ocp.classdesign.instanceOf;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestInstanceof {

    static class A {
    }

    static class B {
    }

    static class C extends A {
    }

    interface D {
    }

    static class E implements D {
    }

    @Test
    public void testInstanceof() {
        A a = new A();
        B b = new B();
        C c = new C();
        E e = new E();
        assertTrue(c instanceof A);
        //assertFalse(b instanceof A); Compilation error
        assertTrue(e instanceof D);
        assertFalse(b instanceof D);
        assertFalse(a instanceof C);
        a = null;
        assertFalse(a instanceof A);
        C[] ca = new C[0];
        assertTrue(ca instanceof A[]);

    }
}
