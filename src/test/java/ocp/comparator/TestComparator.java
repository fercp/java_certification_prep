package ocp.comparator;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestComparator {

    static class A implements Comparator<A>, Comparable<A> {

        private final String s;

        A(String s) {
            this.s = s;
        }

        @Override
        public int compare(A a, A t1) {
            return a.s.compareTo(t1.s);
        }

        @Override
        public int compareTo(A a) {
            return a.s.compareTo(this.s);
        }

        @Override
        public boolean equals(Object o){
            return ((A)o).s.equals(s);
        }

        @Override
        public String toString(){
            return s;
        }
    }

    static class B{}

    @Test
    public void testStrange() {
        List<A> list= Arrays.asList(new A("d"),new A("a"),new A("b"),new A("e"));
        list.sort(new A("a"));
        assertEquals(Arrays.asList(new A("a"),new A("b"),new A("d"),new A("e")),list);
        Collections.sort(list);
        assertEquals(Arrays.asList(new A("e"),new A("d"),new A("b"),new A("a")),list);
    }

    @Test
    public void testSortNonComparable(){
        List<B> list=new ArrayList<>();
       // Collections.sort(list); Compilation error
        //Collections.sort(list,Comparator.naturalOrder()); //Compilation error
        Collections.sort(list,Comparator.comparing(k->new A("")).thenComparingInt(k->1));
    }

    @Test
    public void testBinarySearch(){
        List<String> list=Arrays.asList("a","d","c","g","e");
        Collections.sort(list);
        assertEquals(0,Collections.binarySearch(list,"a"));
        assertEquals(1,Collections.binarySearch(list,"c"));
        assertEquals(-2,Collections.binarySearch(list,"b"));
        Collections.sort(list,Comparator.reverseOrder());
        assertEquals(-1,Collections.binarySearch(list,"a"));
        assertEquals(4,Collections.binarySearch(list,"a",Comparator.reverseOrder()));
        assertEquals(-5,Collections.binarySearch(list,"b",Comparator.reverseOrder()));
    }
}
