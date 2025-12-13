package com.questions.strivers.dynamicprogramming.dponsubsequences;
//https://takeuforward.org/data-structure/target-sum-dp-21/

//Problem Description:
//We are given an array ‘ARR’ of size ‘N’ and a number ‘Target’.
//Our task is to build an expression from the given array where we can place a ‘+’ or ‘-’ sign in front of an integer.
//We want to place a sign in front of every integer of the array and get our required target.
//We need to count the number of ways in which we can achieve our required target.
// arr[] = {1,2,3,1} target = 3 => o/p => 2 ways (+1, -2, +3, +1) or (-1, +2, +3, -1)
// code is submitted

// this code is similar to partition code, completely same problem
import java.util.Arrays;

public class TargetSum {

    // 1. ============================ RECURSION ============================
    private static int countWaysRecursive(int index, int target, int[] arr) {
        // Base case: if we're at the first element
        if (index == 0) {
            // If adding or subtracting the current number gives the target, return 1
            if (target == arr[0] || target == -arr[0]) {
                // Special case: if arr[0] == 0, (+0 and -0) are considered two different ways
                return arr[0] == 0 ? 2 : 1;
            }
            return 0; // No way possible
        }

        // Take the number with '+' sign
        int plus = countWaysRecursive(index - 1, target - arr[index], arr);

        // Take the number with '-' sign
        int minus = countWaysRecursive(index - 1, target + arr[index], arr);

        return plus + minus;
    }

    // Time: O(2^n) - because each element has two choices: + or -
    // Space: O(n) - stack space due to recursion


    // 2. ============================ MEMOIZATION ============================
    private static int countWaysMemo(int index, int target, int[] arr, int[][] dp, int offset) {
        // offset is used to avoid negative indexing by shifting range
        if (index == 0) {
            if (target == arr[0] || target == -arr[0]) {
                return arr[0] == 0 ? 2 : 1;
            }
            return 0;
        }

        if (dp[index][target + offset] != -1) {
            return dp[index][target + offset];
        }

        int plus = countWaysMemo(index - 1, target - arr[index], arr, dp, offset);
        int minus = countWaysMemo(index - 1, target + arr[index], arr, dp, offset);

        return dp[index][target + offset] = plus + minus;
    }

    // Time: O(n * sum * 2) ≈ O(n * sum)
    // Space: O(n * sum) + O(n) for recursion stack


    // 3. ============================ TABULATION ============================
    private static int countWaysTabulation(int[] arr, int target) {
        int n = arr.length;

        // Find the range of possible sums to handle negative targets
        int sum = Arrays.stream(arr).sum();
        int offset = sum;

        int[][] dp = new int[n][2 * sum + 1]; // range [-sum ... +sum]

        // Base case initialization
        if (arr[0] == 0) {
            dp[0][offset + 0] = 2; // +0 and -0
        } else {
            dp[0][offset + arr[0]] = 1;
            dp[0][offset - arr[0]] = 1;
        }

        // Build the DP table
        for (int i = 1; i < n; i++) {
            for (int t = -sum; t <= sum; t++) {
                int plus = (t - arr[i] >= -sum && t - arr[i] <= sum) ? dp[i - 1][offset + t - arr[i]] : 0;
                int minus = (t + arr[i] >= -sum && t + arr[i] <= sum) ? dp[i - 1][offset + t + arr[i]] : 0;
                dp[i][offset + t] = plus + minus;
            }
        }

        // Return the result
        return (target < -sum || target > sum) ? 0 : dp[n - 1][offset + target];
    }

    // Time: O(n * sum)
    // Space: O(n * sum)


    // 4. ============================ SPACE OPTIMIZATION ============================
    private static int countWaysSpaceOptimized(int[] arr, int target) {
        int n = arr.length;
        int sum = Arrays.stream(arr).sum();
        int offset = sum;

        int[] prev = new int[2 * sum + 1];
        int[] curr = new int[2 * sum + 1];

        // Base case
        if (arr[0] == 0) {
            prev[offset + 0] = 2;
        } else {
            prev[offset + arr[0]] = 1;
            prev[offset - arr[0]] = 1;
        }

        for (int i = 1; i < n; i++) {
            for (int t = -sum; t <= sum; t++) {
                int plus = (t - arr[i] >= -sum && t - arr[i] <= sum) ? prev[offset + t - arr[i]] : 0;
                int minus = (t + arr[i] >= -sum && t + arr[i] <= sum) ? prev[offset + t + arr[i]] : 0;
                curr[offset + t] = plus + minus;
            }
            prev = curr.clone(); // Update prev row
        }

        return (target < -sum || target > sum) ? 0 : prev[offset + target];
    }

    // Time: O(n * sum)
    // Space: O(2 * sum) ≈ O(sum)


    // ============================ MAIN DRIVER ============================
    public static void main(String[] args) {
        int[] arr = {1, 1, 2, 3};
        int target = 1;
        int n = arr.length;

        // Recursion
        System.out.println("Recursion: " + countWaysRecursive(n - 1, target, arr));

        // Memoization
        int sum = Arrays.stream(arr).sum();
        int offset = sum;
        int[][] dp = new int[n][2 * sum + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("Memoization: " + countWaysMemo(n - 1, target, arr, dp, offset));

        // Tabulation
        System.out.println("Tabulation: " + countWaysTabulation(arr, target));

        // Space Optimized
        System.out.println("Space Optimized: " + countWaysSpaceOptimized(arr, target));
    }
}
