package ocp.collections.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TestMap<T extends Map<String, String>> {
    protected T map;

    @BeforeEach
    public void init() {
        map = createMap();
        map.put("a", "1");
        map.put("b", "2");
        map.put("c", "3");
    }

    protected abstract T createMap();

    @Test
    public void testSize() {
        assertEquals(3, map.size());
    }

    @Test
    public void testIsEmpty() {
        assertFalse(map.isEmpty());
        map.clear();
        assertTrue(map.isEmpty());
    }

    @Test
    public void testContainsKey() {
        assertFalse(map.containsKey("d"));
        assertTrue(map.containsKey("a"));
    }

    @Test
    public void testContainsValue() {
        assertFalse(map.containsValue("4"));
        assertTrue(map.containsValue("1"));
    }

    @Test
    public void testGet() {
        assertEquals("1", map.get("a"));
        assertNull(map.get("d"));
    }

    @Test
    public void testPut() {
        assertEquals("1", map.put("a", "2"));
        assertNull(map.put("d", "4"));
    }


    @Test
    public void testRemove() {
        assertEquals("1", map.remove("a"));
        assertEquals("2", map.remove("b"));
        assertNull(map.remove("d"));
    }

    @Test
    public void testPutAll() {
        Map<String, String> newMap = new HashMap<>();
        newMap.put("d", "4");
        newMap.put("e", "5");
        map.putAll(newMap);
        assertEquals("4", map.get("d"));
        assertEquals("1", map.get("a"));
    }

    //Backed by the map changes reflected / add&addAll UnsupportedOperation
    @Test
    public void testKeySet() {
        Set<String> keySet = map.keySet();
        assertEquals(3, keySet.size());
        assertTrue(keySet.containsAll(Arrays.asList("a", "b", "c")));
        keySet.remove("a");
        assertEquals(2, map.size());
        assertFalse(map.containsKey("a"));
        assertThrows(UnsupportedOperationException.class, () -> keySet.add("a"));
    }

    //Backed by the map changes reflected add&addAll UnsupportedOperation
    @Test
    public void testValues() {
        Collection<String> values = map.values();
        assertEquals(3, values.size());
        assertTrue(values.containsAll(Arrays.asList("1", "2", "3")));
        values.remove("1");
        assertEquals(2, map.size());
        assertFalse(map.containsKey("a"));
        assertThrows(UnsupportedOperationException.class, () -> values.add("1"));
    }

    @Test
    public void testEntrySet() {
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        assertEquals(3, entrySet.size());
        entrySet.removeIf(e -> e.getKey().equals("a"));
        assertEquals(2, map.size());
        assertFalse(map.containsKey("a"));
    }

    @Test
    public void testGetOrDefault() {
        assertEquals("1", map.getOrDefault("a", "2"));
        assertEquals("4", map.getOrDefault("d", "4"));
    }

    /*
     for (Map.Entry<K, V> entry : map.entrySet())
          action.accept(entry.getKey(), entry.getValue());
     */
    @Test
    public void testForEach() {
        map.forEach((a, b) -> assertEquals(b, map.get(a)));
    }

    /*
     for (Map.Entry<K, V> entry : map.entrySet())
         entry.setValue(function.apply(entry.getKey(), entry.getValue()));
     */
    @Test
    public void testReplaceAll() {
        map.replaceAll((a, b) -> a + b);
        assertEquals("a1", map.get("a"));
        assertEquals("b2", map.get("b"));
    }


    /* V v = map.get(key);
       if (v == null)
           v = map.put(key, value);

        return v;*/
    @Test
    public void testPutIfAbsent() {
        assertNull(map.putIfAbsent("d", "4"));
        assertEquals("1", map.putIfAbsent("a", "2"));
        assertEquals("1",map.get("a"));
        map.put("a", null);
        assertNull(map.putIfAbsent("a", "2"));
        assertEquals("2", map.get("a"));
    }

    /*
     if (map.containsKey(key) && Objects.equals(map.get(key), value)) {
     map.remove(key);
     return true;
 } else
     return false;
     */
    @Test
    public void testConditionalRemove() {
        assertTrue(map.remove("a", "1"));
        assertFalse(map.remove("b", "3"));
        assertFalse(map.remove("d", "1"));
    }

    @Test
    public void testReplace() {
        assertEquals("1", map.replace("a", "2"));
        assertEquals("2", map.get("a"));
        assertNull(map.replace("d", "1"));
        assertFalse(map.containsKey("d"));
    }


    /*
    if (map.containsKey(key) && Objects.equals(map.get(key), value)) {
     map.put(key, newValue);
     return true;
 } else
     return false;
     */
    @Test
    public void testConditionalReplace() {
        assertTrue(map.replace("a", "1", "2"));
        assertEquals("2", map.get("a"));
        assertFalse(map.replace("b", "3", "4"));
        assertEquals("2", map.get("b"));
        assertFalse(map.replace("d", "1", "2"));
        assertFalse(map.containsKey("d"));
    }

    /*

    map.computeIfAbsent(key, k -> new HashSet<V>()).add(v);

     if (map.get(key) == null) {
     V newValue = mappingFunction.apply(key);
     if (newValue != null)
         map.put(key, newValue);
 }
     */
    @Test
    public void testComputeIfAbsent() {
        assertEquals("1", map.computeIfAbsent("a", k -> k + "-"));
        assertEquals("1",map.get("a"));
        assertEquals("d-", map.computeIfAbsent("d", k -> k + "-"));
        assertEquals("d-", map.get("d"));
        assertNull(map.computeIfAbsent("e", k -> null));
        assertFalse(map.containsKey("e"));
        assertNull(map.put("f",null));
        assertEquals("3",map.computeIfAbsent("f",k -> "3"));
    }

    /*
    if (map.get(key) != null) {
     V oldValue = map.get(key);
     V newValue = remappingFunction.apply(key, oldValue);
     if (newValue != null)
         map.put(key, newValue);
     else
         map.remove(key);
 }
     */
    @Test
    public void testComputeIfPresent() {
        assertEquals("a-1", map.computeIfPresent("a", (k, v) -> k + "-" + v));
        assertEquals("a-1", map.get("a"));
        assertNull(map.computeIfPresent("d", (k, v) -> k + "-" + v));
        assertFalse(map.containsKey("d"));
        assertNull(map.computeIfPresent("a", (k, v) -> null));
        assertFalse(map.containsKey("a"));
        map.put("d", null);
        assertNull(map.computeIfPresent("d", (k, v) -> "1"));
        assertNull(map.get("d"));
        assertTrue(map.containsKey("d"));
        assertNull(map.computeIfPresent("d", (k, v) -> null));
        assertTrue(map.containsKey("d"));
    }

    /*
     V oldValue = get(key);

        V newValue = remappingFunction.apply(key, oldValue);
        if (newValue == null) {
            // delete mapping
            if (oldValue != null || containsKey(key)) {
                // something to remove
                remove(key);
                return null;
            } else {
                // nothing to do. Leave things as they were.
                return null;
            }
        } else {
            // add or replace old mapping
            put(key, newValue);
            return newValue;
        }
     */
    @Test
    public void testCompute() {
        assertEquals("a-1", map.compute("a", (k, v) -> k + "-" + v));
        assertEquals("a-1", map.get("a"));
        assertNull( map.compute("a", (k, v) -> null));
        assertFalse(map.containsKey("a"));
        assertEquals("d-null", map.compute("d", (k, v) -> k + "-"+v));
        assertEquals("d-null", map.get("d"));
        assertNull(map.compute("e", (k, v) -> null));
        assertFalse(map.containsKey("e"));
        assertNull(map.put("e",null));
        assertTrue(map.containsKey("e"));
        assertNull(map.compute("e",(k,v)->null));
        assertFalse(map.containsKey("e"));

    }

    /*
     if(value==null||remappingFunction==null)
       throw new NullPointerException();

     V oldValue = map.get(key);
     V newValue = (oldValue == null) ? value :
                  remappingFunction.apply(oldValue, value);
     if (newValue == null)
         map.remove(key);
     else
         map.put(key, newValue);
     */
    @Test
    public void testMerge() {
        assertEquals("1-3", map.merge("a", "3", (v1, v2) -> v1 + "-" + v2));
        assertEquals("1-3", map.get("a"));
        assertNull(map.merge("a", "4", (v1, v2) -> null));
        assertFalse(map.containsKey("a"));
        assertEquals("1", map.merge("d", "1", (v1, v2) -> v1 + "-"+v2));
        assertEquals("1", map.get("d"));
        map.put("e", null);
        assertEquals("2",map.merge("e", "2", (v1, v2) -> null));
        assertEquals("2", map.get("e"));
        map.put("f", null);
        assertThrows(NullPointerException.class,()->map.merge("f", null, (v1, v2) -> "4"));
    }

    @Test
    public abstract void testAllowNullKey();

    @Test
    public abstract void testAllowNullValue();


}

