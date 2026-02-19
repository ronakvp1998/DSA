package com.questions.strivers.dynamicprogramming.dponstocks;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: BEST TIME TO BUY AND SELL STOCK (LeetCode 121)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
 * You want to maximize your profit by choosing a single day to buy one stock and choosing a
 * different day in the future to sell that stock.
 * Return the maximum profit.
 *
 * EXAMPLE:
 * Input: prices = [7,1,5,3,6,4]
 * Output: 5 (Buy at 1, Sell at 6)
 *
 * --------------------------------------------------------------------------------------------------
 * OVERVIEW OF 5 APPROACHES
 * --------------------------------------------------------------------------------------------------
 * 1. RECURSION: Try all choices (Buy/Sell/Skip) with a transaction limit 'k=1'.
 * 2. MEMOIZATION: Cache the recursive states.
 * 3. TABULATION: Bottom-Up DP building the table.
 * 4. SPACE OPTIMIZATION: Reducing DP table to O(1) space.
 * 5. GREEDY (Optimal): The standard interview answer (Track MinPrice & MaxProfit).
 * ==================================================================================================
 */
public class BestTimeToBuyAndSellStock {

    public static void main(String[] args) {
        int[] prices = {7, 1, 5, 3, 6, 4};
        System.out.println("Prices: " + Arrays.toString(prices));
        System.out.println("--------------------------------------------------");

        // 1. Recursion
        // Start at index 0, canBuy = 1 (true), cap = 1 (1 transaction allowed)
        System.out.println("1. Recursion       : " + solveRecursive(0, 1, 1, prices));

        // 2. Memoization
        // dp[n][2][2] -> dp[index][buy][cap]
        int[][][] dp = new int[prices.length][2][2];
        for (int[][] row : dp) for (int[] col : row) Arrays.fill(col, -1);
        System.out.println("2. Memoization     : " + solveMemoization(0, 1, 1, prices, dp));

        // 3. Tabulation
        System.out.println("3. Tabulation      : " + solveTabulation(prices));

        // 4. Space Optimization (DP way)
        System.out.println("4. Space Opt (DP)  : " + solveSpaceOptimizedDP(prices));

        // 5. Greedy (Standard Interview Answer)
        System.out.println("5. Greedy (Best)   : " + solveGreedy(prices));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION
     * ----------------------------------------------------------------------
     * LOGIC:
     * We have 3 state parameters:
     * - ind: Current day index.
     * - buy: 1 if we can buy, 0 if we assume we have bought and must sell.
     * - cap: Number of transactions allowed (starts at 1).
     *
     * Transitions:
     * - If cap == 0: No transactions left, return 0.
     * - If buy == 1:
     * Option A: Buy (-price[i]) and move to next day with buy=0.
     * Option B: Skip (0) and move to next day with buy=1.
     * - If buy == 0:
     * Option A: Sell (+price[i]) and move to next day with cap-1.
     * Option B: Skip (0) and move to next day with buy=0.
     *
     * Complexity: O(2^N) -> Exponential
     */
    private static int solveRecursive(int ind, int buy, int cap, int[] prices) {
        // Base Case: If we processed all days or ran out of transactions
        if (ind == prices.length || cap == 0) {
            return 0;
        }

        int profit = 0;
        if (buy == 1) {
            // Choice 1: Buy stock (Profit decreases by price, can't buy next)
            int bought = -prices[ind] + solveRecursive(ind + 1, 0, cap, prices);
            // Choice 2: Don't buy (Profit unchanged, can still buy next)
            int skipped = 0 + solveRecursive(ind + 1, 1, cap, prices);
            profit = Math.max(bought, skipped);
        } else {
            // Choice 1: Sell stock (Profit increases by price, transaction complete: cap becomes 0)
            int sold = prices[ind] + solveRecursive(ind + 1, 1, cap - 1, prices);
            // Choice 2: Don't sell (Profit unchanged, still holding stock)
            int skipped = 0 + solveRecursive(ind + 1, 0, cap, prices);
            profit = Math.max(sold, skipped);
        }
        return profit;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Cache the results of (ind, buy, cap).
     *
     * Complexity: O(N * 2 * 1) => O(N)
     * Space: O(N) + Stack
     */
    private static int solveMemoization(int ind, int buy, int cap, int[] prices, int[][][] dp) {
        if (ind == prices.length || cap == 0) return 0;
        if (dp[ind][buy][cap] != -1) return dp[ind][buy][cap];

        int profit;
        if (buy == 1) {
            profit = Math.max(
                    -prices[ind] + solveMemoization(ind + 1, 0, cap, prices, dp),
                    0 + solveMemoization(ind + 1, 1, cap, prices, dp)
            );
        } else {
            profit = Math.max(
                    prices[ind] + solveMemoization(ind + 1, 1, cap - 1, prices, dp),
                    0 + solveMemoization(ind + 1, 0, cap, prices, dp)
            );
        }
        return dp[ind][buy][cap] = profit;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Build the table from N down to 0.
     * dp[ind][buy][cap]
     *
     * Complexity: O(N)
     * Space: O(N)
     */
    private static int solveTabulation(int[] prices) {
        int n = prices.length;
        // dp array size: [N+1] days, [2] buy states, [2] cap states (0 and 1)
        int[][][] dp = new int[n + 1][2][2];

        // Base cases are 0 (Java default), so explicit loops for cap=0 or ind=n not needed.

        // Loop backwards from day N-1 to 0
        for (int ind = n - 1; ind >= 0; ind--) {
            // Loop for buy state: 0 (holding) and 1 (not holding)
            for (int buy = 0; buy <= 1; buy++) {
                // Loop for cap: We only care about cap=1 (since cap=0 is base case return 0)
                // However, the loop logic should handle cap 1.
                for (int cap = 1; cap <= 1; cap++) {
                    if (buy == 1) { // We can buy
                        dp[ind][buy][cap] = Math.max(
                                -prices[ind] + dp[ind + 1][0][cap], // Buy
                                0 + dp[ind + 1][1][cap]             // Skip
                        );
                    } else { // We must sell
                        dp[ind][buy][cap] = Math.max(
                                prices[ind] + dp[ind + 1][1][cap - 1], // Sell (cap reduces to 0)
                                0 + dp[ind + 1][0][cap]                // Skip
                        );
                    }
                }
            }
        }
        // Result is at day 0, with ability to buy (1), and cap (1)
        return dp[0][1][1];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED DP
     * ----------------------------------------------------------------------
     * LOGIC:
     * We only need the 'next' row to compute 'curr'.
     * Since 'cap' is constant (always 1 for this problem), we can even remove that dimension.
     * Effectively:
     * dp[1][1] (Buy) depends on dp[0][1] (Holding)
     * dp[0][1] (Holding) depends on dp[1][0] (Sold/Base)
     *
     * Complexity: O(N)
     * Space: O(1) -> Two 2D arrays of constant size
     */
    private static int solveSpaceOptimizedDP(int[] prices) {
        int n = prices.length;
        int[][] next = new int[2][2]; // Corresponds to ind+1
        int[][] curr = new int[2][2]; // Corresponds to ind

        for (int ind = n - 1; ind >= 0; ind--) {
            for (int buy = 0; buy <= 1; buy++) {
                for (int cap = 1; cap <= 1; cap++) {
                    if (buy == 1) {
                        curr[buy][cap] = Math.max(
                                -prices[ind] + next[0][cap],
                                next[1][cap]
                        );
                    } else {
                        curr[buy][cap] = Math.max(
                                prices[ind] + next[1][cap - 1],
                                next[0][cap]
                        );
                    }
                }
            }
            // Move curr to next
            for(int i=0; i<2; i++) next[i] = curr[i].clone();
        }
        return next[1][1];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 5: GREEDY (THE "REAL" INTERVIEW SOLUTION)
     * ----------------------------------------------------------------------
     * LOGIC:
     * 1. To maximize Profit = (Sell - Buy), we need to Buy at the lowest possible price.
     * 2. As we iterate, we keep track of the `minPrice` seen *so far*.
     * 3. For every price, we calculate `currentPrice - minPrice`.
     * 4. If this difference is greater than `maxProfit`, we update it.
     *
     *
     *
     * Complexity: O(N)
     * Space: O(1)
     */
    private static int solveGreedy(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            // Keep track of the lowest valley
            if (price < minPrice) {
                minPrice = price;
            }
            // Check if selling today gives better profit
            else if (price - minPrice > maxProfit) {
                maxProfit = price - minPrice;
            }
        }
        return maxProfit;
    }
}