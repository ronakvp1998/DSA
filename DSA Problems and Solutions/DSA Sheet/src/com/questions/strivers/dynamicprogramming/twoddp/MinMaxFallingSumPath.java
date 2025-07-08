package com.questions.strivers.dynamicprogramming.twoddp;
//https://takeuforward.org/data-structure/minimum-maximum-falling-path-sum-dp-12/
public class MinMaxFallingSumPath {
        // ====================== 1. PURE RECURSION ======================
        // Time Complexity: O(3^N), as we explore 3 choices at every level (down, diag-left, diag-right)
        // Space Complexity: O(N), due to recursion stack
        public static int maxPathSumRec(int[][] matrix) {
            int n = matrix.length;
            int m = matrix[0].length;
            int max = Integer.MIN_VALUE;

            // Try all possible starting positions in the first row
            for (int j = 0; j < m; j++) {
                max = Math.max(max, recursive(matrix, 0, j));
            }
            return max;
        }

        // Recursive helper function to calculate max path sum from (i, j)
        private static int recursive(int[][] mat, int i, int j) {
            int n = mat.length, m = mat[0].length;

            // If column index goes out of bounds, we return a very small value
            if (j < 0 || j >= m) return Integer.MIN_VALUE;

            // Base case: if we reached the last row, return the value at this cell
            if (i == n - 1) return mat[i][j];

            // Explore all 3 valid directions
            int down = recursive(mat, i + 1, j);         // ↓
            int diagLeft = recursive(mat, i + 1, j - 1); // ↙
            int diagRight = recursive(mat, i + 1, j + 1);// ↘

            // Return current cell value + maximum of the three paths
            return mat[i][j] + Math.max(down, Math.max(diagLeft, diagRight));
        }

        // ====================== 2. MEMOIZATION (TOP-DOWN DP) ======================
        // Time Complexity: O(N*M)
        // Space Complexity: O(N*M) for DP + O(N) recursion stack
        public static int maxPathSumMemo(int[][] matrix) {
            int n = matrix.length;
            int m = matrix[0].length;

            // Initialize memoization table (dp) with "uncomputed" values
            int[][] dp = new int[n][m];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < m; j++)
                    dp[i][j] = Integer.MIN_VALUE;

            int max = Integer.MIN_VALUE;

            // Try all starting points in the first row
            for (int j = 0; j < m; j++) {
                max = Math.max(max, memo(matrix, 0, j, dp));
            }
            return max;
        }

        // Memoized version of recursive function
        private static int memo(int[][] mat, int i, int j, int[][] dp) {
            int n = mat.length;
            int m = mat[0].length;

            // Out-of-bounds condition
            if (j < 0 || j >= m) return Integer.MIN_VALUE;

            // Base case
            if (i == n - 1) return mat[i][j];

            // If already calculated, return stored value
            if (dp[i][j] != Integer.MIN_VALUE) return dp[i][j];

            // Otherwise compute and store it
            int down = memo(mat, i + 1, j, dp);
            int diagLeft = memo(mat, i + 1, j - 1, dp);
            int diagRight = memo(mat, i + 1, j + 1, dp);

            // Store the result in dp table
            return dp[i][j] = mat[i][j] + Math.max(down, Math.max(diagLeft, diagRight));
        }

        // ====================== 3. TABULATION (BOTTOM-UP DP) ======================
        // Time Complexity: O(N*M)
        // Space Complexity: O(N*M)
        public static int maxPathSumTab(int[][] mat) {
            int n = mat.length;
            int m = mat[0].length;

            // Create a DP table to store solutions to subproblems
            int[][] dp = new int[n][m];

            // Base case: first row values are copied as is
            for (int j = 0; j < m; j++) {
                dp[0][j] = mat[0][j];
            }

            // Fill the DP table row-by-row
            for (int i = 1; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    // Move ↓ from above
                    int down = dp[i - 1][j];

                    // Move ↙ from top-left (if valid)
                    int diagLeft = (j > 0) ? dp[i - 1][j - 1] : Integer.MIN_VALUE;

                    // Move ↘ from top-right (if valid)
                    int diagRight = (j < m - 1) ? dp[i - 1][j + 1] : Integer.MIN_VALUE;

                    // Store current cell result = current matrix value + max of 3 paths
                    dp[i][j] = mat[i][j] + Math.max(down, Math.max(diagLeft, diagRight));
                }
            }

            // Final answer is the max element in the last row
            int max = Integer.MIN_VALUE;
            for (int j = 0; j < m; j++) {
                max = Math.max(max, dp[n - 1][j]);
            }

            return max;
        }

        // ====================== 4. SPACE OPTIMIZED DP ======================
        // Time Complexity: O(N*M)
        // Space Complexity: O(M)
        public static int maxPathSumSpaceOpt(int[][] mat) {
            int n = mat.length;
            int m = mat[0].length;

            // prev[] will store the DP results for the previous row
            int[] prev = new int[m];

            // Base case: initialize with first row
            for (int j = 0; j < m; j++) {
                prev[j] = mat[0][j];
            }

            // Build row by row, only keeping 1 row in memory at a time
            for (int i = 1; i < n; i++) {
                int[] curr = new int[m]; // Current row DP results

                for (int j = 0; j < m; j++) {
                    int down = prev[j]; // From above
                    int diagLeft = (j > 0) ? prev[j - 1] : Integer.MIN_VALUE;
                    int diagRight = (j < m - 1) ? prev[j + 1] : Integer.MIN_VALUE;

                    // Calculate max path sum for current cell
                    curr[j] = mat[i][j] + Math.max(down, Math.max(diagLeft, diagRight));
                }

                // Move to the next row: current becomes previous
                prev = curr;
            }

            // Final result is max in last processed row
            int max = Integer.MIN_VALUE;
            for (int val : prev) {
                max = Math.max(max, val);
            }

            return max;
        }

        // ====================== MAIN METHOD FOR TESTING ======================
        public static void main(String[] args) {
            // Sample matrix (4x6)
            int[][] matrix = {
                    {10, 10, 2, 0, 20, 4},
                    {1, 0, 0, 30, 2, 5},
                    {0, 10, 4, 0, 2, 0},
                    {1, 0, 2, 20, 0, 4}
            };

            // Test all four approaches
            System.out.println("Recursion:        " + maxPathSumRec(matrix));
            System.out.println("Memoization:      " + maxPathSumMemo(matrix));
            System.out.println("Tabulation:       " + maxPathSumTab(matrix));
            System.out.println("Space Optimized:  " + maxPathSumSpaceOpt(matrix));
        }

}

