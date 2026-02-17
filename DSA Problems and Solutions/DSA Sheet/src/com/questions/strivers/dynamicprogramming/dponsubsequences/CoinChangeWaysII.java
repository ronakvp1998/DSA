package com.questions.strivers.dynamicprogramming.dponsubsequences;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: COIN CHANGE II (LeetCode 518)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * You are given an integer array 'coins' representing coins of different denominations
 * and an integer 'amount' representing a total amount of money.
 * * Return the number of combinations that make up that amount.
 * If that amount of money cannot be made up by any combination of the coins, return 0.
 * * You may assume that you have an infinite number of each kind of coin.
 *
 * EXAMPLE 1:
 * Input: amount = 5, coins = [1, 2, 5]
 * Output: 4
 * Explanation: there are 4 ways to make up the amount:
 * 5=5
 * 5=2+2+1
 * 5=2+1+1+1
 * 5=1+1+1+1+1
 *
 * KEY INSIGHT (UNBOUNDED KNAPSACK):
 * This is a variation of the Knapsack problem where we have an **Infinite Supply** of items.
 * 1. If we don't pick a coin: We move to the next coin (index - 1).
 * 2. If we PICK a coin: We stay at the SAME index (index), allowing us to pick it again.
 * * The total ways = (Ways without using current coin) + (Ways using current coin).
 * ==================================================================================================
 */
public class CoinChangeWaysII {

    public static void main(String[] args) {
        int[] coins = {1, 2, 3};
        int target = 4;
        int n = coins.length;

        System.out.println("Coins: " + Arrays.toString(coins));
        System.out.println("Target: " + target);
        System.out.println("--------------------------------------------------");

        // 1. Recursive Approach
        System.out.println("1. Recursion       : " + countWaysRecursive(n - 1, coins, target));

        // 2. Memoization Approach
        int[][] dp = new int[n][target + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("2. Memoization     : " + countWaysMemoization(n - 1, coins, target, dp));

        // 3. Tabulation Approach
        System.out.println("3. Tabulation      : " + countWaysTabulation(coins, target));

        // 4. Space Optimized Approach (Single 1D Array)
        System.out.println("4. Space Optimized : " + countWaysSpaceOptimized(coins, target));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Try to generate the target using the last coin.
     * Two Choices:
     * 1. Not Take: Move to the previous index (index - 1).
     * 2. Take: Stay at current index (index), reduce target.
     * * COMPLEXITY:
     * - Time: O(2^Target) -> Exponential
     * - Space: O(Target) -> Recursion stack
     */
    private static int countWaysRecursive(int index, int[] coins, int target) {
        // Base Case: When we are at the 0th coin (first coin in array)
        if (index == 0) {
            // Can we form the target using only coins[0]?
            // Yes, if target is divisible by coins[0].
            return (target % coins[0] == 0) ? 1 : 0;
        }

        // Choice 1: Do not take the current coin
        int notTake = countWaysRecursive(index - 1, coins, target);

        // Choice 2: Take the current coin (if valid)
        int take = 0;
        if (coins[index] <= target) {
            // CRITICAL: We pass 'index' again, NOT 'index - 1'
            // This represents the infinite supply logic.
            take = countWaysRecursive(index, coins, target - coins[index]);
        }

        return take + notTake;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Cache results in `dp[index][target]` to avoid re-computing states.
     * * COMPLEXITY:
     * - Time: O(N * Target)
     * - Space: O(N * Target) + Stack
     */
    private static int countWaysMemoization(int index, int[] coins, int target, int[][] dp) {
        if (index == 0) {
            return (target % coins[0] == 0) ? 1 : 0;
        }

        if (dp[index][target] != -1) {
            return dp[index][target];
        }

        int notTake = countWaysMemoization(index - 1, coins, target, dp);

        int take = 0;
        if (coins[index] <= target) {
            take = countWaysMemoization(index, coins, target - coins[index], dp);
        }

        return dp[index][target] = take + notTake;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * dp[i][t] = Number of ways to form sum 't' using first 'i' coins.
     * Iterate coin by coin, and target by target.
     * * COMPLEXITY:
     * - Time: O(N * Target)
     * - Space: O(N * Target)
     */
    private static int countWaysTabulation(int[] coins, int target) {
        int n = coins.length;
        int[][] dp = new int[n][target + 1];

        // 1. Initialize Base Case (Index 0)
        for (int t = 0; t <= target; t++) {
            dp[0][t] = (t % coins[0] == 0) ? 1 : 0;
        }

        // 2. Build Table
        for (int i = 1; i < n; i++) {
            for (int t = 0; t <= target; t++) {

                // Option 1: Not Take (Inherit from row above)
                int notTake = dp[i - 1][t];

                // Option 2: Take (Value from SAME row, shifted left)
                int take = 0;
                if (coins[i] <= t) {
                    take = dp[i][t - coins[i]];
                }

                dp[i][t] = take + notTake;
            }
        }

        return dp[n - 1][target];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED (SINGLE 1D ARRAY)
     * ----------------------------------------------------------------------
     * LOGIC:
     * In Tabulation, notice that `dp[i][t]` depends on:
     * 1. `dp[i-1][t]` (Row Above -> Current value in `dp` array)
     * 2. `dp[i][t - coin]` (Same Row, Left Column -> Updated value in `dp` array)
     * * If we iterate `target` from Left to Right, `dp[t-coin]` will already contain
     * the value for the *current* coin iteration (which is what we want for infinite supply).
     * * COMPLEXITY:
     * - Time: O(N * Target)
     * - Space: O(Target)
     */
    private static int countWaysSpaceOptimized(int[] coins, int target) {
        int n = coins.length;
        int[] dp = new int[target + 1];

        // 1. Base Case (Index 0)
        for (int t = 0; t <= target; t++) {
            dp[t] = (t % coins[0] == 0) ? 1 : 0;
        }

        // 2. Iterate through remaining coins
        for (int i = 1; i < n; i++) {
            for (int t = 0; t <= target; t++) {
                // notTake is implied (it's the value currently in dp[t])
                // take adds the value found at dp[t - coin]

                if (coins[i] <= t) {
                    dp[t] = dp[t] + dp[t - coins[i]];
                }
            }
        }

        return dp[target];
    }
}