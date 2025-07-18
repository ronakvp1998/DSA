package com.questions.strivers.dynamicprogramming.dponsubsequences;
//https://takeuforward.org/data-structure/coin-change-2-dp-22/
//Problem Link: Ways to Make a Coin Change
//We are given an array Arr with N distinct coins and a target.
//We have an infinite supply of each coin denomination.
//We need to find the number of ways we sum up the coin values to give us the target.
//Each coin can be used any number of times.
//Example: arr = [1,2,3] target = 4 => answer: 4 (1+1+1+1, 1+1+2, 2+2, 1+3)
// code tested and submitted
import java.util.Arrays;
public class CoinChangeWays {

    // 1. Recursive Approach (Brute Force)
    public static int countWaysRecursive(int index, int[] arr, int target) {
        // Base case: when we are at the 0th coin
        // We can only form the target if it's divisible by arr[0]
        if (index == 0) {
            return (target % arr[0] == 0) ? 1 : 0;
        }

        // Choice 1: Do not take the current coin, move to the previous coin
        int notTake = countWaysRecursive(index - 1, arr, target);

        // Choice 2: Take the current coin if it does not exceed target
        int take = 0;
        if (arr[index] <= target) {
            // Since coins are unlimited, stay at the same index
            take = countWaysRecursive(index, arr, target - arr[index]);
        }

        // Total ways = take + notTake
        return take + notTake;
    }
    // Time Complexity: O(2^target) — exponential due to 2 choices at each step
    // Space Complexity: O(target) — max recursion stack depth

    // 2. Memoization Approach (Top-Down Dynamic Programming)
    public static int countWaysMemoization(int index, int[] arr, int target, int[][] dp) {
        // Base case: check if target is divisible by arr[0]
        if (index == 0) {
            return (target % arr[0] == 0) ? 1 : 0;
        }

        // If already computed, return cached result
        if (dp[index][target] != -1) {
            return dp[index][target];
        }

        // Option 1: Do not take current coin
        int notTake = countWaysMemoization(index - 1, arr, target, dp);

        // Option 2: Take current coin if valid
        int take = 0;
        if (arr[index] <= target) {
            take = countWaysMemoization(index, arr, target - arr[index], dp);
        }

        // Store result in dp array and return
        return dp[index][target] = take + notTake;
    }
    // Time Complexity: O(N * Target) — every subproblem is solved once
    // Space Complexity: O(N * Target) + O(Target) recursion stack

    // 3. Tabulation Approach (Bottom-Up DP)
    public static int countWaysTabulation(int[] arr, int target) {
        int n = arr.length;

        // dp[i][t] represents number of ways to make sum 't' using first (i+1) coins
        int[][] dp = new int[n][target + 1];

        // Initialize base case: using only the first coin
        for (int t = 0; t <= target; t++) {
            dp[0][t] = (t % arr[0] == 0) ? 1 : 0;  // use arr[0] repeatedly
        }

        // Build the dp table row by row
        for (int i = 1; i < n; i++) { // for each coin
            for (int t = 0; t <= target; t++) { // for each target sum
                int notTake = dp[i - 1][t]; // don't take coin i
                int take = 0;
                if (arr[i] <= t) {
                    take = dp[i][t - arr[i]]; // take coin i, stay at same i
                }
                dp[i][t] = take + notTake; // total ways
            }
        }

        return dp[n - 1][target]; // answer is in last row, column 'target'
    }
    // Time Complexity: O(N * Target)
    // Space Complexity: O(N * Target)

    // 4. Space Optimized Approach
    public static int countWaysSpaceOptimized(int[] arr, int target) {
        int n = arr.length;

        // prev[t] stores ways to form sum 't' using coins up to previous row
        int[] prev = new int[target + 1];

        // Initialize base case for first coin
        for (int t = 0; t <= target; t++) {
            prev[t] = (t % arr[0] == 0) ? 1 : 0;
        }

        // Process one coin at a time
        for (int i = 1; i < n; i++) {
            // Current row dp
            int[] curr = new int[target + 1];

            for (int t = 0; t <= target; t++) {
                int notTake = prev[t]; // don't take current coin
                int take = 0;
                if (arr[i] <= t) {
                    take = curr[t - arr[i]]; // take current coin and stay on same row
                }
                curr[t] = take + notTake; // total ways
            }

            // Move current row to prev for next iteration
            prev = curr;
        }

        return prev[target];
    }
    // Time Complexity: O(N * Target)
    // Space Complexity: O(Target)

    // Driver Code to test all methods
    public static void main(String[] args) {
        int[] arr = {1, 2, 3}; // Available coin denominations
        int target = 4;        // Amount we want to form
        int n = arr.length;    // Number of coins

        // 1. Recursive
        System.out.println("Recursive: " + countWaysRecursive(n - 1, arr, target));

        // 2. Memoization
        int[][] dp = new int[n][target + 1];
        for (int[] row : dp) Arrays.fill(row, -1); // Initialize memo table
        System.out.println("Memoization: " + countWaysMemoization(n - 1, arr, target, dp));

        // 3. Tabulation
        System.out.println("Tabulation: " + countWaysTabulation(arr, target));

        // 4. Space Optimized
        System.out.println("Space Optimized: " + countWaysSpaceOptimized(arr, target));
    }
}
