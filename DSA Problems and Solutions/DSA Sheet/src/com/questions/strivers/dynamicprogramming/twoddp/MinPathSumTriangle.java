package com.questions.strivers.dynamicprogramming.twoddp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ==================================================================================================
 * PROBLEM: TRIANGLE (LeetCode 120)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given a triangle array, return the minimum path sum from top to bottom.
 * For each step, you may move to an adjacent number of the row below.
 * More formally, if you are on index 'i' on the current row, you may move to either:
 * - index 'i' (directly below-left)
 * - index 'i + 1' (directly below-right)
 * on the next row.
 *
 *
 * EXAMPLE:
 * Input: triangle = [[2], [3,4], [6,5,7], [4,1,8,3]]
 * 2
 * 3 4
 * 6 5 7
 * 4 1 8 3
 *
 * Output: 11
 * Explanation: The minimum path sum from top to bottom is 2 + 3 + 5 + 1 = 11.
 *
 * KEY INSIGHT:
 * In a Triangle, every cell (row, col) has two children in the next row:
 * 1. (row + 1, col)
 * 2. (row + 1, col + 1)
 *
 * It is best to solve this BOTTOM-UP (start from the last row and move up).
 * Why?
 * - If we start from the top, we need to check boundary conditions for every move.
 * - If we start from the bottom, every cell is guaranteed to have two children below it.
 * ==================================================================================================
 */
public class MinPathSumTriangle {

    public static void main(String[] args) {
        // Constructing the example input: [[2], [3,4], [6,5,7], [4,1,8,3]]
        List<List<Integer>> triangle = new ArrayList<>();
        triangle.add(Arrays.asList(2));
        triangle.add(Arrays.asList(3, 4));
        triangle.add(Arrays.asList(6, 5, 7));
        triangle.add(Arrays.asList(4, 1, 8, 3));

        MinPathSumTriangle solver = new MinPathSumTriangle();

        System.out.println("Triangle Height: " + triangle.size());
        System.out.println("--------------------------------------------------");

        // 1. Recursive Approach
        System.out.println("1. Recursion       : " + solver.solveRecursive(triangle));

        // 2. Memoization Approach
        System.out.println("2. Memoization     : " + solver.solveMemoization(triangle));

        // 3. Tabulation Approach
        System.out.println("3. Tabulation      : " + solver.solveTabulation(triangle));

        // 4. Space Optimized Approach (The Main Method)
        System.out.println("4. Space Optimized : " + solver.minimumTotal(triangle));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Start at (0,0). Recursively find min path for (r+1, c) and (r+1, c+1).
     *
     * COMPLEXITY:
     * - Time: O(2^N) -> Exponential.
     * - Space: O(N) -> Recursion stack.
     */
    public int solveRecursive(List<List<Integer>> triangle) {
        return recursion(0, 0, triangle);
    }

    private int recursion(int row, int col, List<List<Integer>> triangle) {
        // Base Case: Reached the last row
        if (row == triangle.size() - 1) {
            return triangle.get(row).get(col);
        }

        // Recursive Calls
        int down = recursion(row + 1, col, triangle);
        int diagonal = recursion(row + 1, col + 1, triangle);

        return triangle.get(row).get(col) + Math.min(down, diagonal);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Cache results of recursion in a 2D array `dp`.
     * * * * BUG FIX:
     * Used Integer[][] instead of int[][] to handle negative path sums correctly.
     * Checking for 'null' is safer than checking for '-1' because a valid path sum
     * could theoretically be -1.
     *
     * COMPLEXITY:
     * - Time: O(N^2)
     * - Space: O(N^2)
     */
    public int solveMemoization(List<List<Integer>> triangle) {
        int n = triangle.size();
        Integer[][] dp = new Integer[n][n]; // Used Integer for null check

        return memoization(0, 0, triangle, dp);
    }

    private int memoization(int row, int col, List<List<Integer>> triangle, Integer[][] dp) {
        if (row == triangle.size() - 1) {
            return triangle.get(row).get(col);
        }

        // Safe null check (handles negative values correctly)
        if (dp[row][col] != null) return dp[row][col];

        int down = memoization(row + 1, col, triangle, dp);
        int diagonal = memoization(row + 1, col + 1, triangle, dp);

        return dp[row][col] = triangle.get(row).get(col) + Math.min(down, diagonal);
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Fill a 2D table starting from the last row up to the first.
     * No recursion overhead.
     *
     *
     * COMPLEXITY:
     * - Time: O(N^2)
     * - Space: O(N^2)
     */
    public int solveTabulation(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] dp = new int[n][n];

        // 1. Fill Last Row
        for (int j = 0; j < n; j++) {
            dp[n - 1][j] = triangle.get(n - 1).get(j);
        }

        // 2. Iterate Upwards
        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                int down = dp[i + 1][j];
                int diagonal = dp[i + 1][j + 1];

                dp[i][j] = triangle.get(i).get(j) + Math.min(down, diagonal);
            }
        }
        return dp[0][0];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 4: SPACE OPTIMIZED DP (BEST SOLUTION)
     * ----------------------------------------------------------------------
     * LOGIC:
     * We use a single 1D array `front` to store the minimum path sums of the *next* row.
     * We iterate from the second-to-last row upwards.
     *
     * State Transition:
     * front[j] = triangle[i][j] + Math.min(front[j], front[j+1])
     *
     * COMPLEXITY:
     * - Time: O(N^2) -> Iterate through all cells.
     * - Space: O(N) -> Single array of size N (number of rows).
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();

        // Array to store the minimum path sum for the *next* row.
        // Initially, we fill it with the values from the bottom-most row.
        int[] front = new int[n];

        // 1. Initialize with bottom row
        for (int j = 0; j < n; j++) {
            front[j] = triangle.get(n - 1).get(j);
        }

        // 2. Iterate from second-last row UP to the top (row 0)
        for (int i = n - 2; i >= 0; i--) {
            int[] curr = new int[n]; // Temp array for current row calculations

            // For row 'i', there are 'i+1' elements
            for (int j = 0; j <= i; j++) {

                // Calculate the two possible paths from below:
                // 1. Down: front[j]
                // 2. Diagonal: front[j+1]
                int down = front[j];
                int diagonal = front[j + 1];

                // Current cost + min of the two paths below
                curr[j] = triangle.get(i).get(j) + Math.min(down, diagonal);
            }

            // Move current row results to 'front' for the next iteration (moving up)
            front = curr;
        }

        // The answer bubbles up to the tip of the triangle (index 0)
        return front[0];
    }
}