package com.questions.ctobhaiya.heaps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ============================================================================
 * ROLE: Senior DSA Interviewer & Competitive Programming Evaluator
 * ============================================================================
 * * 1. HEADER & PROBLEM CONTEXT
 * ---------------------------
 * Problem Statement: Design and implement a Min Heap data structure from scratch.
 * The class must support insertion, peeking at the minimum element, extracting
 * the minimum element, and building a heap from an unsorted array in optimal time.
 * * Constraints:
 * - 1 <= values <= 10^9
 * - Up to 10^5 calls will be made to insert, pop, and peek.
 * * Input/Output Format:
 * Example 1:
 * Input: insert(100), insert(50), insert(200), pop()
 * Output: Returns 50. Array state becomes [100, 200].
 * * Example 2:
 * Input: buildHeap([9, 6, 4, 1, 5, 3]), pop()
 * Output: Returns 1. Array state restructures to maintain min heap property.
 * * Conceptual Visualization (Tree to Array mapping):
 * For any node at index `i`:
 * - Left Child is at `2i + 1`
 * - Right Child is at `2i + 2`
 * - Parent is at `(i - 1) / 2`
 * * Array State: [50, 100, 200]
 * 50 (idx 0)
 * /   \
 * 100   200
 * (idx 1)  (idx 2)
 * * ============================================================================
 * 2. PROGRESSIVE IMPLEMENTATION ROADMAP
 * -------------------------------------
 * Phase 1: Brute Force Approach
 * - Use an unsorted ArrayList. Insert is O(1), but finding/removing the min
 * requires an O(N) linear scan.
 * * Phase 2: Optimal Approach - Binary Min Heap (Implemented Below).
 * - Uses a complete binary tree represented as an array to achieve
 * O(log N) insertions and deletions, and O(1) minimum retrieval.
 * ============================================================================
 */
public class MinHeap {

    private final List<Integer> heap;

    public MinHeap() {
        heap = new ArrayList<>();
    }

    /**
     * Constructor for O(N) Heap Construction (Phase 2 Optimal)
     * --------------------------------------------------------
     * Intuition: Instead of inserting N elements one by one (O(N log N)),
     * dump all elements into the array and call `heapifyDown` on all non-leaf
     * nodes from bottom to top. Since half the nodes are leaves, we start
     * at (N/2) - 1 and work backward to index 0.
     * * Complexity Analysis:
     * - Time: O(N) - Based on the Taylor series expansion; most nodes are near
     * the bottom and do very little work.
     * - Space: O(N) - To store the elements in the heap.
     */
    public MinHeap(int[] nums) {
        heap = new ArrayList<>();
        for (int num : nums) {
            heap.add(num);
        }
        // Start from the last non-leaf node and sift down
        for (int i = (heap.size() / 2) - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    /**
     * Phase 2: Peek
     * -------------
     * Intuition: The minimum element in a Min Heap is always at the root (index 0).
     * * Complexity Analysis:
     * - Time: O(1)
     * - Space: O(1)
     */
    public int peek() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty. Cannot peek.");
        }
        return heap.get(0);
    }

    /**
     * Phase 2: Pop (Extract Min)
     * --------------------------
     * Intuition: We cannot simply remove index 0, as it splits the tree into two.
     * Instead, swap the root with the last element, remove the last element (O(1)),
     * and then "sift down" the new root to its correct position to restore the
     * Min Heap property.
     * * Complexity Analysis:
     * - Time: O(log N) - Height of the tree.
     * - Space: O(1) auxiliary space.
     */
    public int pop() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty. Cannot pop.");
        }

        int minValue = heap.get(0);
        int lastIdx = heap.size() - 1;

        // Swap root with the last element
        heap.set(0, heap.get(lastIdx));
        heap.remove(lastIdx);

        // Restore heap property if elements still exist
        if (!heap.isEmpty()) {
            heapifyDown(0);
        }

        return minValue;
    }

    /**
     * Phase 2: Insert
     * ---------------
     * Intuition: Add the new element to the very end of the array to maintain
     * the "complete tree" shape. Then, "sift up" by comparing it with its parent
     * and swapping if the newly inserted node is SMALLER than its parent.
     * * Complexity Analysis:
     * - Time: O(log N) - Height of the tree in the worst case.
     * - Space: O(1) auxiliary space (amortized O(1) for ArrayList expansion).
     */
    public void insert(int value) {
        heap.add(value);
        int index = heap.size() - 1;

        // Sift Up Iteratively
        while (index > 0) {
            int parentIdx = (index - 1) / 2;

            // For Min Heap: If parent is GREATER than current node, swap them.
            if (heap.get(parentIdx) > heap.get(index)) {
                Collections.swap(heap, parentIdx, index);
                index = parentIdx;
            } else {
                break; // Min heap property satisfied
            }
        }
    }

    /**
     * Phase 2: Heapify Down (Sift Down)
     * ---------------------------------
     * Intuition: Compares a node against its children. If a child is smaller,
     * swap the node with the SMALLEST of its children. Repeat this process down
     * the tree until the node is smaller than both children or it becomes a leaf.
     * * Complexity Analysis:
     * - Time: O(log N)
     * - Space: O(1) auxiliary space (iterative approach prevents call stack overhead).
     */
    private void heapifyDown(int index) {
        int size = heap.size();

        while (index < size) {
            int smallest = index; // Tracks the index of the minimum value
            int leftChildIdx = 2 * index + 1;
            int rightChildIdx = 2 * index + 2;

            // Check if left child exists and is smaller than current smallest
            if (leftChildIdx < size && heap.get(leftChildIdx) < heap.get(smallest)) {
                smallest = leftChildIdx;
            }
            // Check if right child exists and is smaller than current smallest
            if (rightChildIdx < size && heap.get(rightChildIdx) < heap.get(smallest)) {
                smallest = rightChildIdx;
            }

            // If the smallest is not the current node, swap and continue sifting down
            if (smallest != index) {
                Collections.swap(heap, index, smallest);
                index = smallest;
            } else {
                break; // The subtree is correctly heapified
            }
        }
    }

    /**
     * Helper Method: Print Heap State
     */
    public void print() {
        System.out.println(heap);
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     */
    public static void main(String[] args) {
        System.out.println("--- Test 1: Standard Insertions and Pop ---");
        MinHeap minHeap = new MinHeap();
        minHeap.insert(100);
        minHeap.insert(50);
        minHeap.insert(200);
        minHeap.insert(10);

        System.out.print("Heap state after inserts: ");
        minHeap.print(); // Expected: [10, 50, 200, 100] (or similar valid Min Heap)

        System.out.println("Peek Min: " + minHeap.peek()); // Expected: 10
        System.out.println("Popped Min: " + minHeap.pop()); // Expected: 10

        System.out.print("Heap state after pop: ");
        minHeap.print(); // Expected: [50, 100, 200]

        System.out.println("\n--- Test 2: O(N) Array Heapification ---");
        int[] rawArray = {9, 6, 4, 1, 5, 3, 2, 8};
        MinHeap arrayHeap = new MinHeap(rawArray);

        System.out.print("O(N) Heapified Array: ");
        arrayHeap.print(); // Expected: [1, 5, 2, 6, 9, 3, 4, 8] (or similar valid Min Heap)

        System.out.println("Popped Min: " + arrayHeap.pop()); // Expected: 1
        System.out.println("Next Min is now: " + arrayHeap.peek()); // Expected: 2
    }
}