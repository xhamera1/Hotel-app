package pl.edu.agh.kis.pz1;

import java.util.List;

/**
 * The Map interface defines basic operations for a map data structure,
 * which associates keys with values. Each key in the map is unique and
 * is assigned exactly one value.
 *
 * @param <K> The type of keys maintained by this map.
 * @param <V> The type of values associated with the keys.
 */
public interface Map<K, V> {
    /**
     * Associates the specified value with the specified key in this map.
     *
     * @param key   The key with which the specified value is to be associated.
     * @param value The value to be associated with the specified key.
     */
    void put(K key, V value);

    /**
     * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
     *
     * @param key The key whose associated value is to be returned.
     * @return The value associated with the specified key, or null if the key is not present in the map.
     */
    V get(K key);

    /**
     * Returns a list of all keys contained in this map.
     *
     * @return A list of all keys in this map.
     */
    List<K> keys();

    /**
     * Removes the mapping for the specified key from this map if present.
     *
     * @param key The key whose mapping is to be removed from the map.
     */
    void remove(K key);


}
