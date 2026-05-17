package com.questions.strivers.heaps.basics;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: Implement a Min Heap
 * ==================================================================================================
 * Design a data structure that supports the following operations:
 * 1. insert(val): Add a value to the heap.
 * 2. extractMin(): Remove and return the minimum element.
 * 3. peek(): Return the minimum element without removing it.
 *
 * A Min Heap is a Complete Binary Tree where the value of each node is less than or
 * equal to the values of its children. The root is always the minimum element.
 * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (List + Manual Sorting)
 * ==================================================================================================
 * Use a simple dynamic list.
 * - Insert: Add to the end of the list.
 * - extractMin: Sort the entire list, remove the first element, and return it.
 * Drawback: Sorting every time we extract takes O(N log N), making it very inefficient.
 * ==================================================================================================
 * APPROACH 2: OPTIMAL (Heap / Priority Queue Logic)
 * ==================================================================================================
 * Use an array-based Binary Heap.
 * - Array Mapping:
 * Parent(i) = (i - 1) / 2
 * LeftChild(i) = 2*i + 1
 * RightChild(i) = 2*i + 2
 * - insert: Add to the end and "Bubble Up" (Shift Up) to maintain heap property.
 * - extractMin: Swap root with the last element, remove last, and "Bubble Down" (Shift Down).
 * ==================================================================================================
 */

public class MinHeapImplementation {

    // ----------------------------------------------------------------------
    // MAIN METHOD FOR TESTING
    // ----------------------------------------------------------------------
    public static void main(String[] args) {
        MinHeap heap = new MinHeap(10);

        System.out.println("Inserting: 15, 10, 20, 5, 30");
        heap.insert(15);
        heap.insert(10);
        heap.insert(20);
        heap.insert(5);
        heap.insert(30);

        System.out.println("Current Min (Peek): " + heap.peek()); // Expected: 5

        System.out.println("Extracted Min: " + heap.extractMin()); // Expected: 5
        System.out.println("Next Min: " + heap.peek());          // Expected: 10

        heap.insert(2);
        System.out.println("Inserted 2. New Min: " + heap.peek()); // Expected: 2
    }

    // ----------------------------------------------------------------------
    // OPTIMAL HEAP CLASS
    // ----------------------------------------------------------------------
    static class MinHeap {
        private int[] heap;
        private int size;
        private int capacity;

        public MinHeap(int capacity) {
            this.capacity = capacity;
            this.size = 0;
            this.heap = new int[capacity];
        }

        // Helper: Get Parent Index
        private int parent(int i) { return (i - 1) / 2; }
        // Helper: Get Left Child Index
        private int left(int i) { return 2 * i + 1; }
        // Helper: Get Right Child Index
        private int right(int i) { return 2 * i + 2; }

        /**
         * Inserts a value into the heap.
         * Time Complexity: O(log N)
         */
        public void insert(int val) {
            if (size == capacity) {
                System.out.println("Heap is full!");
                return;
            }

            // 1. Place the new value at the end of the array
            size++;
            int i = size - 1;
            heap[i] = val;

            // 2. "Bubble Up": Fix the min-heap property by comparing with parent
            // While we are not the root and our value is smaller than our parent's
            while (i != 0 && heap[parent(i)] > heap[i]) {
                swap(i, parent(i));
                i = parent(i); // Move up to the parent's index
            }
        }

        /**
         * Returns and removes the minimum element (the root).
         * Time Complexity: O(log N)
         */
        public int extractMin() {
            if (size <= 0) return Integer.MAX_VALUE;
            if (size == 1) {
                size--;
                return heap[0];
            }

            // 1. Store the root value (minimum)
            int root = heap[0];

            // 2. Move the last element to the root
            heap[0] = heap[size - 1];
            size--;

            // 3. "Bubble Down" (Min-Heapify): Fix the heap property from the root
            minHeapify(0);

            return root;
        }

        /**
         * Recursive helper to maintain Min-Heap property.
         */
        private void minHeapify(int i) {
            int l = left(i);
            int r = right(i);
            int smallest = i;

            // Check if left child is smaller than current smallest
            if (l < size && heap[l] < heap[smallest]) {
                smallest = l;
            }
            // Check if right child is smaller than current smallest
            if (r < size && heap[r] < heap[smallest]) {
                smallest = r;
            }

            // If the smallest is not the current node, swap and recurse
            if (smallest != i) {
                swap(i, smallest);
                minHeapify(smallest);
            }
        }

        public int peek() {
            return (size <= 0) ? Integer.MAX_VALUE : heap[0];
        }

        private void swap(int i, int j) {
            int temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }
    }
}