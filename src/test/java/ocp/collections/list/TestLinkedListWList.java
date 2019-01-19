package ocp.collections.list;

import java.util.LinkedList;

public class TestLinkedListWList extends TestList<LinkedList<String>> {
    @Override
    public LinkedList<String> createCollection() {
        return new LinkedList<>();
    }
}
