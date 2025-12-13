package com.questions.strivers.dynamicprogramming.twoddp;

//https://takeuforward.org/data-structure/3-d-dp-ninja-and-his-friends-dp-13/
//We are given an ‘N*M’ matrix. Every cell of the matrix has some chocolates on it,
//        mat[i][j] gives us the number of chocolates.
//We have two friends ‘Alice’ and ‘Bob’. initially,
//        Alice is standing on the cell(0,0) and Bob is standing on the cell(0, M-1).
//Both of them can move only to the cells below them in these three directions:
//        to the bottom cell (↓), to the bottom-right cell(↘), or to the bottom-left cell(↙).
//When Alica and Bob visit a cell, they take all the chocolates from that cell with them.
//It can happen that they visit the same cell, in that case, the chocolates need to be considered only once.
//They cannot go out of the boundary of the given matrix,
//we need to return the maximum number of chocolates that Bob and Alice can together collect.
// 2  3  1  2
// 3  4  2  2
// 5  6  3  5
// Alice(2 + 4 + 6) + Bob(2 + 2 + 5) = 21
import java.util.Arrays;

public class MinMaxFallingSumPath {

    public static void main(String[] args) {
        // Example input grid where each cell contains chocolates
        int[][] grid = {
                {2, 3, 1, 2},
                {3, 4, 2, 2},
                {5, 6, 3, 5}
        };

        // Print maximum chocolates collected using each approach
        System.out.println("Recursive (Plain): " + getMaxChocoRecursive(grid));
        System.out.println("Recursive + Memoization: " + getMaxChocoMemo(grid));
        System.out.println("Tabulation: " + getMaxChocoTab(grid));
        System.out.println("Space Optimized: " + getMaxChocoSpaceOpt(grid));
    }

    // -------------------- 0. Plain Recursive ----------------------
    private static int getMaxChocoRecursive(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        return recursiveHelper(0, 0, m - 1, grid);
    }

    private static int recursiveHelper(int i, int j1, int j2, int[][] grid) {
        int n = grid.length, m = grid[0].length;

        // Base case 1: Out-of-bounds check
        if (j1 < 0 || j1 >= m || j2 < 0 || j2 >= m) return Integer.MIN_VALUE;

        // Base case 2: if we are on the last row (reached the bottom)
        // alice grid[i][j1] and bob grid[i][j2]
        if (i == n - 1) return (j1 == j2) ? grid[i][j1] : grid[i][j1] + grid[i][j2];

        int max = Integer.MIN_VALUE; // Track maximum chocolates

        // Try all possible moves (3 for Alice * 3 for Bob = 9 combinations)
        // for every movement of alice (3 movements), bob also has 3 movements, so total 9 combinations of moves
        // rows are fixed, we are moving downwards, columns are -1, 0, +1
        for (int dj1 = -1; dj1 <= 1; dj1++) {
            for (int dj2 = -1; dj2 <= 1; dj2++) {
                int nextJ1 = j1 + dj1, nextJ2 = j2 + dj2;

                // Chocolates collected at current step
                int chocolates = (j1 == j2) ? grid[i][j1] : grid[i][j1] + grid[i][j2];

                // Add result from recursive call
                chocolates += recursiveHelper(i + 1, nextJ1, nextJ2, grid);

                // Update max if this path yields more
                max = Math.max(max, chocolates);
            }
        }

        return max; // Return the best among all 9 options
    }
    // Time: O(3^N * 3^N) worst case, exponential
    // Space: O(N) recursion stack depth

    // -------------------- 1. Recursive + Memoization ----------------------

    private static int getMaxChocoMemo(int[][] grid) {
        int n = grid.length, m = grid[0].length; // Grid dimensions

        // 3D memoization table initialized with -1
        int[][][] dp = new int[n][m][m];
        for (int[][] layer : dp) {
            for (int[] row : layer) Arrays.fill(row, -1);
        }

        // Start recursion from (0,0) for Alice and (0,m-1) for Bob
        return memoHelper(0, 0, m - 1, grid, dp);
    }

    private static int memoHelper(int i, int j1, int j2, int[][] grid, int[][][] dp) {
        int n = grid.length, m = grid[0].length;

        // Out-of-bounds check
        if (j1 < 0 || j1 >= m || j2 < 0 || j2 >= m) return Integer.MIN_VALUE;

        // Base case: if we are on the last row
        if (i == n - 1) return (j1 == j2) ? grid[i][j1] : grid[i][j1] + grid[i][j2];

        // If already computed, return cached result
        if (dp[i][j1][j2] != -1) return dp[i][j1][j2];

        int max = Integer.MIN_VALUE; // Track maximum chocolates

        // Try all possible moves (3 for Alice * 3 for Bob = 9 combinations)
        for (int dj1 = -1; dj1 <= 1; dj1++) {
            for (int dj2 = -1; dj2 <= 1; dj2++) {
                int nextJ1 = j1 + dj1, nextJ2 = j2 + dj2; // Next positions

                // Chocolates collected at current step
                int chocolates = (j1 == j2) ? grid[i][j1] : grid[i][j1] + grid[i][j2];

                // Add result from recursive call
                chocolates += memoHelper(i + 1, nextJ1, nextJ2, grid, dp);

                // Update max if this path yields more
                max = Math.max(max, chocolates);
            }
        }

        // Store result and return
        return dp[i][j1][j2] = max;
    }

    // Time: O(N*M*M*9) = O(N*M^2), Space: O(N*M*M) + O(N) stack

    // -------------------- 2. Tabulation ----------------------

    private static int getMaxChocoTab(int[][] grid) {
        int n = grid.length, m = grid[0].length;

        // 3D DP array to hold results
        int[][][] dp = new int[n][m][m];

        // Fill base case for last row
        for (int j1 = 0; j1 < m; j1++) {
            for (int j2 = 0; j2 < m; j2++) {
                if (j1 == j2)
                    dp[n - 1][j1][j2] = grid[n - 1][j1]; // Same cell, count once
                else
                    dp[n - 1][j1][j2] = grid[n - 1][j1] + grid[n - 1][j2]; // Different cells
            }
        }

        // Build solution bottom-up
        for (int i = n - 2; i >= 0; i--) {
            for (int j1 = 0; j1 < m; j1++) {
                for (int j2 = 0; j2 < m; j2++) {
                    int max = Integer.MIN_VALUE;
                    // Try all combinations of movements
                    for (int dj1 = -1; dj1 <= 1; dj1++) {
                        for (int dj2 = -1; dj2 <= 1; dj2++) {
                            int nj1 = j1 + dj1, nj2 = j2 + dj2;
                            // Check if within bounds
                            if (nj1 >= 0 && nj1 < m && nj2 >= 0 && nj2 < m) {
                                int choco = (j1 == j2) ? grid[i][j1] : grid[i][j1] + grid[i][j2];
                                choco += dp[i + 1][nj1][nj2]; // Add future state
                                max = Math.max(max, choco);
                            }
                        }
                    }
                    dp[i][j1][j2] = max; // Store best result for state
                }
            }
        }

        // Final answer: start at top-left for Alice and top-right for Bob
        return dp[0][0][m - 1];
    }

    // Time: O(N*M*M*9) = O(N*M^2), Space: O(N*M*M)

    // -------------------- 3. Space Optimized ----------------------

    private static int getMaxChocoSpaceOpt(int[][] grid) {
        int n = grid.length, m = grid[0].length;

        // Only keep current and previous 2D state
        int[][] front = new int[m][m];

        // Fill base case for last row
        for (int j1 = 0; j1 < m; j1++) {
            for (int j2 = 0; j2 < m; j2++) {
                front[j1][j2] = (j1 == j2) ? grid[n - 1][j1] : grid[n - 1][j1] + grid[n - 1][j2];
            }
        }

        // Iterate from second last row to top
        for (int i = n - 2; i >= 0; i--) {
            int[][] curr = new int[m][m]; // Current row's state

            for (int j1 = 0; j1 < m; j1++) {
                for (int j2 = 0; j2 < m; j2++) {
                    int max = Integer.MIN_VALUE;
                    for (int dj1 = -1; dj1 <= 1; dj1++) {
                        for (int dj2 = -1; dj2 <= 1; dj2++) {
                            int nj1 = j1 + dj1, nj2 = j2 + dj2;
                            if (nj1 >= 0 && nj1 < m && nj2 >= 0 && nj2 < m) {
                                int choco = (j1 == j2) ? grid[i][j1] : grid[i][j1] + grid[i][j2];
                                choco += front[nj1][nj2]; // Use previous row's results
                                max = Math.max(max, choco);
                            }
                        }
                    }
                    curr[j1][j2] = max; // Update current state
                }
            }

            front = curr; // Move curr to front for next iteration
        }

        // Answer is when Alice at (0,0) and Bob at (0,m-1)
        return front[0][m - 1];
    }

    // Time: O(N*M*M*9) = O(N*M^2), Space: O(M*M)
}






