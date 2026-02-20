package com.questions.strivers.greedyalgorithm.medium;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: 146. LRU Cache (Medium / Hard)
 * ==================================================================================================
 * Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
 * * Implement the LRUCache class:
 * - LRUCache(int capacity): Initialize the LRU cache with positive size capacity.
 * - int get(int key): Return the value of the key if the key exists, otherwise return -1.
 * - void put(int key, int value): Update the value of the key if the key exists.
 * Otherwise, add the key-value pair to the cache. If the number of keys exceeds the capacity
 * from this operation, evict the least recently used key.
 * * The functions get and put must each run in O(1) average time complexity.
 *
 * Example:
 * Input: ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
 * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
 * Output: [null, null, null, 1, null, -1, null, -1, 3, 4]
 * * Explanation:
 * LRUCache lRUCache = new LRUCache(2);
 * lRUCache.put(1, 1); // cache is {1=1}
 * lRUCache.put(2, 2); // cache is {1=1, 2=2}
 * lRUCache.get(1);    // return 1
 * lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
 * lRUCache.get(2);    // returns -1 (not found)
 * lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
 * lRUCache.get(1);    // return -1 (not found)
 * lRUCache.get(3);    // return 3
 * lRUCache.get(4);    // return 4
 * ==================================================================================================
 */

import java.util.HashMap;

public class LRUCache {

    // ----------------------------------------------------------------------
    // DOUBLY LINKED LIST NODE DEFINITION
    // ----------------------------------------------------------------------
    class Node {
        int key;
        int value;
        Node prev;
        Node next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    // Dummy head and tail nodes to avoid tedious null checks during insertions/deletions
    private Node head = new Node(-1, -1);
    private Node tail = new Node(-1, -1);

    private int capacity;

    // HashMap maps the Key (int) to the actual Node object in the Doubly Linked List
    private HashMap<Integer, Node> map = new HashMap<>();

    /**
     * Constructor to initialize the cache with a specific capacity.
     */
    public LRUCache(int capacity) {
        this.capacity = capacity;
        // Connect the dummy head and tail initially
        head.next = tail;
        tail.prev = head;
    }

    /**
     * Helper Method: Adds a node right after the dummy 'head'.
     * The node right after the head is considered the MOST RECENTLY USED.
     */
    private void addNode(Node node) {
        Node temp = head.next;

        // Link new node to head and temp
        node.next = temp;
        node.prev = head;

        // Update head and temp to point to the new node
        head.next = node;
        temp.prev = node;
    }

    /**
     * Helper Method: Removes a node from anywhere in the Doubly Linked List.
     */
    private void deleteNode(Node node) {
        Node delPrev = node.prev;
        Node delNext = node.next;

        // Bypass the node to delete it from the chain
        delPrev.next = delNext;
        delNext.prev = delPrev;
    }

    /**
     * Fetches the value for a given key.
     * If found, the node is moved to the "Most Recently Used" position (front).
     */
    public int get(int key) {
        if (map.containsKey(key)) {
            Node resNode = map.get(key);

            // Move the accessed node to the front (right after head)
            deleteNode(resNode);
            addNode(resNode);

            return resNode.value;
        }
        return -1; // Cache miss
    }

    /**
     * Inserts or updates a key-value pair in the cache.
     * Evicts the Least Recently Used item if capacity is exceeded.
     */
    public void put(int key, int value) {
        // Case 1: Key already exists. Update its value and make it Most Recently Used.
        if (map.containsKey(key)) {
            Node existingNode = map.get(key);
            existingNode.value = value; // Update the value

            // Move it to the front
            deleteNode(existingNode);
            addNode(existingNode);
        }
        // Case 2: New Key. We must insert it.
        else {
            // Check if cache is full
            if (map.size() == capacity) {
                // The node right before the dummy 'tail' is the LEAST RECENTLY USED
                Node lruNode = tail.prev;

                // Remove it from both the HashMap and the Linked List
                map.remove(lruNode.key);
                deleteNode(lruNode);
            }

            // Create the new node, add it to the front of the list, and put it in the map
            Node newNode = new Node(key, value);
            addNode(newNode);
            map.put(key, newNode);
        }
    }

    // ----------------------------------------------------------------------
    // MAIN METHOD FOR TESTING
    // ----------------------------------------------------------------------
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);

        cache.put(1, 1);
        System.out.println("Put (1,1)");

        cache.put(2, 2);
        System.out.println("Put (2,2)");

        System.out.println("Get 1: " + cache.get(1));    // returns 1, makes 1 most recently used

        cache.put(3, 3);
        System.out.println("Put (3,3) -> Evicts 2");   // evicts key 2

        System.out.println("Get 2: " + cache.get(2));    // returns -1 (not found)

        cache.put(4, 4);
        System.out.println("Put (4,4) -> Evicts 1");   // evicts key 1

        System.out.println("Get 1: " + cache.get(1));    // returns -1 (not found)
        System.out.println("Get 3: " + cache.get(3));    // returns 3
        System.out.println("Get 4: " + cache.get(4));    // returns 4
    }
}