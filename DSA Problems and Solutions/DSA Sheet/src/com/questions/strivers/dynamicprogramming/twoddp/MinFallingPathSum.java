package com.questions.strivers.dynamicprogramming.twoddp;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: MINIMUM FALLING PATH SUM (LeetCode 931)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given an n x n array of integers 'matrix', return the minimum sum of any falling path through matrix.
 *
 * A falling path starts at any element in the first row and chooses the element in the next row
 * that is either directly below or diagonally left/right.
 *
 * Specifically, the next element from position (row, col) will be:
 * - (row + 1, col - 1)
 * - (row + 1, col)
 * - (row + 1, col + 1)
 *
 *
 *
 * EXAMPLE:
 * Input: matrix = [[2,1,3],
 *                  [6,5,4],
 *                  [7,8,9]]
 * Output: 13
 * Explanation: There are two falling paths with a minimum sum as shown.
 * 1. (1 -> 5 -> 7) = 13
 * 2. (1 -> 4 -> 8) = 13
 *
 * APPROACH STRATEGY:
 * Since we can end at ANY cell in the last row, the answer is:
 * Min( f(n-1, 0), f(n-1, 1), ..., f(n-1, n-1) )
 * We will define our function f(i, j) as:
 * "The minimum path sum to REACH cell (i, j) from the top row."
 * ==================================================================================================
 */
public class MinFallingPathSum {

    public static void main(String[] args) {
        int[][] matrix = {
                {2, 1, 3},
                {6, 5, 4},
                {7, 8, 9}
        };

        int n = matrix.length;
        int m = matrix[0].length;

        System.out.println("Matrix Size: " + n + "x" + m);
        System.out.println("--------------------------------------------------");

        // 1. Recursive Approach
        // We must check every possible ending point in the last row
        int minRecursive = Integer.MAX_VALUE;
        for (int j = 0; j < m; j++) {
            minRecursive = Math.min(minRecursive, solveRecursive(n - 1, j, matrix));
        }
        System.out.println("1. Recursion       : " + minRecursive);

        // 2. Memoization Approach
        int[][] dp = new int[n][m];
        for (int[] row : dp) Arrays.fill(row, -100000); // Using unlikely value for initialization
        // Note: For production, use Integer[][] and null checks.
        // Here we use a separate wrapper method.
        System.out.println("2. Memoization     : " + solveMemoizationWrapper(matrix));


        // 3. Tabulation Approach
        System.out.println("3. Tabulation      : " + solveTabulation(matrix));

        // 4. Space Optimized Approach
        System.out.println("4. Space Optimized : " + solveSpaceOptimized(matrix));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * We work backwards from the last row (row n-1) up to row 0.
     * To reach cell (i, j), we could have come from:
     * 1. Top-Left: (i-1, j-1)
     * 2. Top:      (i-1, j)
     * 3. Top-Right:(i-1, j+1)
     *
     * We take the minimum of these three parents and add matrix[i][j].
     *
     * COMPLEXITY:
     * - Time: O(3^N) -> Exponential (3 branches per step).
     * - Space: O(N) -> Recursion stack depth.
     */
    private static int solveRecursive(int i, int j, int[][] matrix) {
        // Base Case 1: Out of bounds
        // If 'j' is invalid, return Infinity so this path is ignored by Math.min()
        if (j < 0 || j >= matrix[0].length) {
            return (int) 1e9; // 1 Billion (Safe infinity)
        }

        // Base Case 2: Reached the first row
        // The cost to reach a cell in the first row is just its value.
        if (i == 0) {
            return matrix[0][j];
        }

        // Recursive Calls
        int up = solveRecursive(i - 1, j, matrix);
        int upLeft = solveRecursive(i - 1, j - 1, matrix);
        int upRight = solveRecursive(i - 1, j + 1, matrix);

        return matrix[i][j] + Math.min(up, Math.min(upLeft, upRight));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Same as recursion, but we cache results in `dp[i][j]`.
     * Wrapper method iterates through the last row to find the overall minimum.
     *
     * COMPLEXITY:
     * - Time: O(N * M)
     * - Space: O(N * M) + O(N) Stack
     */
    private static int solveMemoizationWrapper(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        // Use Integer to handle potential negative sums safely using null check
        Integer[][] dp = new Integer[n][m];

        int minPath = Integer.MAX_VALUE;

        // Try ending at every column in the last row
        for (int j = 0; j < m; j++) {
            minPath = Math.min(minPath, memoization(n - 1, j, matrix, dp));
        }
        return minPath;
    }

    private static int memoization(int i, int j, int[][] matrix, Integer[][] dp) {
        if (j < 0 || j >= matrix[0].length) return (int) 1e9;
        if (i == 0) return matrix[0][j];

        if (dp[i][j] != null) return dp[i][j];

        int up = memoization(i - 1, j, matrix, dp);
        int upLeft = memoization(i - 1, j - 1, matrix, dp);
        int upRight = memoization(i - 1, j + 1, matrix, dp);

        return dp[i][j] = matrix[i][j] + Math.min(up, Math.min(upLeft, upRight));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Start from Row 0. Initialize DP table with Row 0 values.
     * Calculate Row 1 based on Row 0, then Row 2 based on Row 1, etc.
     *
     * This is slightly cleaner than recursion because we don't need a separate loop
     * for the "starting points". We process the whole grid.
     *
     * COMPLEXITY:
     * - Time: O(N * M)
     * - Space: O(N * M)
     */
    private static int solveTabulation(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] dp = new int[n][m];

        // 1. Initialize Base Case (First Row)
        for (int j = 0; j < m; j++) {
            dp[0][j] = matrix[0][j];
        }

        // 2. Iterate Rows 1 to N-1
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < m; j++) {

                int up = dp[i - 1][j];

                // Handle left boundary
                int upLeft = (j > 0) ? dp[i - 1][j - 1] : (int) 1e9;

                // Handle right boundary
                int upRight = (j < m - 1) ? dp[i - 1][j + 1] : (int) 1e9;

                dp[i][j] = matrix[i][j] + Math.min(up, Math.min(upLeft, upRight));
            }
        }

        // 3. The answer is the minimum value in the LAST row of the DP table
        int minPath = dp[n - 1][0];
        for (int j = 1; j < m; j++) {
            minPath = Math.min(minPath, dp[n - 1][j]);
        }
        return minPath;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED DP (BEST SOLUTION)
     * ----------------------------------------------------------------------
     * LOGIC:
     * To compute `curr` row, we only need `prev` row.
     * We can discard older rows.
     *
     * COMPLEXITY:
     * - Time: O(N * M)
     * - Space: O(M) -> Only one row stored.
     */
    private static int solveSpaceOptimized(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        // Initialize 'prev' with the first row of matrix
        int[] prev = new int[m];
        for (int j = 0; j < m; j++) {
            prev[j] = matrix[0][j];
        }

        // Iterate through remaining rows
        for (int i = 1; i < n; i++) {
            int[] curr = new int[m];

            for (int j = 0; j < m; j++) {
                int up = prev[j];
                int upLeft = (j > 0) ? prev[j - 1] : (int) 1e9;
                int upRight = (j < m - 1) ? prev[j + 1] : (int) 1e9;

                curr[j] = matrix[i][j] + Math.min(up, Math.min(upLeft, upRight));
            }
            // Update prev to be the current row
            prev = curr;
        }

        // Find min in the final 'prev' array (which holds the last row's results)
        int minPath = prev[0];
        for (int j = 1; j < m; j++) {
            minPath = Math.min(minPath, prev[j]);
        }
        return minPath;
    }
}