package com.questions.strivers.dynamicprogramming.twoddp;
import java.util.Arrays;
//https://takeuforward.org/data-structure/minimum-path-sum-in-a-grid-dp-10/
//We are given an “N*M” matrix of integers.
// We need to find a path from the top-left corner to the bottom-right corner of the matrix,
// such that there is a minimum cost past that we select.
//At every cell, we can move in only two directions: right and bottom.
// The cost of a path is given as the sum of values of cells of the given matrix.
// n=2, m=3
//  5  9  6
// 11  5  2
// output = 21 (5+9+5+2) MinPath Sum
public class MinPathSumGrid {

    // Recursive approach
    // Time Complexity: O(2^(N*M)), exponential due to recursion
    // Space Complexity: O(N*M), recursion stack space
    public static int minPathSumRecursive(int i, int j, int[][] grid) {
        // Base case: if we reach the top-left corner, return its value
        if (i == 0 && j == 0) return grid[i][j];
        // If out of bounds, return a large value (infinity)
        if (i < 0 || j < 0) return Integer.MAX_VALUE;

        // Recursive calls for top and left cells
        int up = minPathSumRecursive(i - 1, j, grid);
        int left = minPathSumRecursive(i, j - 1, grid);

        // Return the current cell value + minimum of top and left paths
        return grid[i][j] + Math.min(up, left);
    }

    // Memoization approach
    // Time Complexity: O(N*M), each cell is computed once
    // Space Complexity: O(N*M) for dp array + O(N*M) recursion stack space
    public static int minPathSumMemoization(int i, int j, int[][] grid, int[][] dp) {
        // Base case: if we reach the top-left corner, return its value
        if (i == 0 && j == 0) return grid[i][j];
        // If out of bounds, return a large value (infinity)
        if (i < 0 || j < 0) return Integer.MAX_VALUE;

        // If already computed, return the stored value
        if (dp[i][j] != -1) return dp[i][j];

        // Recursive calls for top and left cells
        int up = minPathSumMemoization(i - 1, j, grid, dp);
        int left = minPathSumMemoization(i, j - 1, grid, dp);

        // Store the result in dp array and return
        return dp[i][j] = grid[i][j] + Math.min(up, left);
    }

    // Tabulation approach
    // Time Complexity: O(N*M), iterates through the matrix
    // Space Complexity: O(N*M), space for dp array
    public static int minPathSumTabulation(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        int[][] dp = new int[n][m]; // Create a dp array to store results

        // Initialize the starting cell
        dp[0][0] = grid[0][0];

        // Fill the first row
        for (int j = 1; j < m; j++) {
            dp[0][j] = grid[0][j] + dp[0][j - 1];
        }

        // Fill the first column
        for (int i = 1; i < n; i++) {
            dp[i][0] = grid[i][0] + dp[i - 1][0];
        }

        // Fill the rest of the dp table
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                dp[i][j] = grid[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
            }
        }

        return dp[n - 1][m - 1]; // Return the result for the bottom-right cell
    }

    // Space-optimized approach
    // Time Complexity: O(N*M), iterates through the matrix
    // Space Complexity: O(M), space for a single row
    public static int minPathSumSpaceOptimized(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        int[] prev = new int[m]; // Array to store results for the previous row

        // Initialize the first row
        prev[0] = grid[0][0];
        for (int j = 1; j < m; j++) {
            prev[j] = grid[0][j] + prev[j - 1];
        }

        // Update the row iteratively for subsequent rows
        for (int i = 1; i < n; i++) {
            int[] curr = new int[m]; // Array to store results for the current row
            curr[0] = grid[i][0] + prev[0];
            for (int j = 1; j < m; j++) {
                curr[j] = grid[i][j] + Math.min(prev[j], curr[j - 1]);
            }
            prev = curr; // Update previous row results
        }

        return prev[m - 1]; // Return the result for the bottom-right cell
    }

    public static void main(String[] args) {
        int[][] grid = {
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        };

        // Recursive approach
        System.out.println("Recursive: " + minPathSumRecursive(grid.length - 1, grid[0].length - 1, grid));

        // Memoization approach
        int[][] dp = new int[grid.length][grid[0].length];
        for (int[] row : dp) {
            Arrays.fill(row, -1); // Initialize dp array with -1
        }
        System.out.println("Memoization: " + minPathSumMemoization(grid.length - 1, grid[0].length - 1, grid, dp));

        // Tabulation approach
        System.out.println("Tabulation: " + minPathSumTabulation(grid));

        // Space-optimized approach
        System.out.println("Space Optimized: " + minPathSumSpaceOptimized(grid));
    }
}