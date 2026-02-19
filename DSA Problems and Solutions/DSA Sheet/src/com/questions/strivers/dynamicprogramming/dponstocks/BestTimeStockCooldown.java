package com.questions.strivers.dynamicprogramming.dponstocks;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: BEST TIME TO BUY AND SELL STOCK WITH COOLDOWN (LeetCode 309)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * You are given an array 'prices'. You can buy and sell multiple times.
 * Constraint: After you sell your stock, you cannot buy stock on the next day (Cooldown).
 * Return the maximum profit.
 *
 * EXAMPLE 1:
 * Input: prices = [1, 2, 3, 0, 2]
 * Output: 3
 * Explanation:
 * - Buy at 1 [Day 0]
 * - Sell at 2 [Day 1] (Profit = 1)
 * - Cooldown [Day 2] (Cannot buy)
 * - Buy at 0 [Day 3]
 * - Sell at 2 [Day 4] (Profit = 2)
 * Total = 1 + 2 = 3.
 *
 * KEY INSIGHT:
 * Standard DP State: (index, canBuy)
 * 1. If we BUY at day 'i': We move to day 'i+1' with canBuy=0 (Must Sell).
 * 2. If we SELL at day 'i': We move to day 'i+2' with canBuy=1 (Can Buy).
 * **Why i+2?** Because i+1 is forced cooldown.
 * ==================================================================================================
 */
public class BestTimeStockCooldown {

    public static void main(String[] args) {
        int[] prices = {1, 2, 3, 0, 2};
        System.out.println("Prices: " + Arrays.toString(prices));

        // 1. Recursive
        System.out.println("1. Recursion       : " + maxProfitRecursive(0, 1, prices));

        // 2. Memoization
        int[][] dp = new int[prices.length][2];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("2. Memoization     : " + maxProfitMemo(0, 1, prices, dp));

        // 3. Tabulation
        System.out.println("3. Tabulation      : " + maxProfitTabulation(prices));

        // 4. Space Optimized
        System.out.println("4. Space Optimized : " + maxProfitSpaceOptimized(prices));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION
     * ----------------------------------------------------------------------
     * LOGIC:
     * - canBuy = 1: We can Buy or Cooldown.
     * - Buy: -prices[i] + f(i+1, 0)
     * - canBuy = 0: We can Sell or Cooldown.
     * - Sell: +prices[i] + f(i+2, 1)  <-- NOTICE i+2 (The Cooldown Jump)
     *
     * COMPLEXITY:
     * - Time: O(2^N)
     * - Space: O(N)
     */
    private static int maxProfitRecursive(int i, int canBuy, int[] prices) {
        // Base Case: If index goes out of bounds
        if (i >= prices.length) return 0;

        int profit = 0;
        if (canBuy == 1) {
            // Option 1: Buy (Move to next day, must sell)
            int buy = -prices[i] + maxProfitRecursive(i + 1, 0, prices);
            // Option 2: Cooldown (Move to next day, still can buy)
            int cooldown = 0 + maxProfitRecursive(i + 1, 1, prices);
            profit = Math.max(buy, cooldown);
        } else {
            // Option 1: Sell (Move to i+2 because i+1 is cooldown)
            int sell = prices[i] + maxProfitRecursive(i + 2, 1, prices);
            // Option 2: Cooldown (Move to next day, still must sell)
            int cooldown = 0 + maxProfitRecursive(i + 1, 0, prices);
            profit = Math.max(sell, cooldown);
        }
        return profit;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * Cache results in dp[i][canBuy].
     * Time: O(N)
     * Space: O(N) + Stack
     */
    private static int maxProfitMemo(int i, int canBuy, int[] prices, int[][] dp) {
        if (i >= prices.length) return 0;
        if (dp[i][canBuy] != -1) return dp[i][canBuy];

        int profit;
        if (canBuy == 1) {
            int buy = -prices[i] + maxProfitMemo(i + 1, 0, prices, dp);
            int cooldown = maxProfitMemo(i + 1, 1, prices, dp);
            profit = Math.max(buy, cooldown);
        } else {
            int sell = prices[i] + maxProfitMemo(i + 2, 1, prices, dp);
            int cooldown = maxProfitMemo(i + 1, 0, prices, dp);
            profit = Math.max(sell, cooldown);
        }
        return dp[i][canBuy] = profit;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * Iterate backwards. Handle 'i+2' bounds carefully.
     * dp[i][1] -> Max profit starting at i, allowing Buy.
     * dp[i][0] -> Max profit starting at i, forced to Sell.
     */
    private static int maxProfitTabulation(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n + 2][2]; // Size n+2 to handle the i+2 overflow safely

        // Base cases are implicitly 0 (since array init is 0)

        for (int i = n - 1; i >= 0; i--) {
            // 1. Can Buy State
            // Buy: -price + next day(Sell state)
            // Cooldown: next day(Buy state)
            dp[i][1] = Math.max(-prices[i] + dp[i + 1][0], dp[i + 1][1]);

            // 2. Must Sell State
            // Sell: +price + (i+2) day(Buy state) -> The Jump
            // Cooldown: next day(Sell state)
            dp[i][0] = Math.max(prices[i] + dp[i + 2][1], dp[i + 1][0]);
        }

        return dp[0][1];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED (BEST SOLUTION)
     * ----------------------------------------------------------------------
     * We need data from 'i+1' and 'i+2'.
     * We can maintain three rows or just explicit variables for:
     * - front1 (i+1)
     * - front2 (i+2)
     *
     * Time: O(N)
     * Space: O(1)
     */
    private static int maxProfitSpaceOptimized(int[] prices) {
        int n = prices.length;

        // Corresponds to rows i+1 and i+2
        int[] front1 = new int[2];
        int[] front2 = new int[2];
        int[] curr = new int[2];

        for (int i = n - 1; i >= 0; i--) {
            // Can Buy: Buy or Rest
            curr[1] = Math.max(-prices[i] + front1[0], front1[1]);

            // Must Sell: Sell (jump to front2) or Rest
            curr[0] = Math.max(prices[i] + front2[1], front1[0]);

            // Shift states
            // front2 becomes what front1 was (i+1 becomes i+2 for the next iteration)
            // front1 becomes what curr is (i becomes i+1 for the next iteration)
            front2 = front1.clone();
            front1 = curr.clone();
        }

        return curr[1];
    }
}