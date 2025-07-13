package com.questions.practice.dynamicprogramming;

import java.util.Arrays;

public class DpOnGridK {
    public static void main(String[] args) {
        int[][] grid = {
                {0, 0, 0}, // 0 represents an open cell
                {0, 1, 0}, // 1 represents a blocked cell
                {0, 0, 0}  // Bottom-right cell is the destination
        };

        // Recursive approach
        System.out.println("Recursive: " + uniquePathsRecursive(grid.length - 1, grid[0].length - 1, grid));

//        // Memoization approach
//        int[][] dp = new int[grid.length][grid[0].length];
//        for (int[] row : dp) {
//            Arrays.fill(row, -1); // Initialize dp array with -1
//        }
//        System.out.println("Memoization: " + uniquePathsMemoization(grid.length - 1, grid[0].length - 1, grid, dp));
//
//        // Tabulation approach
//        System.out.println("Tabulation: " + uniquePathsTabulation(grid));
//
//        // Space-optimized approach
//        System.out.println("Space Optimized: " + uniquePathsSpaceOptimized(grid));
    }

    public static int uniquePathsRecursive(int m, int n, int [][]grid){
        if(m == 0 && n == 0){
            return 1;
        }
        if(m < 0 || n < 0){
            return 0;
        }
        if(grid[m][n] == 1){
            return 0;
        }
        int left = uniquePathsRecursive(m,n-1,grid);
        int right = uniquePathsRecursive(m-1,n,grid);
        return left + right;
    }
}
