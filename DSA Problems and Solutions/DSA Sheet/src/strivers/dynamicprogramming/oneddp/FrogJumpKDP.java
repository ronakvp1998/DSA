package com.questions.strivers.dynamicprogramming.oneddp;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: FROG JUMP WITH K DISTANCES (Min Cost to Reach End)
 * Problem Statement:
 * A frog wants to climb a staircase with n steps. Given an integer array heights,
 * where heights[i] contains the height of the ith step, and an integer k.
 * To jump from the ith step to the jth step,
 * the frog requires abs(heights[i] - heights[j]) energy, where abs() denotes the absolute difference.
 * The frog can jump from the ith step to any step in the range [i + 1, i + k], provided it exists.
 * Return the minimum amount of energy required by the frog to go from the 0th step to the (n-1)th step.
 *
 * Examples
 * Example 1:
 * Input: heights = [10, 5, 20, 0, 15], k = 2
 * Output: 15
 * Explanation:
 * 0th step -> 2nd step, cost = abs(10 - 20) = 10
 * 2nd step -> 4th step, cost = abs(20 - 15) = 5
 * Total cost = 10 + 5 = 15.
 *
 * Example 2:
 * Input: heights = [15, 4, 1, 14, 15], k = 3
 * Output: 2
 * Explanation:
 * 0th step -> 3rd step, cost = abs(15 - 14) = 1
 * 3rd step -> 4th step, cost = abs(14 - 15) = 1
 * Total cost = 1 + 1 = 2.
 * ==================================================================================================
 * APPROACH:
 * This is a generalization of the simple "Frog Jump" problem (where K=2).
 * Instead of checking just (i-1) and (i-2), we check all previous stones
 * from (i-1) to (i-K).
 *
 * RECURRENCE RELATION:
 * dp[i] = min( dp[i-j] + abs(height[i] - height[i-j]) )
 * for all j where 1 <= j <= K and (i-j) >= 0
 *
 * ==================================================================================================
 */
public class FrogJumpKDP {

    public static void main(String[] args) {
        int[] heights = {30, 10, 60, 10, 60, 50};
        int n = heights.length;
        int k = 4; // Max jump length

        System.out.println("Target: Reach stair " + (n - 1) + " with max jump K=" + k);
        System.out.println("--------------------------------------------------");

        // 1. Recursive Approach (Brute Force)
        long start = System.nanoTime();
        System.out.println("1. Recursion       : " + recursive(n - 1, heights, k));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");

        // 2. Memoization (Top-Down DP)
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        start = System.nanoTime();
        System.out.println("2. Memoization     : " + memoization(n - 1, heights, dp, k));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");

        // 3. Tabulation (Bottom-Up DP)
        start = System.nanoTime();
        System.out.println("3. Tabulation      : " + tabulation(n, heights, k));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");

        // 4. Space Optimization (Circular Buffer)
        start = System.nanoTime();
        System.out.println("4. Space Optimized : " + spaceOptimized(n, heights, k));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Try every possible jump size from 1 to K.
     * Calculate cost for each valid jump and take the minimum.
     *
     * COMPLEXITY:
     * - Time: O(K^N) -> Exponential. Extremely slow.
     * - Space: O(N) -> Stack depth.
     */
    private static int recursive(int index, int[] arr, int k) {
        // Base Case: Start of the array has 0 cost
        if (index == 0) return 0;

        int minCost = Integer.MAX_VALUE;

        // Try all jumps from 1 to K
        for (int j = 1; j <= k; j++) {
            if (index - j >= 0) {
                int jumpCost = recursive(index - j, arr, k) + Math.abs(arr[index] - arr[index - j]);
                minCost = Math.min(minCost, jumpCost);
            }
        }
        return minCost;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Store the result of each index in 'dp' array.
     * If we visit the index again, return the stored value.
     *
     * COMPLEXITY:
     * - Time: O(N * K) -> Each state computed once, loop runs K times.
     * - Space: O(N) + O(N) -> DP Array + Stack.
     */
    private static int memoization(int index, int[] arr, int[] dp, int k) {
        if (index == 0) return 0;

        if (dp[index] != -1) return dp[index];

        int minCost = Integer.MAX_VALUE;

        for (int j = 1; j <= k; j++) {
            if (index - j >= 0) {
                int jumpCost = memoization(index - j, arr, dp, k) + Math.abs(arr[index] - arr[index - j]);
                minCost = Math.min(minCost, jumpCost);
            }
        }
        return dp[index] = minCost;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Iterate from index 1 to N-1.
     * For each index 'i', look back at 'i-1' through 'i-K' to find the best previous step.
     *
     * COMPLEXITY:
     * - Time: O(N * K)
     * - Space: O(N) -> DP Array.
     */
    private static int tabulation(int n, int[] arr, int k) {
        int[] dp = new int[n];
        dp[0] = 0; // Base case

        for (int i = 1; i < n; i++) {
            int minCost = Integer.MAX_VALUE;

            // Check previous K steps
            for (int j = 1; j <= k; j++) {
                if (i - j >= 0) {
                    int jumpCost = dp[i - j] + Math.abs(arr[i] - arr[i - j]);
                    minCost = Math.min(minCost, jumpCost);
                }
            }
            dp[i] = minCost;
        }
        return dp[n - 1];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED (CIRCULAR BUFFER)
     * ----------------------------------------------------------------------
     * LOGIC:
     * To calculate dp[i], we only need the previous K values: dp[i-1]...dp[i-K].
     * We don't need the full array of size N.
     * We can use an array of size K (or K+1) as a "Rolling Window" or "Circular Buffer".
     *
     * How it works:
     * - `dp[i % k]` stores the cost for index `i`.
     * - When we move to `i`, we overwrite `dp[i-K]` because it is no longer needed.
     *
     * COMPLEXITY:
     * - Time: O(N * K)
     * - Space: O(K) -> Significantly smaller than O(N) if K << N.
     */
    private static int spaceOptimized(int n, int[] arr, int k) {
        int[] dp = new int[k];
        dp[0] = 0; // Cost for index 0

        for (int i = 1; i < n; i++) {
            int minCost = Integer.MAX_VALUE;

            for (int j = 1; j <= k; j++) {
                if (i - j >= 0) {
                    // Retrieve from circular buffer
                    int prevCost = dp[(i - j) % k];
                    int jumpCost = prevCost + Math.abs(arr[i] - arr[i - j]);
                    minCost = Math.min(minCost, jumpCost);
                }
            }
            // Store result in circular buffer
            dp[i % k] = minCost;
        }

        // Final answer is at the circular index of the last element
        return dp[(n - 1) % k];
    }
}