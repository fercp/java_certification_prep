package ocp.generics;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestGenerics {
    class A {
    }

    class B {
    }

    public static <E extends Number> List<E> process(List<E> input){
        return input;
    }

    class C<C>{
        void m1(C c){}
    }
    class D<Object>{}


    @Test
    public void testComplex() {
        List<Integer> l = new ArrayList<>();
        l.add(20);
        l.add(30);
        m3(l);
        assertEquals("[20, 20]",l.toString());
        List<List<Integer>> x=new ArrayList<List<Integer>>();
        List<Integer> o=process(new ArrayList<Integer>());
        List<Integer> p=process(o);
       // ArrayList<Integer> r=process(new ArrayList<Integer>()); Compilation error
    }
    private void m3(List<?> l) {
        m4(l); // 1
    }
    private <T> void m4(List<T> l) {
        l.set(1, l.get(0)); // 2
    }

    @Test
    public void testCast() {
        List<A> aList = new ArrayList<>();
        aList.add(new A());
        List bList = new ArrayList<String>();
        bList.add("Error");
        m1(aList);
        assertThrows(ClassCastException.class, () -> {
            m1(bList);
        });
        m2(aList);
        aList.get(1);//does not throw exception
        assertThrows(ClassCastException.class, () -> aList.get(1)); //throws exception casting occurs when returning result from function
    }


    private void m1(List<A> list) {
        for (A a : list) {
            assertNotNull(a);
        }
    }

    private void m2(List list) {
        list.add(new B());
    }

    class R<E extends W>{
        public void m1(E e){}
    }

    class W {}

    class CW extends W{}

    class X{}

    @Test
    public void testComplexGeneric(){
        R<CW> w1=new R<CW>();
        //R<W> w2=new R<CW>(); Compilation error
        R<? extends W> w3=new R<CW>();
        R<? extends W> w4=new R<W>();
        //R<? super W> w5=new R<CW>();Compilation error
        R<? super W> w6=new R<W>();
    }
}
