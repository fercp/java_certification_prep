package ocp.generics.bounds;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestUpperBound {
    class A{}

    class B extends A{}

    @Test
    public void testUpperBound(){
        List<? extends A> aList=new ArrayList<>();
        //aList.add(new A()); Compilation error
        for(A a:aList){}
        aList=new ArrayList<B>();
    }
}
