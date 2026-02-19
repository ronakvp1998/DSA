package com.questions.strivers.dynamicprogramming.dponlis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ==================================================================================================
 * PROBLEM: LONGEST INCREASING SUBSEQUENCE (LeetCode 300)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given an integer array nums, return the length of the longest strictly increasing subsequence.
 *
 * EXAMPLE 1:
 * Input: nums = [10, 9, 2, 5, 3, 7, 101, 18]
 * Output: 4
 * Explanation: The LIS is [2, 3, 7, 18].
 *
 * EXAMPLE 2:
 * Input: nums = [0, 1, 0, 3, 2, 3]
 * Output: 4
 *
 * --------------------------------------------------------------------------------------------------
 * OVERVIEW OF 5 APPROACHES
 * --------------------------------------------------------------------------------------------------
 * 1. RECURSION: Try to take/not take elements based on previous selection. O(2^N).
 * 2. MEMOIZATION: Cache the state (index, prev_index). O(N^2).
 * 3. TABULATION: Bottom-Up DP. O(N^2).
 * 4. SPACE OPTIMIZATION: Reduce 2D DP to two 1D arrays. O(N^2).
 * 5. BINARY SEARCH (Optimal): Build the subsequence using replacement strategy. O(N log N).
 * ==================================================================================================
 */
public class LongestIncreasingSubsequenceLength {

    public static void main(String[] args) {
        int[] nums = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("Input: " + Arrays.toString(nums));
        System.out.println("--------------------------------------------------");

        // 1. Recursion
        // Start at index 0, with previous index -1 (meaning no element picked yet)
        System.out.println("1. Recursion       : " + solveRecursive(0, -1, nums));

        // 2. Memoization
        // State: dp[index][prev_index + 1] -> shifted coordinate for prev_index
        int n = nums.length;
        int[][] dp = new int[n][n + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("2. Memoization     : " + solveMemoization(0, -1, nums, dp));

        // 3. Tabulation
        System.out.println("3. Tabulation      : " + solveTabulation(nums));

        // 4. Space Optimization
        System.out.println("4. Space Optimized : " + solveSpaceOptimized(nums));

        // 5. Binary Search (Best)
        System.out.println("5. Binary Search   : " + solveBinarySearch(nums));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * For every element, we have two choices:
     * 1. Not Take: Skip the element.
     * 2. Take: Include it ONLY IF it is greater than the 'prev' element.
     *
     * COMPLEXITY:
     * - Time: O(2^N) -> Exponential.
     * - Space: O(N) -> Recursion Stack.
     */
    private static int solveRecursive(int ind, int prev_ind, int[] nums) {
        // Base Case: Reached end of array
        if (ind == nums.length) return 0;

        // Option 1: Not Take
        int lenNotTake = 0 + solveRecursive(ind + 1, prev_ind, nums);

        // Option 2: Take (only if valid)
        int lenTake = 0;
        if (prev_ind == -1 || nums[ind] > nums[prev_ind]) {
            lenTake = 1 + solveRecursive(ind + 1, ind, nums);
        }

        return Math.max(lenNotTake, lenTake);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Cache the result of (ind, prev_ind).
     * Coordinate Shift: Since 'prev_ind' starts at -1, we store it at index 'prev_ind + 1'.
     *
     * COMPLEXITY:
     * - Time: O(N * N)
     * - Space: O(N * N) + Stack
     */
    private static int solveMemoization(int ind, int prev_ind, int[] nums, int[][] dp) {
        if (ind == nums.length) return 0;

        // Check Cache (using shifted index for prev_ind)
        if (dp[ind][prev_ind + 1] != -1) return dp[ind][prev_ind + 1];

        // Not Take
        int lenNotTake = 0 + solveMemoization(ind + 1, prev_ind, nums, dp);

        // Take
        int lenTake = 0;
        if (prev_ind == -1 || nums[ind] > nums[prev_ind]) {
            lenTake = 1 + solveMemoization(ind + 1, ind, nums, dp);
        }

        return dp[ind][prev_ind + 1] = Math.max(lenNotTake, lenTake);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Iterate backwards from N-1 to 0.
     * The nested loop 'prev' goes from 'ind-1' down to -1.
     * dp[ind][prev_ind+1] stores max length.
     *
     * COMPLEXITY:
     * - Time: O(N * N)
     * - Space: O(N * N)
     */
    private static int solveTabulation(int[] nums) {
        int n = nums.length;
        // dp[index][prev_index + 1]
        int[][] dp = new int[n + 1][n + 1];

        // Base case: when ind == n, result is 0 (already 0 by default initialization)

        for (int ind = n - 1; ind >= 0; ind--) {
            for (int prev_ind = ind - 1; prev_ind >= -1; prev_ind--) {

                // Not Take
                // Current state depends on ind+1 with SAME prev_ind
                int lenNotTake = 0 + dp[ind + 1][prev_ind + 1];

                // Take
                int lenTake = 0;
                if (prev_ind == -1 || nums[ind] > nums[prev_ind]) {
                    // Current state depends on ind+1 with NEW prev_ind (which is 'ind')
                    lenTake = 1 + dp[ind + 1][ind + 1];
                }

                dp[ind][prev_ind + 1] = Math.max(lenNotTake, lenTake);
            }
        }
        return dp[0][0]; // corresponds to ind=0, prev_ind=-1 (+1 shift -> 0)
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZATION (TWO 1D ARRAYS)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Tabulation depends on dp[ind+1] (Next Row) to compute dp[ind] (Current Row).
     * We reduce the N*N matrix to two arrays of size N.
     *
     * COMPLEXITY:
     * - Time: O(N * N)
     * - Space: O(N)
     */
    private static int solveSpaceOptimized(int[] nums) {
        int n = nums.length;
        int[] next = new int[n + 1];
        int[] curr = new int[n + 1];

        for (int ind = n - 1; ind >= 0; ind--) {
            for (int prev_ind = ind - 1; prev_ind >= -1; prev_ind--) {

                int lenNotTake = 0 + next[prev_ind + 1];

                int lenTake = 0;
                if (prev_ind == -1 || nums[ind] > nums[prev_ind]) {
                    lenTake = 1 + next[ind + 1];
                }

                curr[prev_ind + 1] = Math.max(lenNotTake, lenTake);
            }
            // Move current row to next row for next iteration
            next = curr.clone();
        }
        return next[0];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 5: BINARY SEARCH (OPTIMAL O(N log N))
     * ----------------------------------------------------------------------
     * LOGIC (Patience Sorting):
     * We maintain a list 'temp'.
     * 1. If nums[i] > last element of temp: Append nums[i].
     * 2. Else: Use Binary Search (Lower Bound) to find the first element in temp >= nums[i]
     * and REPLACE it with nums[i].
     * Why Replace? It keeps the sequence length the same but lowers the value of the ending element,
     * allowing us to potentially extend the sequence further in future steps.
     *
     *
     * The Algorithm
     * Initialize an empty list tails. Iterate through every number x in nums:
     *
     * Case 1: Extend (Append)
     * If x is larger than the last element in tails, it means we can extend our longest existing subsequence.
     * Action: Append x to tails.
     *
     * Case 2: Replace (Update)
     * If x is smaller or equal to the last element, it means x can replace an existing number to create a "better" (smaller) tail for a subsequence of that specific length.
     * Action: Find the first element in tails that is >= x (using Binary Search) and overwrite it with x.
     *
     * Result: The size of tails is the length of the LIS.
     *
     * COMPLEXITY:
     * - Time: O(N * log N)
     * - Space: O(N)
     */
    private static int solveBinarySearch(int[] nums) {
        if (nums.length == 0) return 0;

        List<Integer> temp = new ArrayList<>();
        temp.add(nums[0]);

        for (int i = 1; i < nums.length; i++) {
            // Case 1: Extend
            if (nums[i] > temp.get(temp.size() - 1)) {
                temp.add(nums[i]);
            }
            // Case 2: Replace
            else {
                int ind = lowerBound(temp, nums[i]);
                temp.set(ind, nums[i]);
            }
        }
        return temp.size();
    }

    // Helper: Find first index where element >= target
    private static int lowerBound(List<Integer> list, int target) {
        int left = 0, right = list.size() - 1;
        int ans = right;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) >= target) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }
}