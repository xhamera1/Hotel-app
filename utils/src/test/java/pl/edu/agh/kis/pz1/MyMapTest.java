package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MyMapTest {

    @Test
    void testPutAndGet() {
        MyMap<String, Integer> map = new MyMap<>();
        map.put("one", 1);
        map.put("two", 2);

        assertEquals(1, map.get("one"));
        assertEquals(2, map.get("two"));
        assertNull(map.get("three"));
    }

    @Test
    void testUpdateValue() {
        MyMap<String, String> map = new MyMap<>();
        map.put("key", "value1");
        assertEquals("value1", map.get("key"));

        map.put("key", "value2");
        assertEquals("value2", map.get("key"));
    }

    @Test
    void testKeys() {
        MyMap<Integer, String> map = new MyMap<>();
        map.put(1, "one");
        map.put(2, "two");

        List<Integer> keys = map.keys();
        assertEquals(2, keys.size());
        assertTrue(keys.contains(1));
        assertTrue(keys.contains(2));
    }

    @Test
    void testGetValues() {
        MyMap<Integer, String> map = new MyMap<>();
        map.put(1, "one");
        map.put(2, "two");

        List<String> values = map.getValues();
        assertEquals(2, values.size());
        assertTrue(values.contains("one"));
        assertTrue(values.contains("two"));
    }

    @Test
    void testRemove() {
        MyMap<String, Integer> map = new MyMap<>();
        map.put("one", 1);
        map.put("two", 2);

        map.remove("one");
        assertNull(map.get("one"));
        assertEquals(2, map.get("two"));

        List<String> keys = map.keys();
        assertEquals(1, keys.size());
        assertTrue(keys.contains("two"));
    }

    @Test
    void testIsEmpty() {
        MyMap<String, String> map = new MyMap<>();
        assertTrue(map.isEmpty());

        map.put("key", "value");
        assertFalse(map.isEmpty());

        map.remove("key");
        assertTrue(map.isEmpty());
    }

    @Test
    void testEntrySet() {
        MyMap<String, Integer> map = new MyMap<>();
        map.put("one", 1);
        map.put("two", 2);

        Set<MyMap<String, Integer>.MyEntry> entries = map.entrySet();
        assertEquals(2, entries.size());

        for (MyMap<String, Integer>.MyEntry entry : entries) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            assertEquals(map.get(key), value);
        }
    }

    @Test
    void testMyEntrySize() {
        MyMap<String, Integer> map = new MyMap<>();
        map.put("one", 1);

        Set<MyMap<String, Integer>.MyEntry> entries = map.entrySet();
        for (MyMap<String, Integer>.MyEntry entry : entries) {
            assertEquals(entries.size(), entry.size());
        }
    }
}
