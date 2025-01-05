package pl.edu.agh.kis.pz1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The MyMap class implements a custom Map data structure that stores keys and their corresponding
 * values in two separate lists. Each key in the 'keys' list has a corresponding value in the 'values'
 * list at the same index. The class supports basic operations such as adding, retrieving, and removing elements.
 *
 * @param <K> The type of keys maintained by this map.
 * @param <V> The type of values associated with the keys.
 */
public class MyMap<K, V> implements Map<K, V> {
    private List<K> keys;
    private List<V> values;

    /**
     * Constructs a MyMap object. Initializes empty lists for keys and values.
     */
    public MyMap() {
        this.keys = new ArrayList<>();
        this.values = new ArrayList<>();
    }

    /**
     * Adds a new key-value pair to the map. If the key already exists, its value is updated.
     *
     * @param key   The key to be added or updated.
     * @param value The value to be associated with the specified key.
     */
    @Override
    public void put(K key, V value) {
        int index = keys.indexOf(key);
        if (index == -1) {
            keys.add(key);
            values.add(value);
        } else {
            values.set(index, value);
        }
    }

    /**
     * Retrieves the value associated with the specified key.
     *
     * @param key The key whose associated value is to be returned.
     * @return The value associated with the key, or null if the key is not present in the map.
     */
    @Override
    public V get(K key) {
        int index = keys.indexOf(key);
        if (index == -1) {
            return null;
        }
        return values.get(index);
    }

    /**
     * Returns a list of all keys stored in the map.
     *
     * @return A list of keys.
     */
    @Override
    public List<K> keys() {
        return new ArrayList<>(keys);
    }


    /**
     * Returns a list of all values stored in the map.
     *
     * @return A list of values.
     */
    public List<V> getValues(){
        return this.values;
    }

    /**
     * Removes the key and its corresponding value from the map, if the key exists.
     *
     * @param key The key to be removed.
     */
    @Override
    public void remove(K key) {
        int index = keys.indexOf(key);
        if (index != -1) {
            keys.remove(index);
            values.remove(index);
        }
    }


    /**
     * Checks if the map is empty.
     *
     * @return true if the map contains no key-value mappings, false otherwise.
     */
    public boolean isEmpty(){
        return keys.isEmpty();
    }

    /**
     * Returns a set of MyEntry objects, where each entry contains a key and its associated value.
     *
     * @return A set of key-value pairs contained in this map.
     */
    public Set<MyEntry> entrySet() {
        // MyEntry to para klucz-warotsc w MyMapie
        Set<MyEntry> entrySet = new HashSet<>();
        for (int i = 0; i < keys.size(); i++) {
            K key = keys.get(i);
            V value = values.get(i);
            entrySet.add(new MyEntry(key, value));
        }
        return entrySet;
    }

    /**
     * Inner class representing a key-value pair in MyMap.
     */
    public class MyEntry {
        private final K key;
        private final V value;

        /**
         * Constructs a new MyEntry with the specified key and value.
         *
         * @param key   the key corresponding to this entry.
         * @param value the value corresponding to this entry.
         */
        public MyEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Retrieves the key of this entry.
         *
         * @return the key corresponding to this entry.
         */
        public K getKey() {
            return key;
        }

        /**
         * Retrieves the value of this entry.
         *
         * @return the value corresponding to this entry.
         */
        public V getValue() {
            return value;
        }

        /**
         * Returns a string representation of this entry.
         * The format is "key = value".
         *
         * @return the string representation of this entry.
         */
        @Override
        public String toString() {
            return key + " = " + value;
        }

        /**
         * Returns a size of entry
         *
         * @return the int representing a size of entry
         */
        public int size() {
            return entrySet().size();
        }
    }
}
