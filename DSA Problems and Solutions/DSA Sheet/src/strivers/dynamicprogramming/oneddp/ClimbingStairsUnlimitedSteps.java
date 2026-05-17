package com.questions.strivers.dynamicprogramming.oneddp;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: CLIMBING STAIRS (VARIABLE JUMP SIZE 1...N)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * You are climbing a staircase. It takes 'n' steps to reach the top.
 * From any step 'i', you can jump to ANY higher step (i+1, i+2, ..., n).
 *
 * Goal: Count the number of distinct ways to reach the top.
 *
 * EXAMPLE:
 * Input: n = 3
 * Output: 4
 * Explanation:
 * 1. 0 -> 3 (Direct jump)
 * 2. 0 -> 1 -> 3
 * 3. 0 -> 2 -> 3
 * 4. 0 -> 1 -> 2 -> 3
 *
 * MATHEMATICAL INSIGHT (For Space Optimization):
 * Ways(n) = Ways(n-1) + Ways(n-2) + ... + Ways(0)
 * Ways(n) = 2 * Ways(n-1)  (See logic below)
 * ==================================================================================================
 */
public class ClimbingStairsUnlimitedSteps {

    public static void main(String[] args) {
        int n = 4; // Try n=4. Expected Output: 8 (Sequence: 1, 2, 4, 8...)

        System.out.println("Target Stair: " + n);
        System.out.println("--------------------------------------------------");

        // 1️⃣ Recursive Approach
        long start = System.nanoTime();
        System.out.println("1. Recursion       : " + recursion(n));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");

        // 2️⃣ Memoization (Top-Down DP)
        int[] dp = new int[n + 1];
        Arrays.fill(dp, -1);
        start = System.nanoTime();
        System.out.println("2. Memoization     : " + memoization(n, dp));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");

        // 3️⃣ Tabulation (Bottom-Up DP)
        start = System.nanoTime();
        System.out.println("3. Tabulation      : " + tabulation(n));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");

        // 4️⃣ Space Optimization (Math Pattern)
        start = System.nanoTime();
        System.out.println("4. Space Optimized : " + spaceOptimized(n));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * To reach step N, we could have jumped from N-1, N-2, ... down to 0.
     * We simply sum up the ways to reach all previous steps.
     *
     * COMPLEXITY:
     * - Time: O(N^N) -> Extremely exponential. Each step branches into N calls.
     * - Space: O(N) -> Recursion stack depth.
     */
    private static int recursion(int n) {
        // Base Case: Reached the ground (or start point). This counts as 1 valid path.
        if (n == 0) {
            return 1;
        }

        int totalWays = 0;

        // Try every possible jump size that lands exactly on 'n'
        // This is equivalent to summing recursion(n-1) + recursion(n-2) + ... + recursion(0)
        for (int jump = 1; jump <= n; jump++) {
            totalWays += recursion(n - jump);
        }

        return totalWays;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Same as recursion, but we store the result of the loop in dp[n].
     * If we see 'n' again, return the stored value.
     *
     * COMPLEXITY:
     * - Time: O(N^2) -> There are N states, and for each state, we loop O(N) times.
     * - Space: O(N) -> DP Array + Recursion Stack.
     */
    private static int memoization(int n, int[] dp) {
        if (n == 0) return 1;

        if (dp[n] != -1) return dp[n];

        int totalWays = 0;
        // Sum up ways from all previous steps
        for (int jump = 1; jump <= n; jump++) {
            totalWays += memoization(n - jump, dp);
        }

        return dp[n] = totalWays;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * We build the table from 0 to N.
     * dp[i] = sum(dp[0] + dp[1] + ... + dp[i-1])
     *
     * COMPLEXITY:
     * - Time: O(N^2) -> Nested loop (Outer 1 to N, Inner 0 to i).
     * - Space: O(N) -> DP Array.
     */
    private static int tabulation(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1; // Base case: 1 way to be at start

        // Fill DP table
        for (int i = 1; i <= n; i++) {
            // dp[i] is the sum of all previous dp values
            for (int j = 0; j < i; j++) {
                dp[i] += dp[j];
            }
        }
        return dp[n];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED (MATH PATTERN)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Look at the Tabulation pattern:
     * dp[0] = 1
     * dp[1] = dp[0] = 1
     * dp[2] = dp[0] + dp[1] = 1 + 1 = 2
     * dp[3] = dp[0] + dp[1] + dp[2] = 1 + 1 + 2 = 4
     * dp[4] = dp[0] + ... + dp[3] = 1 + 1 + 2 + 4 = 8
     *
     * Observation: dp[i] = 2 * dp[i-1]
     * Why? Because dp[i-1] ALREADY contains the sum of (dp[0]...dp[i-2]).
     * So adding dp[i-1] to that sum is effectively doubling it.
     *
     * COMPLEXITY:
     * - Time: O(N) -> Single loop.
     * - Space: O(1) -> Only one variable needed.
     */
    private static int spaceOptimized(int n) {
        if (n == 0) return 1;

        int prev = 1; // Represents ways(1)

        // Start loop from 2 because ways(1) is already 1
        for (int i = 2; i <= n; i++) {
            prev = 2 * prev;
        }

        return prev;
    }
}