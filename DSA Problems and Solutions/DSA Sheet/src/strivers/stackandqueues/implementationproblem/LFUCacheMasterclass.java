package strivers.stackandqueues.implementationproblem;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * 460. LFU Cache
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Design and implement a data structure for a Least Frequently Used (LFU) cache.
 * * Implement the LFUCache class:
 * - LFUCache(int capacity) Initializes the object with the capacity of the data structure.
 * - int get(int key) Gets the value of the key if the key exists in the cache. Otherwise, returns -1.
 * - void put(int key, int value) Update the value of the key if present, or inserts the key if not already present.
 * When the cache reaches its capacity, it should invalidate and remove the least frequently used key before
 * inserting a new item. For this problem, when there is a tie (i.e., two or more keys with the same frequency),
 * the least recently used key would be invalidated.
 * * To determine the least frequently used key, a use counter is maintained for each key in the cache.
 * The key with the smallest use counter is the least frequently used key.
 * * The functions get and put must each run in O(1) average time complexity.
 *
 * Example 1:
 * Input
 * ["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "get"]
 * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [3], [4, 4], [1], [3], [4]]
 * Output
 * [null, null, null, 1, null, -1, 3, null, -1, 3, 4]
 *
 * CONSTRAINTS:
 * 1 <= capacity <= 10^4
 * 0 <= key <= 10^5
 * 0 <= value <= 10^9
 * At most 2 * 10^5 calls will be made to get and put.
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Two HashMaps + Doubly Linked Lists (O(1) Time)
 * Phase 2: Brute Force Approach - Object List Tracking (O(N) Time)
 * Phase 3: Alternative Approach - Priority Queue / Min-Heap (O(log N) Time)
 * ============================================================================
 */
public class LFUCacheMasterclass {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Two HashMaps + Doubly Linked Lists)
     * ============================================================================
     * Detailed Intuition:
     * To achieve strict O(1) time complexity for both `get` and `put`, we need an
     * architecture capable of instantly locating nodes and instantly resolving
     * LFU/LRU ties. We accomplish this by combining two HashMaps and custom
     * Doubly Linked Lists (DLL).
     * * 1. `cache`: A Map<Integer, Node> to provide O(1) direct access to any key.
     * 2. `freqMap`: A Map<Integer, DoublyLinkedList> linking a specific frequency
     * count to a DLL of all nodes currently at that frequency.
     * 3. Within each DLL, the Most Recently Used (MRU) node is kept at the head,
     * and the Least Recently Used (LRU) node falls to the tail.
     * 4. We maintain a `minFreq` integer. When the cache hits capacity, we query
     * `freqMap.get(minFreq)` to get the DLL of the least frequently used elements,
     * and we pop its tail (which natively resolves the LRU tiebreaker in O(1)).
     * * When a node is accessed (via get or put), its frequency increments. We sever
     * it from its current DLL and append it to the head of the DLL for `freq + 1`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(1) for both get() and put(). Map lookups and DLL pointer
     * manipulations are constant time operations.
     * - Space Complexity: O(capacity) heap space. Memory scales linearly with the
     * number of unique keys stored.
     * ============================================================================
     */
    public static class LFUCacheOptimal {

        class Node {
            int key, val, freq;
            Node prev, next;
            Node(int key, int val) {
                this.key = key;
                this.val = val;
                this.freq = 1;
            }
        }

        class DoublyLinkedList {
            int size;
            Node head, tail;

            DoublyLinkedList() {
                this.size = 0;
                this.head = new Node(-1, -1);
                this.tail = new Node(-1, -1);
                head.next = tail;
                tail.prev = head;
            }

            void addFirst(Node node) {
                Node nextNode = head.next;
                head.next = node;
                node.prev = head;
                node.next = nextNode;
                nextNode.prev = node;
                size++;
            }

            void removeNode(Node node) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                size--;
            }

            Node removeLast() {
                if (size > 0) {
                    Node lruNode = tail.prev;
                    removeNode(lruNode);
                    return lruNode;
                }
                return null;
            }
        }

        private final int capacity;
        private int minFreq;
        private final Map<Integer, Node> cache;
        private final Map<Integer, DoublyLinkedList> freqMap;

        public LFUCacheOptimal(int capacity) {
            this.capacity = capacity;
            this.minFreq = 0;
            this.cache = new HashMap<>();
            this.freqMap = new HashMap<>();
        }

        public int get(int key) {
            if (!cache.containsKey(key)) return -1;
            Node node = cache.get(key);
            updateNodeFrequency(node);
            return node.val;
        }

        public void put(int key, int value) {
            if (capacity == 0) return;

            if (cache.containsKey(key)) {
                Node node = cache.get(key);
                node.val = value;
                updateNodeFrequency(node);
            } else {
                if (cache.size() == capacity) {
                    DoublyLinkedList minList = freqMap.get(minFreq);
                    Node lruNode = minList.removeLast();
                    cache.remove(lruNode.key);
                }
                Node newNode = new Node(key, value);
                cache.put(key, newNode);
                freqMap.computeIfAbsent(1, k -> new DoublyLinkedList()).addFirst(newNode);
                minFreq = 1; // Reset minFreq to 1 for the newly inserted element
            }
        }

        private void updateNodeFrequency(Node node) {
            int currentFreq = node.freq;
            DoublyLinkedList currentList = freqMap.get(currentFreq);
            currentList.removeNode(node);

            // If the current list was the minimum frequency list and is now empty, increment minFreq
            if (currentFreq == minFreq && currentList.size == 0) {
                minFreq++;
            }

            node.freq++;
            freqMap.computeIfAbsent(node.freq, k -> new DoublyLinkedList()).addFirst(node);
        }
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Object List Tracking) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * A straightforward simulation without complex nested data structures. We keep
     * a single List of CacheEntry objects. Each object stores the key, value,
     * access frequency, and a "last accessed" timestamp.
     * * When capacity is reached during a `put`, we must iterate through the entire
     * List to find the entry with the absolute lowest frequency. If multiple entries
     * share that lowest frequency, we compare their timestamps to drop the oldest.
     *
     * Complexity Analysis:
     * - Time Complexity: O(N) where N is capacity. `get` and `put` require scanning
     * the List to find keys or determine the LFU/LRU eviction target.
     * - Space Complexity: O(capacity) heap space.
     * ============================================================================
     */
    public static class LFUCacheBruteForce {
        class CacheEntry {
            int key, val, freq, timestamp;
            CacheEntry(int k, int v, int t) {
                key = k; val = v; freq = 1; timestamp = t;
            }
        }

        private final int capacity;
        private int time;
        private final List<CacheEntry> list;

        public LFUCacheBruteForce(int capacity) {
            this.capacity = capacity;
            this.time = 0;
            this.list = new ArrayList<>();
        }

        public int get(int key) {
            for (CacheEntry entry : list) {
                if (entry.key == key) {
                    entry.freq++;
                    entry.timestamp = ++time;
                    return entry.val;
                }
            }
            return -1;
        }

        public void put(int key, int value) {
            if (capacity == 0) return;

            for (CacheEntry entry : list) {
                if (entry.key == key) {
                    entry.val = value;
                    entry.freq++;
                    entry.timestamp = ++time;
                    return;
                }
            }

            if (list.size() == capacity) {
                CacheEntry evictTarget = list.get(0);
                for (CacheEntry entry : list) {
                    if (entry.freq < evictTarget.freq ||
                            (entry.freq == evictTarget.freq && entry.timestamp < evictTarget.timestamp)) {
                        evictTarget = entry;
                    }
                }
                list.remove(evictTarget);
            }
            list.add(new CacheEntry(key, value, ++time));
        }
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (Priority Queue / Min-Heap)
     * ============================================================================
     * Detailed Intuition:
     * We can optimize the Brute Force search by using a Priority Queue (Min-Heap).
     * The heap maintains entries ordered primarily by frequency (ascending) and
     * secondarily by a monotonically increasing timestamp (ascending, representing LRU).
     * * Finding the eviction target becomes O(1) via `peek()`. However, updating the
     * frequency of an existing key requires removing it from the heap and re-inserting
     * it so the heap can re-balance. Standard Java PriorityQueue removal is O(N).
     *
     * Complexity Analysis:
     * - Time Complexity: O(N) for operations affecting existing keys due to `pq.remove()`.
     * (Can be optimized to O(log N) using a custom Indexed Priority Queue or Lazy Deletion).
     * - Space Complexity: O(capacity) heap space.
     * ============================================================================
     */
    public static class LFUCacheAlternative {
        class Node {
            int key, val, freq, timestamp;
            Node(int k, int v, int t) {
                key = k; val = v; freq = 1; timestamp = t;
            }
        }

        private final int capacity;
        private int time;
        private final Map<Integer, Node> cache;
        private final PriorityQueue<Node> pq;

        public LFUCacheAlternative(int capacity) {
            this.capacity = capacity;
            this.time = 0;
            this.cache = new HashMap<>();
            this.pq = new PriorityQueue<>((a, b) -> a.freq == b.freq ?
                    Integer.compare(a.timestamp, b.timestamp) :
                    Integer.compare(a.freq, b.freq));
        }

        public int get(int key) {
            if (!cache.containsKey(key)) return -1;
            Node node = cache.get(key);
            pq.remove(node); // O(N) operation in standard Java PQ
            node.freq++;
            node.timestamp = ++time;
            pq.offer(node);
            return node.val;
        }

        public void put(int key, int value) {
            if (capacity == 0) return;

            if (cache.containsKey(key)) {
                Node node = cache.get(key);
                pq.remove(node);
                node.val = value;
                node.freq++;
                node.timestamp = ++time;
                pq.offer(node);
            } else {
                if (cache.size() == capacity) {
                    Node evictNode = pq.poll();
                    cache.remove(evictNode.key);
                }
                Node newNode = new Node(key, value, ++time);
                cache.put(key, newNode);
                pq.offer(newNode);
            }
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
        System.out.println("=== LFU Cache Testing Suite ===\n");

        // Helper interface to test all variants uniformly
        interface CacheAdapter {
            int get(int key);
            void put(int key, int value);
        }

        int capacity = 2;

        System.out.println("Running standard LC Example 1...");

        // Strategy to run identical assertions across all implementations
        java.util.function.Consumer<CacheAdapter> runTestScenario = adapter -> {
            adapter.put(1, 1);
            adapter.put(2, 2);
            System.out.println("get(1) -> Expected: 1  | Actual: " + adapter.get(1)); // freq(1)=2, freq(2)=1
            adapter.put(3, 3); // evicts 2 (LFU)
            System.out.println("get(2) -> Expected: -1 | Actual: " + adapter.get(2));
            System.out.println("get(3) -> Expected: 3  | Actual: " + adapter.get(3)); // freq(3)=2, freq(1)=2
            adapter.put(4, 4); // tiebreaker: 1 is LRU, evicts 1
            System.out.println("get(1) -> Expected: -1 | Actual: " + adapter.get(1));
            System.out.println("get(3) -> Expected: 3  | Actual: " + adapter.get(3));
            System.out.println("get(4) -> Expected: 4  | Actual: " + adapter.get(4));
            System.out.println("-------------------------------------------------");
        };

        // 1. Test Optimal Approach
        System.out.println("\n-- Testing Optimal (O(1) Two Maps + DLL) --");
        LFUCacheOptimal optimal = new LFUCacheOptimal(capacity);
        runTestScenario.accept(new CacheAdapter() {
            public int get(int k) { return optimal.get(k); }
            public void put(int k, int v) { optimal.put(k, v); }
        });

        // 2. Test Brute Force Approach
        System.out.println("\n-- Testing Brute Force (O(N) Array List) --");
        LFUCacheBruteForce brute = new LFUCacheBruteForce(capacity);
        runTestScenario.accept(new CacheAdapter() {
            public int get(int k) { return brute.get(k); }
            public void put(int k, int v) { brute.put(k, v); }
        });

        // 3. Test Alternative Approach
        System.out.println("\n-- Testing Alternative (Priority Queue) --");
        LFUCacheAlternative alt = new LFUCacheAlternative(capacity);
        runTestScenario.accept(new CacheAdapter() {
            public int get(int k) { return alt.get(k); }
            public void put(int k, int v) { alt.put(k, v); }
        });

        // Outputting Zero Capacity Edge Case Check using Java Streams purely for visual separation
        System.out.println(java.util.stream.Stream.generate(() -> "=").limit(50).collect(Collectors.joining()));
        System.out.println("Testing Zero Capacity Edge Case (Optimal):");
        LFUCacheOptimal zeroCache = new LFUCacheOptimal(0);
        zeroCache.put(1, 1);
        System.out.println("get(1) on 0 capacity -> Expected: -1 | Actual: " + zeroCache.get(1));
    }
}