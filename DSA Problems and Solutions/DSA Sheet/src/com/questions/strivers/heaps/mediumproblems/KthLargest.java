package com.questions.strivers.heaps.mediumproblems;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: Kth Largest Element in an Array
 * ==================================================================================================
 * Given an integer array nums and an integer k, return the kth largest element in the array.
 * Note that it is the kth largest element in the sorted order, not the kth distinct element.
 *
 * Example 1:
 * Input: nums = [1, 2, 3, 4, 5], k = 2
 * Output: 4
 *
 * Example 2:
 * Input: nums = [-5, 4, 1, 2, -3], k = 5
 * Output: -5
 * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (Sorting)
 * ==================================================================================================
 * The simplest way to find the kth largest element is to sort the array in descending
 * order and return the element at index (k-1).
 * Drawback: Sorting the entire array takes O(N log N) time.
 * ==================================================================================================
 * APPROACH 2: OPTIMAL (Min-Heap / Priority Queue)
 * ==================================================================================================
 * We can use a Min-Heap of size K to track the K largest elements seen so far.
 * 1. Iterate through the array.
 * 2. Add each element to the Min-Heap.
 * 3. If the heap size exceeds K, remove the smallest element (the root).
 * 4. After processing all elements, the root of the Min-Heap will be the Kth largest element.
 * Why? The heap contains the K largest values, and the root is the smallest among those K values.
 * ==================================================================================================
 */

import java.util.PriorityQueue;
import java.util.Arrays;

public class KthLargest {

    // ----------------------------------------------------------------------
    // MAIN METHOD FOR TESTING
    // ----------------------------------------------------------------------
    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 4, 5};
        int k1 = 2;
        System.out.println("Test Case 1 (K=2):");
        System.out.println("Brute Force : " + findKthLargestBruteForce(nums1, k1));
        System.out.println("Optimal Heap: " + findKthLargestOptimal(nums1, k1));

        int[] nums2 = {-5, 4, 1, 2, -3};
        int k2 = 5;
        System.out.println("\nTest Case 2 (K=5):");
        System.out.println("Brute Force : " + findKthLargestBruteForce(nums2, k2));
        System.out.println("Optimal Heap: " + findKthLargestOptimal(nums2, k2));
    }

    /**
     * Finds the Kth largest element using Sorting.
     * Time Complexity: O(N log N)
     * Space Complexity: O(1)
     */
    public static int findKthLargestBruteForce(int[] nums, int k) {
        // Sort the array in ascending order
        Arrays.sort(nums);
        // The Kth largest is at (length - k)
        return nums[nums.length - k];
    }

    /**
     * Finds the Kth largest element using a Min-Heap.
     * Time Complexity: O(N log K)
     * Space Complexity: O(K)
     */
    public static int findKthLargestOptimal(int[] nums, int k) {
        // Create a Min-Heap (PriorityQueue in Java is Min-Heap by default)
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : nums) {
            // Add current number to the heap
            minHeap.add(num);

            // If the size exceeds k, remove the smallest element
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        // The root of the heap is the Kth largest element
        return minHeap.peek();
    }
}