package com.questions.strivers.heaps.mediumproblems;

/**
 * ==================================================================================================
 * PROBLEM STATEMENT: Kth Smallest Element in an Array
 * ==================================================================================================
 * Given an integer array nums and an integer k, return the kth smallest element in the array.
 * Note that it is the kth smallest element in sorted order, not the kth distinct element.
 *
 * Example 1:
 * Input: nums = [7, 10, 4, 3, 20, 15], k = 3
 * Output: 7
 * Explanation: Sorted array is [3, 4, 7, 10, 15, 20]. The 3rd smallest is 7.
 *
 * Example 2:
 * Input: nums = [7, 10, 4, 20, 15], k = 4
 * Output: 15
 * ==================================================================================================
 * APPROACH 1: BRUTE FORCE (Sorting)
 * ==================================================================================================
 * Sort the array in ascending order and return the element at index (k-1).
 * Drawback: Sorting the entire array takes O(N log N) time, which is inefficient if k is small.
 * ==================================================================================================
 * APPROACH 2: OPTIMAL (Max-Heap / Priority Queue)
 * ==================================================================================================
 * To find the Kth SMALLEST element, we use a MAX-HEAP of size K.
 * 1. Iterate through the array.
 * 2. Add each element to the Max-Heap.
 * 3. If the heap size exceeds K, remove the largest element (the root).
 * 4. By the end, the heap contains the K smallest elements seen so far.
 * 5. The root (top) of the Max-Heap is the "largest of the K smallest," which is the Kth smallest.
 * ==================================================================================================
 */

import java.util.PriorityQueue;
import java.util.Collections;
import java.util.Arrays;

public class KthSmallest {

    public static void main(String[] args) {
        int[] nums1 = {7, 10, 4, 3, 20, 15};
        int k1 = 3;
        System.out.println("Test Case 1 (K=3):");
        System.out.println("Brute Force : " + findKthSmallestBruteForce(nums1, k1));
        System.out.println("Optimal Heap: " + findKthSmallestOptimal(nums1, k1));

        int[] nums2 = {7, 10, 4, 20, 15};
        int k2 = 4;
        System.out.println("\nTest Case 2 (K=4):");
        System.out.println("Brute Force : " + findKthSmallestBruteForce(nums2, k2));
        System.out.println("Optimal Heap: " + findKthSmallestOptimal(nums2, k2));
    }

    /**
     * BRUTE FORCE: Sort and Access
     * Time Complexity: O(N log N)
     * Space Complexity: O(1)
     */
    public static int findKthSmallestBruteForce(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[k - 1];
    }

    /**
     * OPTIMAL: Max-Heap of size K
     * Time Complexity: O(N log K)
     * Space Complexity: O(K)
     */
    public static int findKthSmallestOptimal(int[] nums, int k) {
        // We use Collections.reverseOrder() to turn the default Min-Heap into a Max-Heap
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (int num : nums) {
            // Add current number to the heap
            maxHeap.add(num);

            // If heap size grows larger than k, remove the largest element (the root)
            // This ensures we only keep the 'k' smallest elements encountered
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        // The top of the heap is the largest of the k smallest elements
        return maxHeap.peek();
    }
}