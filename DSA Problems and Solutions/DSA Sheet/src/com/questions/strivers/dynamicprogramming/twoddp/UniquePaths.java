package com.questions.strivers.dynamicprogramming.twoddp;

import java.util.Arrays;

/**
 * ==================================================================================================
 * PROBLEM: UNIQUE PATHS (LeetCode 62)
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * There is a robot on an m x n grid. The robot is initially located at the top-left corner (grid[0][0]).
 * The robot tries to move to the bottom-right corner (grid[m - 1][n - 1]).
 * The robot can only move either DOWN or RIGHT at any point in time.
 *
 * Constraints:
 * 1 <= m, n <= 100
 *
 * Given the two integers m and n, return the number of possible unique paths that the robot can take.
 *
 * EXAMPLE:
 * Input: m = 3, n = 7
 * Output: 28
 *
 * Input: m = 3, n = 2
 * Output: 3
 * Explanation: From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
 * 1. Right -> Down -> Down
 * 2. Down -> Down -> Right
 * 3. Down -> Right -> Down
 *
 * APPROACH SUMMARY:
 * 1. Recursion: Explore all paths (Top-Down). High complexity.
 * 2. Memoization: Cache recursive results (Top-Down DP).
 * 3. Tabulation: Build table from (0,0) to (m,n) (Bottom-Up DP).
 * 4. Space Optimization: Reduce 2D table to 1D array.
 * 5. Combinatorics: Mathematical formula (Best Time/Space).
 * ==================================================================================================
 */
public class UniquePaths {

    public static void main(String[] args) {
        int m = 3, n = 7;

        System.out.println("Grid Size: " + m + "x" + n);
        System.out.println("--------------------------------------------------");

        // 1. Recursive Approach
        System.out.println("1. Recursion       : " + uniquePathsRecursive(m - 1, n - 1));

        // 2. Memoization Approach
        int[][] dp = new int[m][n];
        for (int[] row : dp) Arrays.fill(row, -1);
        System.out.println("2. Memoization     : " + uniquePathsMemoization(m - 1, n - 1, dp));

        // 3. Tabulation Approach
        System.out.println("3. Tabulation      : " + uniquePathsTabulation(m, n));

        // 4. Space Optimized Approach
        System.out.println("4. Space Optimized : " + uniquePathsSpaceOptimized(m, n));

        // 5. Combinatorics (Math) Approach
        System.out.println("5. Combinatorics   : " + uniquePathsMath(m, n));
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ----------------------------------------------------------------------
     * LOGIC:
     * We start from the Target (m-1, n-1) and try to reach the Start (0,0).
     * At any cell (i, j), we could have arrived from:
     * 1. Top (i-1, j)
     * 2. Left (i, j-1)
     * Total ways(i, j) = ways(i-1, j) + ways(i, j-1).
     *
     * COMPLEXITY:
     * - Time: O(2^(M+N)) -> Exponential. We re-calculate the same subproblems many times.
     * - Space: O(M+N) -> Recursion stack depth is the path length.
     */
    private static int uniquePathsRecursive(int row, int col) {
        // Base Case: Reached the Start (0,0) -> Found 1 valid path
        if (row == 0 && col == 0) {
            return 1;
        }

        // Base Case: Out of bounds (negative index) -> 0 paths
        if (row < 0 || col < 0) {
            return 0;
        }

        // Recursive Calls
        int fromTop = uniquePathsRecursive(row - 1, col);  // Path coming from above
        int fromLeft = uniquePathsRecursive(row, col - 1); // Path coming from the left

        return fromTop + fromLeft;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * Same as recursion, but we store the result of each state (row, col) in a DP table.
     * If we encounter the same state again, we return the cached value.
     *
     * COMPLEXITY:
     * - Time: O(M * N) -> Each cell is computed exactly once.
     * - Space: O(M * N) + O(M + N) -> DP Array + Stack.
     */
    private static int uniquePathsMemoization(int row, int col, int[][] dp) {
        if (row == 0 && col == 0) return 1;
        if (row < 0 || col < 0) return 0;

        // Step 1: Check Cache
        if (dp[row][col] != -1) {
            return dp[row][col];
        }

        // Step 2: Compute
        int fromTop = uniquePathsMemoization(row - 1, col, dp);
        int fromLeft = uniquePathsMemoization(row, col - 1, dp);

        // Step 3: Store and Return
        return dp[row][col] = fromTop + fromLeft;
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ----------------------------------------------------------------------
     * LOGIC:
     * We build the solution from the Start (0,0) to Target (m-1, n-1).
     * dp[i][j] represents the number of unique paths to reach cell (i, j).
     *
     * Base Cases:
     * - First Row: All cells are 1 (can only come from Left).
     * - First Col: All cells are 1 (can only come from Top).
     *
     * COMPLEXITY:
     * - Time: O(M * N) -> Iterate through the grid.
     * - Space: O(M * N) -> DP Array.
     */
    private static int uniquePathsTabulation(int m, int n) {
        int[][] dp = new int[m][n];

        // Fill DP Table
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                // Base Case: Start position
                if (i == 0 && j == 0) {
                    dp[i][j] = 1;
                    continue;
                }

                int fromTop = 0;
                int fromLeft = 0;

                // Move from Top (if valid)
                if (i > 0) fromTop = dp[i - 1][j];

                // Move from Left (if valid)
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
     * Notice that to calculate the current row `curr[j]`, we only need:
     * 1. The value directly above it (`prev[j]`).
     * 2. The value directly to the left (`curr[j-1]`).
     *
     * We do not need the entire 2D matrix. We only need the previous row.
     *
     * COMPLEXITY:
     * - Time: O(M * N)
     * - Space: O(N) -> Only storing one row.
     */
    private static int uniquePathsSpaceOptimized(int m, int n) {
        int[] prev = new int[n];

        // Initialize first row (Base Case: all 1s)
        Arrays.fill(prev, 1);

        // Iterate through rows starting from 1
        for (int i = 1; i < m; i++) {
            int[] curr = new int[n];
            // First column is always 1 (can only come from top)
            Arrays.fill(curr, 1);

            for (int j = 1; j < n; j++) {
                // Current cell = (Cell Above) + (Cell Left)
                // Cell Above is prev[j]
                // Cell Left is curr[j-1]
                curr[j] = prev[j] + curr[j - 1];
            }
            // Update prev row to be the current row for the next iteration
            prev = curr;
        }

        return prev[n - 1];
    }

    /**
     * ----------------------------------------------------------------------
     * APPROACH 5: COMBINATORICS (MATH) - OPTIMAL
     * ----------------------------------------------------------------------
     * LOGIC:
     * To reach (m, n) from (1, 1), we MUST take exactly:
     * - m-1 Down moves
     * - n-1 Right moves
     *
     * Total steps = (m-1) + (n-1) = m + n - 2.
     * Out of these total steps, we just need to choose where to place the (m-1) Down moves.
     *
     * Formula: Combinations C(total_steps, down_moves)
     * Answer = (m+n-2)! / ((m-1)! * (n-1)!)
     *
     * COMPLEXITY:
     * - Time: O(m-1) or O(n-1) -> Linear time to calculate combinations.
     * - Space: O(1) -> No extra space.
     */
    private static int uniquePathsMath(int m, int n) {
        int N = n + m - 2; // Total steps
        int R = m - 1;     // Number of Down moves (or could use n-1 for Right moves)

        double res = 1;

        // Calculate nCr efficiently:
        // nCr = (n * (n-1) * ... * (n-r+1)) / (1 * 2 * ... * r)
        for (int i = 1; i <= R; i++) {
            res = res * (N - R + i) / i;
        }

        return (int) res;
    }
}