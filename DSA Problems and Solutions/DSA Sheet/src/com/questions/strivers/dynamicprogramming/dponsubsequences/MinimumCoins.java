package com.questions.practice.dynamicprogramming;

import java.util.Arrays;

/**
 * ==================================================================================================
 * MASTERCLASS: MINIMUM COINS (LeetCode 322: Coin Change)
 * ==================================================================================================
 *
 * ### 1. Problem Documentation
 *
 * **Problem Statement:**
 * You are given an integer array 'coins' representing coins of different denominations
 * and an integer 'amount' representing a total amount of money.
 * Return the fewest number of coins that you need to make up that amount.
 * If that amount of money cannot be made up by any combination of the coins, return -1.
 * You may assume that you have an infinite number of each kind of coin.
 *
 * **Constraints:**
 * - 1 <= coins.length <= 12
 * - 1 <= coins[i] <= 2^31 - 1
 * - 0 <= amount <= 10^4
 *
 * --------------------------------------------------------------------------------------------------
 * **Conceptual Visualization**
 *
 * **Recursion Tree (Unbounded Knapsack Decision Tree):**
 * Example: coins = [1, 2], amount = 3
 * f(index, amount) -> returns min coins
 *
 *                          f(1, 3)  [Coin = 2]
 *                              /         \
 *              NOT PICK (Amt=3)          PICK (Amt = 3-2=1)
 *                      /                          \
 *                  f(0, 3)                  1 + f(1, 1)
 *                  [Coin = 1]                   [Coin = 2]
 *                  /        \                   /         \
 *              NOT PICK    PICK             NOT PICK       PICK
 *                  /           \                /              \
 *              f(-1, 3)     1 + f(0, 2)       f(0, 1)       1 + f(1, -1)
 *              (Invalid)    (Path to ans)   (Path to ans)    (Invalid)
 *
 *
 * **Final DP Array Filled:**
 * Example: coins = [1, 2], amount = 3
 * Index (i) \ Amount (a) ->  0   1   2   3
 * ----------------------------------------
 * 0 (Coin: 1)             |  0   1   2   3
 * 1 (Coin: 2)             |  0   1   1   2  <-- Final Answer: 2 coins (1+2)
 *
 * ==================================================================================================
 */
public class MinimumCoins {

    public static void main(String[] args) {
        System.out.println("=== LeetCode 322: Coin Change Testing Suite ===\n");

        int[] coins1 = {1, 2, 5}; int amount1 = 11;
        int[] coins2 = {2};       int amount2 = 3;
        int[] coins3 = {1};       int amount3 = 0;

        System.out.println("Test 1 (Expected 3): " + solveAllPhases(coins1, amount1));
        System.out.println("Test 2 (Expected -1): " + solveAllPhases(coins2, amount2));
        System.out.println("Test 3 (Expected 0): " + solveAllPhases(coins3, amount3));
    }

    private static String solveAllPhases(int[] coins, int amount) {
        int n = coins.length;

        // Phase 1
        int ans1 = phase1BruteForce(n - 1, amount, coins);
        ans1 = ans1 >= (int)1e9 ? -1 : ans1;

        // Phase 2
        int[][] dp = new int[n][amount + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        int ans2 = phase2Memoization(n - 1, amount, coins, dp);
        ans2 = ans2 >= (int)1e9 ? -1 : ans2;

        // Phase 3 & 4
        int ans3 = phase3Tabulation(coins, amount);
        int ans4 = phase4SpaceOptimized(coins, amount);

        return String.format("\n  P1 (Brute): %d \n  P2 (Memo):  %d \n  P3 (Tab):   %d \n  P4 (Opt):   %d\n", ans1, ans2, ans3, ans4);
    }

    /**
     * ==========================================================================
     * Phase 1: Brute Force Recursion (The "Think it" stage)
     * ==========================================================================
     * **Detailed Intuition:**
     * Since we have an infinite supply of each coin, this is the "Unbounded Knapsack" pattern.
     * At each coin, we can either:
     * 1. Not Pick it: Move to the previous coin `(i - 1)`.
     * 2. Pick it: Add 1 to our coin count, subtract the coin's value from the amount,
     * and stay at the SAME coin `(i)` because we can pick it again!
     * * *CRITICAL TRAP:* If a path is invalid, we return a large number. We CANNOT return
     * `Integer.MAX_VALUE` because the caller adds 1 (`1 + f(...)`), which causes integer
     * overflow, flipping the value to a massive negative number. We use `(int)1e9` instead.
     *
     * **Complexity Analysis:**
     * - Time Complexity: >> O(2^N) - Exponential, as we stay on the same index multiple times.
     * - Space Complexity: O(Amount) - Auxiliary stack depth can reach `amount` if we pick 1 repeatedly.
     */
    public static int phase1BruteForce(int i, int amount, int[] coins) {
        if (i == 0) {
            // If the remaining amount is completely divisible by the 0th coin, return the count
            if (amount % coins[0] == 0) return amount / coins[0];
            // Otherwise, it's an invalid path
            return (int) 1e9;
        }

        int notPick = phase1BruteForce(i - 1, amount, coins);

        int pick = (int) 1e9;
        if (coins[i] <= amount) {
            // Unbounded Knapsack: Stay at index 'i' after picking!
            pick = 1 + phase1BruteForce(i, amount - coins[i], coins);
        }

        return Math.min(notPick, pick);
    }

    /**
     * ==========================================================================
     * Phase 2: Top-Down Memoization (The "Refine it" stage)
     * ==========================================================================
     * **Detailed Intuition:**
     * The brute force approach calculates identical states (same index, same remaining amount)
     * repeatedly. We introduce a 2D cache `dp[index][amount]` to store calculated results.
     * If a state is already computed (!= -1), return it in O(1) time.
     *
     * **Complexity Analysis:**
     * - Time Complexity: O(N * Amount) - We process each state exactly once.
     * - Space Complexity: O(N * Amount) for the DP Matrix (Heap) + O(Amount) for Auxiliary Stack.
     */
    public static int phase2Memoization(int i, int amount, int[] coins, int[][] dp) {
        if (i == 0) {
            if (amount % coins[0] == 0) return amount / coins[0];
            return (int) 1e9;
        }

        if (dp[i][amount] != -1) return dp[i][amount];

        int notPick = phase2Memoization(i - 1, amount, coins, dp);

        int pick = (int) 1e9;
        if (coins[i] <= amount) {
            pick = 1 + phase2Memoization(i, amount - coins[i], coins, dp);
        }

        return dp[i][amount] = Math.min(notPick, pick);
    }

    /**
     * ==========================================================================
     * Phase 3: Bottom-Up Tabulation (The "Build it" stage)
     * ==========================================================================
     * **Detailed Intuition:**
     * We replace the recursion stack with iteration to avoid StackOverflow on large amounts.
     * We start by pre-filling the base case (Row 0) for the very first coin.
     * * **DP Array State Before Loops (Base Cases Initialization):**
     * Example: coins = [2, 3], amount = 5
     * * Index (i) \ Amount (a) ->  0     1     2     3     4     5
     * ------------------------------------------------------------
     * 0 (Coin: 2)             |  0    1e9    1    1e9    2    1e9  <- Pre-filled!
     * 1 (Coin: 3)             |  0     0     0     0     0     0
     * * Notice how row 0 only has valid counts for amounts perfectly divisible by 2.
     * Everything else is poisoned with our safe infinity (1e9).
     *
     * **Complexity Analysis:**
     * - Time Complexity: O(N * Amount) - Nested loops.
     * - Space Complexity: O(N * Amount) - DP Matrix on the heap, ZERO stack space!
     */
    public static int phase3Tabulation(int[] coins, int amount) {
        int n = coins.length;
        int[][] dp = new int[n][amount + 1];

        // 1. Initialize Base Case for Index 0
        for (int a = 0; a <= amount; a++) {
            if (a % coins[0] == 0) {
                dp[0][a] = a / coins[0];
            } else {
                dp[0][a] = (int) 1e9;
            }
        }

        // 2. Iterate DP Table
        for (int i = 1; i < n; i++) {
            for (int a = 0; a <= amount; a++) {

                int notPick = dp[i - 1][a];

                int pick = (int) 1e9;
                if (coins[i] <= a) {
                    // Unbounded: We look at the CURRENT row `i` for the pick state!
                    pick = 1 + dp[i][a - coins[i]];
                }

                dp[i][a] = Math.min(notPick, pick);
            }
        }

        int ans = dp[n - 1][amount];
        return ans >= (int) 1e9 ? -1 : ans;
    }

    /**
     * ==========================================================================
     * Phase 4: Space Optimization (The "Perfect it" stage)
     * ==========================================================================
     * **Detailed Intuition:**
     * Look closely at the inner loop of Tabulation:
     * `notPick` relies on `dp[i - 1][a]` (the row exactly above us).
     * `pick` relies on `dp[i][a - coins[i]]` (the CURRENT row, but to the left).
     * * Because we need values from the *current* row that we just updated, we can compress
     * this into a single 1D array, and unlike 0/1 Knapsack, we iterate FORWARDS.
     * Moving forwards guarantees that when we check `dp[a - coins[i]]`, it holds the
     * already-updated value for the current coin!
     *
     * **Complexity Analysis:**
     * - Time Complexity: O(N * Amount)
     * - Space Complexity: O(Amount) - Flattened from 2D matrix to a single 1D array.
     */
    public static int phase4SpaceOptimized(int[] coins, int amount) {
        int n = coins.length;
        int[] dp = new int[amount + 1];

        // 1. Base Case Initialization
        for (int a = 0; a <= amount; a++) {
            if (a % coins[0] == 0) {
                dp[a] = a / coins[0];
            } else {
                dp[a] = (int) 1e9;
            }
        }

        // 2. Iterate Array
        for (int i = 1; i < n; i++) {
            // Unbounded Knapsack -> Traverse FORWARDS!
            for (int a = 0; a <= amount; a++) {
                int notPick = dp[a];

                int pick = (int) 1e9;
                if (coins[i] <= a) {
                    pick = 1 + dp[a - coins[i]]; // Uses freshly updated value
                }

                dp[a] = Math.min(notPick, pick);
            }
        }

        int ans = dp[amount];
        return ans >= (int) 1e9 ? -1 : ans;
    }

    /**
     * ==========================================================================
     * Phase 5: Alternative Approaches
     * ==========================================================================
     * 1. Greedy Algorithm:
     * - Intuition: Sort coins descending, keep dividing amount by the largest coin.
     * - Why it FAILS: Greedy works for standard currency systems (like US coins: 1, 5, 10, 25)
     * but fails for arbitrary denominations.
     * Example: coins = [1, 3, 4], amount = 6.
     * Greedy picks 4 -> remainder 2. Then picks 1, 1. Total = 3 coins.
     * Optimal DP picks 3, 3. Total = 2 coins.
     * * 2. Breadth-First Search (BFS):
     * - We can view this as finding the Shortest Path in an unweighted graph where
     * nodes are amounts (from 0 to `amount`) and edges are the coin denominations.
     * This works very well and can be slightly faster in practice if the target is
     * reached early, though worst-case time remains O(N * Amount).
     */
}