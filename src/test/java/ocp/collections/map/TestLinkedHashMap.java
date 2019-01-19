package ocp.collections.map;

import java.util.LinkedHashMap;

import static org.junit.Assert.*;

public class TestLinkedHashMap extends TestMap<LinkedHashMap<String, String>> {
    @Override
    protected LinkedHashMap<String, String> createMap() {
        return new LinkedHashMap<>();
    }

    @Override
    public void testAllowNullKey() {
        assertNull(map.put(null, "a"));
        assertEquals("a", map.get("a"));
        assertEquals("a", map.put(null, "b"));
    }

    @Override
    public void testAllowNullValue() {
        assertNull(map.put("d", null));
        assertTrue(map.containsKey("d"));
        assertNull(map.get("d"));
        assertNull(map.put(null, null));
        assertTrue(map.containsKey(null));
    }
}
