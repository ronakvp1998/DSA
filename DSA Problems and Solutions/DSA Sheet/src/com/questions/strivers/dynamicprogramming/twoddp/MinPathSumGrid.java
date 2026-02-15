package com.questions.strivers.dynamicprogramming.twoddp;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: MINIMUM PATH SUM (LeetCode 64)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given a m x n grid filled with non-negative numbers, find a path from top-left (0,0)
 * to bottom-right (m-1, n-1) which minimizes the sum of all numbers along its path.
 * * Constraint: You can only move either DOWN or RIGHT at any point in time.
 *
 * EXAMPLE:
 * Input: grid = [[1,3,1],
 * [1,5,1],
 * [4,2,1]]
 * Output: 7
 * Explanation: Path is 1 → 3 → 1 → 1 → 1. Sum = 7.
 * ==================================================================================================
 */
public class MinPathSumGrid {

    public static void main(String[] args) {
        int[][] grid = {
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        };

        int m = grid.length;
        int n = grid[0].length;

        System.out.println("Grid Size: " + m + "x" + n);
        System.out.println("--------------------------------------------------");

        // 1. Recursive Approach
        System.out.println("1. Recursion       : " + minPathSumRecursive(m - 1, n - 1, grid));

        // 2. Memoization Approach
        int[][] dp = new int[m][n];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("2. Memoization     : " + minPathSumMemoization(m - 1, n - 1, grid, dp));

        // 3. Tabulation Approach
        System.out.println("3. Tabulation      : " + minPathSumTabulation(grid));

        // 4. Space Optimized Approach
        System.out.println("4. Space Optimized : " + minPathSumSpaceOptimized(grid));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Start from the bottom-right (m-1, n-1) and recursively find the minimum path to (0,0).
     * At any cell (i, j), the cost is:
     * Cost = grid[i][j] + min(Path from Top, Path from Left)
     *
     * CRITICAL FIX:
     * We return (int) 1e9 (1 Billion) instead of Integer.MAX_VALUE for out-of-bounds cases.
     * Why? Because grid[i][j] + Integer.MAX_VALUE causes Integer Overflow (negative number),
     * which breaks the Math.min() logic.
     *
     * COMPLEXITY:
     * - Time: O(2^(M+N)) -> Exponential.
     * - Space: O(M+N) -> Recursion stack depth.
     */
    private static int minPathSumRecursive(int i, int j, int[][] grid) {
        // Base Case: Reached the start (0,0)
        if (i == 0 && j == 0) return grid[i][j];

        // Base Case: Out of bounds
        // Return a large number to ensure this path is NOT chosen by Math.min()
        if (i < 0 || j < 0) return (int) 1e9;

        // Recursive Calls
        int up = minPathSumRecursive(i - 1, j, grid);
        int left = minPathSumRecursive(i, j - 1, grid);

        // Current cell value + minimum of the two previous paths
        return grid[i][j] + Math.min(up, left);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Same as recursion, but we store the result of `minPath(i, j)` in `dp[i][j]`.
     * If we encounter the same cell again, we return the stored value.
     *
     * COMPLEXITY:
     * - Time: O(M * N) -> Each cell is computed exactly once.
     * - Space: O(M * N) + O(M + N) -> DP Array + Stack.
     */
    private static int minPathSumMemoization(int i, int j, int[][] grid, int[][] dp) {
        if (i == 0 && j == 0) return grid[i][j];
        if (i < 0 || j < 0) return (int) 1e9; // Large value to avoid overflow

        // Step 1: Check Cache
        if (dp[i][j] != -1) return dp[i][j];

        // Step 2: Compute
        int up = minPathSumMemoization(i - 1, j, grid, dp);
        int left = minPathSumMemoization(i, j - 1, grid, dp);

        // Step 3: Store and Return
        return dp[i][j] = grid[i][j] + Math.min(up, left);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Build the DP table from top-left (0,0) to bottom-right (m-1, n-1).
     * * dp[i][j] represents the minimum path sum to reach cell (i, j).
     * - First Row: Can only come from the Left.
     * - First Col: Can only come from the Top.
     * - Inner Cells: min(Top, Left) + Current.
     *
     * COMPLEXITY:
     * - Time: O(M * N) -> Iterates through the matrix once.
     * - Space: O(M * N) -> DP Array.
     */
    private static int minPathSumTabulation(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] dp = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                // Base Case: Start position
                if (i == 0 && j == 0) {
                    dp[i][j] = grid[i][j];
                    continue;
                }

                // Initialize paths as "Infinity"
                int up = (int) 1e9;
                int left = (int) 1e9;

                // Move Down (Look Up)
                if (i > 0) up = dp[i - 1][j];

                // Move Right (Look Left)
                if (j > 0) left = dp[i][j - 1];

                // Current cost = Value + Min of previous paths
                dp[i][j] = grid[i][j] + Math.min(up, left);
            }
        }
        return dp[n - 1][m - 1];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED DP
     * ----------------------------------------------------------------------
     * LOGIC:
     * To calculate the current row `curr`, we only need the values from the
     * previous row `prev` (for 'up' moves) and the current row being built (for 'left' moves).
     *
     * - `prev[j]` represents the value from Top (dp[i-1][j]).
     * - `curr[j-1]` represents the value from Left (dp[i][j-1]).
     *
     * COMPLEXITY:
     * - Time: O(M * N)
     * - Space: O(M) -> Single row array.
     */
    private static int minPathSumSpaceOptimized(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        int[] prev = new int[m];

        for (int i = 0; i < n; i++) {
            int[] curr = new int[m];

            for (int j = 0; j < m; j++) {
                // Base Case: Start position
                if (i == 0 && j == 0) {
                    curr[j] = grid[i][j];
                    continue;
                }

                int up = (int) 1e9;
                int left = (int) 1e9;

                // Look Up: Value comes from 'prev' array
                if (i > 0) up = prev[j];

                // Look Left: Value comes from 'curr' array (just computed)
                if (j > 0) left = curr[j - 1];

                curr[j] = grid[i][j] + Math.min(up, left);
            }
            // Update prev to be the current row for the next iteration
            prev = curr;
        }

        return prev[m - 1];
    }
}