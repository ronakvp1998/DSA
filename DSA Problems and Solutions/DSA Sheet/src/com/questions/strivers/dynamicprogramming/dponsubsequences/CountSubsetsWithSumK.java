package com.questions.strivers.dynamicprogramming.dponsubsequences;
//https://takeuforward.org/data-structure/count-subsets-with-sum-k-dp-17/
//Problem Statement: Count Subsets with Sum K
//        Pre-req: Subset Sum equal to target, Recursion on Subsequences
//        Problem Link: Count Subsets With Sum K
//        We are given an array ‘ARR’ with N positive integers and an integer K.
//        We need to find the number of subsets whose sum is equal to K.
// arr[] = {1,2,2,3} k = 3 => {1,2} or {2,1} or {3} => 3 subsets

// not tested
// correct for array containing 0's
import java.util.Arrays;

public class CountSubsetsWithSumK {

    // ===================== 1. RECURSION =====================
    // Time Complexity: O(2^n), Space Complexity: O(n) for recursion stack
    public static int countSubsetsRecursive(int index, int target, int[] arr) {
        // Base Case: If we are at index 0
        if (index == 0) {
            // If target is 0 and element is also 0, we have 2 options: take or not take
            if (target == 0 && arr[0] == 0) return 2;
            // If target is 0 OR arr[0] == target, one valid subset
            if (target == 0 || target == arr[0]) return 1;
            return 0;
        }

        // Not pick the current element
        int notPick = countSubsetsRecursive(index - 1, target, arr);

        // Pick the element only if it's <= target
        int pick = 0;
        if (arr[index] <= target)
            pick = countSubsetsRecursive(index - 1, target - arr[index], arr);

        return pick + notPick;
    }

    // ===================== 2. MEMOIZATION =====================
    // Time Complexity: O(n * k), Space: O(n * k) + O(n) recursion stack
    public static int countSubsetsMemo(int index, int target, int[] arr, int[][] dp) {
        if (index == 0) {
            if (target == 0 && arr[0] == 0) return 2;
            if (target == 0 || target == arr[0]) return 1;
            return 0;
        }

        // If already computed
        if (dp[index][target] != -1) return dp[index][target];

        int notPick = countSubsetsMemo(index - 1, target, arr, dp);

        int pick = 0;
        if (arr[index] <= target)
            pick = countSubsetsMemo(index - 1, target - arr[index], arr, dp);

        return dp[index][target] = pick + notPick;
    }

    // ===================== 3. TABULATION =====================
    // Time Complexity: O(n * k), Space: O(n * k)
    public static int countSubsetsTabulation(int[] arr, int k) {
        int n = arr.length;
        int[][] dp = new int[n][k + 1];

        // Initialize base cases
        if (arr[0] == 0)
            dp[0][0] = 2; // 2 ways: take or not take
        else
            dp[0][0] = 1; // Only one way: don't take

        // If first element is <= target, mark its value
        if (arr[0] != 0 && arr[0] <= k)
            dp[0][arr[0]] = 1;

        // Fill dp table
        for (int index = 1; index < n; index++) {
            for (int target = 0; target <= k; target++) {
                int notPick = dp[index - 1][target];
                int pick = 0;
                if (arr[index] <= target)
                    pick = dp[index - 1][target - arr[index]];
                dp[index][target] = pick + notPick;
            }
        }

        return dp[n - 1][k];
    }

    // ===================== 4. SPACE OPTIMIZATION =====================
    // Time Complexity: O(n * k), Space: O(k)
    public static int countSubsetsSpaceOptimized(int[] arr, int k) {
        int n = arr.length;
        int[] prev = new int[k + 1];
        int[] curr = new int[k + 1];

        // Base case initialization
        if (arr[0] == 0)
            prev[0] = 2;
        else
            prev[0] = 1;

        if (arr[0] != 0 && arr[0] <= k)
            prev[arr[0]] = 1;

        // Fill table row by row using 1D arrays
        for (int index = 1; index < n; index++) {
            for (int target = 0; target <= k; target++) {
                int notPick = prev[target];
                int pick = 0;
                if (arr[index] <= target)
                    pick = prev[target - arr[index]];
                curr[target] = pick + notPick;
            }
            prev = curr.clone(); // Update for next iteration
        }

        return prev[k];
    }

    // ===================== MAIN METHOD =====================
    public static void main(String[] args) {
        int[] arr = {1, 2, 2, 3};
        int k = 3;
        int n = arr.length;

        System.out.println("Recursive Count: " + countSubsetsRecursive(n - 1, k, arr));

        int[][] dp = new int[n][k + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("Memoization Count: " + countSubsetsMemo(n - 1, k, arr, dp));

        System.out.println("Tabulation Count: " + countSubsetsTabulation(arr, k));

        System.out.println("Space Optimized Count: " + countSubsetsSpaceOptimized(arr, k));
    }
}
