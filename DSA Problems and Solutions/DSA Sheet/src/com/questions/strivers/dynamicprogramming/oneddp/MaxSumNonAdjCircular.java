package com.questions.strivers.dynamicprogramming.oneddp;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: HOUSE ROBBER II (CIRCULAR HOUSES)
 * You are a professional robber planning to rob houses along a street.
 * Each house has a certain amount of money stashed.
 * All houses at this place are arranged in a circle.
 * That means the first house is the neighbor of the last one.
 * Meanwhile, adjacent houses have a security system connected,
 * and it will automatically contact the police if two adjacent houses were broken into on the same night.
 *
 * Given an integer array nums representing the amount of money of each house,
 * return the maximum amount of money you can rob tonight without alerting the police.
 *
 * Example 1:
 *
 * Input: nums = [2,3,2]
 * Output: 3
 * Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2), because they are adjacent houses.
 * Example 2:
 *
 * Input: nums = [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 * Total amount you can rob = 1 + 3 = 4.
 * Example 3:
 *
 * Input: nums = [1,2,3]
 * Output: 3
 *
 *
 * Constraints:
 *
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 1000
 * ==================================================================================================
 * PROBLEM LINK: https://leetcode.com/problems/house-robber-ii/
 *
 * APPROACH:
 * Since the array is circular, the First (index 0) and Last (index n-1) houses are neighbors.
 * You cannot rob both. This creates two scenarios:
 *
 * Scenario 1: Rob from index 0 to n-2 (Exclude the Last House).
 * Scenario 2: Rob from index 1 to n-1 (Exclude the First House).
 *
 * The answer is simply: Max(Scenario 1, Scenario 2).
 * Both scenarios are reduced to the linear "House Robber I" problem.
 * ==================================================================================================
 */
public class MaxSumNonAdjCircular {

    public static void main(String[] args) {
        // Example: [2, 3, 2] -> Max is 3 (We can't rob 2+2 because they are circular neighbors)
        int[] arr = {2, 3, 2};
        int n = arr.length;

        System.out.println("Houses: " + Arrays.toString(arr));
        System.out.println("--------------------------------------------------");

        // 1. Recursive Approach (Brute Force)
        System.out.println("1. Recursive       : " + houseRobberRecursive(arr));

        // 2. Memoization (Top-Down DP)
        System.out.println("2. Memoization     : " + houseRobberMemoization(arr));

        // 3. Tabulation (Bottom-Up DP)
        System.out.println("3. Tabulation      : " + houseRobberTabulation(arr));

        // 4. Space Optimized (Best Solution)
        System.out.println("4. Space Optimized : " + houseRobberSpaceOptimized(arr));
    }

    // ==============================================================================================
    // APPROACH 1: RECURSION (BRUTE FORCE)
    // Complexity: Time O(2^N) | Space O(N)
    // ==============================================================================================
    private static int houseRobberRecursive(int[] arr) {
        int n = arr.length;
        if (n == 1) return arr[0]; // Edge Case: Only 1 house

        // Solve two linear problems
        int case1 = solveRecursive(arr, 0, n - 2); // Exclude Last
        int case2 = solveRecursive(arr, 1, n - 1); // Exclude First

        return Math.max(case1, case2);
    }

    // Helper: Solves linear House Robber from 'start' to 'end' indices recursively
    private static int solveRecursive(int[] arr, int start, int end) {
        if (end < start) return 0;       // Base case: No houses left
        if (end == start) return arr[start]; // Base case: 1 house left

        // Option A: Pick current (end) + solve for remainder (end-2)
        int pick = arr[end] + solveRecursive(arr, start, end - 2);

        // Option B: Don't pick current + solve for remainder (end-1)
        int notPick = solveRecursive(arr, start, end - 1);

        return Math.max(pick, notPick);
    }

    // ==============================================================================================
    // APPROACH 2: MEMOIZATION (TOP-DOWN DP)
    // Complexity: Time O(N) | Space O(N) + O(N) stack
    // ==============================================================================================
    private static int houseRobberMemoization(int[] arr) {
        int n = arr.length;
        if (n == 1) return arr[0];

        // DP arrays for caching
        int[] dp1 = new int[n];
        int[] dp2 = new int[n];
        Arrays.fill(dp1, -1);
        Arrays.fill(dp2, -1);

        int case1 = solveMemoization(arr, 0, n - 2, dp1);
        int case2 = solveMemoization(arr, 1, n - 1, dp2);

        return Math.max(case1, case2);
    }

    // Helper: Memoized linear solver
    private static int solveMemoization(int[] arr, int start, int end, int[] dp) {
        if (end < start) return 0;
        if (dp[end] != -1) return dp[end];

        int pick = arr[end] + solveMemoization(arr, start, end - 2, dp);
        int notPick = solveMemoization(arr, start, end - 1, dp);

        return dp[end] = Math.max(pick, notPick);
    }

    // ==============================================================================================
    // APPROACH 3: TABULATION (BOTTOM-UP DP)
    // Complexity: Time O(N) | Space O(N)
    // ==============================================================================================
    private static int houseRobberTabulation(int[] arr) {
        int n = arr.length;
        if (n == 1) return arr[0];

        int case1 = solveTabulation(arr, 0, n - 2);
        int case2 = solveTabulation(arr, 1, n - 1);

        return Math.max(case1, case2);
    }

    // Helper: Tabulated linear solver
    // Note: To handle the 'start' offset, we treat the subarray as a new 0-indexed problem
    private static int solveTabulation(int[] arr, int start, int end) {
        // Effective size of the range
        int len = end - start + 1;
        if (len <= 0) return 0;

        int[] dp = new int[len];
        dp[0] = arr[start]; // First element in range

        for (int i = 1; i < len; i++) {
            // Actual index in original array is 'start + i'
            int currentVal = arr[start + i];

            // Pick: Current + (i-2) if exists
            int pick = currentVal;
            if (i > 1) pick += dp[i - 2];

            // Not Pick: (i-1)
            int notPick = dp[i - 1];

            dp[i] = Math.max(pick, notPick);
        }

        return dp[len - 1];
    }

    // ==============================================================================================
    // APPROACH 4: SPACE OPTIMIZED (BEST)
    // Complexity: Time O(N) | Space O(1)
    // ==============================================================================================
    private static int houseRobberSpaceOptimized(int[] arr) {
        int n = arr.length;
        if (n == 1) return arr[0];

        int case1 = solveSpaceOptimized(arr, 0, n - 2);
        int case2 = solveSpaceOptimized(arr, 1, n - 1);

        return Math.max(case1, case2);
    }

    // Helper: Space Optimized linear solver (Standard House Robber 1 logic)
    private static int solveSpaceOptimized(int[] arr, int start, int end) {
        int prev2 = 0; // Represents dp[i-2]
        int prev1 = 0; // Represents dp[i-1]

        for (int i = start; i <= end; i++) {
            // Logic: max( pick, notPick )
            // pick = arr[i] + prev2
            // notPick = prev1
            int current = Math.max(arr[i] + prev2, prev1);

            // Shift pointers
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }
}