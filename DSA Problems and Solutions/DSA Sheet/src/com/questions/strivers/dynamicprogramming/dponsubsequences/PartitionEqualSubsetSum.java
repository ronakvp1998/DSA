package com.questions.strivers.dynamicprogramming.dponsubsequences;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: PARTITION EQUAL SUBSET SUM (LeetCode 416)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given an integer array nums, return true if you can partition the array into two subsets
 * such that the sum of the elements in both subsets is equal or false otherwise.
 *
 * EXAMPLE 1:
 * Input: nums = [1,5,11,5]
 * Output: true
 * Explanation: The array can be partitioned as [1, 5, 5] and [11]. Both sum to 11.
 *
 * EXAMPLE 2:
 * Input: nums = [1,2,3,5]
 * Output: false
 * Explanation: Total sum is 11. We cannot divide 11 into two equal integers.
 *
 * KEY INSIGHT:
 * 1. Calculate the `TotalSum` of the array.
 * 2. If `TotalSum` is ODD, we cannot split it into two equal integers. Return False.
 * 3. If `TotalSum` is EVEN, the problem reduces to:
 * "Does there exist a subset with sum equal to TotalSum / 2?"
 * 4. This is exactly the "Subset Sum Problem".
 * ==================================================================================================
 */
public class PartitionEqualSubsetSum {

    public static void main(String[] args) {
        int[] arr = {2, 3, 3, 3, 4, 5};
        int n = arr.length;
        int totalSum = Arrays.stream(arr).sum();

        System.out.println("Input Array: " + Arrays.toString(arr));
        System.out.println("Total Sum: " + totalSum);

        // Pre-check: Odd sum cannot be partitioned
        //
        if (totalSum % 2 != 0) {
            System.out.println("Result: False (Odd Sum)");
            return;
        }

        int target = totalSum / 2;
        System.out.println("Target for each subset: " + target);
        System.out.println("--------------------------------------------------");

        // 1. Recursive Approach
        System.out.println("1. Recursion       : " + canPartitionRecursive(n - 1, target, arr));

        // 2. Memoization Approach
        // DP table size: [N][Target + 1]
        // -1 represents unvisited state
        int[][] dp = new int[n][target + 1];
        for (int[] row : dp) Arrays.fill(row, -1);

        // Helper function maps int result (1/0) to boolean
        boolean memoResult = canPartitionMemo(n - 1, target, arr, dp) == 1;
        System.out.println("2. Memoization     : " + memoResult);

        // 3. Tabulation Approach
        System.out.println("3. Tabulation      : " + canPartitionTabulation(arr));

        // 4. Space Optimized Approach
        System.out.println("4. Space Optimized : " + canPartitionSpaceOptimized(arr));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Standard "Pick / Not Pick" pattern.
     * At each index, we either:
     * 1. Pick the element (subtract from target).
     * 2. Not Pick the element (target stays same).
     *
     * COMPLEXITY:
     * - Time: O(2^N) -> Binary recursion tree.
     * - Space: O(N) -> Recursion stack.
     */
    private static boolean canPartitionRecursive(int index, int target, int[] arr) {
        // Base Case 1: Target reached
        if (target == 0) return true;

        // Base Case 2: Reached the first element
        // If we are at index 0, we can only form the target if arr[0] IS the target
        if (index == 0) return arr[0] == target;

        // Choice 1: Do NOT include current element
        boolean notPick = canPartitionRecursive(index - 1, target, arr);

        // Choice 2: Include current element (only if it fits)
        boolean pick = false;
        if (arr[index] <= target) {
            pick = canPartitionRecursive(index - 1, target - arr[index], arr);
        }

        return pick || notPick;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Same as recursion, but we cache the result in `dp[index][target]`.
     * We use int[][] instead of boolean[][] because 'null' (unvisited) is harder
     * to represent in primitive boolean arrays without a wrapper class.
     * 1 = True, 0 = False, -1 = Unvisited.
     *
     * COMPLEXITY:
     * - Time: O(N * Target) -> Total unique states.
     * - Space: O(N * Target) + O(N) Stack.
     */
    private static int canPartitionMemo(int index, int target, int[] arr, int[][] dp) {
        if (target == 0) return 1;
        if (index == 0) return (arr[0] == target) ? 1 : 0;

        // Step 1: Check Cache
        if (dp[index][target] != -1) {
            return dp[index][target];
        }

        // Step 2: Compute
        // Not Pick
        boolean notPick = (canPartitionMemo(index - 1, target, arr, dp) == 1);

        // Pick
        boolean pick = false;
        if (arr[index] <= target) {
            pick = (canPartitionMemo(index - 1, target - arr[index], arr, dp) == 1);
        }

        // Step 3: Store and Return
        return dp[index][target] = (pick || notPick) ? 1 : 0;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Iteratively build the table `dp[index][target]`.
     * dp[i][t] means: "Can we form sum 't' using the first 'i' items?"
     * * Base Cases:
     * 1. Target 0 is always True (empty subset).
     * 2. Index 0 can only form sum == arr[0].
     *
     * COMPLEXITY:
     * - Time: O(N * Target)
     * - Space: O(N * Target)
     */
    private static boolean canPartitionTabulation(int[] arr) {
        int n = arr.length;
        int totalSum = 0;
        for (int num : arr) totalSum += num;

        // Odd sum check (already done in main, but good practice to have in method)
        if (totalSum % 2 != 0) return false;
        int target = totalSum / 2;

        boolean[][] dp = new boolean[n][target + 1];

        // Base Case 1: Target 0 is always possible
        for (int i = 0; i < n; i++) dp[i][0] = true;

        // Base Case 2: First element
        if (arr[0] <= target) dp[0][arr[0]] = true;

        // Fill DP table
        for (int i = 1; i < n; i++) {
            for (int t = 1; t <= target; t++) {

                // Option 1: Not Pick (Inherit from previous row)
                boolean notPick = dp[i - 1][t];

                // Option 2: Pick (Check previous row with reduced target)
                boolean pick = false;
                if (arr[i] <= t) {
                    pick = dp[i - 1][t - arr[i]];
                }

                dp[i][t] = pick || notPick;
            }
        }

        return dp[n - 1][target];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED DP (BEST SOLUTION)
     * ----------------------------------------------------------------------
     * LOGIC:
     * We only need the previous row `prev` to compute the current row `curr`.
     * Reduces space complexity from O(N*Target) to O(Target).
     *
     * COMPLEXITY:
     * - Time: O(N * Target)
     * - Space: O(Target)
     */
    private static boolean canPartitionSpaceOptimized(int[] arr) {
        int n = arr.length;
        int totalSum = 0;
        for (int num : arr) totalSum += num;

        if (totalSum % 2 != 0) return false;
        int target = totalSum / 2;

        boolean[] prev = new boolean[target + 1];

        // Base Case 1: Target 0 is always possible
        prev[0] = true;

        // Base Case 2: First element
        if (arr[0] <= target) prev[arr[0]] = true;

        // Iterate
        for (int i = 1; i < n; i++) {
            boolean[] curr = new boolean[target + 1];
            curr[0] = true; // Target 0 is always true

            for (int t = 1; t <= target; t++) {
                boolean notPick = prev[t];
                boolean pick = false;
                if (arr[i] <= t) {
                    pick = prev[t - arr[i]];
                }
                curr[t] = pick || notPick;
            }
            prev = curr;
        }

        return prev[target];
    }
}