package com.questions.strivers.dynamicprogramming.dponsubsequences;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: COUNT PARTITIONS WITH GIVEN DIFFERENCE (Striver DP-18)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given an array 'arr' of N positive integers and an integer 'D'.
 * Count the number of ways we can partition the array into two subsets, S1 and S2, such that:
 * 1. S1 - S2 = D
 * 2. S1 >= S2
 *
 * EXAMPLE:
 * Input: arr = [1, 2, 3, 4], D = 2
 * Output: 2
 * Explanation:
 * - Partition 1: {1, 3} and {2, 4} -> Sums are 4 and 6. Diff = 2.
 * - Partition 2: {1, 2, 3} and {4} -> Sums are 6 and 4. Diff = 2.
 *
 * MATHEMATICAL DERIVATION:
 * We need to find two subsets S1 and S2 such that:
 * 1. Sum(S1) - Sum(S2) = D
 * 2. Sum(S1) + Sum(S2) = TotalSum (Since they partition the array)
 *
 * Adding the two equations:
 * 2 * Sum(S1) = TotalSum + D
 * Sum(S1) = (TotalSum + D) / 2
 *
 * Alternatively, subtracting the equations:
 * 2 * Sum(S2) = TotalSum - D
 * Sum(S2) = (TotalSum - D) / 2
 *
 * CONCLUSION:
 * The problem reduces to: "Count the number of subsets with sum equal to [(TotalSum - D) / 2] ".
 *
 *
 * EDGE CASES:
 * 1. If (TotalSum - D) < 0, it's impossible (Sum cannot be negative).
 * 2. If (TotalSum - D) is odd, it's impossible (Sum of integers must be integer).
 * 3. Array containing Zeros: {0, 0, 1}, Target=1 -> Must handle zeros as distinct elements.
 * ==================================================================================================
 */
public class CountPartitionsWithGivenDifference {

    static int MOD = (int) 1e9 + 7;

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 3};
        int d = 3;

        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Difference (D): " + d);

        // Calculate result
        int ways = countPartitions(arr, d);
        System.out.println("Number of partitions with given difference: " + ways);
    }

    // ==================================================================================
    // 🚀 MAIN DRIVER LOGIC
    // ==================================================================================
    static int countPartitions(int[] arr, int d) {
        int totalSum = Arrays.stream(arr).sum();

        // CHECK 1: If TotalSum is smaller than D, we can't have a positive S2.
        // CHECK 2: If (TotalSum - D) is odd, we can't divide it into two integer sums.
        if (totalSum < d || (totalSum - d) % 2 != 0) return 0;

        // We need to find the count of subsets with this specific target sum.
        int target = (totalSum - d) / 2;

        // Choose approach (Uncomment to test others)

        // 1. Recursion
        // return countSubsetsRecursive(arr.length - 1, target, arr);

        // 2. Memoization
        // int[][] dp = new int[arr.length][target + 1];
        // for (int[] row : dp) Arrays.fill(row, -1);
        // return countSubsetsMemo(arr.length - 1, target, arr, dp);

        // 3. Tabulation
        // return countSubsetsTabulation(arr, target);

        // 4. Space Optimization
        return countSubsetsSpaceOptimized(arr, target);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION
     * ----------------------------------------------------------------------
     * LOGIC:
     * Standard "Pick/Not Pick" recursion.
     * Special handling for 0s in base case.
     *
     * COMPLEXITY:
     * - Time: O(2^N)
     * - Space: O(N) Stack
     */
    static int countSubsetsRecursive(int index, int target, int[] arr) {
        // Base Case: Index 0
        if (index == 0) {
            // Case A: Target is 0 and Element is 0 -> 2 ways (Pick 0, Not Pick 0)
            if (target == 0 && arr[0] == 0) return 2;
            // Case B: Target is 0 OR Target == Element -> 1 way
            if (target == 0 || target == arr[0]) return 1;
            return 0;
        }

        // Choice 1: Not Pick
        int notPick = countSubsetsRecursive(index - 1, target, arr);

        // Choice 2: Pick (if valid)
        int pick = 0;
        if (arr[index] <= target)
            pick = countSubsetsRecursive(index - 1, target - arr[index], arr);

        return (pick + notPick) % MOD;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Cache results in DP table.
     *
     *
     * COMPLEXITY:
     * - Time: O(N * Target)
     * - Space: O(N * Target)
     */
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

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * dp[i][t] = Number of ways to get sum 't' using first 'i' elements.
     * * Base Case Initialization handles the '0' logic carefully.
     *
     * COMPLEXITY:
     * - Time: O(N * Target)
     * - Space: O(N * Target)
     */
    static int countSubsetsTabulation(int[] arr, int target) {
        int n = arr.length;
        int[][] dp = new int[n][target + 1];

        // Base Case: Initialize for index 0
        for (int t = 0; t <= target; t++) {
            if (arr[0] == 0 && t == 0) dp[0][t] = 2; // Two ways for 0 (take, not take)
            else if (t == 0) dp[0][t] = 1;           // One way for 0 (not take)
            else if (arr[0] == t) dp[0][t] = 1;      // One way for target == arr[0] (take)
        }

        // Fill Table
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

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZATION (BEST)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Use 1D array 'prev' to store the previous row's results.
     *
     * COMPLEXITY:
     * - Time: O(N * Target)
     * - Space: O(Target)
     */
    static int countSubsetsSpaceOptimized(int[] arr, int target) {
        int n = arr.length;
        int[] prev = new int[target + 1];

        // Base Case
        for (int t = 0; t <= target; t++) {
            if (arr[0] == 0 && t == 0) prev[t] = 2;
            else if (t == 0) prev[t] = 1;
            else if (arr[0] == t) prev[t] = 1;
        }

        // Iterate
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
}