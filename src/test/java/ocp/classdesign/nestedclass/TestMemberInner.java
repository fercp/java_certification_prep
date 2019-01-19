package ocp.classdesign.nestedclass;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class D {
    class BB {
        class E {
            class C{}
            void go() {
                System.out.println("go!");
            }
        }
    }
}

public class TestMemberInner {
    private String name = "Outer";

    static interface Intf {
    } // interfaces are static

    static enum En {} //enums are static

    class A {
        private int x = 10;
        //static int x; compilation error
        //static void m2(){} compilation error
        static final String name = "Inner"; //ok final

        private void m1() {
            assertEquals("Outer", TestMemberInner.this.m1());
            assertEquals("Outer", TestMemberInner.this.name);
            assertEquals("Inner", this.name);
        }

        class B extends A{
            private int x = 20;

            class C extends B {
                private int x = 30;

                private void m1() {
                    assertEquals(30, x);
                    assertEquals(20, B.this.x);
                    assertEquals(10, A.this.x);
                    assertEquals(20, super.x);
                }
            }
        }
    }

    private String m1() {
        return "Outer";
    }

    static A createA() {
        TestMemberInner testMemberInner = new TestMemberInner();
        A a = testMemberInner.new A();
        A.B b = a.new B();
        A.B.C c = b.new C();
        c.m1();
        return a;
    }

    @Test
    public void testInner() {
        A a = new A();
        a.m1();
        createA();
    }

    @Test
    public void testDeepInner(){
       D d=new D();
     //  D.BB db=d.new D.BB(); compilation error
        D.BB db=d.new BB();
        D.BB.E e=db.new E();
        D.BB.E.C c=e.new C();
    }
}
