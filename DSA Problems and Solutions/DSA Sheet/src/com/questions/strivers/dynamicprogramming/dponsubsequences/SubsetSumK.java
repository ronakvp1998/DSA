package com.questions.strivers.dynamicprogramming.dponsubsequences;
//https://takeuforward.org/data-structure/subset-sum-equal-to-target-dp-14/

//Subset sum equal to target.
//In this article, we will be going to understand the pattern of dynamic programming on subsequences of an array.
//We will be using the problem "Subset Sum Equal to K".
//First, we need to understand what a subsequence/subset is.
//A subset/subsequence is a contiguous or non-contiguous part of an array,
//where elements appear in the same order as the original array.
//For example, for the array: [2,3,1] , the subsequences will be [{2},{3},{1},{2,3},{2,1},{3,1},{2,3,1}}
//but {3,2} is not a subsequence because its elements are not in the same order as the original array.
//Problem Link: Subset Sum Equal to K
//We are given an array ‘ARR’ with N positive integers.
//We need to find if there is a subset in “ARR” with a sum equal to K. If there is, return true else return false.
// Output: arr = [1,2,3,4 ] target = 4  answer: true (1,3) or (4)

import java.util.Arrays;

public class SubsetSumK {

    // 1️⃣ Plain Recursion
    // Time: O(2^N), Space: O(N)
    public static boolean subsetSumRecursive(int index, int target, int[] arr) {
        // Base case: If target is 0, subset exists (empty subset)
        if (target == 0) return true;

        // Base case: Only one element to consider
        if (index == 0) return arr[0] == target;

        // Choice 1: Do not pick current element
        boolean notPick = subsetSumRecursive(index - 1, target, arr);

        // Choice 2: Pick current element (only if it's <= target)
        boolean pick = false;
        if (arr[index] <= target)
            pick = subsetSumRecursive(index - 1, target - arr[index], arr);

        // Return true if either pick or notPick is true
        return pick || notPick;
    }

    // 2️⃣ Memoization (Top-down DP)
    // Time: O(N*K), Space: O(N*K + N) (dp table + recursion stack)
    public static boolean subsetSumMemo(int index, int target, int[] arr, int[][] dp) {
        // Base case: Target 0 is always achievable
        if (target == 0) return true;
        if (index == 0) return arr[0] == target;

        // If value already computed, return it
        if (dp[index][target] != -1)
            return dp[index][target] == 1;

        // Don't pick current element
        boolean notPick = subsetSumMemo(index - 1, target, arr, dp);

        // Pick current element
        boolean pick = false;
        if (arr[index] <= target)
            pick = subsetSumMemo(index - 1, target - arr[index], arr, dp);

        // Store result: 1 for true, 0 for false
        dp[index][target] = (pick || notPick) ? 1 : 0;

        return pick || notPick;
    }

    // 3️⃣ Tabulation (Bottom-up DP)
    // Time: O(N*K), Space: O(N*K)
    public static boolean subsetSumTabulation(int n, int k, int[] arr) {
        boolean[][] dp = new boolean[n][k + 1];

        // Initialize all dp[i][0] = true (target = 0)
        for (int i = 0; i < n; i++) dp[i][0] = true;

        // First row: can we make target = arr[0]?
        if (arr[0] <= k) dp[0][arr[0]] = true;

        // Fill DP table
        for (int i = 1; i < n; i++) {
            for (int target = 1; target <= k; target++) {
                boolean notPick = dp[i - 1][target];
                boolean pick = false;
                if (arr[i] <= target)
                    pick = dp[i - 1][target - arr[i]];
                dp[i][target] = pick || notPick;
            }
        }

        return dp[n - 1][k];
    }

    // 4️⃣ Space Optimized DP
    // Time: O(N*K), Space: O(K)
    public static boolean subsetSumSpaceOptimized(int n, int k, int[] arr) {
        boolean[] prev = new boolean[k + 1];

        prev[0] = true; // target = 0 is always achievable
        if (arr[0] <= k) prev[arr[0]] = true;

        for (int i = 1; i < n; i++) {
            boolean[] curr = new boolean[k + 1];
            curr[0] = true;

            for (int target = 1; target <= k; target++) {
                boolean notPick = prev[target];
                boolean pick = false;
                if (arr[i] <= target)
                    pick = prev[target - arr[i]];
                curr[target] = pick || notPick;
            }
            prev = curr; // Move to next row
        }

        return prev[k];
    }

    // 🔍 Test the functions
    public static void main(String[] args) {
        int[] arr = {2, 3, 1, 5};
        int k = 6;
        int n = arr.length;

        System.out.println("1. Recursion: " + subsetSumRecursive(n - 1, k, arr));

        int[][] dp = new int[n][k + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("2. Memoization: " + subsetSumMemo(n - 1, k, arr, dp));

        System.out.println("3. Tabulation: " + subsetSumTabulation(n, k, arr));

        System.out.println("4. Space Optimized: " + subsetSumSpaceOptimized(n, k, arr));
    }
}
