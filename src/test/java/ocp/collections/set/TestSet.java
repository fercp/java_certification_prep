package ocp.collections.set;

import ocp.collections.TestCollection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class TestSet<T extends Set<String>> extends TestCollection<T> {
    @Override
    @Test
    public void testContainsItself() {
        Set set = new HashSet();
        assertTrue(set.add(set));
        assertThrows(Throwable.class, ()->set.contains(set));
    }

    @Override
    @Test
    public  void testAllowDuplicates(){
        Assertions.assertFalse(collection.add("a"));
    }
}
