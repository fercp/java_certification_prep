package ocp.collections.set;

import java.util.TreeSet;

public class TestTreeSet extends TestNavigableSet<TreeSet<String>> {
    @Override
    public TreeSet<String> createCollection() {
        return new TreeSet<>();
    }
}
