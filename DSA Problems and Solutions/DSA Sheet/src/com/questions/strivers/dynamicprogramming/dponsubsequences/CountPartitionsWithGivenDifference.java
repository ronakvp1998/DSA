package com.questions.strivers.dynamicprogramming.dponsubsequences;

import java.util.Arrays;
// not tested
public class CountPartitionsWithGivenDifference {

    static int MOD = (int)1e9 + 7;

    // ================= 1. Plain Recursion =================
    // Time: O(2^n), Space: O(n) recursion stack
    static int countSubsetsRecursive(int index, int target, int[] arr) {
        if (index == 0) {
            if (target == 0 && arr[0] == 0) return 2; // include or exclude zero
            if (target == 0 || target == arr[0]) return 1;
            return 0;
        }

        int notPick = countSubsetsRecursive(index - 1, target, arr);
        int pick = 0;
        if (arr[index] <= target)
            pick = countSubsetsRecursive(index - 1, target - arr[index], arr);

        return (pick + notPick) % MOD;
    }

    // ================= 2. Memoization =================
    // Time: O(n * target), Space: O(n * target) + O(n) recursion stack
    static int countSubsetsMemo(int index, int target, int[] arr, int[][] dp) {
        if (index == 0) {
            if (target == 0 && arr[0] == 0) return 2;
            if (target == 0 || target == arr[0]) return 1;
            return 0;
        }

        if (dp[index][target] != -1) return dp[index][target];

        int notPick = countSubsetsMemo(index - 1, target, arr, dp);
        int pick = 0;
        if (arr[index] <= target)
            pick = countSubsetsMemo(index - 1, target - arr[index], arr, dp);

        return dp[index][target] = (pick + notPick) % MOD;
    }

    // ================= 3. Tabulation =================
    // Time: O(n * target), Space: O(n * target)
    static int countSubsetsTabulation(int[] arr, int target) {
        int n = arr.length;
        int[][] dp = new int[n][target + 1];

        // Base Case
        for (int t = 0; t <= target; t++) {
            if (arr[0] == 0 && t == 0) dp[0][t] = 2;
            else if (t == 0) dp[0][t] = 1;
            else if (arr[0] == t) dp[0][t] = 1;
        }

        // Build DP table
        for (int i = 1; i < n; i++) {
            for (int t = 0; t <= target; t++) {
                int notPick = dp[i - 1][t];
                int pick = 0;
                if (arr[i] <= t)
                    pick = dp[i - 1][t - arr[i]];
                dp[i][t] = (pick + notPick) % MOD;
            }
        }

        return dp[n - 1][target];
    }

    // ================= 4. Space Optimization =================
    // Time: O(n * target), Space: O(target)
    static int countSubsetsSpaceOptimized(int[] arr, int target) {
        int n = arr.length;
        int[] prev = new int[target + 1];

        // Base Case
        for (int t = 0; t <= target; t++) {
            if (arr[0] == 0 && t == 0) prev[t] = 2;
            else if (t == 0) prev[t] = 1;
            else if (arr[0] == t) prev[t] = 1;
        }

        for (int i = 1; i < n; i++) {
            int[] curr = new int[target + 1];
            for (int t = 0; t <= target; t++) {
                int notPick = prev[t];
                int pick = 0;
                if (arr[i] <= t)
                    pick = prev[t - arr[i]];
                curr[t] = (pick + notPick) % MOD;
            }
            prev = curr;
        }

        return prev[target];
    }

    // ================= Main Method to Calculate Partitions =================
    static int countPartitions(int[] arr, int d) {
        int totalSum = Arrays.stream(arr).sum();

        // Check for invalid cases
        if (totalSum < d || (totalSum - d) % 2 != 0) return 0;

        int target = (totalSum - d) / 2;

        // Choose the approach here 👇
        // return countSubsetsRecursive(arr.length - 1, target, arr);

        int[][] dp = new int[arr.length][target + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        // return countSubsetsMemo(arr.length - 1, target, arr, dp);

        // return countSubsetsTabulation(arr, target);

        return countSubsetsSpaceOptimized(arr, target);
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 3};
        int d = 3;

        int ways = countPartitions(arr, d);
        System.out.println("Number of partitions with given difference: " + ways);
    }
}
