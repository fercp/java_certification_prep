package ocp.classdesign.nestedclass;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStaticInner {
    private int y = 6;

    static class A {
        int x = 5;

        void m1(){
          // y=5; compilation error
        }
        class B{}
    }



    static A createA() {
        return new A();
    }

    @Test
    public void testStaticInner() {
        A a = createA();
        assertEquals(5, a.x);
        final class B{}
        int y;
    }


}


abstract class E {

    private static class First extends E {
        @Override
        public String doIt() {
            //return  someField; //error
            return super.someField;
        }
    }

    private static class Second extends E {

        @Override
        public String doIt() {
            //return someField; //error
            return super.someField;
        }
    }

    public static final E FIRST = new First();
    public static final E SECOND = new Second();

    private String someField;

    public abstract String doIt();
}