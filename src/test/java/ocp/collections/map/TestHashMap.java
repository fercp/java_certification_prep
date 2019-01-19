package ocp.collections.map;


import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestHashMap extends TestMap<HashMap<String, String>> {
    @Override
    protected HashMap<String, String> createMap() {
        return new HashMap<>();
    }

    @Override
    @Test
    public void testAllowNullKey() {
        assertNull(map.put(null, "a"));
        assertEquals("1", map.get("a"));
        assertEquals("a", map.put(null, "b"));
    }

    @Override
    @Test
    public void testAllowNullValue() {
        assertNull(map.put("d", null));
        assertTrue(map.containsKey("d"));
        assertNull(map.get("d"));
        assertNull(map.put(null, null));
        assertTrue(map.containsKey(null));
        Map<String ,String> concurentMap=new ConcurrentHashMap<>();
        assertThrows(NullPointerException.class,()->concurentMap.put(null,""));
        assertThrows(NullPointerException.class,()->concurentMap.put("",null));
    }
}
