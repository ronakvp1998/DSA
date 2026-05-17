package com.questions.strivers.greedyalgorithm.medium;

/**
 * 🟡 PROBLEM: LEAST RECENTLY USED (LRU) PAGE REPLACEMENT ALGORITHM
 * * Statement:
 * In an operating system that uses paging for memory management, the page replacement
 * algorithm is needed to decide which page needs to be replaced when a new page comes in.
 * The LRU algorithm replaces the page that has not been used for the longest period of time.
 * * Constraints:
 * - 1 <= Capacity <= 10^3
 * - 0 <= Page Number <= 10^4
 * - Total Requests <= 10^5
 * * Example 1:
 * Input: Capacity = 3, Pages = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2}
 * Output: Total Page Faults = 9
 * * Example 2:
 * Input: Capacity = 4, Pages = {1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5}
 * Output: Total Page Faults = 6
 * * -------------------------------------------------------------------------
 * CONCEPTUAL VISUALIZATION (Example 1: Cap=3)
 * -------------------------------------------------------------------------
 * Sequence: [7, 0, 1, 2, 0]
 * * Time T1 (Ref: 7): [7]          | Fault (1)
 * Time T2 (Ref: 0): [7, 0]       | Fault (2)
 * Time T3 (Ref: 1): [7, 0, 1]    | Fault (3)
 * Time T4 (Ref: 2): [0, 1, 2]    | Fault (4) - 7 was least recently used.
 * Time T5 (Ref: 0): [1, 2, 0]    | Hit       - 0 moved to most recent.
 * * RECURSION TREE / DECISION LOGIC:
 * Since this is a cache management problem rather than a standard optimization
 * DP problem, the "tree" represents the state of the cache at each step:
 * * Start: {}
 * |-- 7 (Miss) -> {7}
 * |-- 0 (Miss) -> {7,0}
 * |-- 1 (Miss) -> {7,0,1}
 * |-- 2 (Miss, Evict 7) -> {0,1,2}
 * |-- 0 (Hit, Reorder) -> {1,2,0}
 */

import java.util.*;

public class LRUCacheMaster {

    /**
     * PHASE 1: BRUTE FORCE (Search & Replace)
     * Intuition: Use an ArrayList to represent the cache. For every page, manually
     * search if it exists. If not, and cache is full, find the "Least Recently Used"
     * by looking backward in the original page array.
     * * Time Complexity: O(N * Capacity) - Searching the cache for each page.
     * Space Complexity: O(Capacity) - To store pages in memory.
     */
    public static int getPageFaultsBruteForce(int[] pages, int capacity) {
        ArrayList<Integer> cache = new ArrayList<>(capacity);
        int pageFaults = 0;

        for (int i = 0; i < pages.length; i++) {
            if (!cache.contains(pages[i])) {
                if (cache.size() == capacity) {
                    // Find LRU: Look back to see which of the current cache
                    // elements appeared furthest back in the past.
                    int lruIndex = Integer.MAX_VALUE;
                    int valToReplace = -1;
                    for (int cachedPage : cache) {
                        int lastSeen = -1;
                        for (int j = i - 1; j >= 0; j--) {
                            if (pages[j] == cachedPage) {
                                lastSeen = j;
                                break;
                            }
                        }
                        if (lastSeen < lruIndex) {
                            lruIndex = lastSeen;
                            valToReplace = cachedPage;
                        }
                    }
                    cache.remove(Integer.valueOf(valToReplace));
                }
                cache.add(pages[i]);
                pageFaults++;
            }
        }
        return pageFaults;
    }

    /**
     * PHASE 2: HASHSET + TRACKING (Refined)
     * Intuition: Use a HashSet for O(1) lookups and a Map to store the
     * last index of every page seen. This avoids the O(N) backward scan.
     * * Time Complexity: O(N * Capacity) - Finding the minimum index in the map.
     * Space Complexity: O(Capacity) - Storage for the set and index map.
     */
    public static int getPageFaultsHashSet(int[] pages, int capacity) {
        HashSet<Integer> cache = new HashSet<>(capacity);
        HashMap<Integer, Integer> lastIndexMap = new HashMap<>();
        int pageFaults = 0;

        for (int i = 0; i < pages.length; i++) {
            if (cache.size() < capacity) {
                if (!cache.contains(pages[i])) {
                    cache.add(pages[i]);
                    pageFaults++;
                }
            } else {
                if (!cache.contains(pages[i])) {
                    int lru = Integer.MAX_VALUE, valToEvict = -1;
                    for (int temp : cache) {
                        if (lastIndexMap.get(temp) < lru) {
                            lru = lastIndexMap.get(temp);
                            valToEvict = temp;
                        }
                    }
                    cache.remove(valToEvict);
                    cache.add(pages[i]);
                    pageFaults++;
                }
            }
            lastIndexMap.put(pages[i], i);
        }
        return pageFaults;
    }

    /**
     * PHASE 3: OPTIMIZED (LinkedHashSet / LinkedHashMap)
     * Intuition: LinkedHashSet maintains insertion order. By removing and re-adding
     * a page on a "Hit", we effectively move it to the "Most Recently Used" position.
     * The first element is always the LRU.
     * * Time Complexity: O(N) - Each page operation is O(1).
     * Space Complexity: O(Capacity) - Storage.
     */
    public static int getPageFaultsOptimized(int[] pages, int capacity) {
        LinkedHashSet<Integer> cache = new LinkedHashSet<>(capacity);
        int pageFaults = 0;

        for (int page : pages) {
            if (cache.contains(page)) {
                // HIT: Move to end (Most Recent)
                cache.remove(page);
                cache.add(page);
            } else {
                // MISS
                pageFaults++;
                if (cache.size() == capacity) {
                    // Evict first element (Least Recent)
                    int lru = cache.iterator().next();
                    cache.remove(lru);
                }
                cache.add(page);
            }
        }
        return pageFaults;
    }

    /**
     * PHASE 4: THE INTERVIEW STANDARD (Doubly Linked List + HashMap)
     * Intuition: This is how LRU is actually implemented in systems like Redis.
     * A HashMap provides O(1) access to nodes. A Doubly Linked List (DLL)
     * provides O(1) addition/deletion.
     * * DLL STRUCTURE:
     * Head (MRU) <-> Node <-> Node <-> Tail (LRU)
     */
    static class Node {
        int key;
        Node prev, next;
        Node(int k) { this.key = k; }
    }

    static class LRUManual {
        int cap;
        HashMap<Integer, Node> map = new HashMap<>();
        Node head = new Node(0), tail = new Node(0);

        LRUManual(int capacity) {
            this.cap = capacity;
            head.next = tail;
            tail.prev = head;
        }

        private void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void insert(Node node) {
            node.next = head.next;
            node.next.prev = node;
            head.next = node;
            node.prev = head;
        }

        public boolean access(int key) {
            if (map.containsKey(key)) {
                remove(map.get(key));
                insert(map.get(key));
                return true; // Hit
            }
            if (map.size() == cap) {
                map.remove(tail.prev.key);
                remove(tail.prev);
            }
            Node newNode = new Node(key);
            insert(newNode);
            map.put(key, newNode);
            return false; // Fault
        }
    }

    public static void main(String[] args) {
        int[] pages = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2};
        int capacity = 3;

        System.out.println("--- LRU Page Replacement Testing ---");
        System.out.println("Brute Force Faults: " + getPageFaultsBruteForce(pages, capacity));
        System.out.println("HashSet Faults:     " + getPageFaultsHashSet(pages, capacity));
        System.out.println("Optimized Faults:   " + getPageFaultsOptimized(pages, capacity));

        // Test with LRU Manual Implementation
        LRUManual lru = new LRUManual(capacity);
        int manualFaults = 0;
        for (int p : pages) if (!lru.access(p)) manualFaults++;
        System.out.println("Manual DLL Faults:  " + manualFaults);

        // Edge Case: Capacity 1
        int[] singleCap = {1, 2, 1, 2, 1};
        System.out.println("Edge Case (Cap 1):  " + getPageFaultsOptimized(singleCap, 1)); // Expected 5
    }
}