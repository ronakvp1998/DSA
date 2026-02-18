package com.questions.strivers.dynamicprogramming.lis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: PRINTING LONGEST INCREASING SUBSEQUENCE (Striver DP-42)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given an integer array 'arr', return the Longest Increasing Subsequence (LIS).
 * If there are multiple LIS with the same length, return the one that is
 * index-wise lexicographically smallest.
 *
 * EXAMPLE 1:
 * Input: arr = [10, 22, 9, 33, 21, 50, 41, 60, 80]
 * Output: [10, 22, 33, 50, 60, 80]
 *
 * EXAMPLE 2:
 * Input: arr = [1, 3, 2, 4]
 * Output: [1, 3, 4]
 * Explanation:
 * LIS 1: [1, 3, 4] (indices 0, 1, 3)
 * LIS 2: [1, 2, 4] (indices 0, 2, 3)
 * Indices (0, 1, 3) < (0, 2, 3), so we pick [1, 3, 4].
 *
 * ALGORITHM:
 * 1. dp[i] stores the length of the LIS ending at index i.
 * 2. hash[i] stores the index of the previous element in the LIS ending at i.
 * 3. Iterate i from 0 to n-1:
 * Iterate j from 0 to i-1:
 * If arr[j] < arr[i] AND 1 + dp[j] > dp[i]:
 * dp[i] = 1 + dp[j]
 * hash[i] = j  (Track parent)
 * 4. Find the index with the maximum value in dp[]. (Break ties by choosing smallest index).
 * 5. Backtrack using the hash[] array to rebuild the sequence.
 * 6. Reverse the list (since we backtracked).
 * ==================================================================================================
 */
public class PrintingLongestIncreasingSubsequence {

    public static void main(String[] args) {
        int[] arr1 = {10, 22, 9, 33, 21, 50, 41, 60, 80};
        System.out.println("Input: " + Arrays.toString(arr1));
        System.out.println("LIS: " + printLIS(arr1));

        int[] arr2 = {1, 3, 2, 4, 6, 5};
        System.out.println("\nInput: " + Arrays.toString(arr2));
        System.out.println("LIS: " + printLIS(arr2));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH: TABULATION WITH BACKTRACKING (O(N^2))
     * ----------------------------------------------------------------------
     */
    public static List<Integer> printLIS(int[] arr) {
        int n = arr.length;
        if (n == 0) return new ArrayList<>();

        // dp[i] stores the length of LIS ending at index i
        int[] dp = new int[n];
        // hash[i] stores the previous index in the LIS sequence
        int[] hash = new int[n];

        // Initialize arrays
        Arrays.fill(dp, 1);
        for (int i = 0; i < n; i++) {
            hash[i] = i; // Initially, every element points to itself
        }

        int maxLen = 1;
        int lastIndex = 0;

        // -------------------------------------------------------
        // Step 1: Build the DP Table
        // -------------------------------------------------------
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // If current element is greater than previous AND extending gives longer LIS
                // NOTE: Use '>' (strict) to prefer the first occurring predecessor (Lexicographically Smallest)
                if (arr[j] < arr[i] && 1 + dp[j] > dp[i]) {
                    dp[i] = 1 + dp[j];
                    hash[i] = j; // Store trace
                }
            }

            // Track the global maximum length and its end position
            // NOTE: Use '>' (strict) to prefer the first occurring end index
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                lastIndex = i;
            }
        }

        // -------------------------------------------------------
        // Step 2: Backtrack to reconstruct the path
        // -------------------------------------------------------
        List<Integer> lis = new ArrayList<>();
        lis.add(arr[lastIndex]);

        // Loop until the element points to itself (start of sequence)
        while (hash[lastIndex] != lastIndex) {
            lastIndex = hash[lastIndex];
            lis.add(arr[lastIndex]);
        }

        // -------------------------------------------------------
        // Step 3: Reverse to get correct order
        // -------------------------------------------------------
        Collections.reverse(lis);
        return lis;
    }
}