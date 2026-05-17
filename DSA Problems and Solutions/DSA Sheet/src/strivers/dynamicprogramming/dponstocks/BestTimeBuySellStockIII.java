package com.questions.strivers.dynamicprogramming.dponstocks;
/**
 * ============================================================================
 * MASTERCLASS SOLUTION: 123. Best Time to Buy and Sell Stock III
 * ============================================================================
 * * PROBLEM STATEMENT:
 * You are given an array `prices` where `prices[i]` is the price of a given
 * stock on the ith day. Find the maximum profit you can achieve. You may
 * complete at most two transactions.
 * * Note: You may not engage in multiple transactions simultaneously (i.e., you
 * must sell the stock before you buy again).
 * * CONSTRAINTS:
 * - 1 <= prices.length <= 10^5
 * - 0 <= prices[i] <= 10^5
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION
 * ============================================================================
 * * 1. State Definition:
 * At any day 'i', our state is defined by three changing parameters:
 * - index (day): Current day from 0 to N-1
 * - buy (boolean/int): 1 if we are allowed to buy, 0 if we must sell
 * - cap (int): Number of transactions remaining (2, 1, or 0)
 *
 * * 2. Recursion Tree (Partial for prices = [3,3,5...]):
 *                              * f(day=0, buy=1, cap=2)
 *                              /                        \
 *                  [Buy at 3] /                          \ [Skip]
 *               f(day=1, buy=0, cap=2)               f(day=1, buy=1, cap=2)
 *              /              \                        /              \
 *          [Sell at 3]        [Skip]               [Buy at 3]        [Skip]
 *          f(d=2,b=1,c=1)  f(d=2,b=0,c=2)          f(d=2,b=0,c=2)    f(d=2,b=1,c=2)
 *
 * * 3. DP Table Transitions:
 * If buy == 1:
 * Profit = max( -prices[i] + dp[i+1][0][cap],    // Action: Buy
 * 0 + dp[i+1][1][cap] )           // Action: Skip
 * If buy == 0:
 * Profit = max( prices[i] + dp[i+1][1][cap-1],   // Action: Sell (cap decreases!)
 * 0 + dp[i+1][0][cap] )            // Action: Skip
 * * ============================================================================
 */

import java.util.Arrays;

public class BestTimeBuySellStockIII {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * Intuition:
     * On every single day, we have a choice. If we don't hold a stock, we can
     * either buy or skip. If we hold a stock, we can either sell or skip.
     * We explore every single valid combination of buys and sells and return
     * the maximum profit found.
     * * Complexity Analysis:
     * - Time Complexity: O(2^N) - At each step we make 2 choices, leading to an
     * exponential explosion of subproblems. Will TLE on LeetCode.
     * - Space Complexity: O(N) - Auxiliary stack space for recursion depth.
     */
    public int maxProfitBruteForce(int[] prices) {
        return solveRecursive(0, 1, 2, prices);
    }

    private int solveRecursive(int index, int buy, int cap, int[] prices) {
        // Base cases: If we run out of days or transactions, profit is 0.
        if (cap == 0 || index == prices.length) {
            return 0;
        }

        if (buy == 1) {
            // We can buy the stock. Take the max of buying today vs skipping.
            int takeBuy = -prices[index] + solveRecursive(index + 1, 0, cap, prices);
            int skipBuy = 0 + solveRecursive(index + 1, 1, cap, prices);
            return Math.max(takeBuy, skipBuy);
        } else {
            // We can sell the stock. Take the max of selling today vs skipping.
            // Note: Completing a sell decreases our transaction capacity.
            int takeSell = prices[index] + solveRecursive(index + 1, 1, cap - 1, prices);
            int skipSell = 0 + solveRecursive(index + 1, 0, cap, prices);
            return Math.max(takeSell, skipSell);
        }
    }

    /**
     * ========================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" stage)
     * ========================================================================
     * Intuition:
     * The recursion tree heavily recalculates overlapping subproblems (e.g.,
     * arriving at day 4 with 1 transaction left and no stock held can happen
     * through multiple different paths). We cache the results in a 3D array
     * memo[index][buy][cap] to compute each state exactly once.
     * * Complexity Analysis:
     * - Time Complexity: O(N * 2 * 3) ~ O(N) - We only calculate each state once.
     * - Space Complexity: O(N * 2 * 3) ~ O(N) heap space for the memo array,
     * plus O(N) auxiliary stack space for recursion.
     */
    public int maxProfitMemoization(int[] prices) {
        int n = prices.length;
        // 3D array: [days][buy (0 or 1)][capacity (0 to 2)]
        int[][][] memo = new int[n][2][3];
        for (int[][] arr2D : memo) {
            for (int[] arr1D : arr2D) {
                Arrays.fill(arr1D, -1);
            }
        }
        return solveMemo(0, 1, 2, prices, memo);
    }

    private int solveMemo(int index, int buy, int cap, int[] prices, int[][][] memo) {
        if (cap == 0 || index == prices.length) return 0;

        if (memo[index][buy][cap] != -1) return memo[index][buy][cap];

        if (buy == 1) {
            int takeBuy = -prices[index] + solveMemo(index + 1, 0, cap, prices, memo);
            int skipBuy = 0 + solveMemo(index + 1, 1, cap, prices, memo);
            return memo[index][buy][cap] = Math.max(takeBuy, skipBuy);
        } else {
            int takeSell = prices[index] + solveMemo(index + 1, 1, cap - 1, prices, memo);
            int skipSell = 0 + solveMemo(index + 1, 0, cap, prices, memo);
            return memo[index][buy][cap] = Math.max(takeSell, skipSell);
        }
    }

    /**
     * ========================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" stage)
     * ========================================================================
     * Intuition:
     * To eliminate recursion overhead and avoid stack overflow risks, we build
     * the DP table iteratively from the base cases up. Day N is initialized to 0,
     * and we work backward to Day 0.
     * * Complexity Analysis:
     * - Time Complexity: O(N * 2 * 3) ~ O(N) - 3 nested loops.
     * - Space Complexity: O(N * 2 * 3) ~ O(N) - Heap space for DP table, but
     * auxiliary stack space is completely eliminated.
     */
    public int maxProfitTabulation(int[] prices) {
        int n = prices.length;
        // Note: size n+1 to handle the index + 1 base case safely.
        int[][][] dp = new int[n + 1][2][3];

        // Base cases are natively 0 in Java, so explicit initialization isn't
        // strictly needed, but represented here conceptually:
        // for(int index = 0; index <= n; index++) { ... }
        // for(int buy = 0; buy <= 1; buy++) { ... }

        for (int index = n - 1; index >= 0; index--) {
            for (int buy = 0; buy <= 1; buy++) {
                // capacity 0 is always 0 profit, so we loop from 1 to 2
                for (int cap = 1; cap <= 2; cap++) {
                    if (buy == 1) {
                        int takeBuy = -prices[index] + dp[index + 1][0][cap];
                        int skipBuy = 0 + dp[index + 1][1][cap];
                        dp[index][buy][cap] = Math.max(takeBuy, skipBuy);
                    } else {
                        int takeSell = prices[index] + dp[index + 1][1][cap - 1];
                        int skipSell = 0 + dp[index + 1][0][cap];
                        dp[index][buy][cap] = Math.max(takeSell, skipSell);
                    }
                }
            }
        }
        return dp[0][1][2];
    }

    /**
     * ========================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" stage)
     * ========================================================================
     * Intuition:
     * Notice in tabulation that `dp[index]` relies ONLY on `dp[index + 1]`.
     * We do not need the entire N-sized array. We only need a 2D array representing
     * the "ahead" day and another representing the "current" day.
     * * Complexity Analysis:
     * - Time Complexity: O(N)
     * - Space Complexity: O(1) - specifically O(2 * 3) which is constant heap space.
     */
    public int maxProfitSpaceOptimized(int[] prices) {
        int n = prices.length;
        int[][] ahead = new int[2][3];
        int[][] curr = new int[2][3];

        for (int index = n - 1; index >= 0; index--) {
            for (int buy = 0; buy <= 1; buy++) {
                for (int cap = 1; cap <= 2; cap++) {
                    if (buy == 1) {
                        curr[buy][cap] = Math.max(-prices[index] + ahead[0][cap], ahead[1][cap]);
                    } else {
                        curr[buy][cap] = Math.max(prices[index] + ahead[1][cap - 1], ahead[0][cap]);
                    }
                }
            }
            // Move current day's calculations to 'ahead' for the next iteration
            for (int i = 0; i < 2; i++) {
                System.arraycopy(curr[i], 0, ahead[i], 0, 3);
            }
        }
        return ahead[1][2];
    }

    /**
     * ========================================================================
     * PHASE 5: STATE MACHINE APPROACH (The "Show-Off / Alternative" stage)
     * ========================================================================
     * Intuition:
     * We can push the space optimization even further by tracking exactly 4 variables
     * representing the maximum profit at 4 specific states throughout the timeline:
     * 1. After first buy (cost we want to minimize, hence max of negative price)
     * 2. After first sell (profit we want to maximize)
     * 3. After second buy (profit from 1st sell - cost of 2nd stock)
     * 4. After second sell (total maximized profit)
     * * Complexity Analysis:
     * - Time Complexity: O(N) - Single pass through the array.
     * - Space Complexity: O(1) - Just 4 primitive integer variables.
     */
    public int maxProfitStateMachine(int[] prices) {
        if (prices == null || prices.length == 0) return 0;

        // Initialize buys to negative infinity, sells to 0.
        int buy1 = Integer.MIN_VALUE;
        int sell1 = 0;
        int buy2 = Integer.MIN_VALUE;
        int sell2 = 0;

        for (int price : prices) {
            // The order matters slightly if conceptually isolating them, but taking
            // the max concurrently works perfectly in this sequential update.
            buy1 = Math.max(buy1, -price);              // Maximize balance after 1st buy
            sell1 = Math.max(sell1, buy1 + price);      // Maximize balance after 1st sell
            buy2 = Math.max(buy2, sell1 - price);       // Maximize balance after 2nd buy
            sell2 = Math.max(sell2, buy2 + price);      // Maximize balance after 2nd sell
        }

        return sell2; // Sell 2 will hold the absolute maximum profit.
    }
}