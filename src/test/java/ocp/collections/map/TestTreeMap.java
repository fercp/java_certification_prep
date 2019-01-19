package ocp.collections.map;

import org.junit.jupiter.api.Test;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestTreeMap extends TestMap<TreeMap<String, String>> {
    @Override
    protected TreeMap<String, String> createMap() {
        return new TreeMap<>();
    }

    @Override
    @Test
    public void testAllowNullKey() {
        assertThrows(NullPointerException.class, () -> map.put(null, "1"));
    }

    @Override
    @Test
    public void testAllowNullValue() {
        assertNull(map.put("d", null));
        assertNull(map.put("e", null));
    }

    @Test
    public void testPoll(){
        assertEquals("a",map.pollFirstEntry().getKey());
    }


    @Test
    public void testNotComparable() {
        TreeMap map = new TreeMap();
        X key = new X();
        assertThrows(ClassCastException.class, () -> map.put(key, "error"));
        TreeMap map2 = new TreeMap((x, t1) -> x.equals(t1)?0:1);
        assertNull(map2.put(key, "ok"));
        assertEquals("ok", map2.get(key));
    }

    static class X {
    }
}
