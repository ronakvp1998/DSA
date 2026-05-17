package com.questions.strivers.heaps.basics;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: Convert Min Heap to Max Heap
 * ==================================================================================================
 * Given an array of integers representing a Min Heap, convert it into a Max Heap in-place.
 * A Max Heap is a complete binary tree where every parent node is greater than
 * or equal to its child nodes (nums[parent] >= nums[child]).
 *
 * Example:
 * Input: [3, 4, 8, 9, 10] (A valid Min Heap)
 * Output: [10, 9, 8, 4, 3] (A valid Max Heap)
 * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (New Heap Construction)
 * ==================================================================================================
 * Create a brand new empty array. Iterate through the Min Heap elements and insert
 * each one into the new array using the standard Max-Heap "Insert" (Shift Up) logic.
 * Drawback: Requires O(N) extra space and takes O(N log N) time.
 * ==================================================================================================
 * APPROACH 2: OPTIMAL (Bottom-Up Heapify / Floyd's Algorithm)
 * ==================================================================================================
 * Instead of inserting elements one by one, we use the "Heapify" approach.
 * 1. We start from the last non-leaf node (last parent) and move up to the root.
 * 2. For each node, we perform a "Shift Down" (Max-Heapify) operation to ensure
 * the subtree rooted at that node satisfies the Max Heap property.
 * 3. The last internal node (parent) is always at index: (n - 2) / 2.
 * This approach is mathematically proven to be O(N).
 * ==================================================================================================
 */

import java.util.Arrays;

public class HeapConverter {

    // ----------------------------------------------------------------------
    // MAIN METHOD FOR TESTING
    // ----------------------------------------------------------------------
    public static void main(String[] args) {
        int[] minHeap = {3, 4, 8, 9, 10};
        System.out.println("Original Min Heap: " + Arrays.toString(minHeap));

        convertMinToMaxHeap(minHeap);

        System.out.println("Converted Max Heap: " + Arrays.toString(minHeap));
    }

    /**
     * Converts a Min Heap array into a Max Heap in-place.
     * Time Complexity: O(N)
     * Space Complexity: O(1) (excluding recursion stack)
     */
    public static void convertMinToMaxHeap(int[] nums) {
        int n = nums.length;
        if (n <= 1) return;

        // Start from the last internal node (parent) and go up to the root
        // Index of the last parent = (last_index - 1) / 2 = ((n-1) - 1) / 2
        for (int i = (n - 2) / 2; i >= 0; i--) {
            maxHeapify(nums, i, n);
        }
    }

    /**
     * Helper: Restores the Max-Heap property for a subtree rooted at index i.
     * Logic: Compares parent with children and swaps with the largest child.
     */
    private static void maxHeapify(int[] nums, int i, int n) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;

        // Check if left child is larger than current largest
        if (left < n && nums[left] > nums[largest]) {
            largest = left;
        }

        // Check if right child is larger than current largest
        if (right < n && nums[right] > nums[largest]) {
            largest = right;
        }

        // If the largest is not the parent, swap and recurse down
        if (largest != i) {
            swap(nums, i, largest);
            maxHeapify(nums, largest, n);
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}