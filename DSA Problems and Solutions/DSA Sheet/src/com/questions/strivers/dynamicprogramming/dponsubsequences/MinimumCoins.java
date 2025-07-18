package com.questions.strivers.dynamicprogramming.dponsubsequences;
//https://takeuforward.org/data-structure/minimum-coins-dp-20/

//Minimum Coins
// We are given a target sum of ‘X’ and ‘N’ distinct numbers denoting the coin denominations.
// We need to tell the minimum number of coins required to reach the target sum.
// We can pick a coin denomination for any number of times we want.
// arr[] = {1,2,3} target = 7 => solution = 3 => we will take 3 coins (3,3,1)
// greedy approach will not work here because values are not uniform
import java.util.Arrays;

public class MinimumCoins {

    // ---------------------------------------
    // 1. Plain Recursion (Top-down without memo)
    // ---------------------------------------
    // Time: Exponential O(2^target)
    // Space: O(target) stack space due to recursion
    public static int recursive(int index, int target, int[] coins) {
        // Base case: when we are at coin 0
        if (index == 0) {
            // If target is divisible by coin[0], return count
            if (target % coins[0] == 0) {
                return target / coins[0];
            } else {
                return (int) 1e9; // Large value = impossible case
            }
        }
        // Not take the coin
        int notTake = recursive(index - 1, target, coins);
        // Take the coin (if it's not more than target)
        int take = (int) 1e9;
        if (coins[index] <= target) {
            take = 1 + recursive(index, target - coins[index], coins);
        }
         return Math.min(take, notTake);
    }

    // ---------------------------------------
    // 2. Recursion + Memoization
    // ---------------------------------------
    // Time: O(N * target)
    // Space: O(N * target) for dp + O(target) recursion stack
    public static int memo(int index, int target, int[] coins, int[][] dp) {
        if (index == 0) {
            if (target % coins[0] == 0) {
                return target / coins[0];
            } else {
                return (int) 1e9;
            }
        }

        if (dp[index][target] != -1) return dp[index][target];

        int notTake = memo(index - 1, target, coins, dp);

        int take = (int) 1e9;
        if (coins[index] <= target) {
            take = 1 + memo(index, target - coins[index], coins, dp);
        }

        return dp[index][target] = Math.min(take, notTake);
    }

    // ---------------------------------------
    // 3. Tabulation (Bottom-Up DP)
    // ---------------------------------------
    // Time: O(N * target)
    // Space: O(N * target)
    public static int tabulation(int[] coins, int target) {
        int n = coins.length;
        int[][] dp = new int[n][target + 1];

        // Base case initialization
        for (int t = 0; t <= target; t++) {
            if (t % coins[0] == 0) {
                dp[0][t] = t / coins[0];
            } else {
                dp[0][t] = (int) 1e9;
            }
        }

        for (int i = 1; i < n; i++) {
            for (int t = 0; t <= target; t++) {
                int notTake = dp[i - 1][t];
                int take = (int) 1e9;
                if (coins[i] <= t) {
                    take = 1 + dp[i][t - coins[i]];
                }
                dp[i][t] = Math.min(take, notTake);
            }
        }

        int ans = dp[n - 1][target];
        return ans >= (int) 1e9 ? -1 : ans;
    }

    // ---------------------------------------
    // 4. Space Optimization
    // ---------------------------------------
    // Time: O(N * target)
    // Space: O(target)
    public static int spaceOptimized(int[] coins, int target) {
        int n = coins.length;
        int[] prev = new int[target + 1];

        // Base case for coin[0]
        for (int t = 0; t <= target; t++) {
            if (t % coins[0] == 0) {
                prev[t] = t / coins[0];
            } else {
                prev[t] = (int) 1e9;
            }
        }

        for (int i = 1; i < n; i++) {
            int[] curr = new int[target + 1];
            for (int t = 0; t <= target; t++) {
                int notTake = prev[t];
                int take = (int) 1e9;
                if (coins[i] <= t) {
                    take = 1 + curr[t - coins[i]]; // still same row: curr[]
                }
                curr[t] = Math.min(take, notTake);
            }
            prev = curr;
        }

        return prev[target] >= (int) 1e9 ? -1 : prev[target];
    }

    // ---------------------------------------
    // Main method to test all approaches
    // ---------------------------------------
    public static void main(String[] args) {
        int[] coins = {1, 2, 5};
        int target = 11;
        int n = coins.length;

        // 1. Plain Recursion
        System.out.println("Recursive: " + recursive(n - 1, target, coins)); // Not recommended for large input

        // 2. Memoization
        int[][] dp = new int[n][target + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        int memoAns = memo(n - 1, target, coins, dp);
        System.out.println("Memoization: " + (memoAns >= (int) 1e9 ? -1 : memoAns));

        // 3. Tabulation
        System.out.println("Tabulation: " + tabulation(coins, target));

        // 4. Space Optimized
        System.out.println("Space Optimized: " + spaceOptimized(coins, target));
    }
}
