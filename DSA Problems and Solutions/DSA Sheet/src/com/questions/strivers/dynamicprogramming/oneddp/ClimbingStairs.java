package com.questions.strivers.dynamicprogramming.oneddp;

import java.util.Arrays;

// Problem: Given a number of stairs 'n', starting from 0th stair,
// we need to reach the 'nth' stair. We can climb either 1 or 2 stairs at a time.
// Goal: Count the number of distinct ways to reach the nth stair.

// Reference: https://takeuforward.org/data-structure/dynamic-programming-climbing-stairs/
public class ClimbingStairs {
    public static void main(String[] args) {
        int n = 3; // Target stair

        // ----------------- Memoization (Top-Down DP) -----------------
        int dp[] = new int[n + 1]; // Array to store intermediate results
        Arrays.fill(dp, -1); // Initialize all elements to -1
        System.out.println(memoraization(n, dp)); // Output: 3

        // ----------------- Tabulation (Bottom-Up DP) -----------------
        System.out.println(tabulation(n, dp)); // Output: 3

        // ----------------- Space Optimization ------------------------
        System.out.println(spaceOpt(n)); // Output: 3
    }

    // Method 1: Memoization (Top-Down DP)
    // Time Complexity: O(n)
    // Space Complexity: O(n) for dp[] + O(n) recursion stack space
    public static int memoraization(int n, int[] dp) {
        // Base cases: 1 way to reach stair 0 or stair 1
        if (n == 0 || n == 1) {
            return 1;
        }

        // If result already computed, return it (Avoid recomputation)
        if (dp[n] != -1) {
            return dp[n];
        }

        // Recursively calculate ways to reach (n-1) and (n-2)
        dp[n] = memoraization(n - 1, dp) + memoraization(n - 2, dp);
        return dp[n];
    }

    // Method 2: Tabulation (Bottom-Up DP)
    // Time Complexity: O(n)
    // Space Complexity: O(n) for dp[]
    public static int tabulation(int n, int[] dp){
        dp[0] = 1; // 1 way to reach stair 0
        dp[1] = 1; // 1 way to reach stair 1

        // Fill the dp[] array from bottom to top
        for(int i = 2; i <= n; i++){
            dp[i] = dp[i - 1] + dp[i - 2]; // Sum of ways to reach previous 2 stairs
        }
        return dp[n]; // Final answer at dp[n]
    }

    // Method 3: Space Optimized DP
    // Time Complexity: O(n)
    // Space Complexity: O(1)
    public static int spaceOpt(int n) {
        if (n == 0 || n == 1) return 1;

        int prev1 = 1; // ways to reach (i - 2)
        int prev2 = 1; // ways to reach (i - 1)

        // Compute ways iteratively using only two variables
        for (int i = 2; i <= n; i++) {
            int curr = prev1 + prev2; // Current ways = prev1 + prev2
            prev1 = prev2; // Update prev1 to prev2
            prev2 = curr;  // Update prev2 to current
        }

        return prev2; // Final result is in prev2
    }
}
