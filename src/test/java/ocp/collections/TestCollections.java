package ocp.collections;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestCollections {
    @Test
    public void testSort() {
        List<String> collection = new ArrayList<>(Arrays.asList("a", "b", "c", null));
        assertThrows(NullPointerException.class, () -> Collections.sort(collection));
        collection.remove(null);
        collection.remove("b");
        collection.add("b");
        Collections.sort(collection, (a, b) -> a.compareTo(b));
        assertEquals(Arrays.asList("a", "b", "c"), collection);
        collection.remove("b");
        collection.add("b");
        Collections.sort(collection,null);
        assertEquals(Arrays.asList("a", "b", "c"), collection);
        List<A> list=new ArrayList<>();
        list.add(new A());
        //Collections.sort(list); compilation error
        Collections.sort(list,null);
        list.add(new A());
        assertThrows(ClassCastException.class,()->Collections.sort(list,null));
    }

    class A {}
}

