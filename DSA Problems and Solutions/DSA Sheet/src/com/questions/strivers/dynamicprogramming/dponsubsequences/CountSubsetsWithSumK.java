package com.questions.strivers.dynamicprogramming.dponsubsequences;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: COUNT SUBSETS WITH SUM K (Striver DP-17)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given an array 'arr' of N integers and an integer 'target', find the number of subsets
 * of the array whose sum is equal to 'target'.
 *
 * EXAMPLE 1:
 * Input: arr = [1, 2, 2, 3], target = 3
 * Output: 3
 * Explanation: The subsets are:
 * 1. {1, 2} (using first 2)
 * 2. {1, 2} (using second 2)
 * 3. {3}
 *
 * EXAMPLE 2:
 * Input: arr = [1, 2, 3, 4, 5], K = 5
 * Output: 3
 * Explanation: The subsets are
 * 1. [5],
 * 2. [2, 3],
 * 3. [1, 4].
 *
 * EXAMPLE 3(Handling Zeros):
 * Input: arr = [0, 1], target = 1
 * Output: 2
 * Explanation:
 * 1. {1}
 * 2. {0, 1}
 *
 * APPROACH STRATEGY:
 * This is a variation of the "Subset Sum" problem.
 * Instead of returning true/false, we return the count of valid paths (pick + notPick).
 *
 * CRITICAL EDGE CASE (ZEROS):
 * If the array contains zeros (e.g., [0, 0, 1] target=1), standard DP initialization (dp[0]=1) fails.
 * We must handle the case where picking a '0' doesn't change the sum but counts as a distinct subset.
 * ==================================================================================================
 */
public class CountSubsetsWithSumK {

    public static void main(String[] args) {
        // Test Case 1: Standard positive integers
        int[] arr1 = {1, 2, 2, 3};
        int k1 = 3;

        // Test Case 2: Array containing Zeros (Edge Case)
        int[] arr2 = {0, 0, 1};
        int k2 = 1;

        System.out.println("--- Test Case 1 ---");
        testAllApproaches(arr1, k1);

        System.out.println("\n--- Test Case 2 (Zeros) ---");
        testAllApproaches(arr2, k2);
    }

    private static void testAllApproaches(int[] arr, int k) {
        int n = arr.length;
        System.out.println("Array: " + Arrays.toString(arr) + ", Target: " + k);

        // 1. Recursion
        System.out.println("1. Recursion       : " + countSubsetsRecursive(n - 1, k, arr));

        // 2. Memoization
        int[][] dp = new int[n][k + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("2. Memoization     : " + countSubsetsMemo(n - 1, k, arr, dp));

        // 3. Tabulation
        System.out.println("3. Tabulation      : " + countSubsetsTabulation(arr, k));

        // 4. Space Optimized
        System.out.println("4. Space Optimized : " + countSubsetsSpaceOptimized(arr, k));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * For every element, we have two choices:
     * 1. Pick: Subtract value from target (if value <= target).
     * 2. Not Pick: Target remains the same.
     * We return the sum of ways from both choices.
     *
     *
     * COMPLEXITY:
     * - Time: O(2^N) -> Exponential
     * - Space: O(N) -> Recursion Stack
     */
    private static int countSubsetsRecursive(int index, int target, int[] arr) {
        // Base Case: We are at the first element (index 0)
        if (index == 0) {
            // Case A: Target is 0 and Element is 0
            // We can take the 0 or not take it. Both result in sum 0. (2 ways)
            if (target == 0 && arr[0] == 0) return 2;

            // Case B: Target is 0 (and Element != 0) -> Not pick (1 way)
            // Case C: Target == Element -> Pick (1 way)
            if (target == 0 || target == arr[0]) return 1;

            return 0;
        }

        // Choice 1: Not Pick
        int notPick = countSubsetsRecursive(index - 1, target, arr);

        // Choice 2: Pick (Only if valid)
        int pick = 0;
        if (arr[index] <= target) {
            pick = countSubsetsRecursive(index - 1, target - arr[index], arr);
        }

        return pick + notPick;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Cache results in `dp[index][target]`.
     * Handles overlapping subproblems.
     *
     * COMPLEXITY:
     * - Time: O(N * K)
     * - Space: O(N * K) + O(N) Stack
     */
    private static int countSubsetsMemo(int index, int target, int[] arr, int[][] dp) {
        if (index == 0) {
            if (target == 0 && arr[0] == 0) return 2;
            if (target == 0 || target == arr[0]) return 1;
            return 0;
        }

        if (dp[index][target] != -1) return dp[index][target];

        int notPick = countSubsetsMemo(index - 1, target, arr, dp);

        int pick = 0;
        if (arr[index] <= target) {
            pick = countSubsetsMemo(index - 1, target - arr[index], arr, dp);
        }

        return dp[index][target] = pick + notPick;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * dp[i][t] = Number of subsets using first 'i' elements summing to 't'.
     *
     * Base Case Initialization (Index 0):
     * - If arr[0] == 0: dp[0][0] = 2 (Pick 0, Not Pick 0).
     * - Else: dp[0][0] = 1 (Not Pick).
     * - If arr[0] <= K: dp[0][arr[0]] = 1 (Pick arr[0]).
     *
     * COMPLEXITY:
     * - Time: O(N * K)
     * - Space: O(N * K)
     */
    private static int countSubsetsTabulation(int[] arr, int k) {
        int n = arr.length;
        int[][] dp = new int[n][k + 1];

        // 1. Initialize Base Case for Index 0
        if (arr[0] == 0) {
            dp[0][0] = 2; // Two ways to make sum 0: {} and {0}
        } else {
            dp[0][0] = 1; // One way to make sum 0: {}
        }

        if (arr[0] != 0 && arr[0] <= k) {
            dp[0][arr[0]] = 1; // One way to make sum arr[0]: {arr[0]}
        }

        // 2. Iterate DP Table
        for (int index = 1; index < n; index++) {
            for (int target = 0; target <= k; target++) {

                int notPick = dp[index - 1][target];

                int pick = 0;
                if (arr[index] <= target) {
                    pick = dp[index - 1][target - arr[index]];
                }

                dp[index][target] = pick + notPick;
            }
        }

        return dp[n - 1][k];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED DP
     * ----------------------------------------------------------------------
     * LOGIC:
     * We only need the `prev` row to compute `curr`.
     * `prev` represents dp[index-1], `curr` represents dp[index].
     *
     * COMPLEXITY:
     * - Time: O(N * K)
     * - Space: O(K) -> Single row array
     */
    private static int countSubsetsSpaceOptimized(int[] arr, int k) {
        int n = arr.length;
        int[] prev = new int[k + 1];

        // 1. Base Case Initialization
        if (arr[0] == 0) prev[0] = 2;
        else prev[0] = 1;

        if (arr[0] != 0 && arr[0] <= k) prev[arr[0]] = 1;

        // 2. Iterate
        for (int index = 1; index < n; index++) {
            int[] curr = new int[k + 1]; // Temp array for current row

            for (int target = 0; target <= k; target++) {
                int notPick = prev[target];

                int pick = 0;
                if (arr[index] <= target) {
                    pick = prev[target - arr[index]];
                }

                curr[target] = pick + notPick;
            }
            // Update prev to curr
            prev = curr;
        }

        return prev[k];
    }
}