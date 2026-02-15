package com.questions.strivers.dynamicprogramming.oneddp;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: CLIMBING STAIRS
 *
 * You are climbing a staircase. It takes n steps to reach the top.
 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 *
 * Example 1:
 * Input: n = 2
 * Output: 2
 * Explanation: There are two ways to climb to the top.
 * 1. 1 step + 1 step
 * 2. 2 steps
 *
 * Example 2:
 * Input: n = 3
 * Output: 3
 * Explanation: There are three ways to climb to the top.
 * 1. 1 step + 1 step + 1 step
 * 2. 1 step + 2 steps
 * 3. 2 steps + 1 step
 *
 * Constraints:
 *
 * 1 <= n <= 45
 * ==================================================================================================
 * APPROACH:
 * The problem follows the Fibonacci pattern. To reach step 'i', you must have come from:
 * 1. Step 'i-1' (by taking 1 step)
 * 2. Step 'i-2' (by taking 2 steps)
 *
 * Therefore: Ways(i) = Ways(i-1) + Ways(i-2)
 *
 * This file demonstrates the evolution of the solution:
 * Recursion -> Memoization -> Tabulation -> Space Optimization
 * ==================================================================================================
 */
public class ClimbingStairs {

    public static void main(String[] args) {
        int n = 5; // Target stair (Try n=5. Expected Output: 8)
        System.out.println("Calculating distinct ways to climb " + n + " stairs...");
        System.out.println("--------------------------------------------------");

        // 1. Recursive Approach (Brute Force)
        long start = System.nanoTime();
        System.out.println("1. Recursion       : " + recursion(n));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");

        // 2. Memoization (Top-Down DP)
        // We initialize a DP array with -1 to represent "unsolved" states.
        int[] dpMemo = new int[n + 1];
        Arrays.fill(dpMemo, -1);

        start = System.nanoTime();
        System.out.println("2. Memoization     : " + memoization(n, dpMemo));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");

        // 3. Tabulation (Bottom-Up DP)
        int[] dpTab = new int[n + 1]; // Clean array for tabulation
        start = System.nanoTime();
        System.out.println("3. Tabulation      : " + tabulation(n, dpTab));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");

        // 4. Space Optimization (Best Solution)
        start = System.nanoTime();
        System.out.println("4. Space Optimized : " + spaceOpt(n));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: BASIC RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Directly implements the recurrence relation: f(n) = f(n-1) + f(n-2).
     *
     * COMPLEXITY:
     * - Time: O(2^n) -> Exponential. We re-calculate the same subproblems repeatedly.
     * - Space: O(n) -> Recursion stack depth.
     */
    private static int recursion(int n) {
        // Base Case: If we are at step 0 or 1, there is only 1 way (stand there or take 1 step).
        // both becomes valid count so for n=0 & n=1 return 1
        if (n <= 1) {
            return 1;
        }

        // Recursive Step: Sum of ways from the previous two steps
        return recursion(n - 1) + recursion(n - 2);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * We use an array 'dp' to store the result of each step 'n' once we compute it.
     * Before computing, we check if dp[n] != -1. If true, we return the stored value.
     *
     * COMPLEXITY:
     * - Time: O(n) -> Each state (0 to n) is computed exactly once.
     * - Space: O(n) + O(n) -> DP Array + Recursion Stack.
     */
    private static int memoization(int n, int[] dp) {
        // Base Case
        if (n <= 1) return 1;

        // Step 1: Check Cache (Memoization)
        if (dp[n] != -1) {
            return dp[n];
        }

        // Step 2: Compute and Store
        // We compute the result and save it to the array before returning.
        dp[n] = memoization(n - 1, dp) + memoization(n - 2, dp);

        return dp[n];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Instead of recursion, we use a loop. We start from the base cases (0 and 1)
     * and fill the table upwards to 'n'.
     *
     * COMPLEXITY:
     * - Time: O(n) -> Simple loop from 2 to n.
     * - Space: O(n) -> DP Array to store intermediate values.
     */
    private static int tabulation(int n, int[] dp) {
        // Base Cases Initialization
        dp[0] = 1; // 1 way to be at start (do nothing)
        dp[1] = 1; // 1 way to reach step 1

        // Iteratively fill the table
        for (int i = 2; i <= n; i++) {
            // The value at current step is sum of previous two steps
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        // The answer for 'n' is now stored at the last index
        return dp[n];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED (INTERVIEW STANDARD)
     * ----------------------------------------------------------------------
     * LOGIC:
     * We observed in Tabulation that to find dp[i], we ONLY need dp[i-1] and dp[i-2].
     * We do not need the entire array. We can just maintain two variables.
     *
     * COMPLEXITY:
     * - Time: O(n) -> Single loop.
     * - Space: O(1) -> Constant space (only 3 integer variables).
     */
    private static int spaceOpt(int n) {
        if (n <= 1) return 1;

        int prev2 = 1; // Initially represents ways(0)
        int prev1 = 1; // Initially represents ways(1)

        for (int i = 2; i <= n; i++) {
            int current = prev1 + prev2; // ways(i) = ways(i-1) + ways(i-2)

            // Shift the window for the next iteration
            prev2 = prev1;   // The old 'i-1' becomes 'i-2' for the next step
            prev1 = current; // The old 'current' becomes 'i-1' for the next step
        }

        return prev1; // prev1 holds the result for step n
    }
}