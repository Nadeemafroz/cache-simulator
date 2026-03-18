package com.systemdesign.cache_simulator.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LFUCache<K, V> implements Cache<K, V> {

    private final int capacity;
    private int curSize;
    private int minFreq;

    private final Map<K, Node<K, V>> keyNode;
    private final Map<Integer, DoublyLinkedList<K, V>> freqListMap;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.curSize = 0;
        this.minFreq = 0;
        this.keyNode = new HashMap<>();
        this.freqListMap = new HashMap<>();
    }

    // 🔹 Node class
    static class Node<K, V> {
        K key;
        V value;
        int freq;
        Node<K, V> prev, next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }

    // 🔹 Doubly Linked List
    static class DoublyLinkedList<K, V> {
        int size;
        Node<K, V> head, tail;

        DoublyLinkedList() {
            head = new Node<>(null, null);
            tail = new Node<>(null, null);
            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        void addFront(Node<K, V> node) {
            Node<K, V> temp = head.next;
            node.next = temp;
            node.prev = head;
            head.next = node;
            temp.prev = node;
            size++;
        }

        void removeNode(Node<K, V> node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            size--;
        }

        Node<K, V> removeLast() {
            if (size > 0) {
                Node<K, V> last = tail.prev;
                removeNode(last);
                return last;
            }
            return null;
        }
    }

    @Override
    public V get(K key) {
        if (!keyNode.containsKey(key)) return null;

        Node<K, V> node = keyNode.get(key);
        updateFreq(node);
        return node.value;
    }

    @Override
    public void put(K key, V value) {
        if (capacity == 0) return;

        if (keyNode.containsKey(key)) {
            Node<K, V> node = keyNode.get(key);
            node.value = value;
            updateFreq(node);
        } else {
            if (curSize == capacity) {
                DoublyLinkedList<K, V> list = freqListMap.get(minFreq);
                Node<K, V> evict = list.removeLast();
                keyNode.remove(evict.key);
                curSize--;
            }

            Node<K, V> newNode = new Node<>(key, value);
            minFreq = 1;

            DoublyLinkedList<K, V> list =
                    freqListMap.getOrDefault(1, new DoublyLinkedList<>());
            list.addFront(newNode);

            freqListMap.put(1, list);
            keyNode.put(key, newNode);
            curSize++;
        }
    }

    private void updateFreq(Node<K, V> node) {
        int freq = node.freq;

        DoublyLinkedList<K, V> list = freqListMap.get(freq);
        list.removeNode(node);

        if (freq == minFreq && list.size == 0) {
            minFreq++;
        }

        node.freq++;

        DoublyLinkedList<K, V> newList =
                freqListMap.getOrDefault(node.freq, new DoublyLinkedList<>());
        newList.addFront(node);

        freqListMap.put(node.freq, newList);
    }

    @Override
    public int size() {
        return curSize;
    }

    @Override
    public int capacity() {
        return capacity;
    }


    @Override
    public List<K> getCacheKeys() {
        List<K> result = new ArrayList<>();

        for (int freq : freqListMap.keySet()) {
            DoublyLinkedList<K, V> list = freqListMap.get(freq);
            Node<K, V> curr = list.head.next;

            while (curr != list.tail) {
                result.add(curr.key);
                curr = curr.next;
            }
        }

        return result;
    }
}
