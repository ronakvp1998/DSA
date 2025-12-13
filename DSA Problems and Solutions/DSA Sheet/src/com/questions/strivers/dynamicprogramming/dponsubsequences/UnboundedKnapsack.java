package com.questions.strivers.dynamicprogramming.dponsubsequences;
//Unbounded Knapsack
//A thief wants to rob a store. He is carrying a bag of capacity W.
//The store has ‘n’ items of infinite supply.
//Its weight is given by the ‘wt’ array and its value by the ‘val’ array.
//He can either include an item in its knapsack or exclude it but can’t partially have it as a fraction.
//We need to find the maximum value of items that the thief can steal.
//He can take a single item any number of times he wants and put it in his knapsack.
// N=3, W=10
// wt = {2, 4, 6}
// val = {5, 11, 13}
// answer = 27 (11 + 11 + 5) => 2 items of weight 4 and 1 item of weight 2

import java.util.Arrays;

public class UnboundedKnapsack {

    // 1️⃣ Recursive Approach
    private static int unboundedKnapsackRecursive(int index, int[] wt, int[] val, int W) {
        // Base case: when we're at the first item (index 0)
        // We can take this item as many times as it fits into capacity W
        if (index == 0) {
            return (W / wt[0]) * val[0]; // Max times we can take item 0
        }

        // Choice 1: Do not take the current item
        int notTake = 0 + unboundedKnapsackRecursive(index - 1, wt, val, W);

        // Choice 2: Take the current item if it fits in remaining capacity
        int take = 0;
        if (wt[index] <= W) {
            // If taking, stay at the same index since items are infinite
            take = val[index] + unboundedKnapsackRecursive(index, wt, val, W - wt[index]);
        }

        // Return the maximum of both choices
        return Math.max(take, notTake);
    }
    // Time Complexity: O(2^W) — exponential due to overlapping choices
    // Space Complexity: O(W) — recursion stack depth

    // 2️⃣ Memoization (Top-Down DP)
    private static int unboundedKnapsackMemo(int index, int[] wt, int[] val, int W, int[][] dp) {
        // Base case: only one item to consider
        if (index == 0) {
            return (W / wt[0]) * val[0];
        }

        // If already computed, return cached result
        if (dp[index][W] != -1)
            return dp[index][W];

        int notTake = unboundedKnapsackMemo(index - 1, wt, val, W, dp);
        int take = 0;
        if (wt[index] <= W) {
            take = val[index] + unboundedKnapsackMemo(index, wt, val, W - wt[index], dp);
        }

        return dp[index][W] = Math.max(take, notTake);
    }
    // Time Complexity: O(N * W) — each (index, W) pair computed once
    // Space Complexity: O(N * W) + O(W) stack depth

    // 3️⃣ Tabulation (Bottom-Up DP)
    private static int unboundedKnapsackTabulation(int[] wt, int[] val, int W) {
        int n = wt.length;

        // dp[i][w] = max value using first i+1 items to make weight w
        int[][] dp = new int[n][W + 1];

        // Fill base case: using only the first item (index 0)
        for (int w = 0; w <= W; w++) {
            dp[0][w] = (w / wt[0]) * val[0]; // fill using only item 0
        }

        // Fill dp table
        for (int i = 1; i < n; i++) {
            for (int w = 0; w <= W; w++) {
                int notTake = dp[i - 1][w]; // don't take item i
                int take = 0;
                if (wt[i] <= w) {
                    take = val[i] + dp[i][w - wt[i]]; // take item i, stay at i
                }
                dp[i][w] = Math.max(take, notTake); // max of taking or not
            }
        }

        return dp[n - 1][W]; // answer using all items
    }
    // Time Complexity: O(N * W)
    // Space Complexity: O(N * W)

    // 4️⃣ Space Optimization
    private static int unboundedKnapsackSpaceOptimized(int[] wt, int[] val, int W) {
        int n = wt.length;

        // Create a 1D array to store current dp state
        int[] prev = new int[W + 1];

        // Base case: using only item 0
        for (int w = 0; w <= W; w++) {
            prev[w] = (w / wt[0]) * val[0];
        }

        // Build the DP for remaining items
        for (int i = 1; i < n; i++) {
            int[] curr = new int[W + 1];

            for (int w = 0; w <= W; w++) {
                int notTake = prev[w]; // don't take item i
                int take = 0;
                if (wt[i] <= w) {
                    take = val[i] + curr[w - wt[i]]; // take item i
                }
                curr[w] = Math.max(take, notTake);
            }

            // Update prev row for next iteration
            prev = curr;
        }

        return prev[W];
    }
    // Time Complexity: O(N * W)
    // Space Complexity: O(W)

    // 🔍 Main method to test all approaches
    public static void main(String[] args) {
        int[] wt = {2, 4, 6};   // weights of items
        int[] val = {5, 11, 13}; // values of items
        int W = 10;             // capacity of knapsack
        int n = wt.length;      // number of items

        // 1. Recursive
        System.out.println("Recursive: " + unboundedKnapsackRecursive(n - 1, wt, val, W));

        // 2. Memoization
        int[][] dp = new int[n][W + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("Memoization: " + unboundedKnapsackMemo(n - 1, wt, val, W, dp));

        // 3. Tabulation
        System.out.println("Tabulation: " + unboundedKnapsackTabulation(wt, val, W));

        // 4. Space Optimized
        System.out.println("Space Optimized: " + unboundedKnapsackSpaceOptimized(wt, val, W));
    }
}
