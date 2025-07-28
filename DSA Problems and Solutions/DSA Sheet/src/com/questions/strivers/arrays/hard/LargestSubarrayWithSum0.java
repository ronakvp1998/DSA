package com.questions.strivers.arrays.hard;

import java.util.HashMap;
/* Problem Statement: Given an array containing both positive and negative integers,
we have to find the length of the longest subarray with the sum of all elements equal to zero.

        Examples

        Example 1:
        Input Format: N = 6, array[] = {9, -3, 3, -1, 6, -5}
        Result: 5
        Explanation: The following subarrays sum to zero:
        {-3, 3} , {-1, 6, -5}, {-3, 3, -1, 6, -5}
        Since we require the length of the longest subarray, our answer is 5!

        Example 2:
        Input Format: N = 8, array[] = {6, -2, 2, -8, 1, 7, 4, -10}
        Result: 8
        Subarrays with sum 0 : {-2, 2}, {-8, 1, 7}, {-2, 2, -8, 1, 7}, {6, -2, 2, -8, 1, 7, 4, -10}
        Length of longest subarray = 8

        Example 3:
        Input Format: N = 3, array[] = {1, 0, -5}
        Result: 1
        Subarray : {0}
        Length of longest subarray = 1

        Example 4:
        Input Format: N = 5, array[] = {1, 3, -5, 6, -2}
        Result: 0
        Subarray: There is no subarray that sums to zero */

public class LargestSubarrayWithSum0 {

    /**
     * ✅ Brute Force Approach
     *
     * Approach:
     * - Try all subarrays using two nested loops.
     * - For each subarray from i to j, calculate the sum.
     * - If sum == 0, update the max length.
     *
     * Time Complexity: O(n²)
     *   - For each of the n elements, we run another loop of up to n.
     * Space Complexity: O(1)
     *   - No extra data structures used.
     */
    static int solve(int[] a) {
        int max = 0;

        // Outer loop to fix the starting point
        for (int i = 0; i < a.length; ++i) {
            int sum = 0;

            // Inner loop to calculate the sum of subarray starting from i
            for (int j = i; j < a.length; ++j) {
                sum += a[j];

                // If sum becomes 0, we found a valid subarray
                if (sum == 0) {
                    // Update maximum length found so far
                    max = Math.max(max, j - i + 1);
                }
            }
        }

        return max;
    }

    /**
     * ✅ Optimal Approach Using HashMap
     *
     * Approach:
     * - Keep a running prefix sum while traversing the array.
     * - If the sum becomes 0, that means subarray from 0 to i has sum = 0.
     * - If a sum has been seen before, that means the elements in between sum to 0.
     * - Store the **first occurrence** of each prefix sum in a HashMap.
     *
     * Why it works:
     * - Let prefixSum[i] = prefix sum up to index i.
     * - If prefixSum[i] == prefixSum[j], the sum of elements from (j+1 to i) is 0.
     *
     * Time Complexity: O(n)
     *   - Single pass through the array
     * Space Complexity: O(n)
     *   - For storing prefix sums in HashMap
     */
    int maxLen(int A[], int n) {
        // HashMap to store the first occurrence of each prefix sum
        HashMap<Integer, Integer> mpp = new HashMap<>();

        int maxi = 0; // stores the length of the longest subarray with sum = 0
        int sum = 0;  // running prefix sum

        for (int i = 0; i < n; i++) {
            sum += A[i]; // Update running sum

            // Case 1: If sum = 0, subarray [0...i] has sum = 0
            if (sum == 0) {
                maxi = i + 1;
            }
            // Case 2: If sum has been seen before, subarray in between sums to 0
            else if (mpp.containsKey(sum)) {
                // Calculate the length of subarray and update max
                maxi = Math.max(maxi, i - mpp.get(sum));
            }
            // Case 3: Store the first occurrence of this sum
            else {
                mpp.put(sum, i);
            }
        }

        return maxi; // Return the maximum length found
    }

    public static void main(String args[]) {
        int a[] = {9, -3, 3, -1, 6, -5};

        // Calling brute force approach
        System.out.println("Brute Force Result: " + solve(a));

        // Calling optimal approach
        LargestSubarrayWithSum0 obj = new LargestSubarrayWithSum0();
        System.out.println("Optimal Result: " + obj.maxLen(a, a.length));
    }
}
