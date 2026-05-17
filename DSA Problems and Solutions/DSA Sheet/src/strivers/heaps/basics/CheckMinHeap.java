package com.questions.strivers.heaps.basics;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: Check if an array represents a Min Heap
 * ==================================================================================================
 * Given an array of integers nums, determine if it represents a binary min-heap.
 * A binary min-heap is a complete binary tree where every parent node is less than
 * or equal to its child nodes (nums[parent] <= nums[child]).
 * * Array Mapping for a node at index i:
 * - Left Child:  2 * i + 1
 * - Right Child: 2 * i + 2
 *
 * Example 1:
 * Input: nums = [10, 20, 30, 21, 23]
 * Output: true
 *
 * Example 2:
 * Input: nums = [10, 20, 30, 25, 15]
 * Output: false (Child 15 is smaller than parent 20)
 * ==================================================================================================
 * APPROACH: OPTIMAL (Iterative Parent-Child Comparison)
 * ==================================================================================================
 * Since a heap is a complete binary tree represented in an array, we don't need to
 * build a tree structure. We simply iterate through all "parent" nodes.
 * * Logic:
 * 1. A node at index 'i' is a parent if it has at least one child.
 * The last parent is located at index (n - 2) / 2.
 * 2. For every parent, check if its value is greater than its left child.
 * 3. Check if its value is greater than its right child (if it exists).
 * 4. If any parent is found to be larger than a child, return false.
 * ==================================================================================================
 */

public class CheckMinHeap {

    // ----------------------------------------------------------------------
    // MAIN METHOD FOR TESTING
    // ----------------------------------------------------------------------
    public static void main(String[] args) {
        // Test Case 1: Valid Min Heap
        int[] nums1 = {10, 20, 30, 21, 23};
        System.out.println("Test Case 1 (Valid): " + isMinHeap(nums1));

        // Test Case 2: Invalid Min Heap
        int[] nums2 = {10, 20, 30, 25, 15};
        System.out.println("Test Case 2 (Invalid): " + isMinHeap(nums2));

        // Test Case 3: Single element
        int[] nums3 = {5};
        System.out.println("Test Case 3 (Single): " + isMinHeap(nums3));
    }

    /**
     * Checks if the given array follows Min-Heap properties.
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     */
    public static boolean isMinHeap(int[] nums) {
        int n = nums.length;

        // An empty array or single element array is technically a min-heap
        if (n <= 1) return true;

        // Iterate through all nodes that have at least one child.
        // Nodes from index 0 to (n/2 - 1) are internal nodes (parents).
        for (int i = 0; i <= (n - 2) / 2; i++) {

            int leftChildIdx = 2 * i + 1;
            int rightChildIdx = 2 * i + 2;

            // 1. Check if the Left Child exists and is smaller than the parent
            if (leftChildIdx < n && nums[i] > nums[leftChildIdx]) {
                return false;
            }

            // 2. Check if the Right Child exists and is smaller than the parent
            if (rightChildIdx < n && nums[i] > nums[rightChildIdx]) {
                return false;
            }
        }

        // If no violations were found, it's a valid min-heap
        return true;
    }
}