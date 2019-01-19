package ocp.generics.bounds;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestLowerBound {
    class A {
    }

    class B extends A {
    }

    class C extends B {
    }

    @Test
    public void testLoweBound() {
        List<? super B> aList = new ArrayList<A>();
        aList.add(new B());
        aList.add(new C());
        aList = new ArrayList<B>();
        aList = new ArrayList<Object>();
        //aList=new ArrayList<C>(); compilation error
        B b = m1(new B());
        C c = m1(new C());
        List<C> cList = new ArrayList<>();
        cList.add(new C());
        c = m1(cList);
        b = m3(cList);
        aList.add(new C());
        Object o = m4(aList);
    }

    <T extends B> T m1(T t) {
        return t;
    }

    <T extends B> T m1(List<T> t) {
        return t.get(0);
    }

    B m3(List<? extends B> bList) {
        return bList.get(0);
    }

    Object m4(List<? super B> bList) {
        return bList.get(0);
    }
}
