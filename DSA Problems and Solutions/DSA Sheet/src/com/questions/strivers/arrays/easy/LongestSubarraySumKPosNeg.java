package com.questions.strivers.arrays.easy;

import java.util.HashMap;
import java.util.Map;

/*
 Problem Statement: Given an array (can contain positive, negative, or zero) and an integer k,
 find the length of the longest subarray whose sum equals k.

 Examples:
 Example 1:
 Input:  N = 3, k = 5, array = {2, 3, 5}
 Output: 2
 Explanation: Longest subarray with sum 5 is {2, 3}

 Example 2:
 Input:  N = 3, k = 1, array = {-1, 1, 1}
 Output: 3
 Explanation: Longest subarray with sum 1 is {-1, 1, 1}
*/

public class LongestSubarraySumKPosNeg {

    /**
     * Approach 1: Brute Force - Triple Nested Loop
     * - Fix start index `i`
     * - Fix end index `j`
     * - Loop from i → j to calculate sum
     * Time Complexity: O(N^3)
     * Space Complexity: O(1)
     */
    public static int getLongestSubarray1(int[] a, int k) {
        int n = a.length;
        int len = 0;

        for (int i = 0; i < n; i++) { // start index
            for (int j = i; j < n; j++) { // end index
                int sum = 0;
                for (int idx = i; idx <= j; idx++) { // sum from i to j
                    sum += a[idx];
                }
                if (sum == k) {
                    len = Math.max(len, j - i + 1);
                }
            }
        }
        return len;
    }

    /**
     * Approach 2: Better Brute Force - Double Loop
     * - Fix start index `i`
     * - Keep extending `j` while adding sum incrementally
     * Time Complexity: O(N^2)
     * Space Complexity: O(1)
     */
    public static int getLongestSubarray2(int[] a, int k) {
        int n = a.length;
        int len = 0;

        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum += a[j]; // extend subarray sum
                if (sum == k) {
                    len = Math.max(len, j - i + 1);
                }
            }
        }
        return len;
    }

    /**
     * Approach 3: Optimal - Prefix Sum + HashMap
     * - Works with positives, negatives, and zero
     * - Store first occurrence of each prefix sum
     * - If (prefixSum - k) exists in map → subarray found
     * Time Complexity: O(N)
     * Space Complexity: O(N)
     */
    public static int getLongestSubarray3(int[] a, int k) {
        int n = a.length;
        Map<Integer, Integer> preSumMap = new HashMap<>();
        int sum = 0;
        int maxLen = 0;

        for (int i = 0; i < n; i++) {
            sum += a[i]; // current prefix sum

            // If sum from start to current index equals k
            if (sum == k) {
                maxLen = Math.max(maxLen, i + 1);
            }

            // If there exists a previous prefix sum such that
            // currentSum - previousSum = k → subarray found
            int rem = sum - k;
            if (preSumMap.containsKey(rem)) {
                int len = i - preSumMap.get(rem);
                maxLen = Math.max(maxLen, len);
            }

            // Store prefix sum if not already stored (only first occurrence matters)
            if (!preSumMap.containsKey(sum)) {
                preSumMap.put(sum, i);
            }
        }
        return maxLen;
    }

    public static void main(String[] args) {
        int[] a = {-1, 1, 1};
        int k = 1;

        System.out.println("Approach 1: " + getLongestSubarray1(a, k)); // Expected: 3
        System.out.println("Approach 2: " + getLongestSubarray2(a, k)); // Expected: 3
        System.out.println("Approach 3: " + getLongestSubarray3(a, k)); // Expected: 3
    }
}
