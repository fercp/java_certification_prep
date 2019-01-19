package ocp.collections.list;

import java.util.ArrayList;

public class TestArrayList extends TestList<ArrayList<String>> {
    @Override
    public ArrayList<String> createCollection() {
        return new ArrayList<>();
    }
}
