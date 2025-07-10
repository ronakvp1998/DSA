package com.questions.strivers.dynamicprogramming.oneddp;

import java.util.Arrays;

// Problem Link: https://takeuforward.org/data-structure/dynamic-programming-frog-jump-with-k-distances-dp-4/

/*
🔵 Problem Summary:
- A frog starts at stair 0 and wants to reach stair n-1.
- From any stair i, the frog can jump to i+1, i+2, ..., up to i+k.
- Jump cost = abs(arr[i] - arr[j]).
- Goal: Minimize total jump cost to reach the last stair.
*/

public class FrogJumpKDP {
    public static void main(String[] args) {
        // Heights of each stair
        int arr[] = {30, 10, 60, 10, 60, 50};

        int n = 6;   // Total number of stairs
        int k = 4;   // Maximum number of stairs the frog can jump in one move

        // 1️⃣ Simple recursive approach (inefficient for large inputs)
        System.out.println(recursive(n - 1, arr, k));

        // 2️⃣ Top-down dynamic programming (memoization)
        int dp[] = new int[n];
        Arrays.fill(dp, -1);  // Fill dp with -1 to indicate values not yet computed
        System.out.println(memorization(n - 1, arr, dp, k));

        // 3️⃣ Bottom-up dynamic programming (tabulation)
        Arrays.fill(dp, -1);  // Reset dp array to avoid confusion
        System.out.println(tabulation(n, arr, k));

        // 4️⃣ Space-optimized DP approach
        System.out.println(spaceOptimized(n, arr, k));
    }

    // -------------------- 1️⃣ Recursive (Brute-force) --------------------
    public static int recursive(int n, int[] arr, int k) {
        // ✅ Base condition: No cost to stay at the starting stair
        if (n == 0) {
            return 0;
        }

        int minCost = Integer.MAX_VALUE; // Initialize min cost to a large value

        // 🔁 Try all possible jumps from (n-1), (n-2), ..., (n-k)
        for (int j = 1; j <= k; j++) {
            // 🛑 Condition: Make sure (n-j) is a valid stair index
            if (n - j >= 0) {
                // 📞 Recursive call to compute cost from (n-j) to n
                int jumpCost = recursive(n - j, arr, k) + Math.abs(arr[n] - arr[n - j]);

                // ✅ Choose the minimum of all valid jump costs
                minCost = Math.min(minCost, jumpCost);
            }
            // ❌ If n - j < 0, skip that jump (not a valid stair)
        }

        return minCost; // Return the optimal cost to reach stair 'n'
    }

    // -------------------- 2️⃣ Memoization (Top-down DP) --------------------
    public static int memorization(int n, int[] arr, int[] dp, int k) {
        // ✅ Base condition: cost to reach first stair is 0
        if (n == 0) {
            return 0;
        }

        // 🛑 Condition: If we have already calculated dp[n], return it
        if (dp[n] != -1) {
            return dp[n];  // Avoid recomputation
        }

        int minCost = Integer.MAX_VALUE;

        // 🔁 Try all jumps from (n-1), (n-2), ..., (n-k)
        for (int j = 1; j <= k; j++) {
            // ✅ Only jump if (n-j) is within bounds
            if (n - j >= 0) {
                // 🔄 Recursive call for subproblem + cost to jump to n
                int jumpCost = memorization(n - j, arr, dp, k) + Math.abs(arr[n] - arr[n - j]);

                // 📉 Update minimum cost
                minCost = Math.min(minCost, jumpCost);
            }
            // ❌ Skip if n-j < 0 (invalid stair)
        }

        dp[n] = minCost; // 💾 Store result in dp array to reuse later
        return dp[n];
    }

    // -------------------- 3️⃣ Tabulation (Bottom-up DP) --------------------
    public static int tabulation(int n, int[] arr, int k) {
        int dp[] = new int[n]; // dp[i] = min cost to reach i-th stair
        dp[0] = 0;             // 🟢 Base case: no cost to start at stair 0

        // 🔁 Loop through all stairs from 1 to n-1
        for (int i = 1; i < n; i++) {
            int minCost = Integer.MAX_VALUE; // Track the best option for stair i

            // 🔁 Try all jumps from previous k stairs
            for (int j = 1; j <= k; j++) {
                // ✅ Only proceed if jump from (i-j) is valid
                if (i - j >= 0) {
                    // 🧮 Cost = cost to reach (i-j) + cost to jump from (i-j) to i
                    int cost = dp[i - j] + Math.abs(arr[i] - arr[i - j]);

                    // 📉 Keep track of minimum cost
                    minCost = Math.min(minCost, cost);
                }
                // ❌ Skip if i-j < 0 (invalid jump)
            }

            dp[i] = minCost; // 💾 Save the best cost to reach stair i
        }

        return dp[n - 1]; // 🏁 Return the cost to reach the last stair
    }

    // -------------------- 4️⃣ Space Optimized DP --------------------
    public static int spaceOptimized(int n, int[] arr, int k) {
        int[] dp = new int[k];  // Circular buffer of size k to store recent results
        dp[0] = 0;              // 🟢 Cost to reach stair 0 is 0

        // 🔁 Iterate through all stairs from 1 to n-1
        for (int i = 1; i < n; i++) {
            int minCost = Integer.MAX_VALUE; // Track best cost for current stair

            // 🔁 Try all jump sizes 1 to k
            for (int j = 1; j <= k; j++) {
                // ✅ Check if i-j is valid
                if (i - j >= 0) {
                    // 🧮 Use circular index to access previous result
                    int cost = dp[(i - j) % k] + Math.abs(arr[i] - arr[i - j]);

                    // 📉 Update minimum cost
                    minCost = Math.min(minCost, cost);
                }
                // ❌ Skip invalid stairs (i-j < 0)
            }

            // 🔄 Update current index in circular buffer
            dp[i % k] = minCost;
        }

        return dp[(n - 1) % k]; // 🏁 Final answer is at index (n-1)%k
    }
}
