package com.questions.strivers.dynamicprogramming.oneddp;

import java.util.Arrays;

/**
 * Problem Statement:
 * -------------------
 * Given an array `arr` of size `n`, where arr[i] denotes the height of the ith stair,
 * a frog is trying to reach the last stair (n-1).
 *
 * The frog can jump to:
 *   - the next stair (i -> i+1)
 *   - or skip one stair (i -> i+2)
 *
 * The energy cost of a jump is: |arr[i] - arr[j]|
 *
 * Goal: Minimize the total energy cost to reach the last stair.
 * https://takeuforward.org/data-structure/dynamic-programming-frog-jump-dp-3/
 */

public class FrogJumpDP {

    public static void main(String[] args) {

        // Define the height of each stair
        int arr[] = {30, 10, 60, 10, 60, 50};
        int n = arr.length;

        // ****************** Approach 1: Pure Recursion ******************
        // Simple recursive function without any optimization
        // Time complexity: O(2^n), Space: O(n) (recursion stack)
         System.out.println(recursive(n - 1, arr));

        // ****************** Approach 2: Memoization ******************
        // Initialize the DP array to store computed subproblems
        int dp[] = new int[n];
        Arrays.fill(dp, -1); // fill with -1 indicating uncomputed
        System.out.println("Memoization: " + memorization(n - 1, arr, dp));

        // ****************** Approach 3: Tabulation (Bottom-Up DP) ******************
        // Reuse the same dp[] by re-filling it
        Arrays.fill(dp, -1); // again fill with -1 before use
        System.out.println("Tabulation: " + tabulation(n, arr, dp));

        // ****************** Approach 4: Space Optimized DP ******************
        System.out.println("Space Optimized: " + spaceOptimized(n, arr));
    }

    /**
     * Approach 4: Space Optimized DP
     * Only stores the last two states, instead of full dp[].
     * Time: O(n), Space: O(1)
     */
    public static int spaceOptimized(int n, int[] arr) {
        int prev1 = 0;  // Represents dp[i-1]: cost to reach previous stair
        int prev2 = 0;  // Represents dp[i-2]: cost to reach two stairs before

        // Loop through stairs from index 1 to n-1
        for (int i = 1; i < arr.length; i++) {
            // Jump from i-1 to i
            int jumpOne = prev1 + Math.abs(arr[i] - arr[i - 1]);

            int jumpTwo = Integer.MAX_VALUE;
            if (i > 1) {
                // Jump from i-2 to i, if i-2 exists
                jumpTwo = prev2 + Math.abs(arr[i] - arr[i - 2]);
            }

            // Current stair's cost is the minimum of both jumps
            int curr = Math.min(jumpOne, jumpTwo);

            // Update prev2 and prev1 for next iteration
            prev2 = prev1;
            prev1 = curr;
        }

        // Final answer is stored in prev1 (dp[n-1])
        return prev1;
    }

    /**
     * Approach 3: Tabulation (Bottom-Up DP)
     * Build the solution from base cases upwards
     * Time: O(n), Space: O(n)
     */
    public static int tabulation(int n, int[] arr, int[] dp) {
        dp[0] = 0; // Base case: No energy required to stand on the first stair

        for (int i = 1; i < n; i++) {
            // Cost of jumping from previous stair
            int jumpOne = dp[i - 1] + Math.abs(arr[i] - arr[i - 1]);

            // Initialize jumpTwo with max value
            int jumpTwo = Integer.MAX_VALUE;
            if (i > 1) {
                // Cost of jumping from i-2
                jumpTwo = dp[i - 2] + Math.abs(arr[i] - arr[i - 2]);
            }

            // Store the minimum of both jump options
            dp[i] = Math.min(jumpOne, jumpTwo);
        }

        // Return cost to reach the last stair
        return dp[n - 1];
    }

    /**
     * Approach 2: Memoization (Top-down DP)
     * Recursive solution with caching to avoid recomputation
     * Time: O(n), Space: O(n) for dp[] and recursion stack
     */
    public static int memorization(int n, int[] arr, int[] dp) {
        if (n == 0) {
            // Base case: at first stair, no energy required
            return 0;
        }

        if (dp[n] != -1) {
            // If already computed, return cached result
            return dp[n];
        }

        // Recursive call to compute energy from previous stair
        int left = memorization(n - 1, arr, dp) + Math.abs(arr[n] - arr[n - 1]);

        int right = Integer.MAX_VALUE;
        if (n > 1) {
            // Recursive call to compute energy from two stairs before
            right = memorization(n - 2, arr, dp) + Math.abs(arr[n] - arr[n - 2]);
        }

        // Store the minimum of both options in dp[]
        return dp[n] = Math.min(left, right);
    }

    /**
     * Approach 1: Simple Recursion (No DP)
     * Brute force - explores all paths
     * Time: O(2^n), Space: O(n) recursion stack
     */
    public static int recursive(int n, int[] arr) {
        if (n == 0) {
            // Base case: no energy to stand on the first stair
            return 0;
        }

        // Jump from previous stair
        int left = recursive(n - 1, arr) + Math.abs(arr[n] - arr[n - 1]);

        int right = Integer.MAX_VALUE;
        if (n > 1) {
            // Jump from two stairs before
            right = recursive(n - 2, arr) + Math.abs(arr[n] - arr[n - 2]);
        }

        // Return the minimum of the two jump options
        return Math.min(left, right);
    }
}
