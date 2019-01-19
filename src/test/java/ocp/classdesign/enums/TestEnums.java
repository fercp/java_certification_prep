package ocp.classdesign.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestEnums {
    enum A {
        X, Y, Z
    }

    static int callCount;

    enum B {
        X("x"), Y("y");

        String a;

        B(String a) {
            this.a = a;
        }
    }

    static B b = B.X;

    enum OnlyOne {
        ONCE(true);
        boolean x;

        OnlyOne(boolean b) {
            callCount++;
            x = b;
        }

        void m1() {
            x = false;
        }
    }

    interface D {
        int printValue();
    }

    enum FF implements D {
        A {
            public int printValue() {
                return 0;
            }
        }, B {
            public int printValue() {
                return 0;
            }
        }
    }

    enum E implements D {

        UP(1) {
            public int printValue() {
                return super.value;
            }
        }, DOWN(0);
        private int value;

        E(int value) {
            this.value = value;
        }

        public int printValue() {
            return value;
        }
    }

    @Test
    public void testPrint() {
        assertEquals(1, E.UP.printValue());
        assertEquals(0, E.DOWN.printValue());
    }

    @Test
    public void testEnum() {
        assertEquals("X", A.X.name());
        assertEquals("X", A.X.toString());
        assertEquals(0, A.X.ordinal());
        assertEquals(A.X, A.valueOf("X"));
        assertTrue(A.values() instanceof A[]);
        A a = A.X;
        switch (a) {
            case X:
                //case A.X: compilation error
            case Y:
            case Z:
        }

        OnlyOne firstCall = OnlyOne.ONCE;
        OnlyOne secondCall = OnlyOne.ONCE;

        assertEquals(1, callCount);

        assertEquals("x", b.X.X.X.X.a);

    }
}

enum Suit {
    SPADE(Color.BLACK), HEART(Color.RED),
    DIAMOND(Color.RED), CLUB(Color.BLACK);

    private enum Color {RED, BLACK;}

    private Suit(Color c) {
        color = c;
    }

    public Color color;
}

  enum StaticFail{
     A,B,C;
     String a;
     static String b;

     StaticFail (){
        // a=b;    compilation error illegal reference to static field from initializer
     }

  }

/*

Below code Equals this enum
enum E{
FIRST("F"){
 @Override
        public String doIt() {
            //return  someField; //error
            return super.someField;
        }
}
,SECOND("s"){
 @Override
        public String doIt() {
            //return  someField; //error
            return super.someField;
        }
        };
   private String someField;

    private E(String s){
        someField=s;
    }

    public abstract String doIt();

}


 */
abstract class E {

    private static class First extends E {
        private First(String s) {
            super(s);
        }

        @Override
        public String doIt() {
            //return  someField; //error
            return super.someField;
        }
    }

    private static class Second extends E {

        private Second(String s) {
            super(s);
        }

        @Override
        public String doIt() {
            //return someField; //error
            return super.someField;
        }
    }

    public static final E FIRST = new E.First("f");
    public static final E SECOND = new E.Second("s");

    private String someField;

    private E(String s) {
        someField = s;
    }

    public abstract String doIt();
}