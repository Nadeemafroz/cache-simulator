package com.systemdesign.cache_simulator.controller;

import com.systemdesign.cache_simulator.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    // 🔹 Change cache strategy (LRU / LFU)
    @PostMapping("/strategy")
    public String setStrategy(@RequestParam String type,
                              @RequestParam int capacity) {
        cacheService.setStrategy(type, capacity);
        return "Strategy set to " + type + " with capacity " + capacity;
    }

    // 🔹 Put value in cache
    @PostMapping("/put")
    public String put(@RequestParam String key,
                      @RequestParam String value) {
        cacheService.put(key, value);
        return "Inserted (" + key + ", " + value + ")";
    }

    // 🔹 Get value from cache
    @GetMapping("/get")
    public String get(@RequestParam String key) {
        String value = cacheService.get(key);
        return value != null ? value : "Key not found";
    }

    // 🔹 Cache stats
    @GetMapping("/stats")
    public String stats() {
        return "Size: " + cacheService.size() +
                ", Capacity: " + cacheService.capacity();
    }

    @GetMapping("/metrics")
    public String metrics() {
        return cacheService.getStats();
    }

    @GetMapping("/keys")
    public List<String> getKeys() {
        return cacheService.getKeys();
    }
}
