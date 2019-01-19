package ocp.classdesign.polymorphism;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestVirtualMethInvoc {

    static class A{
        private String x="A";
        private String m1(){return "A";}
        String m2(){return "A";}
    }

    static class B extends A{
        private String x="B";
        private String m1(){return "B";}
        String m2(){return "B";}
    }


    @Test
    public void testVMI(){
        A a=new B();
        assertEquals("A",a.x);
        assertEquals("A",a.m1());
        assertEquals("B",a.m2());
        assertEquals("B",((B)a).m1());
    }
}
