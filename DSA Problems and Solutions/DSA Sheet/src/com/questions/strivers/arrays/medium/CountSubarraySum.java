package com.questions.strivers.arrays.medium;

import java.util.HashMap;
import java.util.Map;
/* https://takeuforward.org/arrays/count-subarray-sum-equals-k/
Count Subarray sum Equals K

Problem Statement: Given an array of integers and an integer k,
return the total number of subarrays whose sum equals k.

A subarray is a contiguous non-empty sequence of elements within an array.

Pre-requisite: Longest subarray with given sum

Example 1:
Input Format: N = 4, array[] = {3, 1, 2, 4}, k = 6
Result: 2
Explanation: The subarrays that sum up to 6 are [3, 1, 2] and [2, 4].

Example 2:
Input Format: N = 3, array[] = {1,2,3}, k = 3
Result: 2
Explanation: The subarrays that sum up to 3 are [1, 2], and [3].
 */
public class CountSubarraySum {

    /**
     * Brute Force Approach 1 (Triple Nested Loops)
     * ---------------------------------------------------
     * For every possible subarray [i...j], calculate its sum by iterating from i to j.
     * If the sum equals k, increment the counter.
     *
     * Time Complexity: O(N^3)
     *  - Outer loop for i (O(N))
     *  - Inner loop for j (O(N))
     *  - Sum calculation loop (O(N))
     * Space Complexity: O(1) (no extra data structures used)
     */
    public static int findAllSubarraysWithGivenSum(int arr[], int k) {
        int n = arr.length; // size of the given array
        int cnt = 0; // variable to store number of subarrays with sum = k

        // Outer loop - starting index of subarray
        for (int i = 0; i < n; i++) {
            // Inner loop - ending index of subarray
            for (int j = i; j < n; j++) {

                int sum = 0; // sum of current subarray
                // Loop to calculate sum of subarray [i...j]
                for (int K = i; K <= j; K++)
                    sum += arr[K];

                // If sum equals k, increment counter
                if (sum == k)
                    cnt++;
            }
        }
        return cnt;
    }

    /**
     * Brute Force Approach 2 (Double Nested Loops)
     * ---------------------------------------------------
     * Instead of recalculating the sum from scratch for every subarray,
     * keep a running sum as we extend the subarray.
     *
     * Time Complexity: O(N^2)
     * Space Complexity: O(1)
     */
    public static int findAllSubarraysWithGivenSum2(int arr[], int k) {
        int n = arr.length; // size of the given array
        int cnt = 0; // variable to store number of subarrays with sum = k

        // Outer loop - starting index of subarray
        for (int i = 0; i < n; i++) {
            int sum = 0; // sum of subarray starting at i

            // Inner loop - ending index of subarray
            for (int j = i; j < n; j++) {
                // Add current element to running sum
                sum += arr[j];

                // If sum equals k, increment counter
                if (sum == k)
                    cnt++;
            }
        }
        return cnt;
    }

    /**
     * Optimal Approach using Prefix Sum + HashMap
     * ---------------------------------------------------
     * We maintain a running prefix sum and store how many times
     * each prefix sum has occurred in a HashMap.
     *
     * If `prefixSum - k` exists in the map, it means there is a subarray
     * ending at the current index whose sum is k.
     *
     * Time Complexity: O(N)
     * Space Complexity: O(N) (HashMap for storing prefix sums)
     */
    public static int findAllSubarraysWithGivenSum3(int arr[], int k) {
        int n = arr.length; // size of the given array
        Map<Integer, Integer> mpp = new HashMap<>(); // stores prefixSum -> frequency
        int preSum = 0; // running prefix sum
        int cnt = 0; // number of subarrays with sum = k

        mpp.put(0, 1); // base case: prefix sum 0 occurs once before starting

        for (int i = 0; i < n; i++) {
            // Add current element to prefix sum
            preSum += arr[i];

            // The sum we need to remove to get sum = k
            int remove = preSum - k;

            // If remove exists, add its frequency to count
            cnt += mpp.getOrDefault(remove, 0);

            // Update the frequency of current prefix sum
            mpp.put(preSum, mpp.getOrDefault(preSum, 0) + 1);
        }
        return cnt;
    }

    public static void main(String[] args) {
        int[] arr = {3, 1, 2, 4};
        int k = 6;

        System.out.println("Brute Force (O(N^3)): " + findAllSubarraysWithGivenSum(arr, k));
        System.out.println("Improved Brute Force (O(N^2)): " + findAllSubarraysWithGivenSum2(arr, k));
        System.out.println("Optimal (O(N)): " + findAllSubarraysWithGivenSum3(arr, k));
    }
}
