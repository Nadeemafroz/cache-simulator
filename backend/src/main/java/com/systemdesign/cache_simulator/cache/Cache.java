package com.systemdesign.cache_simulator.cache;

public interface Cache<K, V> {

    // Add or update value in cache
    void put(K key, V value);

    // Get value from cache
    V get(K key);

    // Current size of cache
    int size();

    // Max capacity of cache
    int capacity();

    java.util.List<K> getCacheKeys();
}