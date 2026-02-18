package com.questions.strivers.dynamicprogramming.stocks;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: BEST TIME TO BUY AND SELL STOCK WITH TRANSACTION FEE (LeetCode 714)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * You are given an array 'prices' and an integer 'fee'.
 * You can buy and sell stocks multiple times, but you must pay the 'fee' for each transaction.
 * Return the maximum profit.
 *
 * EXAMPLE 1:
 * Input: prices = [1, 3, 2, 8, 4, 9], fee = 2
 * Output: 8
 * Explanation:
 * 1. Buy at 1, Sell at 8 -> Profit: (8 - 1) - 2 = 5
 * 2. Buy at 4, Sell at 9 -> Profit: (9 - 4) - 2 = 3
 * Total Profit = 5 + 3 = 8.
 *
 * KEY INSIGHT:
 * Unlike the standard "Infinite Transactions" problem where we just greedily take every positive slope,
 * the fee forces us to be selective. A small profit might turn into a loss after the fee.
 *
 * We track two states for every day:
 * 1. **Holding (Buy State):** Max profit if we end the day holding a stock.
 * 2. **Not Holding (Sell State):** Max profit if we end the day with 0 stocks.
 *
 * TRANSITIONS:
 * - If Holding: We either keep holding (rest) OR we just bought today.
 * - If Not Holding: We either keep not holding (rest) OR we just sold today (and paid the fee).
 * ==================================================================================================
 */
public class BestTimeStockTransactionFee {

    public static void main(String[] args) {
        int[] prices = {1, 3, 2, 8, 4, 9};
        int fee = 2;

        System.out.println("Prices: " + Arrays.toString(prices));
        System.out.println("Fee   : " + fee);
        System.out.println("--------------------------------------------------");

        // 1. Recursive
        System.out.println("1. Recursion       : " + maxProfitRecursive(0, 0, prices, fee));

        // 2. Memoization
        int[][] dp = new int[prices.length][2];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("2. Memoization     : " + maxProfitMemo(0, 0, prices, fee, dp));

        // 3. Tabulation
        System.out.println("3. Tabulation      : " + maxProfitTabulation(prices, fee));

        // 4. Space Optimized
        System.out.println("4. Space Optimized : " + maxProfitSpaceOptimized(prices, fee));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION
     * ----------------------------------------------------------------------
     * LOGIC:
     * Try all possibilities.
     * State: (index, hasStock)
     * hasStock = 0 (No Stock, can Buy), hasStock = 1 (Has Stock, must Sell)
     *
     * Note: We subtract the fee when we SELL to complete the transaction.
     */
    private static int maxProfitRecursive(int i, int hasStock, int[] prices, int fee) {
        if (i == prices.length) return 0;

        int profit = 0;
        if (hasStock == 0) {
            // Option 1: Buy (Subtract price) -> Move to hasStock=1
            int buy = -prices[i] + maxProfitRecursive(i + 1, 1, prices, fee);
            // Option 2: Rest -> Stay hasStock=0
            int rest = maxProfitRecursive(i + 1, 0, prices, fee);
            profit = Math.max(buy, rest);
        } else {
            // Option 1: Sell (Add price, Subtract Fee) -> Move to hasStock=0
            int sell = (prices[i] - fee) + maxProfitRecursive(i + 1, 0, prices, fee);
            // Option 2: Rest -> Stay hasStock=1
            int rest = maxProfitRecursive(i + 1, 1, prices, fee);
            profit = Math.max(sell, rest);
        }
        return profit;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * Cache the results in dp[index][hasStock].
     * Time: O(N)
     * Space: O(N) + Stack
     */
    private static int maxProfitMemo(int i, int hasStock, int[] prices, int fee, int[][] dp) {
        if (i == prices.length) return 0;
        if (dp[i][hasStock] != -1) return dp[i][hasStock];

        int profit;
        if (hasStock == 0) {
            // Can Buy
            profit = Math.max(
                    -prices[i] + maxProfitMemo(i + 1, 1, prices, fee, dp), // Buy
                    maxProfitMemo(i + 1, 0, prices, fee, dp)               // Rest
            );
        } else {
            // Must Sell
            profit = Math.max(
                    (prices[i] - fee) + maxProfitMemo(i + 1, 0, prices, fee, dp), // Sell
                    maxProfitMemo(i + 1, 1, prices, fee, dp)                      // Rest
            );
        }
        return dp[i][hasStock] = profit;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * Iterate backwards from the last day.
     * dp[i][0] = Max profit from day i onwards if we currently have NO stock.
     * dp[i][1] = Max profit from day i onwards if we currently HAVE stock.
     */
    private static int maxProfitTabulation(int[] prices, int fee) {
        int n = prices.length;
        int[][] dp = new int[n + 1][2];

        // Base case dp[n][0] = dp[n][1] = 0 is implicit

        for (int i = n - 1; i >= 0; i--) {
            // State 0: Not Holding (Can Buy or Rest)
            // If Buy: Pay price, move to state 1
            // If Rest: Take profit from next day state 0
            dp[i][0] = Math.max(-prices[i] + dp[i + 1][1], dp[i + 1][0]);

            // State 1: Holding (Can Sell or Rest)
            // If Sell: Gain price, Pay Fee, move to state 0
            // If Rest: Take profit from next day state 1
            dp[i][1] = Math.max((prices[i] - fee) + dp[i + 1][0], dp[i + 1][1]);
        }

        return dp[0][0]; // Start at day 0 with no stock
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED (BEST SOLUTION)
     * ----------------------------------------------------------------------
     * We only rely on 'next' day values.
     * We can maintain two variables:
     * - `effectiveBuy`: Max profit if we end the day Holding a stock.
     * - `effectiveSell`: Max profit if we end the day Not Holding a stock.
     *
     * This essentially tracks the state machine logic forward.
     *
     *
     *
     * Time: O(N)
     * Space: O(1)
     */
    private static int maxProfitSpaceOptimized(int[] prices, int fee) {
        int n = prices.length;

        // Initial states at Day 0
        // If we buy on day 0, profit is -price.
        // If we don't buy on day 0, profit is 0.
        int effectiveBuy = -prices[0];
        int effectiveSell = 0;

        for (int i = 1; i < n; i++) {
            // Update Holding State (effectiveBuy)
            // Either keep holding (effectiveBuy) OR buy new stock today (effectiveSell - prices[i])
            // Note: We use the OLD effectiveSell here, which represents "Not Holding yesterday".
            effectiveBuy = Math.max(effectiveBuy, effectiveSell - prices[i]);

            // Update Not Holding State (effectiveSell)
            // Either keep not holding (effectiveSell) OR sell today (effectiveBuy + prices[i] - fee)
            // Note: We use the NEW effectiveBuy?
            // Actually, in the standard forward iteration logic:
            // To sell today, we must have been holding YESTERDAY.
            // But if we update effectiveBuy first, it might represent buying TODAY.
            // Can we buy and sell on same day?
            // "You may not engage in multiple transactions simultaneously".
            // However, usually we can hold old variables in temps.
            // But interestingly, if we update effectiveBuy first:
            // effectiveBuy = max(hold, sell - price)
            // effectiveSell = max(rest, effectiveBuy + price - fee)
            // If effectiveBuy represented "buy today", then effectiveSell would mean "buy today AND sell today".
            // (price - price - fee) = -fee. This is always worse than 0. So order doesn't strictly break correctness,
            // but conceptually, using temp is cleaner.

            // Let's use the explicit logic from Tabulation to be safe:
            int newBuy = Math.max(effectiveBuy, effectiveSell - prices[i]);
            int newSell = Math.max(effectiveSell, effectiveBuy + prices[i] - fee);

            effectiveBuy = newBuy;
            effectiveSell = newSell;
        }

        return effectiveSell;
    }
}