package com.questions.strivers.dynamicprogramming.twoddp;

import java.util.Arrays;

// Class to solve the unique paths problem with obstacles
public class DPonGridsObstracles {

    // Recursive approach to find unique paths with obstacles
    // Time Complexity: Exponential, O(2^(N*M)), due to recursive calls
    // Space Complexity: O(N+M), recursion stack depth
    public static int uniquePathsRecursive(int row, int col, int[][] grid) {
        // Base case: If we reach the top-left cell and it's not blocked
        if (row == 0 && col == 0 && grid[row][col] != 1) {
            return 1; // Only one path exists if the starting cell is not blocked
        }
        // If we go out of bounds or encounter a blocked cell, return 0
        if (row < 0 || col < 0 || grid[row][col] == 1) {
            return 0; // No valid path exists
        }
        // Recursive calls: Calculate paths by moving up and left
        int bottom = uniquePathsRecursive(row - 1, col, grid); // Move up (to the previous row)
        int right = uniquePathsRecursive(row, col - 1, grid); // Move left (to the previous column)
        return bottom + right; // Sum the paths from both directions
    }

    // Memoization approach to find unique paths with obstacles
    // Time Complexity: O(N*M), each cell is computed once
    // Space Complexity: O(N*M + N+M), space for dp array and recursion stack
    public static int uniquePathsMemoization(int row, int col, int[][] grid, int[][] dp) {
        // Base case: If we reach the top-left cell and it's not blocked
        if (row == 0 && col == 0 && grid[row][col] != 1) {
            return 1; // Only one path exists if the starting cell is not blocked
        }
        // If we go out of bounds or encounter a blocked cell, return 0
        if (row < 0 || col < 0 || grid[row][col] == 1) {
            return 0; // No valid path exists
        }
        // If the result is already computed, return it
        if (dp[row][col] != -1) {
            return dp[row][col]; // Use cached result to avoid redundant calculations
        }
        // Recursive calls: Calculate paths by moving up and left
        int bottom = uniquePathsMemoization(row - 1, col, grid, dp); // Move up (to the previous row)
        int right = uniquePathsMemoization(row, col - 1, grid, dp); // Move left (to the previous column)
        return dp[row][col] = bottom + right; // Store the result in dp array for future use
    }

    // Tabulation approach to find unique paths with obstacles
    // Time Complexity: O(N*M), iterates through the matrix
    // Space Complexity: O(N*M), space for dp array
    public static int uniquePathsTabulation(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        int[][] dp = new int[n][m]; // Create a dp array to store results

        // Initialize the starting cell
        dp[0][0] = grid[0][0] == 1 ? 0 : 1; // If blocked, set to 0; otherwise, set to 1

        // Fill the first row
        for (int j = 1; j < m; j++) {
            dp[0][j] = grid[0][j] == 1 ? 0 : dp[0][j - 1]; // If blocked, set to 0; otherwise, inherit from left cell
        }

        // Fill the first column
        for (int i = 1; i < n; i++) {
            dp[i][0] = grid[i][0] == 1 ? 0 : dp[i - 1][0]; // If blocked, set to 0; otherwise, inherit from top cell
        }

        // Fill the rest of the dp table
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (grid[i][j] == 1) {
                    dp[i][j] = 0; // Blocked cell, no paths possible
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1]; // Sum paths from top and left
                }
            }
        }
        return dp[n - 1][m - 1]; // Return the result for the bottom-right cell
    }

    // Space-optimized approach to find unique paths with obstacles
    // Time Complexity: O(N*M), iterates through the matrix
    // Space Complexity: O(M), space for a single row
    public static int uniquePathsSpaceOptimized(int[][] grid) {
        int n = grid.length, m = grid[0].length;
        int[] prev = new int[m]; // Array to store results for the previous row

        // Initialize the first row
        prev[0] = grid[0][0] == 1 ? 0 : 1; // If blocked, set to 0; otherwise, set to 1
        for (int j = 1; j < m; j++) {
            prev[j] = grid[0][j] == 1 ? 0 : prev[j - 1]; // If blocked, set to 0; otherwise, inherit from left cell
        }

        // Update the row iteratively for subsequent rows
        for (int i = 1; i < n; i++) {
            int[] curr = new int[m]; // Array to store results for the current row
            curr[0] = grid[i][0] == 1 ? 0 : prev[0]; // If blocked, set to 0; otherwise, inherit from top cell
            for (int j = 1; j < m; j++) {
                curr[j] = grid[i][j] == 1 ? 0 : prev[j] + curr[j - 1]; // Sum paths from top and left
            }
            prev = curr; // Update previous row results
        }
        return prev[m - 1]; // Return the result for the bottom-right cell
    }

    public static void main(String[] args) {
        int[][] grid = {
                {0, 0, 0}, // 0 represents an open cell
                {0, 1, 0}, // 1 represents a blocked cell
                {0, 0, 0}  // Bottom-right cell is the destination
        };

        // Recursive approach
        System.out.println("Recursive: " + uniquePathsRecursive(grid.length - 1, grid[0].length - 1, grid));

        // Memoization approach
        int[][] dp = new int[grid.length][grid[0].length];
        for (int[] row : dp) {
            Arrays.fill(row, -1); // Initialize dp array with -1
        }
        System.out.println("Memoization: " + uniquePathsMemoization(grid.length - 1, grid[0].length - 1, grid, dp));

        // Tabulation approach
        System.out.println("Tabulation: " + uniquePathsTabulation(grid));

        // Space-optimized approach
        System.out.println("Space Optimized: " + uniquePathsSpaceOptimized(grid));
    }
}