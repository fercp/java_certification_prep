package ocp.collections.set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class TestHashSet extends TestSet<Set<String>> {
    @Override
    public Set<String> createCollection() {
        return new HashSet<>();
    }

    //Allows Single null value
    @Override
    @Test
    public void testAllowNull() {
        Assertions.assertTrue(collection.add(null));
    }

    //Allows Single null value
    @Override
    @Test
    public void testAllowMultipleNulls() {
        Assertions.assertTrue(collection.add(null));
        Assertions.assertFalse(collection.add(null));
    }
}
