package com.questions.strivers.dynamicprogramming.oneddp;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: FROG JUMP (Min Cost to Reach End)
 * Problem Statement: Given a number of stairs and a frog, the frog wants to climb from the 0th stair to the (N-1)th stair.
 * At a time the frog can climb either one or two steps. A height[N] array is also given.
 * Whenever the frog jumps from a stair i to stair j, the energy consumed in the jump is abs(height[i]- height[j]),
 * where abs() means the absolute difference.
 * We need to return the minimum energy that can be used by the frog to jump from stair 0 to stair N-1..
 *
 * Examples
 * Example 1:
 * Input: heights = [2, 1, 3, 5, 4]
 * Output: 2
 * Explanation: One possible route can be,
 * 0th step -> 2nd Step = abs(2 - 3) = 1
 * 2nd step -> 4th step = abs(3 - 4) = 1
 * Total = 1 + 1 = 2.
 *
 * Example 2:
 * Input: heights = [7, 5, 1, 2, 6]
 * Output: 9
 * Explanation: One possible route can be,
 * 0th step -> 1st Step = abs(7 - 5) = 2
 * 1st step -> 3rd step = abs(5 - 2) = 3
 * 3rd step -> 4th step = abs(2 - 6) = 4
 * Total = 2 + 3 + 4 = 9.
 * ==================================================================================================
 * APPROACH:
 * This is a classic Dynamic Programming problem because:
 * 1. Overlapping Subproblems: To reach step 5, we need step 4 and 3. To reach 4, we need 3 and 2.
 * (Step 3 is calculated twice).
 * 2. Optimal Substructure: The min cost to reach step N depends on the min cost to reach N-1 and N-2.
 *
 * RECURRENCE RELATION:
 * MinCost(i) = min(
 * MinCost(i-1) + abs(height[i] - height[i-1]),
 * MinCost(i-2) + abs(height[i] - height[i-2])
 * )
 * ==================================================================================================
 */
public class FrogJumpDP {

    public static void main(String[] args) {
        // Heights of the stairs
        int[] heights = {30, 10, 60, 10, 60, 50};
        int n = heights.length;

        System.out.println("Target: Reach stair index " + (n - 1) + " with minimum energy.");
        System.out.println("--------------------------------------------------");

        // 1. Recursive Approach (Brute Force)
        // We pass (n-1) because arrays are 0-indexed.
        long start = System.nanoTime();
        System.out.println("1. Recursion       : " + recursive(n - 1, heights));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");

        // 2. Memoization (Top-Down DP)
        int[] dp = new int[n];
        Arrays.fill(dp, -1); // Initialize with -1
        start = System.nanoTime();
        System.out.println("2. Memoization     : " + memoization(n - 1, heights, dp));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");

        // 3. Tabulation (Bottom-Up DP)
        // Resetting DP array isn't strictly necessary as Tabulation overwrites, but good practice.
        int[] dpTab = new int[n];
        start = System.nanoTime();
        System.out.println("3. Tabulation      : " + tabulation(n, heights, dpTab));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");

        // 4. Space Optimization (Best Solution)
        start = System.nanoTime();
        System.out.println("4. Space Optimized : " + spaceOptimized(n, heights));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Try all possible paths. At every step, calculate the cost of jumping 1 step
     * vs jumping 2 steps, and recursively choose the minimum.
     *
     * COMPLEXITY:
     * - Time: O(2^n) -> Exponential.
     * - Space: O(n) -> Stack depth.
     */
    private static int recursive(int index, int[] heights) {
        // Base Case: If we are at the start (index 0), cost is 0.
        if (index == 0) {
            return 0;
        }

        // Option 1: Jump from (index - 1)
        int left = recursive(index - 1, heights) + Math.abs(heights[index] - heights[index - 1]);

        // Option 2: Jump from (index - 2)
        int right = Integer.MAX_VALUE;
        if (index > 1) {
            right = recursive(index - 2, heights) + Math.abs(heights[index] - heights[index - 2]);
        }

        // Return the minimum of the two choices
        return Math.min(left, right);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Same logic as recursion, but before returning, store the result in 'dp'.
     * Before computing, check if 'dp' already has the answer.
     *
     * COMPLEXITY:
     * - Time: O(n) -> Compute each state once.
     * - Space: O(n) + O(n) -> Array + Recursion Stack.
     */
    private static int memoization(int index, int[] heights, int[] dp) {
        if (index == 0) return 0;

        // Step 1: Check Cache
        if (dp[index] != -1) return dp[index];

        // Step 2: Compute
        int left = memoization(index - 1, heights, dp) + Math.abs(heights[index] - heights[index - 1]);

        int right = Integer.MAX_VALUE;
        if (index > 1) {
            right = memoization(index - 2, heights, dp) + Math.abs(heights[index] - heights[index - 2]);
        }

        // Step 3: Store and Return
        return dp[index] = Math.min(left, right);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Start from the base case (index 0) and iteratively fill the table up to N-1.
     * No recursion overhead.
     *
     * COMPLEXITY:
     * - Time: O(n) -> Single loop.
     * - Space: O(n) -> DP Array.
     */
    private static int tabulation(int n, int[] heights, int[] dp) {
        // Base Case
        dp[0] = 0;

        // Iterate from 2nd stair (index 1) to the last stair
        for (int i = 1; i < n; i++) {

            // Calculate cost coming from i-1
            int jumpOne = dp[i - 1] + Math.abs(heights[i] - heights[i - 1]);

            // Calculate cost coming from i-2 (handle edge case for i=1)
            int jumpTwo = Integer.MAX_VALUE;
            if (i > 1) {
                jumpTwo = dp[i - 2] + Math.abs(heights[i] - heights[i - 2]);
            }

            // Store min cost
            dp[i] = Math.min(jumpOne, jumpTwo);
        }

        // Return the value at the last index
        return dp[n - 1];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED (INTERVIEW STANDARD)
     * ----------------------------------------------------------------------
     * LOGIC:
     * To find dp[i], we only need dp[i-1] and dp[i-2].
     * We don't need the whole array. We maintain 'prev1' (i-1) and 'prev2' (i-2).
     *
     * COMPLEXITY:
     * - Time: O(n)
     * - Space: O(1) -> Only 3 variables used.
     */
    private static int spaceOptimized(int n, int[] heights) {
        int prev2 = 0; // Represents dp[i-2], initially dp[0] (cost 0)
        int prev1 = 0; // Represents dp[i-1], initially dp[0] (cost 0)

        for (int i = 1; i < n; i++) {
            // Jump 1 Step
            int jumpOne = prev1 + Math.abs(heights[i] - heights[i - 1]);

            // Jump 2 Steps
            int jumpTwo = Integer.MAX_VALUE;
            if (i > 1) {
                jumpTwo = prev2 + Math.abs(heights[i] - heights[i - 2]);
            }

            int current = Math.min(jumpOne, jumpTwo);

            // Shift pointers for next iteration
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }
}

/*
1. Why Greedy FailsA common mistake is to think "I should always take the jump that gives the smaller immediate cost."
Example: [30, 10, 60, 10, 60, 50]
Greedy at index 0 (30):Jump to 1 (10): Cost $|30-10| = 20$.Jump to 2 (60): Cost $|30-60| = 30$.Greedy chooses Index
1.DP: Sometimes taking a higher immediate cost (jumping to 60) might position you better for the next jump to save massive energy later.
 */