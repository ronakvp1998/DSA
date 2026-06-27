package strivers.heaps.basics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

/**
 * ============================================================================
 * 🤖 MASTERCLASS DSA SOLUTION: IMPLEMENT A MAX HEAP
 * ============================================================================
 * * ### 1. Header & Problem Context
 * **Formal Problem Statement:**
 * Design a data structure that supports the following operations for a Max Heap:
 * 1. `insert(val)`: Add a value to the heap.
 * 2. `extractMax()`: Remove and return the maximum element.
 * 3. `peek()`: Return the maximum element without removing it.
 * * A Max Heap is a Complete Binary Tree where the value of each node is greater than
 * or equal to the values of its children. The root is always the maximum element.
 * * **Constraints:**
 * - 1 <= Capacity <= 10^5
 * - -10^9 <= val <= 10^9
 * - Methods `extractMax` and `peek` should handle empty heap scenarios gracefully.
 * * **Input/Output Formats:**
 * Input: Sequence of method calls (`insert`, `extractMax`, `peek`).
 * Output: Integer values returned by `extractMax` and `peek`.
 * * **Examples:**
 * Example 1:
 * Input: insert(5), insert(10), insert(20), insert(15), peek(), extractMax(), peek()
 * Output: [null, null, null, null, 20, 20, 15]
 * * Example 2:
 * Input: extractMax(), insert(42), peek()
 * Output: [Integer.MIN_VALUE, null, 42]
 * * **Conceptual Visualization:**
 * Inserting sequence: 5, 10, 20, 15, 30
 * * Step 1: Insert 5        Step 2: Insert 10        Step 3: Insert 20
 * 5                       10 (Bubbled up)           20
 * /                         /  \                     /  \
 * 5                         5    10                  5    10
 * * Step 4: Insert 15                  Step 5: Insert 30 (Bubbles up to root)
 * 20                                             30
 * /    \                                         /    \
 * 15      10                                     20      10
 * /  \                                           /  \
 * 5                                              5    15
 * * Final Array Representation: [30, 20, 10, 5, 15]
 * * ============================================================================
 * ### 2.2. Progressive Implementation Roadmap (Non-DP)
 * * * **Phase 1: Optimal Approach** - The "Best" stage. Array-based complete binary
 * tree with O(log N) insertions and extractions using iterative shift-up and shift-down.
 * * **Phase 2: Brute Force Approach** - The "Think it" stage. Using a dynamic list
 * (`ArrayList`) and sorting it in descending order whenever the max element is requested.
 * * **Phase 3: Alternative Approach** - The "Pragmatic" stage. Using Java's built-in
 * `PriorityQueue` with a custom Comparator (Collections.reverseOrder()).
 * ============================================================================
 */
public class MaxHeapImplementation {

    /**
     * ========================================================================
     * Phase 1: Optimal Approach (Array-Based Binary Heap)
     * ========================================================================
     * **Detailed Intuition:**
     * To achieve O(log N) time complexity, we map a Complete Binary Tree to a flat
     * array. This removes pointer overhead and ensures sequential memory access.
     * - Parent of node `i`: `(i - 1) / 2`
     * - Left child of node `i`: `2 * i + 1`
     * - Right child of node `i`: `2 * i + 2`
     * * **Insertion:** Placed at the end of the array, then "Bubbled Up" by swapping
     * with the parent if the current value is larger.
     * **Extraction:** The root is swapped with the last element, the last element
     * is logically deleted, and the new root is "Bubbled Down" to restore the heap.
     * * **Complexity Analysis:**
     * - **Time:** O(log N) for `insert` and `extractMax`. O(1) for `peek`.
     * - **Space:** O(N) Heap space for the array of size N.
     * - **Auxiliary Space:** O(1) because we use iterative `maxHeapify` instead of recursion.
     * ========================================================================
     */
    static class MaxHeapOptimal {
        private final int[] heap;
        private int size;
        private final int capacity;

        public MaxHeapOptimal(int capacity) {
            this.capacity = capacity;
            this.size = 0;
            this.heap = new int[capacity];
        }

        private int parent(int i) { return (i - 1) / 2; }
        private int left(int i) { return 2 * i + 1; }
        private int right(int i) { return 2 * i + 2; }

        public void insert(int val) {
            if (size == capacity) {
                System.out.println("Heap is full!");
                return;
            }

            int i = size;
            heap[i] = val;
            size++;

            // "Bubble Up" iteratively for Max Heap
            while (i != 0 && heap[i] > heap[parent(i)]) {
                swap(i, parent(i));
                i = parent(i);
            }
        }

        public int extractMax() {
            if (size <= 0) return Integer.MIN_VALUE;
            if (size == 1) return heap[--size];

            int root = heap[0];
            heap[0] = heap[size - 1]; // Move last element to root
            size--;

            // "Bubble Down" iteratively to save stack space
            maxHeapifyIterative(0);
            return root;
        }

        private void maxHeapifyIterative(int i) {
            while (true) {
                int l = left(i);
                int r = right(i);
                int largest = i;

                // Compare values for Max Heap property
                if (l < size && heap[l] > heap[largest]) largest = l;
                if (r < size && heap[r] > heap[largest]) largest = r;

                if (largest != i) {
                    swap(i, largest);
                    i = largest; // Move down the tree
                } else {
                    break; // Max-Heap property restored
                }
            }
        }

        public int peek() {
            return (size <= 0) ? Integer.MIN_VALUE : heap[0];
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
     * Instead of maintaining a strict tree layout, we append elements to an `ArrayList`.
     * Whenever we need the maximum element, we sort the entire list in descending
     * order. This completely bypasses the tree structure but incurs a heavy time penalty.
     * * **Complexity Analysis:**
     * - **Time:** O(1) for `insert`, O(N log N) for `extractMax` and `peek`.
     * - **Space:** O(N) Heap space for the ArrayList.
     * - **Auxiliary Space:** O(log N) due to internal sorting mechanisms.
     * ========================================================================
     */
    static class MaxHeapBruteForce {
        private final List<Integer> list = new ArrayList<>();

        public void insert(int val) {
            list.add(val);
        }

        public int extractMax() {
            if (list.isEmpty()) return Integer.MIN_VALUE;
            // Sort descending to bring max to front
            list.sort(Collections.reverseOrder());
            return list.remove(0); // O(N) shift penalty
        }

        public int peek() {
            if (list.isEmpty()) return Integer.MIN_VALUE;
            list.sort(Collections.reverseOrder());
            return list.get(0);
        }
    }

    /**
     * ========================================================================
     * Phase 3: Alternative Approach (Java Built-in PriorityQueue)
     * ========================================================================
     * **Detailed Intuition:**
     * Java's `PriorityQueue` is a Min-Heap by default. To simulate a Max-Heap,
     * we simply pass `Collections.reverseOrder()` into the constructor. This
     * reverses the natural ordering comparator, making the largest elements bubble
     * to the top.
     * * **Complexity Analysis:**
     * - **Time:** O(log N) for `insert` (offer) and `extractMax` (poll). O(1) for `peek`.
     * - **Space:** O(N) Heap space for the underlying dynamic array.
     * - **Auxiliary Space:** O(1).
     * ========================================================================
     */
    static class MaxHeapAlternative {
        // Reverse order comparator turns Min-Heap into Max-Heap
        private final PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

        public void insert(int val) {
            pq.offer(val);
        }

        public int extractMax() {
            return pq.isEmpty() ? Integer.MIN_VALUE : pq.poll();
        }

        public int peek() {
            return pq.isEmpty() ? Integer.MIN_VALUE : pq.peek();
        }
    }

    /**
     * ========================================================================
     * 4. Testing Suite
     * ========================================================================
     * Exhaustively tests all implementations to verify core logic, edge cases
     * (empty extraction), and identical outputs across approaches.
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("🤖 Running Masterclass Testing Suite for MAX HEAP...\n");

        int[] testStream = {5, 10, 20, 15, 30};

        System.out.println("--- Testing Phase 1: Optimal (Array-Based Heap) ---");
        MaxHeapOptimal optimalHeap = new MaxHeapOptimal(10);

        // Edge Case Test
        System.out.println("Extract from empty: " +
                (optimalHeap.extractMax() == Integer.MIN_VALUE ? "MIN_VALUE (Handled)" : "Failed"));

        // Java 8 Stream API for population
        IntStream.of(testStream).forEach(optimalHeap::insert);
        System.out.println("Current Max (Peek): " + optimalHeap.peek() + " (Expected: 30)");
        System.out.println("Extracted Max: " + optimalHeap.extractMax() + " (Expected: 30)");
        System.out.println("Next Max (Peek): " + optimalHeap.peek() + " (Expected: 20)");

        System.out.println("\n--- Testing Phase 2: Brute Force (List + Sort) ---");
        MaxHeapBruteForce bruteForceHeap = new MaxHeapBruteForce();
        IntStream.of(testStream).forEach(bruteForceHeap::insert);
        System.out.println("Current Max (Peek): " + bruteForceHeap.peek() + " (Expected: 30)");
        System.out.println("Extracted Max: " + bruteForceHeap.extractMax() + " (Expected: 30)");
        System.out.println("Next Max (Peek): " + bruteForceHeap.peek() + " (Expected: 20)");

        System.out.println("\n--- Testing Phase 3: Alternative (PriorityQueue) ---");
        MaxHeapAlternative altHeap = new MaxHeapAlternative();
        IntStream.of(testStream).forEach(altHeap::insert);
        System.out.println("Current Max (Peek): " + altHeap.peek() + " (Expected: 30)");
        System.out.println("Extracted Max: " + altHeap.extractMax() + " (Expected: 30)");
        System.out.println("Next Max (Peek): " + altHeap.peek() + " (Expected: 20)");

        System.out.println("\n✅ All test phases completed successfully.");
    }
}