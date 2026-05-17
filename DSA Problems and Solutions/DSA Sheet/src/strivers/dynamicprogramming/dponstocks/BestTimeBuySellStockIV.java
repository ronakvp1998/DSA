package com.questions.strivers.dynamicprogramming.dponstocks;

/**
 * ============================================================================
 * MASTERCLASS SOLUTION: 188. Best Time to Buy and Sell Stock IV
 * ============================================================================
 * * PROBLEM STATEMENT:
 * You are given an integer array prices and an integer k. Find the maximum
 * profit you can achieve. You may complete at most k transactions.
 * Note: You may not engage in multiple transactions simultaneously.
 * * CONSTRAINTS:
 * - 1 <= k <= 100
 * - 1 <= prices.length <= 1000
 * - 0 <= prices[i] <= 1000
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION
 * ============================================================================
 * * 1. State Definition:
 * Exactly the same as Stock III, but 'cap' scales up to 'k'.
 * - index (day): Current day from 0 to N-1
 * - buy (boolean/int): 1 if we can buy, 0 if we must sell
 * - cap (int): Number of transactions remaining (from k down to 0)
 * * 2. Recursion Tree (Partial for k=2, prices=[2,4,1]):
 * f(day=0, buy=1, cap=2)
 * /                        \
 * [Buy at 2] /                          \ [Skip]
 * f(day=1, buy=0, cap=2)               f(day=1, buy=1, cap=2)
 * /              \                     /              \
 * [Sell at 4]        [Skip]             [Buy at 4]        [Skip]
 * f(d=2,b=1,c=1)  f(d=2,b=0,c=2)     f(d=2,b=0,c=2)    f(d=2,b=1,c=2)
 * * 3. DP Table Transitions:
 * If buy == 1:
 * dp[i][1][cap] = max( -prices[i] + dp[i+1][0][cap], dp[i+1][1][cap] )
 * If buy == 0:
 * dp[i][0][cap] = max( prices[i] + dp[i+1][1][cap-1], dp[i+1][0][cap] )
 * * ============================================================================
 */

import java.util.Arrays;

public class BestTimeBuySellStockIV {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * Intuition:
     * On any given day, if we have remaining transaction capacity, we can either
     * execute a transaction (buy/sell) or skip the day. A transaction is only
     * completed (and capacity reduced) when we SELL. We explore all paths.
     * * Complexity Analysis:
     * - Time Complexity: O(2^N) - Exponential branching at every day. Will TLE.
     * - Space Complexity: O(N) - Auxiliary stack space for recursion depth.
     */
    public int maxProfitBruteForce(int k, int[] prices) {
        return solveRecursive(0, 1, k, prices);
    }

    private int solveRecursive(int index, int buy, int cap, int[] prices) {
        if (cap == 0 || index == prices.length) {
            return 0;
        }

        if (buy == 1) {
            int takeBuy = -prices[index] + solveRecursive(index + 1, 0, cap, prices);
            int skipBuy = 0 + solveRecursive(index + 1, 1, cap, prices);
            return Math.max(takeBuy, skipBuy);
        } else {
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
     * We cache the results of overlapping subproblems in a 3D array of size
     * [N][2][k+1]. This prevents recalculating the same state.
     * * Complexity Analysis:
     * - Time Complexity: O(N * 2 * k) -> O(N * k) - Each state is computed once.
     * - Space Complexity: O(N * 2 * k) for the memo table + O(N) stack space.
     */
    public int maxProfitMemoization(int k, int[] prices) {
        int n = prices.length;
        int[][][] memo = new int[n][2][k + 1];
        for (int[][] arr2D : memo) {
            for (int[] arr1D : arr2D) {
                Arrays.fill(arr1D, -1);
            }
        }
        return solveMemo(0, 1, k, prices, memo);
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
     * We convert the memoized logic into an iterative DP table built from the
     * base cases (day N) backwards to day 0.
     * * Complexity Analysis:
     * - Time Complexity: O(N * 2 * k) -> O(N * k)
     * - Space Complexity: O(N * 2 * k) -> O(N * k) for the 3D array.
     */
    public int maxProfitTabulation(int k, int[] prices) {
        int n = prices.length;
        if (n == 0 || k == 0) return 0;

        int[][][] dp = new int[n + 1][2][k + 1];

        for (int index = n - 1; index >= 0; index--) {
            for (int buy = 0; buy <= 1; buy++) {
                for (int cap = 1; cap <= k; cap++) {
                    if (buy == 1) {
                        dp[index][buy][cap] = Math.max(-prices[index] + dp[index + 1][0][cap],
                                0 + dp[index + 1][1][cap]);
                    } else {
                        dp[index][buy][cap] = Math.max(prices[index] + dp[index + 1][1][cap - 1],
                                0 + dp[index + 1][0][cap]);
                    }
                }
            }
        }
        return dp[0][1][k];
    }

    /**
     * ========================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" stage)
     * ========================================================================
     * Intuition:
     * Since `dp[index]` only relies on `dp[index + 1]`, we can compress the
     * N-sized dimension of our 3D array into two 2D arrays (current and ahead).
     * * Complexity Analysis:
     * - Time Complexity: O(N * k)
     * - Space Complexity: O(2 * k) -> O(k) which is highly optimized.
     */
    public int maxProfitSpaceOptimized(int k, int[] prices) {
        int n = prices.length;
        if (n == 0 || k == 0) return 0;

        int[][] ahead = new int[2][k + 1];
        int[][] curr = new int[2][k + 1];

        for (int index = n - 1; index >= 0; index--) {
            for (int buy = 0; buy <= 1; buy++) {
                for (int cap = 1; cap <= k; cap++) {
                    if (buy == 1) {
                        curr[buy][cap] = Math.max(-prices[index] + ahead[0][cap], ahead[1][cap]);
                    } else {
                        curr[buy][cap] = Math.max(prices[index] + ahead[1][cap - 1], ahead[0][cap]);
                    }
                }
            }
            for (int i = 0; i < 2; i++) {
                System.arraycopy(curr[i], 0, ahead[i], 0, k + 1);
            }
        }
        return ahead[1][k];
    }

    /**
     * ========================================================================
     * PHASE 5: THE "INFINITE TRANSACTIONS" OPTIMIZATION (Senior Insight)
     * ========================================================================
     * Intuition:
     * A transaction requires at least 2 days (1 to buy, 1 to sell). If 'k' is
     * greater than or equal to N / 2, it means we effectively have INFINITE
     * transactions available. At this point, the capacity constraint 'k' doesn't
     * matter, and the problem reduces to Best Time to Buy and Sell Stock II.
     * We can just greedily capture every single upward price movement in O(N)
     * time and O(1) space, skipping the O(N * k) DP table entirely.
     * * Complexity Analysis:
     * - Time Complexity: O(N) if k >= N/2, otherwise O(N * k)
     * - Space Complexity: O(1) if k >= N/2, otherwise O(k)
     */
    public int maxProfitUltimate(int k, int[] prices) {
        int n = prices.length;
        if (n == 0 || k == 0) return 0;

        // Peak/Valley Greedy Optimization (Stock II logic)
        if (k >= n / 2) {
            int maxProfit = 0;
            for (int i = 1; i < n; i++) {
                if (prices[i] > prices[i - 1]) {
                    maxProfit += prices[i] - prices[i - 1];
                }
            }
            return maxProfit;
        }

        // Standard Space-Optimized DP fallback
        return maxProfitSpaceOptimized(k, prices);
    }
}