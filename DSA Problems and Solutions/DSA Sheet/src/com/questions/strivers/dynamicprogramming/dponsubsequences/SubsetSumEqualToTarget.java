package com.questions.strivers.dynamicprogramming.dponsubsequences;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: SUBSET SUM EQUAL TO TARGET (Striver DP-14)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * We are given an array ‘ARR’ with N positive integers. We need to find if there is a subset
 * in “ARR” with a sum equal to K. If there is, return true else return false.
 *
 * EXAMPLE:
 * Input: ARR = [1, 2, 3, 4], K = 4
 * Output: true
 * Explanation:
 * - Subset [1, 3] sums to 4.
 * - Subset [4] sums to 4.
 *
 * APPROACH SUMMARY:
 * This is a classic "Take or Not Take" problem.
 * At every index, we have two choices:
 * 1. Exclude the current element (Target remains same).
 * 2. Include the current element (Target reduces by arr[index]).
 * ==================================================================================================
 */
public class SubsetSumEqualToTarget {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4};
        int k = 4;
        int n = arr.length;

        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Target: " + k);
        System.out.println("--------------------------------------------------");

        // 1. Recursive Approach
        System.out.println("1. Recursion       : " + subsetSumRecursive(n - 1, k, arr));

        // 2. Memoization Approach
        // DP table size: [N][K+1]. Initialized with -1.
        int[][] dp = new int[n][k + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("2. Memoization     : " + subsetSumMemoization(n - 1, k, arr, dp));

        // 3. Tabulation Approach
        System.out.println("3. Tabulation      : " + subsetSumTabulation(n, k, arr));

        // 4. Space Optimized Approach
        System.out.println("4. Space Optimized : " + subsetSumSpaceOptimized(n, k, arr));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Try every subset. At index 'ind', we either pick the element or not.
     *
     * COMPLEXITY:
     * - Time: O(2^N) -> Each element has 2 choices.
     * - Space: O(N) -> Recursion stack depth.
     */
    private static boolean subsetSumRecursive(int ind, int target, int[] arr) {
        // Base Case 1: Target reached
        if (target == 0) return true;

        // Base Case 2: We are at the first element
        if (ind == 0) return arr[0] == target;

        // Choice 1: Do NOT take the current element
        boolean notTaken = subsetSumRecursive(ind - 1, target, arr);

        // Choice 2: Take the current element (if valid)
        boolean taken = false;
        if (arr[ind] <= target) {
            taken = subsetSumRecursive(ind - 1, target - arr[ind], arr);
        }

        return notTaken || taken;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Same as recursion, but we store the result of state (ind, target) in a DP table.
     * DP[ind][target] = 1 (True), 0 (False), -1 (Unvisited)
     *
     * COMPLEXITY:
     * - Time: O(N * K) -> There are N*K unique states.
     * - Space: O(N * K) + O(N) Stack.
     */
    private static boolean subsetSumMemoization(int ind, int target, int[] arr, int[][] dp) {
        if (target == 0) return true;
        if (ind == 0) return arr[0] == target;

        // Step 1: Check Cache
        if (dp[ind][target] != -1) {
            return dp[ind][target] == 1;
        }

        // Step 2: Compute
        boolean notTaken = subsetSumMemoization(ind - 1, target, arr, dp);

        boolean taken = false;
        if (arr[ind] <= target) {
            taken = subsetSumMemoization(ind - 1, target - arr[ind], arr, dp);
        }

        // Step 3: Store and Return (1 for True, 0 for False)
        dp[ind][target] = (notTaken || taken) ? 1 : 0;
        return notTaken || taken;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * dp[i][target] represents: "Is it possible to achieve 'target' using subset of first 'i' elements?"
     *
     * Base Cases:
     * 1. Target 0 is always True (empty subset).
     * 2. If we only have the first element (index 0), only target == arr[0] is True.
     *
     *
     *
     * COMPLEXITY:
     * - Time: O(N * K)
     * - Space: O(N * K)
     */
    private static boolean subsetSumTabulation(int n, int k, int[] arr) {
        boolean[][] dp = new boolean[n][k + 1];

        // Base Case 1: Target 0 is always possible
        for (int i = 0; i < n; i++) {
            dp[i][0] = true;
        }

        // Base Case 2: First element can form sum == itself
        if (arr[0] <= k) {
            dp[0][arr[0]] = true;
        }

        // Fill the table
        for (int ind = 1; ind < n; ind++) {
            for (int target = 1; target <= k; target++) {

                // Option 1: Not Taken (Inherit from previous row)
                boolean notTaken = dp[ind - 1][target];

                // Option 2: Taken (Check previous row with reduced target)
                boolean taken = false;
                if (arr[ind] <= target) {
                    taken = dp[ind - 1][target - arr[ind]];
                }

                dp[ind][target] = notTaken || taken;
            }
        }

        return dp[n - 1][k];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED DP (BEST SOLUTION)
     * ----------------------------------------------------------------------
     * LOGIC:
     * To calculate current row `curr`, we only need the previous row `prev`.
     * We reduce space from O(N*K) to O(K).
     *
     * COMPLEXITY:
     * - Time: O(N * K)
     * - Space: O(K) -> Using two rows (prev and curr).
     */
    private static boolean subsetSumSpaceOptimized(int n, int k, int[] arr) {
        boolean[] prev = new boolean[k + 1];

        // Base Case 1: Target 0 is always possible
        prev[0] = true;

        // Base Case 2: First element
        if (arr[0] <= k) {
            prev[arr[0]] = true;
        }

        // Iterate through elements
        for (int ind = 1; ind < n; ind++) {
            boolean[] curr = new boolean[k + 1];
            curr[0] = true; // Target 0 is always true

            for (int target = 1; target <= k; target++) {
                boolean notTaken = prev[target];

                boolean taken = false;
                if (arr[ind] <= target) {
                    taken = prev[target - arr[ind]];
                }

                curr[target] = notTaken || taken;
            }
            // Move current row to previous
            prev = curr;
        }

        return prev[k];
    }
}