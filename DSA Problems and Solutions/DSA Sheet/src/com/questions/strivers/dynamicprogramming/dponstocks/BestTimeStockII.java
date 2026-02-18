package com.questions.strivers.dynamicprogramming.stocks;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: BEST TIME TO BUY AND SELL STOCK II (LeetCode 122)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * You are given an array 'prices'. You can buy and sell the stock multiple times.
 * You can only hold at most one share at a time.
 * Return the maximum profit.
 *
 * EXAMPLE 1:
 * Input: prices = [7, 1, 5, 3, 6, 4]
 * Output: 7
 * Explanation:
 * 1. Buy at 1, Sell at 5 -> Profit 4
 * 2. Buy at 3, Sell at 6 -> Profit 3
 * Total = 4 + 3 = 7.
 *
 * KEY INSIGHT (GREEDY VS DP):
 * 1. **Greedy Approach:**
 * If the price tomorrow is higher than today, we buy today and sell tomorrow.
 * We simply sum up all positive differences: (prices[i] - prices[i-1]).
 * Mathematically, (5 - 1) is the same as buying at 1 and selling at 5.
 * Buying at 1, selling at 3, buying at 3, selling at 5 is (3-1) + (5-3) = 2 + 2 = 4.
 * So, simply capturing every upward slope gives the max profit.
 *
 * 2. **Dynamic Programming Approach:**
 * Useful if the interviewer adds constraints later (like transaction fees or cooldowns).
 * We maintain two states:
 * - State 0 (Not Holding): We either rested or just sold.
 * - State 1 (Holding): We either rested or just bought.
 * ==================================================================================================
 */
public class BestTimeStockII {

    public static void main(String[] args) {
        int[] prices = {7, 1, 5, 3, 6, 4};
        System.out.println("Prices: " + Arrays.toString(prices));

        // 1. Recursive (Brute Force)
        System.out.println("1. Recursive       : " + maxProfitRecursive(0, 1, prices));

        // 2. Memoization
        int[][] memo = new int[prices.length][2];
        for (int[] row : memo) Arrays.fill(row, -1);
        System.out.println("2. Memoization     : " + maxProfitMemo(0, 1, prices, memo));

        // 3. Tabulation
        System.out.println("3. Tabulation      : " + maxProfitTabulation(prices));

        // 4. Space Optimized
        System.out.println("4. Space Optimized : " + maxProfitSpaceOptimized(prices));

        // 5. Greedy (Best Solution for this specific variant)
        System.out.println("5. Greedy          : " + maxProfitGreedy(prices));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION
     * ----------------------------------------------------------------------
     * State: (index, canBuy)
     * canBuy = 1 (Allowed to buy), canBuy = 0 (Must sell or rest)
     */
    private static int maxProfitRecursive(int i, int canBuy, int[] prices) {
        if (i == prices.length) return 0;

        int profit = 0;
        if (canBuy == 1) {
            // Option A: Buy (Subtract price) -> Move to state where we can't buy (0)
            int buy = -prices[i] + maxProfitRecursive(i + 1, 0, prices);
            // Option B: Don't Buy -> Stay in state where we can buy (1)
            int notBuy = 0 + maxProfitRecursive(i + 1, 1, prices);
            profit = Math.max(buy, notBuy);
        } else {
            // Option A: Sell (Add price) -> Move to state where we can buy (1)
            int sell = prices[i] + maxProfitRecursive(i + 1, 1, prices);
            // Option B: Don't Sell -> Stay in state where we can't buy (0)
            int notSell = 0 + maxProfitRecursive(i + 1, 0, prices);
            profit = Math.max(sell, notSell);
        }
        return profit;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * Cache the state dp[index][canBuy]
     */
    private static int maxProfitMemo(int i, int canBuy, int[] prices, int[][] dp) {
        if (i == prices.length) return 0;
        if (dp[i][canBuy] != -1) return dp[i][canBuy];

        int profit = 0;
        if (canBuy == 1) {
            int buy = -prices[i] + maxProfitMemo(i + 1, 0, prices, dp);
            int notBuy = 0 + maxProfitMemo(i + 1, 1, prices, dp);
            profit = Math.max(buy, notBuy);
        } else {
            int sell = prices[i] + maxProfitMemo(i + 1, 1, prices, dp);
            int notSell = 0 + maxProfitMemo(i + 1, 0, prices, dp);
            profit = Math.max(sell, notSell);
        }
        return dp[i][canBuy] = profit;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * Iterate from the last day backwards.
     */
    private static int maxProfitTabulation(int[] prices) {
        int n = prices.length;
        // dp[i][0] -> Max profit starting from day i with NO stock (can buy)
        // dp[i][1] -> Max profit starting from day i WITH stock (must sell)
        // wait, let's stick to the recursive definition:
        // col 1 = canBuy (Not Holding), col 0 = cannotBuy (Holding)
        int[][] dp = new int[n + 1][2];

        // Base case: dp[n][0] = dp[n][1] = 0 is implicit

        for (int i = n - 1; i >= 0; i--) {
            // State: We can buy (Not Holding)
            int buy = -prices[i] + dp[i + 1][0]; // 0 represents "cannot buy next" (Holding)
            int notBuy = 0 + dp[i + 1][1];       // 1 represents "can buy next" (Not Holding)
            dp[i][1] = Math.max(buy, notBuy);

            // State: We cannot buy (Holding -> Must Sell)
            int sell = prices[i] + dp[i + 1][1];
            int notSell = 0 + dp[i + 1][0];
            dp[i][0] = Math.max(sell, notSell);
        }
        return dp[0][1]; // Start at day 0 with ability to buy
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED DP
     * ----------------------------------------------------------------------
     * We only need the 'next' row (future) to calculate 'curr' (present).
     */
    private static int maxProfitSpaceOptimized(int[] prices) {
        int n = prices.length;
        int nextNotHolding = 0, nextHolding = 0;
        int currNotHolding, currHolding;

        // Definition:
        // NotHolding = We have 0 stock, we can buy.
        // Holding    = We have 1 stock, we must sell.

        for (int i = n - 1; i >= 0; i--) {
            // 1. Calculate max profit if we are currently Not Holding
            // Either Buy (-price) and become Holding OR Don't Buy and stay Not Holding
            currNotHolding = Math.max(-prices[i] + nextHolding, 0 + nextNotHolding);

            // 2. Calculate max profit if we are currently Holding
            // Either Sell (+price) and become Not Holding OR Don't Sell and stay Holding
            currHolding = Math.max(prices[i] + nextNotHolding, 0 + nextHolding);

            // Update next for the previous iteration
            nextNotHolding = currNotHolding;
            nextHolding = currHolding;
        }
        return nextNotHolding;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 5: GREEDY (PEAK VALLEY) - BEST FOR INTERVIEW
     * ----------------------------------------------------------------------
     * LOGIC:
     * We don't need DP. If the price goes UP tomorrow, capture that profit.
     * We simply collect every positive slope.
     *
     *
     *
     * Complexity: O(N) Time, O(1) Space.
     */
    private static int maxProfitGreedy(int[] prices) {
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                maxProfit += (prices[i] - prices[i - 1]);
            }
        }
        return maxProfit;
    }
}