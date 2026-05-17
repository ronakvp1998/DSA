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
 * Problem Statement: Design and implement a Max Heap data structure from scratch.
 * The class must support insertion, peeking at the maximum element, extracting
 * the maximum element, and building a heap from an unsorted array in optimal time.
 * * Constraints:
 * - 1 <= values <= 10^9
 * - Up to 10^5 calls will be made to insert, pop, and peek.
 * * Input/Output Format:
 * Example 1:
 * Input: insert(100), insert(200), insert(50), pop()
 * Output: Returns 200. Array state becomes [100, 50].
 * * Example 2:
 * Input: buildHeap([3, 1, 4, 1, 5, 9]), pop()
 * Output: Returns 9. Array state restructures to maintain max heap property.
 * * Conceptual Visualization (Tree to Array mapping):
 * For any node at index `i`:
 * - Left Child is at `2i + 1`
 * - Right Child is at `2i + 2`
 * - Parent is at `(i - 1) / 2`
 * * Array State: [200, 100, 50]
 * 200 (idx 0)
 * /   \
 * 100   50
 * (idx 1)  (idx 2)
 * * ============================================================================
 * 2. PROGRESSIVE IMPLEMENTATION ROADMAP
 * -------------------------------------
 * Phase 1: Brute Force Approach - Using an unsorted ArrayList.
 * - Insert is O(1), but finding/removing the max requires O(N) scan.
 * Phase 2: Optimal Approach - Binary Max Heap (Implemented Below).
 * - Uses a complete binary tree represented as an array to achieve 
 * O(log N) insertions and deletions.
 * ============================================================================
 */
public class MaxHeap {

    private final List<Integer> heap;

    public MaxHeap() {
        heap = new ArrayList<>();
    }

    /**
     * Constructor for O(N) Heap Construction (Phase 2 Optimal)
     * --------------------------------------------------------
     * Intuition: Instead of inserting N elements one by one (which takes O(N log N)),
     * we can dump all elements into the array and call `heapify` on all non-leaf 
     * nodes from bottom to top. Half the nodes in a complete binary tree are leaves, 
     * so we start at (N/2) - 1.
     * * Complexity:
     * - Time: O(N) - Mathematical convergence of sum(h / 2^h).
     * - Space: O(N) - Heap space to store the elements.
     */
    public MaxHeap(int[] nums) {
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
     * Intuition: The maximum element in a Max Heap is always at the root (index 0).
     * * Complexity:
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
     * Phase 2: Pop (Extract Max)
     * --------------------------
     * Intuition: We cannot simply remove index 0, as it splits the tree. 
     * Instead, we swap the root with the last element in the array, remove the 
     * last element (O(1) operation), and then "sift down" the new root to its 
     * correct position to restore the Max Heap property.
     * * Complexity:
     * - Time: O(log N) - Height of the tree.
     * - Space: O(1) auxiliary space.
     */
    public int pop() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty. Cannot pop.");
        }

        int maxValue = heap.get(0);
        int lastIdx = heap.size() - 1;

        // Swap root with the last element
        heap.set(0, heap.get(lastIdx));
        heap.remove(lastIdx);

        // Restore heap property if elements still exist
        if (!heap.isEmpty()) {
            heapifyDown(0);
        }

        return maxValue;
    }

    /**
     * Phase 2: Insert
     * ---------------
     * Intuition: Add the new element to the very end of the array to maintain 
     * the "complete tree" property. Then, "sift up" by comparing it with its 
     * parent and swapping until it's no longer greater than its parent.
     * * Complexity:
     * - Time: O(log N) - Height of the tree in the worst case.
     * - Space: O(1) auxiliary space. (Amortized O(1) for ArrayList expansion).
     */
    public void insert(int value) {
        heap.add(value);
        int index = heap.size() - 1;

        // Sift Up Iteratively
        while (index > 0) {
            int parentIdx = (index - 1) / 2;
            if (heap.get(parentIdx) < heap.get(index)) {
                Collections.swap(heap, parentIdx, index);
                index = parentIdx;
            } else {
                break; // Max heap property satisfied
            }
        }
    }

    /**
     * Phase 2: Heapify Down (Sift Down)
     * ---------------------------------
     * Intuition: Compares a node against its children. If a child is larger,
     * swap the node with the LARGEST of its children. Repeat this process down 
     * the tree until the node is larger than both children or it becomes a leaf.
     * * Complexity:
     * - Time: O(log N)
     * - Space: O(1) auxiliary space (using an iterative approach rather than recursion stack).
     */
    private void heapifyDown(int index) {
        int size = heap.size();

        while (index < size) {
            int largest = index; // CRITICAL FIX: Must reset 'largest' on every loop iteration
            int leftChildIdx = 2 * index + 1;
            int rightChildIdx = 2 * index + 2;

            if (leftChildIdx < size && heap.get(leftChildIdx) > heap.get(largest)) {
                largest = leftChildIdx;
            }
            if (rightChildIdx < size && heap.get(rightChildIdx) > heap.get(largest)) {
                largest = rightChildIdx;
            }

            if (largest != index) {
                Collections.swap(heap, index, largest);
                index = largest; // Move down the tree to continue inspecting
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
        MaxHeap maxHeap = new MaxHeap();
        maxHeap.insert(100);
        maxHeap.insert(200);
        maxHeap.insert(50);

        System.out.print("Heap state after inserts: ");
        maxHeap.print(); // Expected: [200, 100, 50]

        System.out.println("Peek Max: " + maxHeap.peek()); // Expected: 200
        System.out.println("Popped Max: " + maxHeap.pop()); // Expected: 200

        System.out.print("Heap state after pop: ");
        maxHeap.print(); // Expected: [100, 50]

        System.out.println("\n--- Test 2: O(N) Array Heapification ---");
        int[] rawArray = {3, 1, 4, 1, 5, 9, 2, 6};
        MaxHeap arrayHeap = new MaxHeap(rawArray);
        System.out.print("O(N) Heapified Array: ");
        arrayHeap.print(); // Expected: [9, 6, 4, 1, 5, 3, 2, 1] (or similar valid Max Heap)

        System.out.println("Popped Max: " + arrayHeap.pop()); // Expected: 9
    }
}