package com.questions.strivers.dynamicprogramming.dponsubsequences;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: UNBOUNDED KNAPSACK
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * A thief wants to rob a store. He is carrying a bag of capacity W.
 * The store has ‘n’ items of INFINITE supply.
 * - Each item 'i' has a weight 'wt[i]' and a value 'val[i]'.
 * - He can either include an item in his knapsack or exclude it.
 * - He can take a single item ANY number of times.
 * * Find the maximum value of items that the thief can steal without exceeding capacity W.
 *
 * EXAMPLE:
 * Input: N=3, W=10
 * wt = {2, 4, 6}
 * val = {5, 11, 13}
 * Output: 27
 * Explanation:
 * - Take item 1 (wt=4, val=11) -> Rem W=6
 * - Take item 1 (wt=4, val=11) -> Rem W=2
 * - Take item 0 (wt=2, val=5)  -> Rem W=0
 * Total Value = 11 + 11 + 5 = 27.
 *
 * KEY INSIGHT (0/1 vs UNBOUNDED):
 * 1. 0/1 Knapsack: If we pick an item, we move to the next item (index - 1).
 * We process right-to-left in space optimization to avoid re-using.
 * 2. Unbounded: If we pick an item, we stay at the SAME item (index), allowing re-use.
 * We process LEFT-TO-RIGHT in space optimization to explicitly allow re-using.
 * ==================================================================================================
 */
public class UnboundedKnapsack {

    public static void main(String[] args) {
        int[] wt = {2, 4, 6};
        int[] val = {5, 11, 13};
        int W = 10;
        int n = wt.length;

        System.out.println("Weights: " + Arrays.toString(wt));
        System.out.println("Values:  " + Arrays.toString(val));
        System.out.println("Capacity: " + W);
        System.out.println("--------------------------------------------------");

        // 1. Recursive Approach
        System.out.println("1. Recursion       : " + unboundedKnapsackRecursive(n - 1, wt, val, W));

        // 2. Memoization Approach
        int[][] dp = new int[n][W + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("2. Memoization     : " + unboundedKnapsackMemo(n - 1, wt, val, W, dp));

        // 3. Tabulation Approach
        System.out.println("3. Tabulation      : " + unboundedKnapsackTabulation(wt, val, W));

        // 4. Space Optimized Approach (Single 1D Array)
        System.out.println("4. Space Optimized : " + unboundedKnapsackSpaceOptimized(wt, val, W));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Try to fill the bag starting from the last item.
     * Choices:
     * 1. Not Take: Move to index - 1.
     * 2. Take: Add value, subtract weight, but STAY at 'index' (infinite supply).
     *
     * COMPLEXITY:
     * - Time: O(2^W) -> Exponential (roughly).
     * - Space: O(W) -> Recursion stack (max depth W if picking smallest item).
     */
    private static int unboundedKnapsackRecursive(int index, int[] wt, int[] val, int W) {
        // Base Case: Only one item left (index 0)
        // We take as many instances of item 0 as possible.
        if (index == 0) {
            return (W / wt[0]) * val[0];
        }

        // Choice 1: Do not take the current item
        int notTake = unboundedKnapsackRecursive(index - 1, wt, val, W);

        // Choice 2: Take the current item (if it fits)
        int take = Integer.MIN_VALUE;
        if (wt[index] <= W) {
            // CRITICAL: Pass 'index' again, not 'index-1'
            take = val[index] + unboundedKnapsackRecursive(index, wt, val, W - wt[index]);
        }

        return Math.max(take, notTake);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Cache results of (index, W) to avoid re-calculating.
     *
     * COMPLEXITY:
     * - Time: O(N * W)
     * - Space: O(N * W) + Stack
     */
    private static int unboundedKnapsackMemo(int index, int[] wt, int[] val, int W, int[][] dp) {
        if (index == 0) {
            return (W / wt[0]) * val[0];
        }

        if (dp[index][W] != -1) return dp[index][W];

        int notTake = unboundedKnapsackMemo(index - 1, wt, val, W, dp);

        int take = Integer.MIN_VALUE;
        if (wt[index] <= W) {
            take = val[index] + unboundedKnapsackMemo(index, wt, val, W - wt[index], dp);
        }

        return dp[index][W] = Math.max(take, notTake);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * dp[i][w] = Max value using items 0..i with capacity w.
     * Iterate i from 0 to N-1, and w from 0 to W.
     *
     * COMPLEXITY:
     * - Time: O(N * W)
     * - Space: O(N * W)
     */
    private static int unboundedKnapsackTabulation(int[] wt, int[] val, int W) {
        int n = wt.length;
        int[][] dp = new int[n][W + 1];

        // 1. Initialize Base Case (Index 0)
        for (int w = 0; w <= W; w++) {
            dp[0][w] = (w / wt[0]) * val[0];
        }

        // 2. Iterate
        for (int i = 1; i < n; i++) {
            for (int w = 0; w <= W; w++) {

                int notTake = dp[i - 1][w]; // Value from row above

                int take = Integer.MIN_VALUE;
                if (wt[i] <= w) {
                    // Value from SAME row (Unbounded logic)
                    take = val[i] + dp[i][w - wt[i]];
                }

                dp[i][w] = Math.max(take, notTake);
            }
        }

        return dp[n - 1][W];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED (SINGLE 1D ARRAY)
     * ----------------------------------------------------------------------
     * LOGIC:
     * In Tabulation, `dp[i][w]` depends on:
     * 1. `dp[i-1][w]` (Row Above -> 'prev' value in 1D array)
     * 2. `dp[i][w - wt]` (Same Row, Left -> 'curr' updated value in 1D array)
     *
     * Unlike 0/1 Knapsack where we iterate Right-to-Left to avoid reusing items,
     * here we iterate LEFT-TO-RIGHT.
     * When we are at `w`, `dp[w - wt]` has already been updated for the CURRENT item.
     * This means we are using the item again -> Infinite Supply logic achieved!
     *
     *
     * COMPLEXITY:
     * - Time: O(N * W)
     * - Space: O(W) -> Single array
     */
    private static int unboundedKnapsackSpaceOptimized(int[] wt, int[] val, int W) {
        int n = wt.length;
        int[] dp = new int[W + 1];

        // 1. Base Case (Index 0)
        for (int w = 0; w <= W; w++) {
            dp[w] = (w / wt[0]) * val[0];
        }

        // 2. Iterate through items
        for (int i = 1; i < n; i++) {
            // Iterate weights LEFT TO RIGHT
            for (int w = 0; w <= W; w++) {
                int notTake = dp[w]; // Current value (from prev item iteration)

                int take = Integer.MIN_VALUE;
                if (wt[i] <= w) {
                    // Uses updated value (dp[w-wt] was just computed for THIS item)
                    take = val[i] + dp[w - wt[i]];
                }

                dp[w] = Math.max(take, notTake);
            }
        }

        return dp[W];
    }
}