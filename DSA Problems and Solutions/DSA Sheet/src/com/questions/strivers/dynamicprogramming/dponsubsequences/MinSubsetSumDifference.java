package com.questions.strivers.dynamicprogramming.dponsubsequences;
//https://takeuforward.org/data-structure/partition-set-into-2-subsets-with-min-absolute-sum-diff-dp-16/

//Problem Statement:
//        Partition A Set Into Two Subsets With Minimum Absolute Sum Difference
//        Pre-req: Subset Sum equal to target, Recursion on Subsequences
//        Problem Link: Partition A Set Into Two Subsets With Minimum Absolute Sum Difference
//        We are given an array ‘ARR’ with N positive integers.
//        We need to partition the array into two subsets such
//        that the absolute difference of the sum of elements of the subsets is minimum.
//        We need to return only the minimum absolute difference of the sum of elements of the two partitions.
// arr = {1, 2, 3,4} => {4,1} & {2,3} => |5 - 5 | => 0
// not working in leetcode

import java.util.Arrays;

public class MinSubsetSumDifference {

    // --------------------------------------------------------------------------------------------------
    // 1️⃣ RECURSION
    // --------------------------------------------------------------------------------------------------
    // Time Complexity: O(2^N)
    // - Each element has two choices (include in sum1 or not)
    // Space Complexity: O(N) (due to recursion stack depth)

    private static int minDifferenceRecursive(int index, int sum1, int totalSum, int[] arr) {
        // Base Case: when we are at the first element (index == 0)
        if (index == 0) {
            // One subset has sum1, the other has (totalSum - sum1)
            int sum2 = totalSum - sum1;
            // Return absolute difference
            return Math.abs(sum1 - sum2);
        }

        // Option 1: Include current element in subset 1 (sum1)
        int pick = minDifferenceRecursive(index - 1, sum1 + arr[index], totalSum, arr);

        // Option 2: Do not include current element in subset 1
        int notPick = minDifferenceRecursive(index - 1, sum1, totalSum, arr);

        // Return minimum of both options
        return Math.min(pick, notPick);
    }

    // --------------------------------------------------------------------------------------------------
    // 2️⃣ MEMOIZATION (Top-down DP)
    // --------------------------------------------------------------------------------------------------
    // Time Complexity: O(N * totalSum)
    // - Each state (index, sum1) is computed once
    // Space Complexity: O(N * totalSum) + O(N) stack space

    private static int minDifferenceMemo(int index, int sum1, int totalSum, int[] arr, int[][] dp) {
        // Base Case
        if (index == 0) {
            int sum2 = totalSum - sum1;
            return Math.abs(sum1 - sum2);
        }

        // Return precomputed result if already solved
        if (dp[index][sum1] != -1)
            return dp[index][sum1];

        // Choice 1: Include current element in subset 1
        int pick = minDifferenceMemo(index - 1, sum1 + arr[index], totalSum, arr, dp);

        // Choice 2: Do not include it
        int notPick = minDifferenceMemo(index - 1, sum1, totalSum, arr, dp);

        // Store the minimum result in DP table
        dp[index][sum1] = Math.min(pick, notPick);
        return dp[index][sum1];
    }

    // --------------------------------------------------------------------------------------------------
    // 3️⃣ TABULATION (Bottom-up DP)
    // --------------------------------------------------------------------------------------------------
    // Time Complexity: O(N * totalSum)
    // - Two nested loops for number of elements and possible sums
    // Space Complexity: O(N * totalSum)
    // - 2D DP table

    private static int minDifferenceTabulation(int[] arr) {
        int n = arr.length;
        int totalSum = Arrays.stream(arr).sum();

        // dp[i][j] will be true if we can form sum 'j' using first 'i' elements
        boolean[][] dp = new boolean[n][totalSum + 1];

        // Sum 0 is always possible with empty subset
        for (int i = 0; i < n; i++)
            dp[i][0] = true;

        // If first element is less than or equal to totalSum, mark it
        if (arr[0] <= totalSum)
            dp[0][arr[0]] = true;

        // Fill the DP table
        for (int i = 1; i < n; i++) {
            for (int target = 1; target <= totalSum; target++) {
                boolean notPick = dp[i - 1][target]; // Exclude current element
                boolean pick = false;

                if (arr[i] <= target)
                    pick = dp[i - 1][target - arr[i]]; // Include current element

                dp[i][target] = pick || notPick;
            }
        }

        // Find the minimum absolute difference from all possible subset sums
        int minDiff = Integer.MAX_VALUE;

        // We only need to check sums up to totalSum / 2
        for (int s1 = 0; s1 <= totalSum / 2; s1++) {
            if (dp[n - 1][s1]) {
                int s2 = totalSum - s1;
                minDiff = Math.min(minDiff, Math.abs(s2 - s1));
            }
        }

        return minDiff;
    }

    // --------------------------------------------------------------------------------------------------
    // 4️⃣ SPACE OPTIMIZED DP
    // --------------------------------------------------------------------------------------------------
    // Time Complexity: O(N * totalSum)
    // - Same as tabulation
    // Space Complexity: O(totalSum)
    // - 1D array reused for optimization

    private static int minDifferenceSpaceOptimized(int[] arr) {
        int n = arr.length;
        int totalSum = Arrays.stream(arr).sum();

        // Previous row (initially for 0th element)
        boolean[] prev = new boolean[totalSum + 1];
        prev[0] = true;

        if (arr[0] <= totalSum)
            prev[arr[0]] = true;

        // Process for each element
        for (int i = 1; i < n; i++) {
            boolean[] curr = new boolean[totalSum + 1];
            curr[0] = true;

            for (int target = 1; target <= totalSum; target++) {
                boolean notPick = prev[target]; // Don't include current element
                boolean pick = false;

                if (arr[i] <= target)
                    pick = prev[target - arr[i]]; // Include current element

                curr[target] = pick || notPick;
            }

            prev = curr; // Move to next row
        }

        // Find the minimum absolute difference from valid subset sums
        int minDiff = Integer.MAX_VALUE;

        for (int s1 = 0; s1 <= totalSum / 2; s1++) {
            if (prev[s1]) {
                int s2 = totalSum - s1;
                minDiff = Math.min(minDiff, Math.abs(s2 - s1));
            }
        }

        return minDiff;
    }

    // --------------------------------------------------------------------------------------------------
    // 🔍 MAIN FUNCTION TO RUN ALL FOUR APPROACHES
    // --------------------------------------------------------------------------------------------------

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 9};
        int n = arr.length;
        int totalSum = Arrays.stream(arr).sum();

        System.out.println("📌 Recursive: " +
                minDifferenceRecursive(n - 1, 0, totalSum, arr));

        int[][] dp = new int[n][totalSum + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("📌 Memoization: " +
                minDifferenceMemo(n - 1, 0, totalSum, arr, dp));

        System.out.println("📌 Tabulation: " +
                minDifferenceTabulation(arr));

        System.out.println("📌 Space Optimized: " +
                minDifferenceSpaceOptimized(arr));
    }
}
