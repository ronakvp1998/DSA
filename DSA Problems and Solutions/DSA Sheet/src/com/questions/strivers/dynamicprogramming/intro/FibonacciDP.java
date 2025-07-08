package com.questions.strivers.dynamicprogramming.intro;

import java.util.Arrays;
//https://takeuforward.org/data-structure/dynamic-programming-introduction/
public class FibonacciDP {
    public static void main(String[] args) {
        int n = 5;  // Input: Calculate the 5th Fibonacci number

        int dp[] = new int[n + 1];        // Create a DP array of size n+1
        Arrays.fill(dp, -1);              // Fill the array with -1 to indicate uncomputed states

        // Run and print results for all four approaches
        System.out.println("Basic Recursion: " + basicRecursion(n));  // Brute-force recursive method
        System.out.println("Memoization: " + f(n, dp));               // Top-down dynamic programming
        System.out.println("Tabulation: " + tabulation(n, dp));       // Bottom-up dynamic programming
        System.out.println("Space Optimized: " + spaceOpt(n));        // Optimized bottom-up with O(1) space
    }

    // ------------------- 1. Basic Recursive Solution -------------------
    /**
     * Basic Recursive Approach to find the nth Fibonacci number
     *
     * Time Complexity: O(2^n)
     * - Exponential time because each call branches into two more calls
     *
     * Space Complexity: O(n)
     * - Due to the recursion stack in the worst case (linear depth)
     */
    public static int basicRecursion(int n) {
        if (n <= 1) return n;  // Base case: F(0) = 0, F(1) = 1

        // Recursive calls for F(n-1) and F(n-2)
        return basicRecursion(n - 1) + basicRecursion(n - 2);
    }

    // ------------------- 2. Memoization (Top-Down DP) -------------------
    /**
     * Recursive approach with memoization to avoid repeated calculations
     *
     * Time Complexity: O(n)
     * - Each value from 0 to n is computed only once
     *
     * Space Complexity: O(n) + O(n)
     * - O(n) for the dp[] array
     * - O(n) for recursion stack (function call depth)
     */
    public static int f(int n, int[] dp) {
        if (n <= 1) return n;  // Base case

        if (dp[n] != -1) {     // If already computed, return from dp
            return dp[n];
        }

        // Store result in dp[n] and return
        dp[n] = f(n - 1, dp) + f(n - 2, dp);
        return dp[n];
    }

    // ------------------- 3. Tabulation (Bottom-Up DP) -------------------
    /**
     * Iterative dynamic programming approach using a table (array)
     *
     * Time Complexity: O(n)
     * - One loop from 2 to n
     *
     * Space Complexity: O(n)
     * - Array of size n+1 used to store intermediate results
     */
    public static int tabulation(int n, int[] dp) {
        dp[0] = 0;  // Base value: F(0)
        dp[1] = 1;  // Base value: F(1)

        // Fill dp[] from 2 to n using bottom-up approach
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];  // F(i) = F(i-1) + F(i-2)
        }

        return dp[n];  // Final answer
    }

    // ------------------- 4. Space Optimized DP -------------------
    /**
     * Optimized iterative approach using only constant space
     *
     * Time Complexity: O(n)
     * - Single loop from 2 to n
     *
     * Space Complexity: O(1)
     * - Only uses three integer variables
     */
    public static int spaceOpt(int n) {
        if (n <= 1) return n;  // Base cases

        int prev1 = 0;  // Initially F(0)
        int prev2 = 1;  // Initially F(1)

        // Iteratively compute Fibonacci using only last two values
        for (int i = 2; i <= n; i++) {
            int curr = prev1 + prev2;  // F(i) = F(i-1) + F(i-2)

            // Update variables for next iteration
            prev1 = prev2;
            prev2 = curr;
        }

        return prev2;  // prev2 will hold F(n)
    }
}
