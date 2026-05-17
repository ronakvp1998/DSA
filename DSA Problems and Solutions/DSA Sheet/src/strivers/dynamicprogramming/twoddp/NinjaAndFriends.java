package com.questions.strivers.dynamicprogramming.twoddp;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: NINJA AND HIS FRIENDS (LeetCode 1463: Cherry Pickup II)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * We are given an N*M matrix where each cell contains a certain number of chocolates.
 * Two friends, Alice and Bob, start collecting chocolates:
 * - Alice starts at Top-Left (0, 0).
 * - Bob starts at Top-Right (0, M-1).
 *
 * RULES:
 * 1. From cell (i, j), they can move to (i+1, j-1), (i+1, j), or (i+1, j+1).
 * 2. They move simultaneously to the next row.
 * 3. If they land on the same cell, the chocolates are collected ONLY ONCE.
 * 4. If they land on different cells, both collect chocolates from their respective cells.
 * 5. They must stay within the grid boundaries.
 *
 * GOAL:
 * Maximize the total chocolates collected by both friends when they reach the last row.
 *
 * EXAMPLE:
 * Grid:
 * 2  3  1  2
 * 3  4  2  2
 * 5  6  3  5
 *
 * Output: 21
 * Explanation:
 * Path Alice: 2 -> 4 -> 6 (Total 12)
 * Path Bob:   2 -> 2 -> 5 (Total 9)
 * Combined: 12 + 9 = 21.
 * ==================================================================================================
 */
public class NinjaAndFriends {

    public static void main(String[] args) {
        int[][] grid = {
                {2, 3, 1, 2},
                {3, 4, 2, 2},
                {5, 6, 3, 5}
        };

        System.out.println("Grid Dimensions: " + grid.length + "x" + grid[0].length);
        System.out.println("--------------------------------------------------");

        // 1. Recursive Approach
        System.out.println("1. Recursive       : " + getMaxChocoRecursive(grid));

        // 2. Memoization Approach
        System.out.println("2. Memoization     : " + getMaxChocoMemo(grid));

        // 3. Tabulation Approach
        System.out.println("3. Tabulation      : " + getMaxChocoTab(grid));

        // 4. Space Optimized Approach
        System.out.println("4. Space Optimized : " + getMaxChocoSpaceOpt(grid));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * We need to track the positions of both Alice and Bob simultaneously.
     * Since they both move down one row at a time, we only need one 'row' variable 'i'.
     * State: f(i, j1, j2) -> Max chocolates from row 'i' to N-1, with Alice at 'j1' and Bob at 'j2'.
     *
     * Transitions:
     * Alice has 3 choices (-1, 0, +1 column change).
     * Bob has 3 choices (-1, 0, +1 column change).
     * Total Combinations = 3 * 3 = 9 possible next states.
     *
     * COMPLEXITY:
     * - Time: O(3^N * 3^N) -> O(9^N) Exponential! Very slow.
     * - Space: O(N) -> Recursion stack depth.
     */
    private static int getMaxChocoRecursive(int[][] grid) {
        int m = grid[0].length;
        return recursiveHelper(0, 0, m - 1, grid);
    }

    private static int recursiveHelper(int i, int j1, int j2, int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        // Base Case 1: Out of Bounds
        // Return a large negative value to indicate this path is invalid.
        // NOTE: Used -1e8 instead of Integer.MIN_VALUE to prevent integer overflow when adding.
        if (j1 < 0 || j1 >= m || j2 < 0 || j2 >= m) {
            return (int) -1e8;
        }

        // Base Case 2: Destination (Last Row)
        if (i == n - 1) {
            // If they land on the same cell, count once. Else count both.
            if (j1 == j2) return grid[i][j1];
            else return grid[i][j1] + grid[i][j2];
        }

        // Explore all 9 paths
        int max = (int) -1e8;

        // Alice's delta (dj1) and Bob's delta (dj2)
        for (int dj1 = -1; dj1 <= 1; dj1++) {
            for (int dj2 = -1; dj2 <= 1; dj2++) {

                // Calculate current reward
                int value = (j1 == j2) ? grid[i][j1] : grid[i][j1] + grid[i][j2];

                // Add result of the next step
                value += recursiveHelper(i + 1, j1 + dj1, j2 + dj2, grid);

                // Keep the maximum
                max = Math.max(max, value);
            }
        }
        return max;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Same logic as recursion, but we store results in a 3D DP array.
     * dp[i][j1][j2] stores the result for that specific configuration.
     *
     * Why 3D?
     * Because the state depends on Row(i), Alice's Col(j1), and Bob's Col(j2).
     *
     * COMPLEXITY:
     * - Time: O(N * M * M * 9) -> O(N * M^2). Much faster.
     * - Space: O(N * M * M) + O(N) stack space.
     */
    private static int getMaxChocoMemo(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        // Initialize 3D DP table with -1
        int[][][] dp = new int[n][m][m];
        for (int[][] layer : dp) {
            for (int[] row : layer) Arrays.fill(row, -1);
        }

        return memoHelper(0, 0, m - 1, grid, dp);
    }

    private static int memoHelper(int i, int j1, int j2, int[][] grid, int[][][] dp) {
        int n = grid.length;
        int m = grid[0].length;

        // Base Case: Out of bounds
        if (j1 < 0 || j1 >= m || j2 < 0 || j2 >= m) return (int) -1e8;

        // Base Case: Last Row
        if (i == n - 1) {
            if (j1 == j2) return grid[i][j1];
            else return grid[i][j1] + grid[i][j2];
        }

        // Step 1: Check Cache
        if (dp[i][j1][j2] != -1) return dp[i][j1][j2];

        // Step 2: Compute logic
        int max = (int) -1e8;

        // Try all 9 combinations
        for (int dj1 = -1; dj1 <= 1; dj1++) {
            for (int dj2 = -1; dj2 <= 1; dj2++) {

                int value = (j1 == j2) ? grid[i][j1] : grid[i][j1] + grid[i][j2];
                value += memoHelper(i + 1, j1 + dj1, j2 + dj2, grid, dp);

                max = Math.max(max, value);
            }
        }

        // Step 3: Store and Return
        return dp[i][j1][j2] = max;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Start from the LAST row and move UP to row 0.
     * 1. Initialize the DP table for the last row (base case).
     * 2. For every row 'i' from N-2 to 0:
     * - For every possible column 'j1' (Alice)
     * - For every possible column 'j2' (Bob)
     * - Try all 9 moves to 'i+1', verify bounds, take max.
     *
     * COMPLEXITY:
     * - Time: O(N * M^2)
     * - Space: O(N * M^2)
     */
    private static int getMaxChocoTab(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][][] dp = new int[n][m][m];

        // 1. Base Case: Fill Last Row
        for (int j1 = 0; j1 < m; j1++) {
            for (int j2 = 0; j2 < m; j2++) {
                if (j1 == j2) dp[n - 1][j1][j2] = grid[n - 1][j1];
                else dp[n - 1][j1][j2] = grid[n - 1][j1] + grid[n - 1][j2];
            }
        }

        // 2. Iterate Upwards (Row N-2 -> 0)
        for (int i = n - 2; i >= 0; i--) {
            for (int j1 = 0; j1 < m; j1++) {
                for (int j2 = 0; j2 < m; j2++) {

                    int max = (int) -1e8;

                    // Inner Loops: 9 Moves
                    for (int dj1 = -1; dj1 <= 1; dj1++) {
                        for (int dj2 = -1; dj2 <= 1; dj2++) {

                            int ans;
                            // Current cell value
                            if (j1 == j2) ans = grid[i][j1];
                            else ans = grid[i][j1] + grid[i][j2];

                            // Check boundary for next move
                            int nextJ1 = j1 + dj1;
                            int nextJ2 = j2 + dj2;

                            if (nextJ1 >= 0 && nextJ1 < m && nextJ2 >= 0 && nextJ2 < m) {
                                ans += dp[i + 1][nextJ1][nextJ2];
                                max = Math.max(max, ans);
                            } else {
                                // Invalid move, ignore or add large negative
                                max = Math.max(max, (int) -1e8);
                            }
                        }
                    }
                    dp[i][j1][j2] = max;
                }
            }
        }

        // Result is at the starting position: Row 0, Alice(0), Bob(m-1)
        return dp[0][0][m - 1];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED DP
     * ----------------------------------------------------------------------
     * LOGIC:
     * We only need the state of the "next row" (i+1) to calculate the "current row" (i).
     * Instead of a 3D array `dp[n][m][m]`, we use a 2D array `front[m][m]`.
     *
     * COMPLEXITY:
     * - Time: O(N * M^2)
     * - Space: O(M^2) -> Significant reduction from O(N*M^2).
     */
    private static int getMaxChocoSpaceOpt(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        // 'front' stores the results of the (i+1)th row
        int[][] front = new int[m][m];

        // 1. Base Case: Last Row
        for (int j1 = 0; j1 < m; j1++) {
            for (int j2 = 0; j2 < m; j2++) {
                if (j1 == j2) front[j1][j2] = grid[n - 1][j1];
                else front[j1][j2] = grid[n - 1][j1] + grid[n - 1][j2];
            }
        }

        // 2. Iterate Upwards
        for (int i = n - 2; i >= 0; i--) {
            int[][] curr = new int[m][m]; // Temp array for current row

            for (int j1 = 0; j1 < m; j1++) {
                for (int j2 = 0; j2 < m; j2++) {

                    int max = (int) -1e8;

                    for (int dj1 = -1; dj1 <= 1; dj1++) {
                        for (int dj2 = -1; dj2 <= 1; dj2++) {
                            int ans = (j1 == j2) ? grid[i][j1] : grid[i][j1] + grid[i][j2];

                            int nextJ1 = j1 + dj1;
                            int nextJ2 = j2 + dj2;

                            if (nextJ1 >= 0 && nextJ1 < m && nextJ2 >= 0 && nextJ2 < m) {
                                ans += front[nextJ1][nextJ2];
                                max = Math.max(max, ans);
                            } else {
                                max = Math.max(max, (int) -1e8);
                            }
                        }
                    }
                    curr[j1][j2] = max;
                }
            }
            // Update 'front' to be the current row for the next iteration
            front = curr;
        }

        return front[0][m - 1];
    }
}