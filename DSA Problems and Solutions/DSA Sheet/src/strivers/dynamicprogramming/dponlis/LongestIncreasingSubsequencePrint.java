package com.questions.strivers.dynamicprogramming.dponlis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ==================================================================================================
 * PROBLEM: PRINT LONGEST INCREASING SUBSEQUENCE (Striver DP-42)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given an integer array nums, return the Longest Increasing Subsequence (LIS).
 * If multiple exist, return the one that is "index-wise lexicographically smallest".
 *
 * EXAMPLE:
 * Input: nums = [1, 3, 2, 4]
 * Output: [1, 3, 4] (Indices 0,1,3) is smaller index-wise than [1, 2, 4] (Indices 0,2,3).
 * ==================================================================================================
 */
public class LongestIncreasingSubsequencePrint {

    public static void main(String[] args) {
        int[] nums = {10, 22, 9, 33, 21, 50, 41, 60, 80};
        System.out.println("Input: " + Arrays.toString(nums));
        System.out.println("--------------------------------------------------");

        // 1. Recursion (Returns List - Very Slow)
        System.out.println("1. Recursion       : " + solveRecursion(0, -1, nums));

        // 2. Memoization (Returns List - Slow)
        // We use a Object[][] memo because we are storing Lists, not just ints.
        List<Integer>[][] memo = new ArrayList[nums.length][nums.length + 1];
        System.out.println("2. Memoization     : " + solveMemoization(0, -1, nums, memo));

        // 3. Tabulation (Standard & Correct for Printing)
        System.out.println("3. Tabulation      : " + solveTabulation(nums));

        // 4. Space Optimization (Explanation Only)
        System.out.println("4. Space Optimized : [Cannot Print - Path info is lost in optimization]");

        // 5. Binary Search (O(N log N) - Prints Value-Optimal LIS)
        System.out.println("5. Binary Search   : " + solveBinarySearch(nums));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (RETURNS LIST)
     * ----------------------------------------------------------------------
     * LOGIC:
     * We return the actual ArrayList instead of just the length.
     * At each step, we compare the size of the list returned by "Take" vs "Not Take".
     *
     * COMPLEXITY:
     * - Time: O(2^N * N) -> Exponential + List copying.
     * - Space: O(N^2) -> Recursion stack storing lists.
     */
    private static List<Integer> solveRecursion(int ind, int prev_ind, int[] nums) {
        if (ind == nums.length) return new ArrayList<>();

        // Option 1: Not Take
        List<Integer> notTakeList = solveRecursion(ind + 1, prev_ind, nums);

        // Option 2: Take
        List<Integer> takeList = new ArrayList<>();
        if (prev_ind == -1 || nums[ind] > nums[prev_ind]) {
            List<Integer> subResult = solveRecursion(ind + 1, ind, nums);
            takeList.add(nums[ind]);
            takeList.addAll(subResult);
        }

        // Return the longer list
        // If equal, 'takeList' is usually index-wise better because we process left-to-right?
        // Actually, recursion builds from end. Simple size comparison suffices for basic LIS.
        if (takeList.size() > notTakeList.size()) {
            return takeList;
        }
        return notTakeList;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (RETURNS LIST)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Same as recursion, but we cache the Result List.
     * Note: This is memory heavy.
     *
     * COMPLEXITY:
     * - Time: O(N^2 * N) -> List copying adds a factor of N.
     */
    private static List<Integer> solveMemoization(int ind, int prev_ind, int[] nums, List<Integer>[][] memo) {
        if (ind == nums.length) return new ArrayList<>();

        if (memo[ind][prev_ind + 1] != null) return memo[ind][prev_ind + 1];

        // Not Take
        List<Integer> notTakeList = solveMemoization(ind + 1, prev_ind, nums, memo);

        // Take
        List<Integer> takeList = new ArrayList<>();
        if (prev_ind == -1 || nums[ind] > nums[prev_ind]) {
            // Important: Create a NEW list to avoid mutating the referenced memoized list
            List<Integer> subResult = solveMemoization(ind + 1, ind, nums, memo);
            takeList.add(nums[ind]);
            takeList.addAll(subResult);
        }

        if (takeList.size() > notTakeList.size()) {
            return memo[ind][prev_ind + 1] = takeList;
        }
        return memo[ind][prev_ind + 1] = notTakeList;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (THE CORRECT SOLUTION)
     * ----------------------------------------------------------------------
     * LOGIC:
     * 1. dp[i] = Length of LIS ending at index i.
     * 2. hash[i] = Index of the previous element in the LIS.
     * 3. Iterate j from 0 to i-1. If extending gives a longer LIS, update.
     * 4. Tie-Breaking: If lengths are equal, DO NOT update. This ensures we keep
     * the earlier 'j' (smallest index).
     *
     * COMPLEXITY:
     * - Time: O(N^2)
     * - Space: O(N)
     */
    private static List<Integer> solveTabulation(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int[] hash = new int[n];
        Arrays.fill(dp, 1);

        // Initialize hash to point to itself
        for(int i=0; i<n; i++) hash[i] = i;

        int maxLen = 1;
        int lastIndex = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                // If strictly increasing AND extends the sequence length
                if (nums[j] < nums[i] && 1 + dp[j] > dp[i]) {
                    dp[i] = 1 + dp[j];
                    hash[i] = j; // Track the parent
                }
            }
            // Update global max
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                lastIndex = i;
            }
        }

        // Reconstruct path backwards
        List<Integer> lis = new ArrayList<>();
        lis.add(nums[lastIndex]);
        while (hash[lastIndex] != lastIndex) {
            lastIndex = hash[lastIndex];
            lis.add(nums[lastIndex]);
        }
        Collections.reverse(lis);
        return lis;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED
     * ----------------------------------------------------------------------
     * LIMITATION:
     * Space optimization reduces the DP table to two 1D arrays (curr/next).
     * By doing so, we overwrite the "history" of where the LIS came from.
     * Therefore, **we cannot reconstruct/print the sequence** using Space Optimization.
     */

    /**
     * ----------------------------------------------------------------------
     * APPROACH 5: BINARY SEARCH (O(N log N))
     * ----------------------------------------------------------------------
     * LOGIC:
     * 1. 'temp' list stores INDICES of the tails of increasing subsequences.
     * 2. 'parent' array tracks the predecessor index for every element.
     * 3. When we append or replace in 'temp', we link the current index to the
     * element previously at the end of the chain.
     *
     * Note: This usually finds the LIS that is "Lexicographically smallest by Value".
     *
     * COMPLEXITY:
     * - Time: O(N log N)
     * - Space: O(N)
     */
    private static List<Integer> solveBinarySearch(int[] nums) {
        if (nums.length == 0) return new ArrayList<>();

        List<Integer> temp = new ArrayList<>(); // Stores INDICES
        int[] parent = new int[nums.length];
        Arrays.fill(parent, -1);

        temp.add(0); // Add first index

        for (int i = 1; i < nums.length; i++) {
            // Compare values using the indices in 'temp'
            int lastIndexInTemp = temp.get(temp.size() - 1);

            if (nums[i] > nums[lastIndexInTemp]) {
                parent[i] = lastIndexInTemp;
                temp.add(i);
            } else {
                // Find lower bound: First element in temp where nums[tempVal] >= nums[i]
                int ind = lowerBound(temp, nums, nums[i]);

                // Replace index
                temp.set(ind, i);

                // Update parent relationship
                if (ind > 0) {
                    parent[i] = temp.get(ind - 1);
                }
            }
        }

        // Reconstruct
        List<Integer> lis = new ArrayList<>();
        int curr = temp.get(temp.size() - 1);
        while (curr != -1) {
            lis.add(nums[curr]);
            curr = parent[curr];
        }
        Collections.reverse(lis);
        return lis;
    }

    // Binary Search Helper
    private static int lowerBound(List<Integer> tempIndices, int[] nums, int target) {
        int left = 0, right = tempIndices.size() - 1;
        int ans = right;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[tempIndices.get(mid)] >= target) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return ans;
    }
}