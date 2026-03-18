package com.systemdesign.cache_simulator.service;

import com.systemdesign.cache_simulator.cache.Cache;
import com.systemdesign.cache_simulator.cache.LFUCache;
import com.systemdesign.cache_simulator.cache.LRUCache;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CacheService {

    private Cache<String, String> cache;
    private int hits = 0;
    private int misses = 0;

    // Default constructor
    public CacheService() {
        this.cache = new LRUCache<>(3); // default
    }

    // Switch strategy dynamically
    public void setStrategy(String strategy, int capacity) {
        if ("LFU".equalsIgnoreCase(strategy)) {
            cache = new LFUCache<>(capacity);
        } else {
            cache = new LRUCache<>(capacity);
        }
    }

    public void put(String key, String value) {
        cache.put(key, value);
    }

    public String get(String key) {
        String value = cache.get(key);

        if (value != null) {
            hits++;
        } else {
            misses++;
        }

        return value;
    }

    public String getStats() {
        int total = hits + misses;
        double ratio = total == 0 ? 0 : (double) hits / total;

        return "Hits: " + hits +
                ", Misses: " + misses +
                ", Hit Ratio: " + ratio;
    }

    public int size() {
        return cache.size();
    }

    public int capacity() {
        return cache.capacity();
    }

    public List<String> getKeys() {
        return cache.getCacheKeys();
    }
}