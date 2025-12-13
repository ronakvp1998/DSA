package com.questions.practice.dynamicprogramming;

import java.util.Arrays;

public class DPOnGrid {
    public static void main(String[] args) {
        int m = 3, n = 3;

        // Recursive approach
        System.out.println("Recursive: " + uniquePathsRecursive(m - 1, n - 1));

        // Memoization approach
//        int[][] dp = new int[m][n];
//        for (int[] row : dp) {
//            Arrays.fill(row, -1); // Initialize the dp array with -1
//        }
//        System.out.println("Memoization: " + uniquePathsMemoization(m - 1, n - 1, dp));
//
//        // Tabulation approach
//        System.out.println("Tabulation: " + uniquePathsTabulation(m, n));
//
//        // Space-optimized approach
//        System.out.println("Space Optimized: " + uniquePathsSpaceOptimized(m, n));
    }

    private static int uniquePathsRecursive(int m, int n){
        if(m == 0 && n == 0){
            return 1;
        }
        if(m < 0 || n < 0) {
            return 0;
        }
            int right =  uniquePathsRecursive(m, n - 1);
            int bottom = uniquePathsRecursive(m - 1, n);
            return right + bottom;
    }
}
