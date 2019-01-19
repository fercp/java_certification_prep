package ocp.generics.bounds;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestUnbounded {
    class A {
    }

    @Test
    public void testUnbounded() {
        List<A> aList = new ArrayList<>();
        //m1(aList); compilation error
        aList.add(new A());
        m2(aList);
        List<?> bList=aList;
        //aList=bList; compilation error
    }

    private void m1(List<Object> list) {
    }

    private void m2(List<?> list) {
        for (Object x : list) {
        }
        //list.add(new Object()); Compilation error
        list.remove(0);//Ok
    }

}
