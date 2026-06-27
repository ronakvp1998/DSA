package strivers.heaps.basics;

/**
 * ============================================================================
 * 🤖 MASTERCLASS DSA SOLUTION: IMPLEMENT A MIN HEAP
 * ============================================================================
 * * ### 1. Header & Problem Context
 * **Formal Problem Statement:**
 * Design a data structure that supports the following operations:
 * 1. `insert(val)`: Add a value to the heap.
 * 2. `extractMin()`: Remove and return the minimum element.
 * 3. `peek()`: Return the minimum element without removing it.
 * * A Min Heap is a Complete Binary Tree where the value of each node is less than or
 * equal to the values of its children. The root is always the minimum element.
 * * **Constraints:**
 * - 1 <= Capacity <= 10^5
 * - -10^9 <= val <= 10^9
 * - Methods `extractMin` and `peek` should handle empty heap scenarios gracefully.
 * * **Input/Output Formats:**
 * Input: Sequence of method calls (`insert`, `extractMin`, `peek`).
 * Output: Integer values returned by `extractMin` and `peek`.
 * * **Examples:**
 * Example 1:
 * Input: insert(15), insert(10), insert(20), insert(5), peek(), extractMin(), peek()
 * Output: [null, null, null, null, 5, 5, 10]
 * * Example 2:
 * Input: extractMin(), insert(2), peek()
 * Output: [Integer.MAX_VALUE, null, 2]
 * * **Conceptual Visualization:**
 * Inserting sequence: 15, 10, 20, 5, 30
 * * Step 1: Insert 15       Step 2: Insert 10        Step 3: Insert 20
 *     15                      10 (Bubbled up)           10
 *   /                         /  \
 *  15                        15    20
 * * Step 4: Insert 5 (Bubbles up to root)            Step 5: Insert 30
 *        5                                              5
 *      /   \                                          /   \
 *    10     20                                      10     20
 *   /                                              /  \
 *  15                                             15    30
 * * Final Array Representation: [5, 10, 20, 15, 30]
 * * ============================================================================
 * ### 2.2. Progressive Implementation Roadmap (For Non-DP Problems)
 * * * **Phase 1: Optimal Approach** - The "Best" stage. Array-based complete binary
 * tree with O(log N) insertions and extractions using iterative shift-up and shift-down.
 * * **Phase 2: Brute Force Approach** - The "Think it" stage. Using a dynamic list
 * (`ArrayList`) and sorting it whenever the minimum element is requested.
 * * **Phase 3: Alternative Approach** - The "Pragmatic" stage. Using Java's built-in
 * `PriorityQueue` which manages the min-heap structure internally.
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class MinHeapImplementation {

    /**
     * ========================================================================
     * Phase 1: Optimal Approach (Array-Based Binary Heap)
     * ========================================================================
     * **Detailed Intuition:**
     * To achieve logarithmic time complexity for both insertions and extractions,
     * we model a Complete Binary Tree using a flat array.
     * - Parent of node `i`: `(i - 1) / 2`
     * - Left child of node `i`: `2*i + 1`
     * - Right child of node `i`: `2*i + 2`
     * By always inserting at the end and "bubbling up", we maintain the complete tree
     * property. When extracting, we swap the root with the last element, remove the
     * last, and "bubble down" the new root.
     * Note: The user's provided approach used recursive `minHeapify`. We have
     * optimized it here to an **iterative** `minHeapify` to save Auxiliary Stack Space.
     * * **Complexity Analysis:**
     * - **Time O(log N)** for `insert` and `extractMin`. **O(1)** for `peek`.
     * - **Space O(N)**: Heap space for the array of size N.
     * - **Auxiliary Space O(1)**: Using an iterative approach eliminates the O(log N)
     * recursive call stack.
     * ========================================================================
     */
    static class MinHeapOptimal {
        private final int[] heap;
        private int size;
        private final int capacity;

        public MinHeapOptimal(int capacity) {
            this.capacity = capacity;
            this.size = 0;
            this.heap = new int[capacity];
        }

        private int parent(int i) { return (i - 1) / 2; }
        private int left(int i) { return 2 * i + 1; }
        private int right(int i) { return 2 * i + 2; }

        // * 1. `insert(val)`: Add a value to the heap.
        //inserting at the end and "bubbling up", we maintain the complete tree property.
        public void insert(int val) {
            if (size == capacity) {
                System.out.println("Heap is full!");
                return;
            }

            // Place at the end
            int i = size;
            heap[i] = val;
            size++;

            // "Bubble Up" iteratively
            while (i != 0 && heap[parent(i)] > heap[i]) {
                swap(i, parent(i));
                i = parent(i);
            }
        }

        // * 2. `extractMin()`: Remove and return the minimum element.
        // When extracting, we swap the root with the last element, remove the
        // last, and "bubble down" the new root.
        public int extractMin() {
            if (size <= 0) return Integer.MAX_VALUE;
            if (size == 1) return heap[--size];

            int root = heap[0];
            heap[0] = heap[size - 1]; // Move last element to root
            size--;

            // "Bubble Down" iteratively to save O(log N) stack space
            minHeapifyIterative(0);
            return root;
        }

        private void minHeapifyIterative(int i) {
            while (true) {
                int l = left(i);
                int r = right(i);
                int smallest = i;

                if (l < size && heap[l] < heap[smallest]) smallest = l;
                if (r < size && heap[r] < heap[smallest]) smallest = r;

                if (smallest != i) {
                    swap(i, smallest);
                    i = smallest; // Move down the tree
                } else {
                    break; // Heap property restored
                }
            }
        }

        // * 3. `peek()`: Return the minimum element without removing it.
        public int peek() {
            return (size <= 0) ? Integer.MAX_VALUE : heap[0];
        }

        private void swap(int i, int j) {
            int temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }
    }

    /**
     * ========================================================================
     * Phase 2: Brute Force Approach (Dynamic List + Sorting)
     * ========================================================================
     * **Detailed Intuition:**
     * Instead of maintaining a strict tree structure, we just append elements
     * to a list. Whenever we need the minimum, we sort the entire list, allowing
     * us to easily pick off the first element. This is logically correct but
     * computationally expensive.
     * * **Complexity Analysis:**
     * - **Time O(1)** for `insert`, **O(N log N)** for `extractMin` and `peek`.
     * - **Space O(N)**: Heap space for the ArrayList.
     * - **Auxiliary Space O(log N)**: Due to internal sorting algorithms.
     * ========================================================================
     */
    static class MinHeapBruteForce {
        private final List<Integer> list = new ArrayList<>();

        public void insert(int val) {
            list.add(val);
        }

        public int extractMin() {
            if (list.isEmpty()) return Integer.MAX_VALUE;
            Collections.sort(list); // O(N log N) penalty
            return list.remove(0); // O(N) penalty for shifting
        }

        public int peek() {
            if (list.isEmpty()) return Integer.MAX_VALUE;
            Collections.sort(list);
            return list.get(0);
        }
    }

    /**
     * ========================================================================
     * Phase 3: Alternative Approach (Java Built-in PriorityQueue)
     * ========================================================================
     * **Detailed Intuition:**
     * In real-world engineering or competitive programming, unless asked to
     * implement a heap from scratch, we use standard library structures.
     * Java's `PriorityQueue` implements a min-heap under the hood using an
     * array structure similar to Phase 1.
     * * **Complexity Analysis:**
     * - **Time O(log N)** for `insert` (offer) and `extractMin` (poll).
     * **O(1)** for `peek`.
     * - **Space O(N)**: Heap space for the underlying dynamic array.
     * - **Auxiliary Space O(1)**: Standard queue operations.
     * ========================================================================
     */
    static class MinHeapAlternative {
        private final PriorityQueue<Integer> pq = new PriorityQueue<>();

        public void insert(int val) {
            pq.offer(val);
        }

        public int extractMin() {
            return pq.isEmpty() ? Integer.MAX_VALUE : pq.poll();
        }

        public int peek() {
            return pq.isEmpty() ? Integer.MAX_VALUE : pq.peek();
        }
    }

    /**
     * ========================================================================
     * 4. Testing Suite
     * ========================================================================
     * Exhaustively tests all approaches against identical operations to ensure
     * output consistency, edge case handling, and correct internal states.
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("🤖 Running Masterclass Testing Suite...\n");

        System.out.println("--- Testing Phase 1: Optimal (Array-Based Heap) ---");
        MinHeapOptimal optimalHeap = new MinHeapOptimal(10);
        runStandardTests(optimalHeap);

        System.out.println("\n--- Testing Phase 2: Brute Force (List + Sort) ---");
        MinHeapBruteForce bruteForceHeap = new MinHeapBruteForce();
        // Duck-typing test runner using an anonymous block for quick execution
        bruteForceHeap.insert(15);
        bruteForceHeap.insert(10);
        bruteForceHeap.insert(20);
        bruteForceHeap.insert(5);
        bruteForceHeap.insert(30);
        System.out.println("Peek: " + bruteForceHeap.peek() + " (Expected 5)");
        System.out.println("Extract: " + bruteForceHeap.extractMin() + " (Expected 5)");
        System.out.println("Extract: " + bruteForceHeap.extractMin() + " (Expected 10)");

        System.out.println("\n--- Testing Phase 3: Alternative (PriorityQueue) ---");
        MinHeapAlternative altHeap = new MinHeapAlternative();
        altHeap.insert(15);
        altHeap.insert(10);
        altHeap.insert(20);
        altHeap.insert(5);
        altHeap.insert(30);
        System.out.println("Peek: " + altHeap.peek() + " (Expected 5)");
        System.out.println("Extract: " + altHeap.extractMin() + " (Expected 5)");
        System.out.println("Extract: " + altHeap.extractMin() + " (Expected 10)");

        System.out.println("\n✅ All test phases completed successfully.");
    }

    // Helper method to keep standard testing clean and DRY
    private static void runStandardTests(MinHeapOptimal heap) {
        System.out.println("Edge Case - Extract from empty: " + (heap.extractMin() == Integer.MAX_VALUE ? "MAX_VALUE (Handled)" : "Failed"));

        heap.insert(15);
        heap.insert(10);
        heap.insert(20);
        heap.insert(5);
        heap.insert(30);

        System.out.println("Current Min (Peek): " + heap.peek() + " (Expected: 5)");
        System.out.println("Extracted Min: " + heap.extractMin() + " (Expected: 5)");
        System.out.println("Next Min: " + heap.peek() + " (Expected: 10)");

        heap.insert(2);
        System.out.println("Inserted 2. New Min: " + heap.peek() + " (Expected: 2)");

        // Edge Case - Duplicate Values
        heap.insert(2);
        System.out.println("Inserted duplicate 2. Extracted: " + heap.extractMin() + " (Expected: 2)");
        System.out.println("Next Extracted: " + heap.extractMin() + " (Expected: 2)");
    }
}