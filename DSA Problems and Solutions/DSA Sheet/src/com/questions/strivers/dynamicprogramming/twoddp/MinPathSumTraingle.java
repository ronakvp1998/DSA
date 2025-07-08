package com.questions.strivers.dynamicprogramming.twoddp;

import java.util.Arrays;
//https://takeuforward.org/data-structure/minimum-path-sum-in-triangular-grid-dp-11/
public class MinPathSumTraingle {

    // Recursive approach
    // Time Complexity: O(2^N), exponential due to recursion
    // Space Complexity: O(N), recursion stack space
    public static int minPathSumRecursive(int i, int j, int[][] triangle) {
        // Base case: if we reach the last row, return the value of the cell
        if (i == triangle.length - 1) return triangle[i][j];

        // Recursive calls for bottom and bottom-right cells
        int down = minPathSumRecursive(i + 1, j, triangle); // Move to the cell directly below
        int downRight = minPathSumRecursive(i + 1, j + 1, triangle); // Move to the cell diagonally bottom-right

        // Return the current cell value + minimum of bottom and bottom-right paths
        return triangle[i][j] + Math.min(down, downRight);
    }

    // Memoization approach
    // Time Complexity: O(N^2), each cell is computed once
    // Space Complexity: O(N^2) for dp array + O(N) recursion stack space
    public static int minPathSumMemoization(int i, int j, int[][] triangle, int[][] dp) {
        // Base case: if we reach the last row, return the value of the cell
        if (i == triangle.length - 1) return triangle[i][j];

        // If already computed, return the stored value from the dp array
        if (dp[i][j] != -1) return dp[i][j];

        // Recursive calls for bottom and bottom-right cells
        int down = minPathSumMemoization(i + 1, j, triangle, dp); // Move to the cell directly below
        int downRight = minPathSumMemoization(i + 1, j + 1, triangle, dp); // Move to the cell diagonally bottom-right

        // Store the result in dp array and return
        dp[i][j] = triangle[i][j] + Math.min(down, downRight);
        return dp[i][j];
    }

    // Tabulation approach
    // Time Complexity: O(N^2), iterates through the triangle
    // Space Complexity: O(N^2), space for dp array
    public static int minPathSumTabulation(int[][] triangle) {
        int n = triangle.length;
        int[][] dp = new int[n][n]; // Create a dp array to store results

        // Initialize the last row of the dp array with the values from the triangle
        for (int j = 0; j < n; j++) {
            dp[n - 1][j] = triangle[n - 1][j];
        }

        // Fill the dp table from bottom to top
        for (int i = n - 2; i >= 0; i--) { // Start from the second last row
            for (int j = 0; j <= i; j++) { // Iterate through all valid columns in the current row
                // Compute the minimum path sum for the current cell
                dp[i][j] = triangle[i][j] + Math.min(dp[i + 1][j], dp[i + 1][j + 1]);
            }
        }

        // Return the result for the top cell (minimum path sum from top to bottom)
        return dp[0][0];
    }

    // Space-optimized approach
    // Time Complexity: O(N^2), iterates through the triangle
    // Space Complexity: O(N), space for a single row
    public static int minPathSumSpaceOptimized(int[][] triangle) {
        int n = triangle.length;
        int[] prev = new int[n]; // Array to store results for the previous row

        // Initialize the last row of the triangle into the prev array
        for (int j = 0; j < n; j++) {
            prev[j] = triangle[n - 1][j];
        }

        // Update the row iteratively from bottom to top
        for (int i = n - 2; i >= 0; i--) { // Start from the second last row
            int[] curr = new int[n]; // Array to store results for the current row
            for (int j = 0; j <= i; j++) { // Iterate through all valid columns in the current row
                // Compute the minimum path sum for the current cell
                curr[j] = triangle[i][j] + Math.min(prev[j], prev[j + 1]);
            }
            prev = curr; // Update previous row results
        }

        // Return the result for the top cell (minimum path sum from top to bottom)
        return prev[0];
    }

    public static void main(String[] args) {
        int[][] triangle = {
                {2},
                {3, 4},
                {6, 5, 7},
                {4, 1, 8, 3}
        };

        // Recursive approach
        // start from the top of the triangle f(0, 0)
        System.out.println("Recursive: " + minPathSumRecursive(0, 0, triangle));

        // Memoization approach
        int[][] dp = new int[triangle.length][triangle.length];
        for (int[] row : dp) {
            Arrays.fill(row, -1); // Initialize dp array with -1
        }
        System.out.println("Memoization: " + minPathSumMemoization(0, 0, triangle, dp));

        // Tabulation approach
        System.out.println("Tabulation: " + minPathSumTabulation(triangle));

        // Space-optimized approach
        System.out.println("Space Optimized: " + minPathSumSpaceOptimized(triangle));
    }
}
