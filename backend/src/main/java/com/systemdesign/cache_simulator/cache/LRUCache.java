package com.systemdesign.cache_simulator.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LRUCache<K, V> implements Cache<K, V> {

    private final int capacity;
    private final Map<K, Node<K, V>> map;

    private final Node<K, V> head;
    private final Node<K, V> tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();

        // dummy nodes
        head = new Node<>(null, null);
        tail = new Node<>(null, null);

        head.next = tail;
        tail.prev = head;
    }

    // Node class (Doubly Linked List)
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev, next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public void put(K key, V value) {
        if (map.containsKey(key)) {
            Node<K, V> node = map.get(key);
            node.value = value;
            remove(node);
            insertAtFront(node);
        } else {
            if (map.size() == capacity) {
                Node<K, V> lru = tail.prev;
                remove(lru);
                map.remove(lru.key);
            }

            Node<K, V> newNode = new Node<>(key, value);
            insertAtFront(newNode);
            map.put(key, newNode);
        }
    }

    @Override
    public V get(K key) {
        if (!map.containsKey(key)) return null;

        Node<K, V> node = map.get(key);
        remove(node);
        insertAtFront(node);

        return node.value;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public int capacity() {
        return capacity;
    }

    // 🔧 Helper methods

    private void remove(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void insertAtFront(Node<K, V> node) {
        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;
    }

    @Override
    public List<K> getCacheKeys() {
        List<K> keys = new ArrayList<>();
        Node<K, V> current = head.next;

        while (current != tail) {
            keys.add(current.key);
            current = current.next;
        }

        return keys;
    }
}
