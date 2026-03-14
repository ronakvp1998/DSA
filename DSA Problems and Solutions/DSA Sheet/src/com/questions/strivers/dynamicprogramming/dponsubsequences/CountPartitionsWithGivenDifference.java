package com.questions.strivers.dynamicprogramming.dponsubsequences;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: COUNT PARTITIONS WITH GIVEN DIFFERENCE (Striver DP-18 / LeetCode Variation)
 * ==================================================================================================
 * * 1. PROBLEM STATEMENT:
 * Given an array 'arr' of N integers and an integer 'D'.
 * Partition the array into two subsets, S1 and S2, such that:
 * - Sum(S1) - Sum(S2) = D
 * - Sum(S1) >= Sum(S2)
 * Return the total number of ways to achieve this partition. Since the answer can be large,
 * return it modulo 10^9 + 7.
 *
 * 2. CONSTRAINTS:
 * - 1 <= N <= 50
 * - 0 <= D <= 2500
 * - 0 <= arr[i] <= 50 (Array can contain Zeros)
 *
 * 3. THE MATHEMATICAL APPROACH:
 * We know:
 * - S1 + S2 = TotalSum
 * - S1 - S2 = D
 * ------------------
 * Adding them: 2 * S1 = TotalSum + D  =>  S1 = (TotalSum + D) / 2
 * Subtracting: 2 * S2 = TotalSum - D  =>  S2 = (TotalSum - D) / 2
 *
 * Target Logic:
 * We just need to find the number of subsets that sum up to (TotalSum - D) / 2.
 * If (TotalSum - D) is negative or odd, return 0 (as subset sums must be integers).
 *
 * 4. HANDLING ZEROS (Crucial Edge Case):
 * If an element is 0, we have two choices: "Pick it" or "Not Pick it". Both choices
 * result in the same subset sum. This is why the base case `if(target==0 && arr[0]==0) return 2` exists.
 * ==================================================================================================
 */

public class CountPartitionsWithGivenDifference {

    static int MOD = (int) 1e9 + 7;

    public static void main(String[] args) {
        // Example: arr = [1, 2, 2, 3], D = 0
        // TotalSum = 8. Target S2 = (8 - 0) / 2 = 4.
        int[] arr = {1, 2, 2, 3};
        int d = 0;

        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Target Difference (D): " + d);

        int ways = countPartitions(arr, d);
        System.out.println("Number of ways: " + ways);
    }

    /**
     * 🚀 MAIN DRIVER LOGIC
     */
    static int countPartitions(int[] arr, int d) {
        int totalSum = 0;
        for (int x : arr) totalSum += x;

        // Mathematical impossibility checks
        if (totalSum - d < 0 || (totalSum - d) % 2 != 0) return 0;

        int target = (totalSum - d) / 2;

        // return countSubsetsRecursive(arr.length - 1, target, arr); // Approach 1

        int[][] dp = new int[arr.length][target + 1];
        for (int[] row : dp) Arrays.fill(row, -1);
        return countSubsetsMemo(arr.length - 1, target, arr, dp); // Approach 2
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1 & 2: RECURSION + MEMOIZATION
     * ----------------------------------------------------------------------
     * LOGIC:
     * At every index, we decide whether to include arr[index] in our subset or not.
     * * RECURSION TREE STRUCTURE (Example: arr=[1, 2], target=1)
     * * f(1, 1)  <-- index 1 (value 2), target 1
     * /    \
     * NOT PICK: f(0, 1)     PICK: f(0, 1-2) <-- Invalid (target < 0)
     * /    \
     * NOT PICK: f(-1, 1) [Base: 0]   PICK: f(-1, 1-1) [Base: 1]
     * * FINAL DP TABLE (For arr=[1, 2, 2, 3], Target=4):
     * Index \ Target | 0 | 1 | 2 | 3 | 4 |
     * ------------------------------------
     * 0 (val 1)      | 1 | 1 | 0 | 0 | 0 |
     * 1 (val 2)      | 1 | 1 | 1 | 1 | 0 |
     * 2 (val 2)      | 1 | 1 | 2 | 2 | 1 |
     * 3 (val 3)      | 1 | 1 | 2 | 3 | 3 | <-- Final Answer: dp[3][4] = 3
     */
    static int countSubsetsMemo(int index, int target, int[] arr, int[][] dp) {
        // Base Case: If we reach the first element
        if (index == 0) {
            // If target is 0 and element is 0, we can pick or not pick it (2 ways)
            if (target == 0 && arr[0] == 0) return 2;
            // If target is 0 (and arr[0]!=0) OR we pick the element that equals target
            if (target == 0 || target == arr[0]) return 1;
            return 0;
        }

        if (dp[index][target] != -1) return dp[index][target];

        // Logic: Try not picking the current element
        int notPick = countSubsetsMemo(index - 1, target, arr, dp);

        // Logic: Try picking the current element (only if it doesn't exceed target)
        int pick = 0;
        if (arr[index] <= target) {
            pick = countSubsetsMemo(index - 1, target - arr[index], arr, dp);
        }

        return dp[index][target] = (notPick + pick) % MOD;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * dp[i][j] represents the number of ways to form sum 'j' using elements up to index 'i'.
     * * Time Complexity: O(N * Target)
     * Space Complexity: O(N * Target)
     */
    static int countSubsetsTabulation(int[] arr, int target) {
        int n = arr.length;
        int[][] dp = new int[n][target + 1];

        // Initialize Base Case (Row 0)
        if (arr[0] == 0) dp[0][0] = 2; // Case: {0}, target 0
        else dp[0][0] = 1;            // Case: {non-zero}, target 0

        if (arr[0] != 0 && arr[0] <= target) dp[0][arr[0]] = 1;

        for (int i = 1; i < n; i++) {
            for (int t = 0; t <= target; t++) {
                int notPick = dp[i - 1][t];
                int pick = 0;
                if (arr[i] <= t) pick = dp[i - 1][t - arr[i]];

                dp[i][t] = (notPick + pick) % MOD;
            }
        }
        return dp[n - 1][target];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZATION
     * ----------------------------------------------------------------------
     * LOGIC:
     * Since we only ever look at the previous row (`dp[i-1]`), we only need
     * two 1D arrays: `prev` and `curr`.
     * * Time Complexity: O(N * Target)
     * Space Complexity: O(Target)
     */
    static int countSubsetsSpaceOptimized(int[] arr, int target) {
        int n = arr.length;
        int[] prev = new int[target + 1];

        // Base Case for index 0
        if (arr[0] == 0) prev[0] = 2;
        else prev[0] = 1;

        if (arr[0] != 0 && arr[0] <= target) prev[arr[0]] = 1;

        for (int i = 1; i < n; i++) {
            int[] curr = new int[target + 1];
            for (int t = 0; t <= target; t++) {
                int notPick = prev[t];
                int pick = 0;
                if (arr[i] <= t) pick = prev[t - arr[i]];

                curr[t] = (notPick + pick) % MOD;
            }
            prev = curr;
        }
        return prev[target];
    }
}