package com.questions.strivers.binarysearch.bsonanswers;

/*
Problem Statement:
------------------
Given an integer array ‘A’ of size ‘N’ and an integer ‘K’.
Split the array ‘A’ into ‘K’ non-empty subarrays such that the largest sum of any subarray is minimized.
Your task is to return the minimized largest sum of the split.
A subarray is a contiguous part of the array.

Example 1:
Input: N = 5, a[] = {1,2,3,4,5}, k = 3
Output: 6
Explanation: Best split → [1, 2, 3], [4], [5].
The maximum subarray sum = 6 which is minimized.

Example 2:
Input: N = 3, a[] = {3,5,1}, k = 3
Output: 5
Explanation: Split → [3], [5], [1]. Max sum = 5.

Observation:
-------------
This problem is identical to the "Allocate Books" problem.
We are essentially dividing the array into K partitions such that the
maximum subarray sum is minimized.

Approaches:
------------
1. Brute Force (Linear Search over possible max sums):
   - Iterate from max(a[i]) → sum(a) and check feasible partitions.
   - First value of maxSum that allows exactly `k` partitions is answer.
   - Time: O((sum - max) * N), Space: O(1). (Too slow for large inputs).

2. Optimized (Binary Search on Answer):
   - The minimized maximum sum lies in [max(a[i]), sum(a)].
   - Apply Binary Search over this range.
   - For each mid, check how many partitions are required.
   - Adjust low/high based on partition count.
   - Time: O(N * log(sum)), Space: O(1).
*/

public class SplitArrayLargestSum {

    // Helper function: count partitions needed for given maxSum
    public static int countPartitions(int[] a, int maxSum) {
        int n = a.length;
        int partitions = 1; // at least one subarray
        long subarraySum = 0;

        for (int i = 0; i < n; i++) {
            // If adding a[i] does not exceed maxSum, continue in same subarray
            if (subarraySum + a[i] <= maxSum) {
                subarraySum += a[i];
            } else {
                // Otherwise, start new subarray
                partitions++;
                subarraySum = a[i];
            }
        }
        return partitions;
    }

    // Brute Force Approach
    public static int largestSubarraySumMinimized(int[] a, int k) {
        int low = a[0];  // max element
        int high = 0;    // sum of elements

        // find max element and sum of array
        for (int i = 0; i < a.length; i++) {
            low = Math.max(low, a[i]);
            high += a[i];
        }

        // Check each possible maxSum linearly
        for (int maxSum = low; maxSum <= high; maxSum++) {
            if (countPartitions(a, maxSum) == k) {
                return maxSum;
            }
        }
        return low;
    }

    // Optimized helper: same as above
    public static int countPartitions2(int[] a, int maxSum) {
        int n = a.length;
        int partitions = 1;
        long subarraySum = 0;

        for (int i = 0; i < n; i++) {
            if (subarraySum + a[i] <= maxSum) {
                subarraySum += a[i];
            } else {
                partitions++;
                subarraySum = a[i];
            }
        }
        return partitions;
    }

    // Optimized Binary Search Approach
    public static int largestSubarraySumMinimized2(int[] a, int k) {
        int low = a[0];  // max element
        int high = 0;    // sum of array

        // find max element and sum of array
        for (int i = 0; i < a.length; i++) {
            low = Math.max(low, a[i]);
            high += a[i];
        }

        // Binary search over possible max sums
        while (low <= high) {
            int mid = (low + high) / 2;
            int partitions = countPartitions2(a, mid);

            if (partitions > k) {
                // Too many partitions → mid too small → increase lower bound
                low = mid + 1;
            } else {
                // Can partition within k → try minimizing further
                high = mid - 1;
            }
        }
        return low; // minimized maximum subarray sum
    }

    public static void main(String[] args) {
        int[] a = {10, 20, 30, 40};
        int k = 2;

        // Using Optimized Binary Search
        int ans = largestSubarraySumMinimized2(a, k);
        System.out.println("The answer is: " + ans);
    }
}
