package com.questions.strivers.dynamicprogramming.oneddp;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: MAXIMUM SUM OF NON-ADJACENT ELEMENTS (HOUSE ROBBER)
 * Problem Statement: Given an array of N positive integers,
 * we need to return the maximum sum of the subsequence such that no two elements of the subsequence are adjacent elements in the array.
 *
 * Note: A subsequence of an array is a list with elements of the array where some elements are deleted
 * (or not deleted at all) and the elements should be in the same order in the subsequence as in the array.
 *
 * Examples
 * Input: nums = [1, 2, 4]
 * Output: 5
 * Explanation:
 * Subsequence {1,4} gives maximum sum.
 *
 * Input:  [2, 1, 4, 9]
 * Output: 11
 * Explanation:
 * Subsequence {2,9} gives maximum sum
 *
 * 198. House Robber
 * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed,
 * the only constraint stopping you from robbing each of them is that adjacent houses have security systems connected
 * and it will automatically contact the police if two adjacent houses were broken into on the same night.
 *
 * Given an integer array nums representing the amount of money of each house,
 * return the maximum amount of money you can rob tonight without alerting the police.
 *
 * Example 1:
 * Input: nums = [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 * Total amount you can rob = 1 + 3 = 4.
 *
 * Example 2:
 * Input: nums = [2,7,9,3,1]
 * Output: 12
 * Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
 * Total amount you can rob = 2 + 9 + 1 = 12.
 * ==================================================================================================
 * APPROACH:
 * At every house (index 'i'), we have two choices:
 * 1. PICK: Rob this house. We gain 'arr[i]' money, but we cannot rob 'i-1'.
 * We must move to 'i-2'.
 * Sum = arr[i] + solve(i-2)
 *
 * 2. NOT PICK: Skip this house. We gain 0 money from this house.
 * We can rob 'i-1'.
 * Sum = 0 + solve(i-1)
 *
 * RECURRENCE RELATION:
 * dp[i] = max( arr[i] + dp[i-2],  dp[i-1] )
 * ==================================================================================================
 */
public class MaxSumNonAdj {

    public static void main(String[] args) {
        int[] arr = {2, 1, 4, 9}; // Example Input. Expected Output: 11 (2 + 9)
        int n = arr.length;

        System.out.println("House Values: " + Arrays.toString(arr));
        System.out.println("--------------------------------------------------");

        // 1. Recursive Approach
        long start = System.nanoTime();
        // We pass 'n-1' because we start deciding from the last index down to 0
        System.out.println("1. Recursion       : " + recursive(n - 1, arr));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");

        // 2. Memoization (Top-Down DP)
        int[] dp = new int[n];
        Arrays.fill(dp, -1); // Initialize cache with -1
        start = System.nanoTime();
        System.out.println("2. Memoization     : " + memoization(n - 1, arr, dp));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");

        // 3. Tabulation (Bottom-Up DP)
        int[] dpTab = new int[n];
        start = System.nanoTime();
        System.out.println("3. Tabulation      : " + tabulation(n, arr, dpTab));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");

        // 4. Space Optimization (Best Solution)
        start = System.nanoTime();
        System.out.println("4. Space Optimized : " + spaceOptimized(n, arr));
        System.out.println("   Time Taken      : " + (System.nanoTime() - start) + " ns");
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Try both choices (Pick vs Not Pick) at every step and take the maximum.
     *
     * COMPLEXITY:
     * - Time: O(2^N) -> Exponential. We explore two branches for every element.
     * - Space: O(N) -> Recursion stack depth.
     */
    private static int recursive(int index, int[] arr) {
        // Base Case 1: If index < 0, we can't rob any money.
        if (index < 0) return 0;

        // Base Case 2: If we are at index 0, we simply rob it.
        if (index == 0) return arr[0];

        // Option 1: PICK the current element.
        // If we pick 'index', we cannot pick 'index-1', so we recurse for 'index-2'.
        int pick = arr[index] + recursive(index - 2, arr);

        // Option 2: DO NOT PICK the current element.
        // If we skip 'index', we are free to rob 'index-1', so we recurse for 'index-1'.
        int notPick = 0 + recursive(index - 1, arr);

        return Math.max(pick, notPick);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Store the result of each index in 'dp' array to avoid re-calculating.
     *
     * COMPLEXITY:
     * - Time: O(N) -> Each state is computed once.
     * - Space: O(N) + O(N) -> DP Array + Recursion Stack.
     */
    private static int memoization(int index, int[] arr, int[] dp) {
        if (index < 0) return 0;
        if (index == 0) return arr[0];

        // Step 1: Check Cache
        if (dp[index] != -1) return dp[index];

        // Step 2: Compute
        int pick = arr[index] + memoization(index - 2, arr, dp);
        int notPick = 0 + memoization(index - 1, arr, dp);

        // Step 3: Store and Return
        return dp[index] = Math.max(pick, notPick);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Build the solution from index 0 up to N-1 using a loop.
     * dp[i] stores the max money we can rob up to house 'i'.
     *
     * COMPLEXITY:
     * - Time: O(N) -> Single loop.
     * - Space: O(N) -> DP Array.
     */
    private static int tabulation(int n, int[] arr, int[] dp) {
        // Handle edge case for empty array
        if (n == 0) return 0;

        // Base Case: Max money at index 0 is just the value of house 0
        dp[0] = arr[0];

        // Loop from the second element (index 1) to the end
        for (int i = 1; i < n; i++) {
            // Pick: Current value + best value from index i-2 (if it exists)
            int pick = arr[i];
            if (i > 1) {
                pick += dp[i - 2];
            }

            // Not Pick: Just take the best value from index i-1
            int notPick = dp[i - 1];

            // Store the max of both choices
            dp[i] = Math.max(pick, notPick);
        }

        // The answer is the best we can do ending at the last index
        return dp[n - 1];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED (INTERVIEW STANDARD)
     * ----------------------------------------------------------------------
     * LOGIC:
     * To calculate current state, we only need the Previous (i-1) and
     * the Second Previous (i-2) values. We don't need the full array.
     *
     * - prev1: represents dp[i-1]
     * - prev2: represents dp[i-2]
     *
     * COMPLEXITY:
     * - Time: O(N)
     * - Space: O(1) -> Constant space.
     */
    private static int spaceOptimized(int n, int[] arr) {
        if (n == 0) return 0;

        int prev1 = arr[0]; // Represents dp[i-1] (Initially dp[0])
        int prev2 = 0;      // Represents dp[i-2] (Initially 0/dummy)

        for (int i = 1; i < n; i++) {
            // Pick
            int pick = arr[i];
            if (i > 1) {
                pick += prev2; // Add value from 2 steps back
            }

            // Not Pick
            int notPick = prev1; // Take value from 1 step back

            int current = Math.max(pick, notPick);

            // Shift pointers for next iteration
            prev2 = prev1;
            prev1 = current;
        }

        return prev1; // prev1 holds the max value for the full array
    }
}