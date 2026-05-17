package com.questions.strivers.dynamicprogramming.oneddp;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: CLIMBING STAIRS (VARIABLE JUMP SIZE 1...K)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * You are climbing a staircase. It takes 'n' steps to reach the top.
 * From any step 'i', you can jump to any step in range [i+1, i+k].
 * (i.e., jump sizes 1, 2, ..., k)
 *
 * Goal: Count the number of distinct ways to reach the top.
 *
 * EXAMPLE:
 * Input: n = 4, k = 2
 * Output: 5
 * Explanation:
 * 1. 1+1+1+1
 * 2. 1+1+2
 * 3. 1+2+1
 * 4. 2+1+1
 * 5. 2+2
 *
 * OPTIMIZATION INSIGHT:
 * To calculate Ways(i), we need the sum of the previous K values:
 * Ways(i) = Ways(i-1) + Ways(i-2) + ... + Ways(i-k)
 *
 * We can maintain a "Sliding Window Sum" to calculate this in O(1) inside the loop.
 * ==================================================================================================
 */
public class ClimbingStairsKSteps {

    public static void main(String[] args) {
        int n = 5; // Target stair
        int k = 3; // Max jump size

        System.out.println("Target Stair: " + n + ", Max Jump K: " + k);
        System.out.println("--------------------------------------------------");

        // 1️⃣ Recursive Approach
        long start = System.nanoTime();
        System.out.println("1. Recursion       : " + recursion(n, k));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");

        // 2️⃣ Memoization (Top-Down DP)
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);
        start = System.nanoTime();
        System.out.println("2. Memoization     : " + memoization(n, k, dp));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");

        // 3️⃣ Tabulation (Bottom-Up DP)
        start = System.nanoTime();
        System.out.println("3. Tabulation      : " + tabulation(n, k));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");

        // 4️⃣ Space Optimization (Sliding Window)
        start = System.nanoTime();
        System.out.println("4. Space Optimized : " + spaceOptimized(n, k));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Try every jump size from 1 to K.
     * Recursively sum the results.
     *
     * COMPLEXITY:
     * - Time: O(K^N) -> Exponential.
     * - Space: O(N) -> Recursion stack depth.
     */
    private static int recursion(int n, int k) {
        // Base Case: Reached the ground.
        if (n == 0) return 1;

        int totalWays = 0;

        // Try jumps 1 to K, but don't go below 0
        for (int jump = 1; jump <= k; jump++) {
            if (n - jump >= 0) {
                totalWays += recursion(n - jump, k);
            }
        }

        return totalWays;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Same as recursion, but cache the result in dp[].
     *
     * COMPLEXITY:
     * - Time: O(N * K) -> N states, K iterations per state.
     * - Space: O(N) -> DP Array + Stack.
     */
    private static int memoization(int n, int k, int[] dp) {
        if (n == 0) return 1;

        if (dp[n] != -1) return dp[n];

        int totalWays = 0;
        for (int jump = 1; jump <= k; jump++) {
            if (n - jump >= 0) {
                totalWays += memoization(n - jump, k, dp);
            }
        }

        return dp[n] = totalWays;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * dp[i] = Sum(dp[i-1] ... dp[i-k])
     *
     * COMPLEXITY:
     * - Time: O(N * K)
     * - Space: O(N)
     */
    private static int tabulation(int n, int k) {
        int[] dp = new int[n + 1];
        dp[0] = 1; // Base case

        for (int i = 1; i <= n; i++) {
            // Look back at previous K steps
            for (int jump = 1; jump <= k; jump++) {
                if (i - jump >= 0) {
                    dp[i] += dp[i - jump];
                }
            }
        }
        return dp[n];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED (SLIDING WINDOW)
     * ----------------------------------------------------------------------
     * LOGIC:
     * In Tabulation, dp[i] is the sum of the previous K elements.
     * Instead of re-summing every time (O(K)), we can maintain a 'currentWindowSum'.
     *
     * When moving from i to i+1:
     * 1. ADD the new value (dp[i]) to the window sum.
     * 2. REMOVE the old value falling out of the window (dp[i-k]).
     *
     * However, since we need to track exactly which value falls out, we strictly
     * need a List or Queue of size K.
     * BUT, for simplicity in interview without extra data structures, we can use a
     * simpler array of size K+1 or just stick to O(N) space if K is large.
     *
     * Below is the O(N) Space / O(N) Time optimized version (Removing the inner K loop).
     *
     * COMPLEXITY:
     * - Time: O(N) -> Inner loop removed!
     * - Space: O(N) -> Still needs array to track sliding window values.
     * (Getting to pure O(K) space is possible but complex with circular buffers).
     */
    private static int spaceOptimized(int n, int k) {
        int[] dp = new int[n + 1];
        dp[0] = 1;

        int currentWindowSum = 1; // Initially contains dp[0]

        for (int i = 1; i <= n; i++) {
            // dp[i] is the current window sum
            dp[i] = currentWindowSum;

            // PREPARE FOR NEXT ITERATION (i+1):
            // 1. Add the newly computed value (dp[i]) to the window
            currentWindowSum += dp[i];

            // 2. Remove the value that will fall out of the window (i - k + 1)
            // If we are at index i, the window for (i+1) is [i-k+1 ... i]
            // The element falling out is dp[i - k]
            if (i >= k) {
                currentWindowSum -= dp[i - k];
            }
        }

        return dp[n];
    }
}