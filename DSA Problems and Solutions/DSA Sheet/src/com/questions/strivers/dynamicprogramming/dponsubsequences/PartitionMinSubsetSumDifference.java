package com.questions.strivers.dynamicprogramming.dponsubsequences;

import java.util.Arrays;

/** need to revisit it's an hard category problem
 * ==================================================================================================
 * PROBLEM: PARTITION SET INTO 2 SUBSETS WITH MIN ABSOLUTE SUM DIFFERENCE (Striver DP-16)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * You are given an array 'arr' containing N positive integers.
 * Partition the array into two subsets such that the absolute difference between the sums of
 * the two subsets is minimized.
 * Return this minimum absolute difference.
 *
 * EXAMPLE 1:
 * Input: arr = [1, 2, 3, 4]
 * Output: 0
 * Explanation:
 * Subset 1: {1, 4} -> Sum = 5
 * Subset 2: {2, 3} -> Sum = 5
 * Difference: |5 - 5| = 0
 *
 * EXAMPLE 2:
 * Input: arr = [8, 6, 5]
 * Output: 3
 * Explanation:
 * Subset 1: {8} -> Sum = 8
 * Subset 2: {6, 5} -> Sum = 11
 * Difference: |8 - 11| = 3
 *
 * KEY INSIGHT:
 * Let the total sum of the array be 'TotalSum'.
 * If one subset has sum 'S1', the other subset must have sum 'S2 = TotalSum - S1'.
 *
 * We want to minimize: |S1 - S2|
 * Substitute S2:       |S1 - (TotalSum - S1)|
 * |2 * S1 - TotalSum|
 *
 * This reduces the problem to:
 * "Find a subset sum 'S1' from the array such that '2 * S1' is as close to 'TotalSum' as possible."
 *
 * STRATEGY:
 * 1. Use the "Subset Sum" DP (Tabulation) to find ALL possible subset sums achievable.
 * 2. Iterate through the possible sums (from 0 to TotalSum/2) and find the one that minimizes the equation above.
 * ==================================================================================================
 */
public class PartitionMinSubsetSumDifference {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 9};
        int n = arr.length;
        int totalSum = Arrays.stream(arr).sum();

        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Total Sum: " + totalSum);
        System.out.println("--------------------------------------------------");

        // 1. Recursive Approach (Brute Force)
        // Note: For recursion, we pass '0' as currentSum.
        System.out.println("1. Recursion       : " + minDifferenceRecursive(n - 1, 0, totalSum, arr));

        // 2. Memoization Approach
        int[][] dp = new int[n][totalSum + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("2. Memoization     : " + minDifferenceMemo(n - 1, 0, totalSum, arr, dp));

        // 3. Tabulation Approach (Standard Solution)
        System.out.println("3. Tabulation      : " + minDifferenceTabulation(arr));

        // 4. Space Optimized Approach (Best Solution)
        System.out.println("4. Space Optimized : " + minDifferenceSpaceOptimized(arr));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Explore all possible subsets. For each element, either include it in 'S1' or don't.
     * At the end (index < 0), calculate |S1 - S2|.
     *
     * COMPLEXITY:
     * - Time: O(2^N) -> Check every subset.
     * - Space: O(N) -> Recursion stack.
     */
    private static int minDifferenceRecursive(int index, int currentSum, int totalSum, int[] arr) {
        // Base Case: We have considered all elements
        if (index < 0) {
            int subset2Sum = totalSum - currentSum;
            return Math.abs(currentSum - subset2Sum);
        }

        // Option 1: Pick current element into Subset 1
        int pick = minDifferenceRecursive(index - 1, currentSum + arr[index], totalSum, arr);

        // Option 2: Do not pick (Element goes to Subset 2 implicitly)
        int notPick = minDifferenceRecursive(index - 1, currentSum, totalSum, arr);

        return Math.min(pick, notPick);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Cache results based on state (index, currentSum).
     *
     * COMPLEXITY:
     * - Time: O(N * TotalSum)
     * - Space: O(N * TotalSum) + Stack
     */
    private static int minDifferenceMemo(int index, int currentSum, int totalSum, int[] arr, int[][] dp) {
        if (index < 0) {
            int subset2Sum = totalSum - currentSum;
            return Math.abs(currentSum - subset2Sum);
        }

        if (dp[index][currentSum] != -1) return dp[index][currentSum];

        int pick = minDifferenceMemo(index - 1, currentSum + arr[index], totalSum, arr, dp);
        int notPick = minDifferenceMemo(index - 1, currentSum, totalSum, arr, dp);

        return dp[index][currentSum] = Math.min(pick, notPick);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP) - *RECOMMENDED*
     * ----------------------------------------------------------------------
     * LOGIC:
     * This approach uses the "Subset Sum" logic.
     * 1. Create a boolean table `dp[n][totalSum + 1]`.
     * 2. `dp[i][k] = true` means "Is it possible to make sum 'k' using first 'i' elements?".
     * 3. Fill the table completely.
     * 4. The last row `dp[n-1][...]` tells us ALL possible subset sums achievable using the entire array.
     * 5. Iterate through the last row (from 0 to TotalSum/2) to find the best 'S1'.
     *
     *
     *
     * COMPLEXITY:
     * - Time: O(N * TotalSum)
     * - Space: O(N * TotalSum)
     */
    private static int minDifferenceTabulation(int[] arr) {
        int n = arr.length;
        int totalSum = Arrays.stream(arr).sum();

        // dp[i][target] stores whether 'target' sum is possible using first 'i' elements
        boolean[][] dp = new boolean[n][totalSum + 1];

        // Base Case 1: Sum 0 is always possible (empty subset)
        for (int i = 0; i < n; i++) dp[i][0] = true;

        // Base Case 2: First element can form a sum equal to its value
        if (arr[0] <= totalSum) dp[0][arr[0]] = true;

        // Fill DP Table
        for (int i = 1; i < n; i++) {
            for (int target = 1; target <= totalSum; target++) {
                boolean notPick = dp[i - 1][target];
                boolean pick = false;
                if (arr[i] <= target) {
                    pick = dp[i - 1][target - arr[i]];
                }
                dp[i][target] = pick || notPick;
            }
        }

        // Find Minimum Difference
        // We only need to check S1 up to TotalSum/2.
        // S2 will automatically be (TotalSum - S1), which is >= TotalSum/2.
        int minDiff = Integer.MAX_VALUE;

        // Iterate through the last row of DP table
        for (int s1 = 0; s1 <= totalSum / 2; s1++) {
            if (dp[n - 1][s1]) { // If sum s1 is possible
                int s2 = totalSum - s1;
                int currentDiff = Math.abs(s1 - s2);
                minDiff = Math.min(minDiff, currentDiff);
            }
        }

        return minDiff;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZATION (BEST SOLUTION)
     * ----------------------------------------------------------------------
     * LOGIC:
     * We only need the valid sums from the `prev` row to determine valid sums for `curr`.
     * Reduces space from O(N * TotalSum) to O(TotalSum).
     *
     * COMPLEXITY:
     * - Time: O(N * TotalSum)
     * - Space: O(TotalSum)
     */
    private static int minDifferenceSpaceOptimized(int[] arr) {
        int n = arr.length;
        int totalSum = Arrays.stream(arr).sum();

        // 'prev' stores reachability for the previous iteration
        boolean[] prev = new boolean[totalSum + 1];

        // Base Case
        prev[0] = true;
        if (arr[0] <= totalSum) prev[arr[0]] = true;

        // Iterate
        for (int i = 1; i < n; i++) {
            boolean[] curr = new boolean[totalSum + 1];
            curr[0] = true;

            for (int target = 1; target <= totalSum; target++) {
                boolean notPick = prev[target];
                boolean pick = false;
                if (arr[i] <= target) {
                    pick = prev[target - arr[i]];
                }
                curr[target] = pick || notPick;
            }
            prev = curr;
        }

        // Find Minimum Difference using the final 'prev' array
        int minDiff = Integer.MAX_VALUE;

        for (int s1 = 0; s1 <= totalSum / 2; s1++) {
            if (prev[s1]) {
                int s2 = totalSum - s1;
                minDiff = Math.min(minDiff, Math.abs(s1 - s2));
            }
        }

        return minDiff;
    }
}