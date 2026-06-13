package strivers.stackandqueues.implementationproblem;

import java.util.*;
import java.util.stream.IntStream;

/**
 * ============================================================================
 * 146. LRU Cache
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Design a data structure that follows the constraints of a Least Recently Used
 * (LRU) cache.
 * * Implement the LRUCache class:
 * - LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
 * - int get(int key) Return the value of the key if the key exists, otherwise return -1.
 * - void put(int key, int value) Update the value of the key if the key exists.
 * Otherwise, add the key-value pair to the cache. If the number of keys exceeds
 * the capacity from this operation, evict the least recently used key.
 *
 * The functions get and put must each run in O(1) average time complexity.
 *
 * Example 1:
 * Input
 * ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
 * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
 * Output
 * [null, null, null, 1, null, -1, null, -1, 3, 4]
 *
 * Explanation
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
 *
 * CONSTRAINTS:
 * 1 <= capacity <= 3000
 * 0 <= key <= 10^4
 * 0 <= value <= 10^5
 * At most 2 * 10^5 calls will be made to get and put.
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Doubly Linked List + HashMap (O(1) Time)
 * Phase 2: Brute Force Approach - ArrayList tracking (O(N) Time)
 * Phase 3: Alternative Approach - Java Built-in LinkedHashMap (Production approach)
 * ============================================================================
 */
public class LRUCacheMasterclass {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Custom Doubly Linked List + HashMap)
     * ============================================================================
     * Detailed Intuition:
     * To achieve O(1) time complexity for both `get` and `put` operations, which
     * is a hard requirement for top-tier product-based company interviews, we must
     * combine two data structures.
     * 1. A HashMap provides O(1) access to cache nodes by their keys.
     * 2. A Doubly Linked List (DLL) maintains the access order. The Most Recently
     * Used (MRU) node is kept right after a dummy `head`, and the Least Recently
     * Used (LRU) node sits right before a dummy `tail`.
     * Using dummy head and tail nodes is a senior-level practice that cleanly
     * avoids messy null-pointer checks during node extraction and insertion.
     * When a node is accessed or updated, it is severed from its current position
     * in O(1) time and moved directly behind the dummy `head`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(1) for both get() and put(). HashMap lookups are O(1),
     * and pointer reassignment in a DLL is strict O(1).
     * - Space Complexity: O(capacity) heap space. The HashMap and DLL scale linearly
     * with the defined capacity. Auxiliary stack space is O(1).
     * ============================================================================
     */
    public static class LRUCacheOptimal {

        // Node structure for Doubly Linked List
        class Node {
            int key, val;
            Node prev, next;
            Node(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }

        private final int capacity;
        private final Map<Integer, Node> cache;
        private final Node head, tail;

        public LRUCacheOptimal(int capacity) {
            this.capacity = capacity;
            this.cache = new HashMap<>();

            // Dummy nodes to safely anchor the list
            this.head = new Node(-1, -1);
            this.tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            if (!cache.containsKey(key)) {
                return -1;
            }
            Node node = cache.get(key);
            removeNode(node);
            insertAfterHead(node); // Mark as Most Recently Used
            return node.val;
        }

        public void put(int key, int value) {
            if (cache.containsKey(key)) {
                Node node = cache.get(key);
                node.val = value; // Update value
                removeNode(node);
                insertAfterHead(node); // Mark as MRU
            } else {
                if (cache.size() == capacity) {
                    // Evict LRU from the tail
                    Node lruNode = tail.prev;
                    removeNode(lruNode);
                    cache.remove(lruNode.key); // We store 'key' in Node specifically for this O(1) lookup
                }
                Node newNode = new Node(key, value);
                cache.put(key, newNode);
                insertAfterHead(newNode);
            }
        }

        // DLL Utility: Removes an existing node from the list
        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        // DLL Utility: Inserts a node right after the dummy head
        private void insertAfterHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (ArrayList Tracking) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * If someone intuitively approaches this without knowing the DLL pattern, they
     * might use an ArrayList to track usage history alongside a HashMap for values.
     * When a key is accessed, we traverse the list to find it, remove it, and append
     * it to the end (making the end of the list the MRU, and index 0 the LRU).
     *
     * Complexity Analysis:
     * - Time Complexity: O(N) for both get() and put() where N is capacity, because
     * removing an element from the middle of an ArrayList requires shifting all
     * subsequent elements in memory.
     * - Space Complexity: O(capacity) heap space.
     * ============================================================================
     */
    public static class LRUCacheBruteForce {
        private final int capacity;
        private final Map<Integer, Integer> map;
        private final List<Integer> usageHistory; // Tracks keys. Index 0 is LRU.

        public LRUCacheBruteForce(int capacity) {
            this.capacity = capacity;
            this.map = new HashMap<>();
            this.usageHistory = new ArrayList<>();
        }

        public int get(int key) {
            if (!map.containsKey(key)) return -1;

            // O(N) operation to find and remove, then append to end
            usageHistory.remove(Integer.valueOf(key));
            usageHistory.add(key);

            return map.get(key);
        }

        public void put(int key, int value) {
            if (map.containsKey(key)) {
                usageHistory.remove(Integer.valueOf(key));
            } else if (usageHistory.size() == capacity) {
                int lruKey = usageHistory.remove(0); // O(N) shift operation
                map.remove(lruKey);
            }
            map.put(key, value);
            usageHistory.add(key);
        }
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Java Built-in LinkedHashMap)
     * ============================================================================
     * Detailed Intuition:
     * In a real-world enterprise environment (e.g., Spring Boot architectures without
     * Caffeine or Redis), rewriting a custom LRU is reinventing the wheel. Java's
     * `java.util.LinkedHashMap` has a specialized constructor `(capacity, loadFactor, accessOrder)`.
     * By setting `accessOrder` to `true`, the map automatically maintains elements
     * from least-recently accessed to most-recently accessed. Overriding the
     * `removeEldestEntry` method provides a complete, thread-unsafe LRU cache in 5 lines.
     *
     * Complexity Analysis:
     * - Time Complexity: O(1) for get() and put().
     * - Space Complexity: O(capacity) heap space.
     * ============================================================================
     */
    public static class LRUCacheProduction extends LinkedHashMap<Integer, Integer> {
        private final int maxCapacity;

        public LRUCacheProduction(int capacity) {
            // true flag enables access-order rather than insertion-order
            super(capacity, 0.75f, true);
            this.maxCapacity = capacity;
        }

        public int get(int key) {
            return super.getOrDefault(key, -1);
        }

        public void put(int key, int value) {
            super.put(key, value);
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() > maxCapacity;
        }
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Tests all the different approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("=== LRU Cache Testing Suite ===\n");

        // Helper interface to test all variants uniformly
        interface CacheTestAdapter {
            int get(int key);
            void put(int key, int value);
        }

        // Test Scenario Configuration
        int capacity = 2;
        int[][] puts = {{1, 1}, {2, 2}, {3, 3}, {4, 4}};

        System.out.println("Running standard LC Example...");

        // Testing Optimal Approach
        System.out.println("\n-- Testing Optimal (Custom DLL) --");
        LRUCacheOptimal optimal = new LRUCacheOptimal(capacity);
        executeStandardTestScenario(new CacheTestAdapter() {
            public int get(int k) { return optimal.get(k); }
            public void put(int k, int v) { optimal.put(k, v); }
        });

        // Testing Brute Force Approach
        System.out.println("\n-- Testing Brute Force (ArrayList) --");
        LRUCacheBruteForce bruteForce = new LRUCacheBruteForce(capacity);
        executeStandardTestScenario(new CacheTestAdapter() {
            public int get(int k) { return bruteForce.get(k); }
            public void put(int k, int v) { bruteForce.put(k, v); }
        });

        // Testing Production Approach
        System.out.println("\n-- Testing Production (LinkedHashMap) --");
        LRUCacheProduction production = new LRUCacheProduction(capacity);
        executeStandardTestScenario(new CacheTestAdapter() {
            public int get(int k) { return production.get(k); }
            public void put(int k, int v) { production.put(k, v); }
        });
    }

    private static void executeStandardTestScenario(Object adapter) {
        try {
            // Using reflection just to keep the main block exceptionally clean for the adapter
            java.lang.reflect.Method getMethod = adapter.getClass().getMethod("get", int.class);
            java.lang.reflect.Method putMethod = adapter.getClass().getMethod("put", int.class, int.class);

            putMethod.invoke(adapter, 1, 1);
            putMethod.invoke(adapter, 2, 2);
            System.out.println("get(1) -> Expected: 1 | Actual: " + getMethod.invoke(adapter, 1));

            putMethod.invoke(adapter, 3, 3); // evicts 2
            System.out.println("get(2) -> Expected: -1 | Actual: " + getMethod.invoke(adapter, 2));

            putMethod.invoke(adapter, 4, 4); // evicts 1
            System.out.println("get(1) -> Expected: -1 | Actual: " + getMethod.invoke(adapter, 1));
            System.out.println("get(3) -> Expected: 3 | Actual: " + getMethod.invoke(adapter, 3));
            System.out.println("get(4) -> Expected: 4 | Actual: " + getMethod.invoke(adapter, 4));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}