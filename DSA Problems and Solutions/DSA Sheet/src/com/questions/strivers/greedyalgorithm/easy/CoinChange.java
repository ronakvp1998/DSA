package com.questions.strivers.greedyalgorithm.easy;


import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: COIN CHANGE (LeetCode 322)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * You are given an integer array coins representing coins of different denominations
 * and an integer amount representing a total amount of money.
 * Return the fewest number of coins that you need to make up that amount.
 * If that amount of money cannot be made up by any combination of the coins, return -1.
 * You may assume that you have an infinite number of each kind of coin.
 *
 * Example 1:
 * Input: coins = [1,2,5], amount = 11
 * Output: 3
 * Explanation: 11 = 5 + 5 + 1

 * Example 2:
 * Input: coins = [2], amount = 3
 * Output: -1
 * Example 3:
 *
 * Input: coins = [1], amount = 0
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= coins.length <= 12
 * 1 <= coins[i] <= 231 - 1
 * 0 <= amount <= 104
 * ==================================================================================================
 */
public class CoinChange {

    public static void main(String[] args) {
        int[] coins = {1, 3, 4};
        int amount = 6;

        System.out.println("Coins: " + Arrays.toString(coins) + " | Amount: " + amount);
        System.out.println("--------------------------------------------------");

        // Greedy gives the WRONG answer (3 coins: 4, 1, 1)
        System.out.println("1. Greedy Approach : " + coinChangeGreedy(coins, amount) + " (INCORRECT)");

        // DP gives the RIGHT answer (2 coins: 3, 3)
        System.out.println("2. DP Tabulation   : " + coinChangeDP(coins, amount) + " (CORRECT)");
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: GREEDY (DO NOT USE FOR THIS PROBLEM)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Sort coins descending. Take as many of the largest coin as possible,
     * then move to the next largest.
     * * WHY IT FAILS:
     * It gets stuck in local optimums. If coins=[1, 3, 4] and amount=6,
     * Greedy returns 3 (4+1+1) instead of optimal 2 (3+3).
     */
    public static int coinChangeGreedy(int[] coins, int amount) {
        // Sort ascending, then we will iterate backwards
        Arrays.sort(coins);

        int count = 0;
        int remaining = amount;

        // Iterate from largest coin to smallest
        for (int i = coins.length - 1; i >= 0; i--) {
            if (coins[i] <= remaining) {
                // Take as many of this coin as possible
                count += remaining / coins[i];
                // Update remaining amount
                remaining = remaining % coins[i];
            }
            if (remaining == 0) break;
        }

        return remaining == 0 ? count : -1;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: DYNAMIC PROGRAMMING / TABULATION (THE CORRECT WAY)
     * ----------------------------------------------------------------------
     * LOGIC:
     * We build an array 'dp' where dp[i] is the minimum coins needed to make amount 'i'.
     * For every amount from 1 to 'amount', we try every coin.
     * dp[i] = min(dp[i], 1 + dp[i - coin])
     * * COMPLEXITY:
     * - Time: O(amount * N) where N is the number of coins.
     * - Space: O(amount) for the DP array.
     */
    public static int coinChangeDP(int[] coins, int amount) {
        // dp[i] will store the minimum coins needed for amount i.
        int[] dp = new int[amount + 1];

        // Initialize the array with a "max" value.
        // We use (amount + 1) because the max possible coins is 'amount' (all 1s).
        // Using Integer.MAX_VALUE can cause integer overflow when we add 1 to it later.
        int max = amount + 1;
        Arrays.fill(dp, max);

        // Base case: 0 coins are needed to make amount 0.
        dp[0] = 0;

        // Build the table bottom-up for every amount from 1 to 'amount'
        for (int i = 1; i <= amount; i++) {
            // Try every coin denomination
            for (int coin : coins) {
                // If the coin is less than or equal to the current amount we are trying to make
                if (coin <= i) {
                    // Update dp[i] if taking this coin results in a smaller count
                    dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
                }
            }
        }

        // If dp[amount] is still greater than 'amount', it means it was never updated,
        // so we cannot form this amount.
        return dp[amount] > amount ? -1 : dp[amount];
    }
}