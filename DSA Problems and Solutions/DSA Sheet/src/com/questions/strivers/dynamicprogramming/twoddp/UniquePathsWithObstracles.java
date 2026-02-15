package com.questions.strivers.dynamicprogramming.twoddp;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: UNIQUE PATHS II (LeetCode 63)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * You are given an m x n integer array grid. There is a robot initially located at the top-left
 * corner (grid[0][0]). The robot tries to move to the bottom-right corner (grid[m-1][n-1]).
 * The robot can only move either DOWN or RIGHT at any point in time.
 *
 * An obstacle and space are marked as 1 and 0 respectively in grid.
 * A path that the robot takes cannot include any square that is an obstacle.
 *
 * Return the number of possible unique paths that the robot can take to reach the bottom-right corner.
 *
 * EXAMPLE:
 * Input: obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
 * Output: 2
 * Explanation: There is one obstacle in the middle of the 3x3 grid above.
 * There are two ways to reach the bottom-right corner:
 * 1. Right -> Right -> Down -> Down
 * 2. Down -> Down -> Right -> Right
 *
 * CRITICAL EDGE CASE:
 * If the Start (0,0) or End (m-1, n-1) is an obstacle (1), the answer is 0.
 * ==================================================================================================
 */
public class UniquePathsWithObstracles {

    public static void main(String[] args) {
        int[][] grid = {
                {0, 0, 0},
                {0, 1, 0}, // Middle cell is blocked
                {0, 0, 0}
        };

        int m = grid.length;    // Rows
        int n = grid[0].length; // Cols

        System.out.println("Grid Size: " + m + "x" + n);
        System.out.println("--------------------------------------------------");

        // 1. Recursive Approach
        // Note: Check if start is blocked before calling to save time, though recursion handles it.
        if (grid[0][0] == 1) {
            System.out.println("Start is blocked. Paths: 0");
        } else {
            System.out.println("1. Recursion       : " + uniquePathsRecursive(m - 1, n - 1, grid));
        }

        // 2. Memoization Approach
        int[][] dp = new int[m][n];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("2. Memoization     : " + uniquePathsMemoization(m - 1, n - 1, grid, dp));

        // 3. Tabulation Approach
        System.out.println("3. Tabulation      : " + uniquePathsTabulation(grid));

        // 4. Space Optimized Approach
        System.out.println("4. Space Optimized : " + uniquePathsSpaceOptimized(grid));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Start from destination (m-1, n-1) and work backwards to (0,0).
     * At any cell (row, col):
     * - If blocked (1): Return 0 (Dead end).
     * - If start (0,0): Return 1 (Found a path).
     * - Else: Sum of paths from Top + Paths from Left.
     *
     * COMPLEXITY:
     * - Time: O(2^(M+N)) -> Exponential. Recomputes same states.
     * - Space: O(M+N) -> Recursion stack.
     */
    private static int uniquePathsRecursive(int row, int col, int[][] grid) {
        // Base Case 1: Out of bounds
        if (row < 0 || col < 0) {
            return 0;
        }

        // Base Case 2: Obstacle found (Dead End)
        // Note: This covers if the start or end is an obstacle.
        if (grid[row][col] == 1) {
            return 0;
        }

        // Base Case 3: Reached Start successfully
        if (row == 0 && col == 0) {
            return 1;
        }

        // Recursive Calls
        int fromTop = uniquePathsRecursive(row - 1, col, grid);
        int fromLeft = uniquePathsRecursive(row, col - 1, grid);

        return fromTop + fromLeft;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Same logic as recursion, but store results in dp[][] table.
     * dp[row][col] stores "number of ways to reach (row, col) from start".
     *
     * COMPLEXITY:
     * - Time: O(M * N) -> Each cell visited once.
     * - Space: O(M * N) + O(M+N) -> DP Table + Stack.
     */
    private static int uniquePathsMemoization(int row, int col, int[][] grid, int[][] dp) {
        // Base Cases
        if (row < 0 || col < 0) return 0;
        if (grid[row][col] == 1) return 0; // Obstacle
        if (row == 0 && col == 0) return 1;

        // Step 1: Check Cache
        if (dp[row][col] != -1) {
            return dp[row][col];
        }

        // Step 2: Compute
        int fromTop = uniquePathsMemoization(row - 1, col, grid, dp);
        int fromLeft = uniquePathsMemoization(row, col - 1, grid, dp);

        // Step 3: Store and Return
        return dp[row][col] = fromTop + fromLeft;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Build the solution from (0,0) to (m-1, n-1).
     *
     * Handling the First Row/Col:
     * - If we encounter an obstacle at (0, 2), then (0, 3), (0, 4)... become unreachable.
     * - So, for the first row/col, if a cell is 1, all subsequent cells are 0.
     *
     * COMPLEXITY:
     * - Time: O(M * N)
     * - Space: O(M * N)
     */
    private static int uniquePathsTabulation(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];

        // Iterate through every cell
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                // If obstacle, paths = 0 (Skip)
                if (grid[i][j] == 1) {
                    dp[i][j] = 0;
                    continue;
                }

                // Base Case: Start Position
                if (i == 0 && j == 0) {
                    dp[i][j] = 1;
                    continue;
                }

                // Add paths from Top and Left
                int fromTop = 0;
                int fromLeft = 0;

                if (i > 0) fromTop = dp[i - 1][j];
                if (j > 0) fromLeft = dp[i][j - 1];

                dp[i][j] = fromTop + fromLeft;
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED DP
     * ----------------------------------------------------------------------
     * LOGIC:
     * We only need the previous row (`prev`) to calculate the current row (`curr`).
     *
     * Key Update Logic:
     * - If grid[i][j] is an obstacle, curr[j] = 0.
     * - Else, curr[j] = prev[j] (Top) + curr[j-1] (Left).
     *
     * COMPLEXITY:
     * - Time: O(M * N)
     * - Space: O(N) -> Single row array.
     */
    private static int uniquePathsSpaceOptimized(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[] prev = new int[n];

        for (int i = 0; i < m; i++) {
            int[] curr = new int[n];
            for (int j = 0; j < n; j++) {

                // Case 1: Obstacle found
                if (grid[i][j] == 1) {
                    curr[j] = 0;
                    continue;
                }

                // Case 2: Start Position
                if (i == 0 && j == 0) {
                    curr[j] = 1;
                    continue;
                }

                // Case 3: Calculate Paths
                int fromTop = 0;
                int fromLeft = 0;

                // Value from Top comes from 'prev' array at same index 'j'
                if (i > 0) fromTop = prev[j];

                // Value from Left comes from 'curr' array at index 'j-1'
                if (j > 0) fromLeft = curr[j - 1];

                curr[j] = fromTop + fromLeft;
            }
            // Update previous row
            prev = curr;
        }

        return prev[n - 1];
    }
}