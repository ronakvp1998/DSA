package com.questions.strivers.dynamicprogramming.dponsubsequences;
//https://takeuforward.org/data-structure/partition-equal-subset-sum-dp-15/
// Partition Equal Subset Sum
// We are given an array ‘ARR’ with N positive integers.
// We need to find if we can partition the array into two subsets such that
// the sum of elements of each subset is equal to the other.
// If we can partition, return true else return false.
// arr[] = {2,3,3,3,4,5} o/p {2,3,5} & {3,3,4} => true
import java.util.Arrays;

public class PartitionEqualSubsetSum {

    // ------------------------------------------------------------------------------------------
    // 1️⃣ RECURSION
    // ------------------------------------------------------------------------------------------
    // Time Complexity: O(2^N) — Each element has 2 choices (pick/not pick)
    // Space Complexity: O(N) — Maximum depth of recursion stack
    private static boolean canPartitionRecursive(int index, int target, int[] arr) {
        // Base Case 1: if target is 0, we found a valid subset
        if (target == 0) return true;

        // Base Case 2: only one element left, check if it equals target
        if (index == 0) return arr[0] == target;

        // Choice 1: Do NOT include current element in subset
        boolean notPick = canPartitionRecursive(index - 1, target, arr);

        // Choice 2: Include current element (only if it's <= remaining target)
        boolean pick = false;
        if (arr[index] <= target)
            pick = canPartitionRecursive(index - 1, target - arr[index], arr);

        // Return true if any choice leads to target
        return pick || notPick;
    }

    // ------------------------------------------------------------------------------------------
    // 2️⃣ MEMOIZATION (Top-down DP)
    // ------------------------------------------------------------------------------------------
    // Time Complexity: O(N * Target) — Each state (index, target) is visited once
    // Space Complexity: O(N * Target) for DP array + O(N) recursion stack
    private static boolean canPartitionMemo(int index, int target, int[] arr, int[][] dp) {
        // Base Case 1: Subset with sum 0 always possible (empty set)
        if (target == 0) return true;

        // Base Case 2: Check if only 1 element matches target
        if (index == 0) return arr[0] == target;

        // Return cached value if already computed
        if (dp[index][target] != -1)
            return dp[index][target] == 1;

        // Option 1: Exclude current element
        boolean notPick = canPartitionMemo(index - 1, target, arr, dp);

        // Option 2: Include current element (only if valid)
        boolean pick = false;
        if (arr[index] <= target)
            pick = canPartitionMemo(index - 1, target - arr[index], arr, dp);

        // Store result in memo table (1 for true, 0 for false)
        dp[index][target] = (pick || notPick) ? 1 : 0;

        return pick || notPick;
    }

    // ------------------------------------------------------------------------------------------
    // 3️⃣ TABULATION (Bottom-up DP)
    // ------------------------------------------------------------------------------------------
    // Time Complexity: O(N * Target) — Loop through elements and all targets
    // Space Complexity: O(N * Target) — 2D DP table
    private static boolean canPartitionTabulation(int[] arr) {
        int n = arr.length;
        int totalSum = 0;

        // Step 1: Calculate total sum of array
        for (int num : arr) totalSum += num;

        // Step 2: If total sum is odd, can't partition into 2 equal subsets
        if (totalSum % 2 != 0) return false;

        int target = totalSum / 2;

        // Step 3: Create DP table [n][target+1]
        boolean[][] dp = new boolean[n][target + 1];

        // Step 4: Every element can make sum 0 (by empty subset)
        for (int i = 0; i < n; i++) dp[i][0] = true;

        // Step 5: First element can make its own value
        if (arr[0] <= target) dp[0][arr[0]] = true;

        // Step 6: Fill table for remaining elements
        for (int i = 1; i < n; i++) {
            for (int t = 1; t <= target; t++) {
                // Do not pick current element
                boolean notPick = dp[i - 1][t];

                // Pick current element if valid
                boolean pick = false;
                if (arr[i] <= t)
                    pick = dp[i - 1][t - arr[i]];

                // Store result
                dp[i][t] = pick || notPick;
            }
        }

        // Final answer: Can we achieve target sum using all elements?
        return dp[n - 1][target];
    }

    // ------------------------------------------------------------------------------------------
    // 4️⃣ SPACE OPTIMIZED DP
    // ------------------------------------------------------------------------------------------
    // Time Complexity: O(N * Target)
    // Space Complexity: O(Target) — We only need 2 rows at a time
    private static boolean canPartitionSpaceOptimized(int[] arr) {
        int n = arr.length;
        int totalSum = 0;

        // Step 1: Sum all elements
        for (int num : arr) totalSum += num;

        // Step 2: If total is odd, partition not possible
        if (totalSum % 2 != 0) return false;

        int target = totalSum / 2;

        // Step 3: Initialize DP array for 0th element
        boolean[] prev = new boolean[target + 1];
        prev[0] = true; // Empty subset always possible

        if (arr[0] <= target)
            prev[arr[0]] = true;

        // Step 4: Loop through remaining elements
        for (int i = 1; i < n; i++) {
            boolean[] curr = new boolean[target + 1];
            curr[0] = true;

            for (int t = 1; t <= target; t++) {
                boolean notPick = prev[t];
                boolean pick = false;
                if (arr[i] <= t)
                    pick = prev[t - arr[i]];

                curr[t] = pick || notPick;
            }

            // Move current row to previous for next iteration
            prev = curr;
        }

        // Final answer
        return prev[target];
    }

    // ------------------------------------------------------------------------------------------
    // 🚀 MAIN METHOD TO TEST ALL APPROACHES
    // ------------------------------------------------------------------------------------------
    public static void main(String[] args) {
        int[] arr = {2,3,3,3,4,5}; // Example input

        // Step 1: Calculate total sum
        int totalSum = Arrays.stream(arr).sum();

        // Step 2: If total is odd, directly return false
        if (totalSum % 2 != 0) {
            System.out.println("Array cannot be partitioned (sum is odd)");
            return;
        }

        int target = totalSum / 2;
        int n = arr.length;

        // 1. Recursive
        System.out.println("1️⃣ Recursion: " +
                canPartitionRecursive(n - 1, target, arr));

        // 2. Memoization
        int[][] dp = new int[n][target + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("2️⃣ Memoization: " +
                canPartitionMemo(n - 1, target, arr, dp));

        // 3. Tabulation
        System.out.println("3️⃣ Tabulation: " +
                canPartitionTabulation(arr));

        // 4. Space Optimized
        System.out.println("4️⃣ Space Optimized: " +
                canPartitionSpaceOptimized(arr));
    }
}
