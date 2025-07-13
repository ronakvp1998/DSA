package com.questions.strivers.dynamicprogramming.twoddp;

import java.util.Arrays;

// https://takeuforward.org/data-structure/grid-unique-paths-dp-on-grids-dp8/
//Given two values M and N, which represent a matrix[M][N].
//We need to find the total unique paths from the top-left cell (matrix[0][0]) to the rightmost cell (matrix[M-1][N-1]).
//At any cell we are allowed to move in only two directions:- bottom and right.
public class DPonGrids {

    public static void main(String[] args) {
        int m = 3, n = 3;

        // Recursive approach
        System.out.println("Recursive: " + uniquePathsRecursive(m - 1, n - 1));

        // Memoization approach
        int[][] dp = new int[m][n];
        for (int[] row : dp) {
            Arrays.fill(row, -1); // Initialize the dp array with -1
        }
        System.out.println("Memoization: " + uniquePathsMemoization(m - 1, n - 1, dp));

        // Tabulation approach
        System.out.println("Tabulation: " + uniquePathsTabulation(m, n));

        // Space-optimized approach
        System.out.println("Space Optimized: " + uniquePathsSpaceOptimized(m, n));
    }

    // Recursive approach to find unique paths
    // Time Complexity: O(2^(M+N)) - Exponential due to two choices at each step
    // Space Complexity: O(M+N) - Recursion stack depth (path length)
    public static int uniquePathsRecursive(int row, int col) {
        // Base case: If we reach the top-left cell, there's only one path
        if (row == 0 && col == 0) {
            return 1;
        }
        // If we go out of bounds, return 0 (no valid path)
        if (row < 0 || col < 0) {
            return 0;
        }
        // Recursive calls: Move bottom and right
        int bottom = uniquePathsRecursive(row - 1, col); // Move bottom
        int right = uniquePathsRecursive(row, col - 1); // Move right
        return bottom + right; // Sum the paths from both directions
    }

    // Memoization approach to find unique paths
    // Time Complexity: O(M * N) - Each cell is computed once
    // Space Complexity: O(M * N + M+N) - Space for dp array and recursion stack
    public static int uniquePathsMemoization(int row, int col, int[][] dp) {
        // Base case: If we reach the top-left cell, there's only one path
        if (row == 0 && col == 0) {
            return 1;
        }
        // If we go out of bounds, return 0 (no valid path)
        if (row < 0 || col < 0) {
            return 0;
        }
        // If the result is already computed, return it
        if (dp[row][col] != -1) {
            return dp[row][col];
        }
        // Recursive calls: Move bottom and right
        int bottom = uniquePathsMemoization(row - 1, col, dp); // Move bottom
        int right = uniquePathsMemoization(row, col - 1, dp); // Move right
        return dp[row][col] = bottom + right; // Store the result in dp array
    }

    // Tabulation approach to find unique paths
    // Time Complexity: O(M * N) - Iterates through the matrix
    // Space Complexity: O(M * N) - Space for dp array
    public static int uniquePathsTabulation(int m, int n) {
        int[][] dp = new int[m][n]; // Create a dp array to store results

        // Base case: Fill the first row and first column with 1
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1; // Only one way to reach cells in the first column
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1; // Only one way to reach cells in the first row
        }

        // Fill the rest of the dp table
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // The number of ways to reach dp[i][j] is the sum of ways to reach
                // the cell above it (dp[i-1][j]) and the cell to its left (dp[i][j-1])
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1]; // Return the result for the bottom-right cell
    }

    // Space-optimized approach to find unique paths
    // Time Complexity: O(M * N) - Iterates through the matrix
    // Space Complexity: O(N) - Space for a single row
    public static int uniquePathsSpaceOptimized(int m, int n) {
        int[] prev = new int[n]; // Array to store results for the previous row

        // Base case: Initialize the first row with 1
        for (int j = 0; j < n; j++) {
            prev[j] = 1; // Only one way to reach cells in the first row
        }

        // Update the row iteratively for subsequent rows
        for (int i = 1; i < m; i++) {
            int[] curr = new int[n]; // Array to store results for the current row
            curr[0] = 1; // First column is always 1
            for (int j = 1; j < n; j++) {
                // The number of ways to reach curr[j] is the sum of ways to reach
                // the cell above it (prev[j]) and the cell to its left (curr[j-1])
                curr[j] = prev[j] + curr[j - 1];
            }
            prev = curr; // Update previous row results
        }
        return prev[n - 1]; // Return the result for the bottom-right cell
    }
}