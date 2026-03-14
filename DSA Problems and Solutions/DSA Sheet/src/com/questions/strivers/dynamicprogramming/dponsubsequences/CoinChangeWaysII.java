package com.questions.strivers.dynamicprogramming.dponsubsequences;

/**
 * ============================================================================
 * 518. Coin Change II
 * ============================================================================
 * * PROBLEM STATEMENT:
 * You are given an integer array coins representing coins of different
 * denominations and an integer amount representing a total amount of money.
 * Return the number of combinations that make up that amount. If that amount
 * of money cannot be made up by any combination of the coins, return 0.
 * You may assume that you have an infinite number of each kind of coin.
 * The answer is guaranteed to fit into a signed 32-bit integer.
 * * CONSTRAINTS:
 * - 1 <= coins.length <= 300
 * - 1 <= coins[i] <= 5000
 * - All the values of coins are unique.
 * - 0 <= amount <= 5000
 * * EXAMPLES:
 * Example 1:
 * Input: amount = 5, coins = [1,2,5] | Output: 4
 * Explanation: 5=5, 5=2+2+1, 5=2+1+1+1, 5=1+1+1+1+1
 * * Example 2:
 * Input: amount = 3, coins = [2] | Output: 0
 * * Example 3:
 * Input: amount = 10, coins = [10] | Output: 1
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION: RECURSION TREE (Amount = 5, Coins = [1, 2, 5])
 * ============================================================================
 * f(index, amount) -> choices: pick coin (stay at index), skip coin (index-1)
 * *                            f(2, 5) [Consider 5]
 *                            /                     \
 *                  (Pick 5) /                       \ (Skip 5)
 *                  f(2, 0) == 1                f(1, 5) [Consider 2]
 *                            /                    \
 *                   (Pick 2)/                      \(Skip 2)
 *                   f(1, 3)                     f(0, 5) [Consider 1]
 *                  /       \                       | (All 1s) -> 1 way
 *              (Pick 2)    (Skip 2)
 *              f(1, 1)      f(0, 3) -> 1 way
 *              /       \
 *          (Pick 2 X)   (Skip 2)
 *          f(1, -1)==0    f(0, 1) -> 1 way
 *
 * * Total ways = 1 (from f(2,0)) + 1 (from f(0,5)) + 1 (from f(0,3)) + 1 (from f(0,1)) = 4 ways.
 * FINAL DP STATE (After full tabulation):
 *  * Target:  0  1  2  3  4  5
 *  * Coin 1: [1, 1, 1, 1, 1, 1]
 *  * Coin 2: [1, 1, 2, 2, 3, 3]  <- Notice target=5 gives 3 ways using {1,2}
 *  * Coin 5: [1, 1, 2, 2, 3, 4]  <- Final answer at bottom right: 4
 */

import java.util.Arrays;

public class CoinChangeWaysII {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE RECURSION (The "Think it" stage)
     * ========================================================================
     * INTUITION:
     * This is a classic "Take it or Leave it" Unbounded Knapsack problem.
     * Since we have an infinite supply of each coin, if we choose to "Take" a
     * coin, we DO NOT decrement our index—we might want to take it again.
     * If we choose to "Leave" it, we move to the next coin (index - 1).
     * The base cases hit when amount reaches 0 (1 valid way found) or index
     * drops below 0 / amount becomes negative (0 valid ways).
     * * COMPLEXITY:
     * - Time: O(2^(amount/min_coin)) -> Exponential. Every state branches into
     * two, and depth can go up to amount/min_coin.
     * - Space: O(amount/min_coin) -> Auxiliary stack space for recursion depth.
     */
    public int changeRecursive(int amount, int[] coins) {
        return solveRecursive(coins.length - 1, amount, coins);
    }

    private int solveRecursive(int ind, int target, int[] coins) {
        // Base Cases
        if (ind == 0) {
            // If we only have the first coin left, target must be perfectly divisible
            return (target % coins[0] == 0) ? 1 : 0;
        }

        int notTake = solveRecursive(ind - 1, target, coins);
        int take = 0;
        if (coins[ind] <= target) {
            take = solveRecursive(ind, target - coins[ind], coins); // Notice 'ind' stays the same
        }

        return notTake + take;
    }

    /**
     * ========================================================================
     * PHASE 2: TOP-DOWN MEMOIZATION (The "Refine it" stage)
     * ========================================================================
     * INTUITION:
     * The recursion tree above clearly shows overlapping subproblems (e.g.,
     * solving for amount=3 with coins=[1,2] multiple times). We introduce a 2D
     * cache `dp[index][amount]` to store results of `f(ind, target)` so we
     * never compute the same state twice.
     * * COMPLEXITY:
     * - Time: O(N * amount) -> N states for coins, target states up to amount.
     * - Space: O(N * amount) Heap Space for DP array + O(amount) Auxiliary Stack Space.
     */
    public int changeMemo(int amount, int[] coins) {
        int n = coins.length;
        int[][] dp = new int[n][amount + 1];
        for (int[] row : dp) Arrays.fill(row, -1);

        return solveMemo(n - 1, amount, coins, dp);
    }

    private int solveMemo(int ind, int target, int[] coins, int[][] dp) {
        if (ind == 0) return (target % coins[0] == 0) ? 1 : 0;

        if (dp[ind][target] != -1) return dp[ind][target];

        int notTake = solveMemo(ind - 1, target, coins, dp);
        int take = 0;
        if (coins[ind] <= target) {
            take = solveMemo(ind, target - coins[ind], coins, dp);
        }

        return dp[ind][target] = notTake + take;
    }

    /**
     * ========================================================================
     * PHASE 3: BOTTOM-UP TABULATION (The "Build it" stage)
     * ========================================================================
     * INTUITION:
     * We convert our recursive memoization into an iterative approach to eliminate
     * recursion stack overhead. We populate `dp[ind][target]` from base cases up.
     * * INITIAL DP STATE (Amount = 5, Coins = [1, 2, 5]):
     * Right after base case initialization (ind = 0), before the nested loops:
     * Target:  0  1  2  3  4  5
     * Coin 1: [1, 1, 1, 1, 1, 1]  <- Base case populated
     * Coin 2: [0, 0, 0, 0, 0, 0]
     * Coin 5: [0, 0, 0, 0, 0, 0]
     * * FINAL DP STATE (After full tabulation):
     * Target:  0  1  2  3  4  5
     * Coin 1: [1, 1, 1, 1, 1, 1]
     * Coin 2: [1, 1, 2, 2, 3, 3]  <- Notice target=5 gives 3 ways using {1,2}
     * Coin 5: [1, 1, 2, 2, 3, 4]  <- Final answer at bottom right: 4
     * * COMPLEXITY:
     * - Time: O(N * amount) -> Nested loops over N coins and amount.
     * - Space: O(N * amount) Heap Space -> We maintain a full 2D array.
     */
    public int changeTabulation(int amount, int[] coins) {
        int n = coins.length;
        int[][] dp = new int[n][amount + 1];

        // Base Case Initialization: Target divisible by first coin
        for (int T = 0; T <= amount; T++) {
            if (T % coins[0] == 0) dp[0][T] = 1;
        }

        // Main Tabulation Loops
        for (int ind = 1; ind < n; ind++) {
            for (int T = 0; T <= amount; T++) {
                int notTake = dp[ind - 1][T];
                int take = 0;
                if (coins[ind] <= T) {
                    take = dp[ind][T - coins[ind]]; // Check current row (infinite supply)
                }
                dp[ind][T] = notTake + take;
            }
        }

        return dp[n - 1][amount];
    }

    /**
     * ========================================================================
     * PHASE 4: SPACE OPTIMIZATION (The "Perfect it" stage)
     * ========================================================================
     * INTUITION:
     * If you look at the recurrence relation:
     * `dp[ind][T] = dp[ind-1][T] + dp[ind][T-coins[ind]]`
     * To compute the current row (`ind`), we only ever need values from the
     * previous row (`ind-1`) and values to the left on the current row.
     * Therefore, we can reduce the 2D array to a 1D array. As we iterate left to
     * right, `prev[T]` naturally acts as `dp[ind-1][T]`, and newly updated values
     * in `prev` act as `dp[ind][T-coins[ind]]`.
     * * COMPLEXITY:
     * - Time: O(N * amount)
     * - Space: O(amount) -> Reduced from O(N * amount) to a single 1D array!
     */
    public int changeSpaceOptimized(int amount, int[] coins) {
        int[] prev = new int[amount + 1];

        // Base Case Initialization
        for (int T = 0; T <= amount; T++) {
            if (T % coins[0] == 0) prev[T] = 1;
        }

        // Process remaining coins
        for (int ind = 1; ind < coins.length; ind++) {
            // Traverse left to right so we use newly updated values of the same coin
            for (int T = 0; T <= amount; T++) {
                int notTake = prev[T];
                int take = 0;
                if (coins[ind] <= T) {
                    take = prev[T - coins[ind]];
                }
                prev[T] = notTake + take;
            }
        }

        return prev[amount];
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        CoinChangeWaysII solver = new CoinChangeWaysII();

        System.out.println("=== Test Case 1 ===");
        int[] coins1 = {1, 2, 5}; int amount1 = 5;
        System.out.println("Brute Force: " + solver.changeRecursive(amount1, coins1));
        System.out.println("Memoization: " + solver.changeMemo(amount1, coins1));
        System.out.println("Tabulation : " + solver.changeTabulation(amount1, coins1));
        System.out.println("Space Opt  : " + solver.changeSpaceOptimized(amount1, coins1));
        System.out.println("Expected   : 4\n");

        System.out.println("=== Test Case 2 ===");
        int[] coins2 = {2}; int amount2 = 3;
        System.out.println("Space Opt  : " + solver.changeSpaceOptimized(amount2, coins2));
        System.out.println("Expected   : 0\n");

        System.out.println("=== Test Case 3 ===");
        int[] coins3 = {10}; int amount3 = 10;
        System.out.println("Space Opt  : " + solver.changeSpaceOptimized(amount3, coins3));
        System.out.println("Expected   : 1\n");

        System.out.println("=== Edge Case: Amount 0 ===");
        int[] coins4 = {1, 2, 3}; int amount4 = 0;
        System.out.println("Space Opt  : " + solver.changeSpaceOptimized(amount4, coins4));
        System.out.println("Expected   : 1"); // 1 way to make 0 amount: use no coins
    }
}