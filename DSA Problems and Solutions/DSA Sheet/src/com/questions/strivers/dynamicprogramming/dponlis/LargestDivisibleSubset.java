package com.questions.strivers.dynamicprogramming.dponlis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ==================================================================================================
 * PROBLEM: LARGEST DIVISIBLE SUBSET (LeetCode 368)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given a set of distinct positive integers nums, return the largest subset answer such that
 * every pair (answer[i], answer[j]) satisfies:
 * answer[i] % answer[j] == 0  OR  answer[j] % answer[i] == 0.
 *
 * EXAMPLE:
 * Input: nums = [1, 2, 4, 8]
 * Output: [1, 2, 4, 8]
 *
 * KEY STRATEGY:
 * 1. SORT the array. This simplifies the condition.
 * If a < b < c and a|b and b|c, then a|c (Transitivity).
 * We only need to check if nums[i] % nums[j] == 0 for j < i.
 * 2. This becomes "Longest Path in a DAG" or strictly, an LIS variation.
 *
 * APPROACH SUMMARY:
 * 1. Recursion: Try to take/not take. (Returns List -> Very Slow)
 * 2. Memoization: Cache results. (Returns List -> Slow due to copying)
 * 3. Tabulation: Standard DP O(N^2). Best for Printing.
 * 4. Space Optimization: Good for length, impossible for Printing.
 * ==================================================================================================
 */
public class LargestDivisibleSubset {

    public static void main(String[] args) {
        int[] nums = {1, 16, 7, 8, 4};
        // MUST SORT first for all approaches to work logic-wise
        Arrays.sort(nums);
        System.out.println("Sorted Input: " + Arrays.toString(nums));
        System.out.println("--------------------------------------------------");

        // 1. Recursion
        System.out.println("1. Recursion       : " + solveRecursion(0, -1, nums));

        // 2. Memoization
        // Memo table stores Lists to cache the actual subset
        List<Integer>[][] memo = new ArrayList[nums.length][nums.length + 1];
        System.out.println("2. Memoization     : " + solveMemoization(0, -1, nums, memo));

        // 3. Tabulation (The Standard Solution)
        System.out.println("3. Tabulation      : " + solveTabulation(nums));

        // 4. Space Optimization
        System.out.println("4. Space Optimized : [Length Only: " + solveSpaceOptimized(nums) + "] (Cannot Print)");

        // 5. Binary Search Explanation
        System.out.println("5. Binary Search   : [NOT APPLICABLE - See Documentation]");
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (RETURNS LIST)
     * ----------------------------------------------------------------------
     * LOGIC:
     * - Sort array first.
     * - For each element, if it divides the last taken element (or if it's the first),
     * we have a choice: Take it or Leave it.
     * - If it doesn't divide, we must Leave it.
     *
     * COMPLEXITY:
     * - Time: O(2^N * N) (Exponential + List Copying)
     * - Space: O(N^2) (Stack depth * List size)
     */
    private static List<Integer> solveRecursion(int ind, int prev_ind, int[] nums) {
        if (ind == nums.length) return new ArrayList<>();

        // Option 1: Skip (Not Take)
        List<Integer> notTake = solveRecursion(ind + 1, prev_ind, nums);

        // Option 2: Take (Only if valid)
        List<Integer> take = new ArrayList<>();
        if (prev_ind == -1 || nums[ind] % nums[prev_ind] == 0) {
            List<Integer> subResult = solveRecursion(ind + 1, ind, nums);
            take.add(nums[ind]);
            take.addAll(subResult);
        }

        // Return the larger list
        return take.size() > notTake.size() ? take : notTake;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (RETURNS LIST)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Cache the resulting List for state (ind, prev_ind).
     *
     * COMPLEXITY:
     * - Time: O(N^3) (States N^2 * Copying List N)
     * - Space: O(N^3) (Storage for lists)
     */
    private static List<Integer> solveMemoization(int ind, int prev_ind, int[] nums, List<Integer>[][] memo) {
        if (ind == nums.length) return new ArrayList<>();

        // Check cache (prev_ind shifted by +1)
        if (memo[ind][prev_ind + 1] != null) return memo[ind][prev_ind + 1];

        // Not Take
        List<Integer> notTake = solveMemoization(ind + 1, prev_ind, nums, memo);

        // Take
        List<Integer> take = new ArrayList<>();
        if (prev_ind == -1 || nums[ind] % nums[prev_ind] == 0) {
            // Important: Create NEW list to strictly separate references
            List<Integer> subResult = solveMemoization(ind + 1, ind, nums, memo);
            take.add(nums[ind]);
            take.addAll(subResult);
        }

        if (take.size() > notTake.size()) {
            return memo[ind][prev_ind + 1] = take;
        }
        return memo[ind][prev_ind + 1] = notTake;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (STANDARD & OPTIMAL)
     * ----------------------------------------------------------------------
     * LOGIC:
     * 1. dp[i] = Length of Largest Divisible Subset ending at index i.
     * 2. hash[i] = Index of the previous element in that subset (to reconstruct).
     * 3. Loop i from 0 to N. Loop j from 0 to i.
     * 4. If nums[i] % nums[j] == 0 and extending j is better, update dp[i].
     *
     * COMPLEXITY:
     * - Time: O(N^2)
     * - Space: O(N)
     */
    private static List<Integer> solveTabulation(int[] nums) {
        int n = nums.length;
        if (n == 0) return new ArrayList<>();

        int[] dp = new int[n];
        int[] hash = new int[n];
        Arrays.fill(dp, 1);

        // Initialize hash to point to itself
        for(int i=0; i<n; i++) hash[i] = i;

        int maxLen = 1;
        int lastIndex = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // Key Check: Divisibility AND Length Increase
                if (nums[i] % nums[j] == 0 && 1 + dp[j] > dp[i]) {
                    dp[i] = 1 + dp[j];
                    hash[i] = j;
                }
            }
            // Track global max
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                lastIndex = i;
            }
        }

        // Reconstruct Path using 'hash' array
        List<Integer> ans = new ArrayList<>();
        ans.add(nums[lastIndex]);
        while (hash[lastIndex] != lastIndex) {
            lastIndex = hash[lastIndex];
            ans.add(nums[lastIndex]);
        }

        // Optional: Reverse to show Smallest -> Largest
        Collections.reverse(ans);
        return ans;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED (LENGTH ONLY)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Similar to LIS, we can use 1D DP logic, but since Tabulation Approach 3
     * is ALREADY using a 1D DP array (dp[]), it is technically already Space Optimized
     * to O(N).
     *
     * Note: Often "Space Optimization" refers to reducing O(N^2) to O(N).
     * Since LIS/LDS is natively O(N) space, further optimization isn't usually needed
     * or possible while retaining the ability to print.
     *
     * Below is the function that just returns the Max Length.
     */
    private static int solveSpaceOptimized(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int maxLen = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] % nums[j] == 0 && 1 + dp[j] > dp[i]) {
                    dp[i] = 1 + dp[j];
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }
}