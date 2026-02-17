package com.questions.strivers.dynamicprogramming.dponsubsequences;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: MINIMUM COINS (LeetCode 322: Coin Change)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * You are given an integer array 'coins' representing coins of different denominations
 * and an integer 'amount' representing a total amount of money.
 * * Return the fewest number of coins that you need to make up that amount.
 * If that amount of money cannot be made up by any combination of the coins, return -1.
 * * You may assume that you have an infinite number of each kind of coin.
 *
 * EXAMPLE 1:
 * Input: coins = [1,2,5], amount = 11
 * Output: 3
 * Explanation: 11 = 5 + 5 + 1
 *
 * EXAMPLE 2:
 * Input: coins = [2], amount = 3
 * Output: -1
 *
 * Example 3:
 * Input: coins = [1], amount = 0
 * Output: 0
 *
 * Constraints:
 * 1 <= coins.length <= 12
 * 1 <= coins[i] <= 231 - 1
 * 0 <= amount <= 104
 *
 * KEY INSIGHT (UNBOUNDED KNAPSACK):
 * Unlike standard Knapsack (0/1) where we either pick or don't pick an item (move to index-1),
 * here we have an **Infinite Supply**.
 * * 1. If we don't pick a coin: We move to the next coin (index - 1).
 * 2. If we PICK a coin: We stay at the SAME index (index), because we might pick it again.
 *
 * ==================================================================================================
 */
public class MinimumCoins {

    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        int target = 11;
        int n = coins.length;

        System.out.println("Coins: " + Arrays.toString(coins));
        System.out.println("Target: " + target);
        System.out.println("--------------------------------------------------");

        // 1. Recursive Approach
        int resRec = recursive(n - 1, target, coins);
        System.out.println("1. Recursion       : " + (resRec >= (int) 1e9 ? -1 : resRec));

        // 2. Memoization Approach
        int[][] dp = new int[n][target + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        int resMemo = memoization(n - 1, target, coins, dp);
        System.out.println("2. Memoization     : " + (resMemo >= (int) 1e9 ? -1 : resMemo));

        // 3. Tabulation Approach
        System.out.println("3. Tabulation      : " + tabulation(coins, target));

        // 4. Space Optimized Approach (1D Array)
        System.out.println("4. Space Optimized : " + spaceOptimized(coins, target));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Try to generate the target using the last coin.
     * Two Choices:
     * 1. Not Take: Move to the previous index.
     * 2. Take: Stay at current index, reduce target. Add 1 to count.
     *
     * COMPLEXITY:
     * - Time: O(Coins^Target) -> Exponential
     * - Space: O(Target) -> Recursion stack
     */
    private static int recursive(int index, int target, int[] coins) {
        // Base Case: At index 0, we can only solve if target is divisible by coins[0]
        if (index == 0) {
            if (target % coins[0] == 0) return target / coins[0];
            else return (int) 1e9; // Impossible
        }

        // Choice 1: Do not take this coin
        int notTake = recursive(index - 1, target, coins);

        // Choice 2: Take this coin (if valid)
        int take = (int) 1e9;
        if (coins[index] <= target) {
            // CRITICAL: We pass 'index' again, not 'index - 1', because supply is infinite
            take = 1 + recursive(index, target - coins[index], coins);
        }

        return Math.min(take, notTake);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Cache the results of (index, target) to avoid re-computing.
     *
     * COMPLEXITY:
     * - Time: O(N * Target)
     * - Space: O(N * Target) + Stack
     */
    private static int memoization(int index, int target, int[] coins, int[][] dp) {
        if (index == 0) {
            if (target % coins[0] == 0) return target / coins[0];
            else return (int) 1e9;
        }

        if (dp[index][target] != -1) return dp[index][target];

        int notTake = memoization(index - 1, target, coins, dp);

        int take = (int) 1e9;
        if (coins[index] <= target) {
            take = 1 + memoization(index, target - coins[index], coins, dp);
        }

        return dp[index][target] = Math.min(take, notTake);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * dp[i][t] = Min coins to reach target 't' using first 'i' coins.
     *
     * COMPLEXITY:
     * - Time: O(N * Target)
     * - Space: O(N * Target)
     */
    private static int tabulation(int[] coins, int target) {
        int n = coins.length;
        int[][] dp = new int[n][target + 1];

        // 1. Initialize Base Case (Index 0)
        for (int t = 0; t <= target; t++) {
            if (t % coins[0] == 0) dp[0][t] = t / coins[0];
            else dp[0][t] = (int) 1e9;
        }

        // 2. Iterate Loops
        for (int i = 1; i < n; i++) {
            for (int t = 0; t <= target; t++) {

                int notTake = dp[i - 1][t]; // Value from row above

                int take = (int) 1e9;
                if (coins[i] <= t) {
                    // Value from SAME row (Unbounded Logic)
                    take = 1 + dp[i][t - coins[i]];
                }

                dp[i][t] = Math.min(take, notTake);
            }
        }

        int ans = dp[n - 1][target];
        return ans >= (int) 1e9 ? -1 : ans;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED (1D ARRAY) - BEST SOLUTION
     * ----------------------------------------------------------------------
     * LOGIC:
     * Notice that `dp[i][t]` depends on:
     * 1. `dp[i-1][t]` (Row Above -> 'prev')
     * 2. `dp[i][t - coin]` (Same Row, Left Column -> 'curr')
     * * Since we need the *updated* value from the *current* row for the `take` condition,
     * we can use a SINGLE array. When we iterate `t` from left to right, `dp[t-coin]`
     * will already contain the value for the current coin iteration.
     * * COMPLEXITY:
     * - Time: O(N * Target)
     * - Space: O(Target)
     */
    private static int spaceOptimized(int[] coins, int target) {
        int n = coins.length;
        int[] dp = new int[target + 1];

        // 1. Initialize Base Case (Index 0)
        for (int t = 0; t <= target; t++) {
            if (t % coins[0] == 0) dp[t] = t / coins[0];
            else dp[t] = (int) 1e9;
        }

        // 2. Iterate through remaining coins
        for (int i = 1; i < n; i++) {
            for (int t = 0; t <= target; t++) {
                int notTake = dp[t]; // Current value in array (from prev iteration)

                int take = (int) 1e9;
                if (coins[i] <= t) {
                    take = 1 + dp[t - coins[i]]; // Uses updated value (Unbounded)
                }

                dp[t] = Math.min(take, notTake);
            }
        }

        int ans = dp[target];
        return ans >= (int) 1e9 ? -1 : ans;
    }
}