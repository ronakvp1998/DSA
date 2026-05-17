package com.questions.strivers.dynamicprogramming.twoddp;

/**
 * ==================================================================================================
 * PROBLEM: MAXIMUM FALLING PATH SUM
 * ==================================================================================================
 * PROBLEM STATEMENT:
 * Given an n x m array of integers 'matrix', return the MAXIMUM sum of any falling path through matrix.
 *
 * A falling path starts at any element in the first row and chooses the element in the next row
 * that is either directly below or diagonally left/right.
 *
 * Specifically, the next element from position (row, col) will be:
 * - (row + 1, col - 1)
 * - (row + 1, col)
 * - (row + 1, col + 1)
 *
 * EXAMPLE:
 * Input: matrix = [[10, 2, 3],
 * [ 3, 7, 2],
 * [ 8, 1, 5]]
 * Output: 25
 * Explanation: The maximum falling path is (10 -> 7 -> 8) = 25.
 *
 * APPROACH STRATEGY:
 * Since we can end at ANY cell in the last row, the overall answer is the maximum of:
 * Max( f(n-1, 0), f(n-1, 1), ..., f(n-1, m-1) )
 * We define our function f(i, j) as:
 * "The maximum path sum to REACH cell (i, j) from the top row."
 * ==================================================================================================
 *
 *                                  f(2, 1) -> Val: 1
 *                              /     |     \
 *                  (Up-Left) /       |       \ (Up-Right)
 *                          /      (Up)         \
 *                  f(1, 0)        f(1, 1)        f(1, 2)
 *                  /  |  \        /  |  \        /  |  \
 *              ...  ...  ...    ... ... ...    ... ... ...
 *                     ^              ^
 *                     |              |
 *            Ask for f(0,0)    Ask for f(0,0)  <-- 🚨 OVERLAP ALERT!
 *
 * Input Matrix:
 *  [10, 2, 3]
 *  [ 3, 7, 2]
 *  [ 8, 1, 5]
 *
 * | Row        | Col 0                 | Col 1                 | Col 2                 |
 * | :---       | :---                  | :---                  | :---                  |
 * | 0          | 10                    | 2                     | 3                     |
 * | 1          | 13 (3 + max(10,2))    | 17 (7 + max(10,2,3))  | 5 (2 + max(2,3))      |
 * | 2          | 25 (8 + max(13,17))   | 18 (1 + max(13,17,5)) | 22 (5 + max(17,5))    |
 */
public class MaxFallingPathSum {

    public static void main(String[] args) {
        int[][] matrix = {
                {10, 2, 3},
                {3, 7, 2},
                {8, 1, 5}
        };

        int n = matrix.length;
        int m = matrix[0].length;

        System.out.println("Matrix Size: " + n + "x" + m);
        System.out.println("--------------------------------------------------");

        // 1. Recursive Approach Testing
        int maxRecursive = Integer.MIN_VALUE;
        // We must attempt to end our path at every single column in the last row
        for (int j = 0; j < m; j++) {
            maxRecursive = Math.max(maxRecursive, solveRecursive(n - 1, j, matrix));
        }
        System.out.println("1. Recursion       : " + maxRecursive);

        // 2. Memoization Approach Testing
        System.out.println("2. Memoization     : " + solveMemoizationWrapper(matrix));

        // 3. Tabulation Approach Testing
        System.out.println("3. Tabulation      : " + solveTabulation(matrix));

        // 4. Space Optimized Approach Testing
        System.out.println("4. Space Optimized : " + solveSpaceOptimized(matrix));
    }

    /**
     * ======================================================================
     * APPROACH 1: RECURSION (BRUTE FORCE)
     * ======================================================================
     * Logic: From a cell in the current row, recursively check the 3 cells above it.
     * * Time Complexity: O(3^N) - At every step, we branch into 3 paths. Exponential.
     * Space Complexity: O(N) - Maximum depth of the recursion call stack is N rows.
     */
    private static int solveRecursive(int i, int j, int[][] matrix) {
        // EDGE CASE: Column is out of bounds
        // We return a massive NEGATIVE number (-1 billion) instead of Integer.MIN_VALUE.
        // Why? If we returned Integer.MIN_VALUE, adding matrix[i][j] to it later would
        // cause an integer underflow, wrapping around to a huge positive number!
        if (j < 0 || j >= matrix[0].length) {
            return (int) -1e9;
        }

        // BASE CASE: We reached the top row (Row 0)
        // The cost to start at this cell is just the value of the cell itself.
        if (i == 0) {
            return matrix[0][j];
        }

        // EXPLORE: Recursively ask the 3 parent cells for their max paths
        int up = solveRecursive(i - 1, j, matrix);
        int upLeft = solveRecursive(i - 1, j - 1, matrix);
        int upRight = solveRecursive(i - 1, j + 1, matrix);

        // RETURN: The value of the current cell + the maximum of the 3 paths above it
        return matrix[i][j] + Math.max(up, Math.max(upLeft, upRight));
    }

    /**
     * ======================================================================
     * APPROACH 2: MEMOIZATION (TOP-DOWN DP)
     * ======================================================================
     * Logic: Identical to recursion, but we save calculated states in a 2D array `dp`.
     * * Time Complexity: O(N * M) - We calculate each unique cell exactly once.
     * Space Complexity: O(N * M) for the 2D array + O(N) for the recursion stack.
     */
    private static int solveMemoizationWrapper(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        // Use the Integer object wrapper. This initializes the array with 'null'.
        // This is much safer than initializing an int[][] with -1, because -1 could
        // be a valid maximum path sum if the matrix contained negative numbers!
        Integer[][] dp = new Integer[n][m];

        int maxPath = Integer.MIN_VALUE;

        // Iterate through the last row to find which column yields the absolute max sum
        for (int j = 0; j < m; j++) {
            maxPath = Math.max(maxPath, memoization(n - 1, j, matrix, dp));
        }
        return maxPath;
    }

    private static int memoization(int i, int j, int[][] matrix, Integer[][] dp) {
        // Out of bounds check
        if (j < 0 || j >= matrix[0].length) return (int) -1e9;

        // Base case: top row
        if (i == 0) return matrix[0][j];

        // MEMOIZATION CHECK: If not null, we've solved this cell before! Return it instantly.
        if (dp[i][j] != null) return dp[i][j];

        // Explore the 3 paths upwards
        int up = memoization(i - 1, j, matrix, dp);
        int upLeft = memoization(i - 1, j - 1, matrix, dp);
        int upRight = memoization(i - 1, j + 1, matrix, dp);

        // Cache the result in the dp array before returning it
        return dp[i][j] = matrix[i][j] + Math.max(up, Math.max(upLeft, upRight));
    }

    /**
     * ======================================================================
     * APPROACH 3: TABULATION (BOTTOM-UP DP)
     * ======================================================================
     * Logic: Build the solution iteratively from row 0 down to row N-1.
     * * Time Complexity: O(N * M) - We touch every cell once.
     * Space Complexity: O(N * M) - For the 2D DP array. No recursion stack overhead.
     */
    private static int solveTabulation(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] dp = new int[n][m];

        // 1. Initialize Base Case: Copy the first row exactly as it is
        for (int j = 0; j < m; j++) {
            dp[0][j] = matrix[0][j];
        }

        // 2. Iterate from Row 1 down to the last row
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < m; j++) {

                // Path straight up is always valid
                int up = dp[i - 1][j];

                // Path up-left is invalid if we are in the first column (j == 0)
                int upLeft = (j > 0) ? dp[i - 1][j - 1] : (int) -1e9;

                // Path up-right is invalid if we are in the last column (j == m-1)
                int upRight = (j < m - 1) ? dp[i - 1][j + 1] : (int) -1e9;

                // Set current cell max path
                dp[i][j] = matrix[i][j] + Math.max(up, Math.max(upLeft, upRight));
            }
        }

        // 3. The final answer is the maximum value residing in the very last row
        int maxPath = dp[n - 1][0];
        for (int j = 1; j < m; j++) {
            maxPath = Math.max(maxPath, dp[n - 1][j]);
        }
        return maxPath;
    }

    /**
     * ======================================================================
     * APPROACH 4: SPACE OPTIMIZED DP (BEST SOLUTION)
     * ======================================================================
     * Logic: Notice in Tabulation, `dp[i]` only ever looks at `dp[i-1]`.
     * We don't need a full NxM matrix. We just need to track the "previous" row!
     * * Time Complexity: O(N * M) - Still touch every cell.
     * Space Complexity: O(M) - We only allocate a 1D array of size M!
     */
    private static int solveSpaceOptimized(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        // This 1D array represents the calculations for the row ABOVE our current position
        int[] prev = new int[m];

        // Initialize 'prev' with the first row of the matrix
        for (int j = 0; j < m; j++) {
            prev[j] = matrix[0][j];
        }

        // Iterate through remaining rows
        for (int i = 1; i < n; i++) {
            // This 1D array represents the row we are CURRENTLY calculating
            int[] curr = new int[m];

            for (int j = 0; j < m; j++) {

                int up = prev[j];
                int upLeft = (j > 0) ? prev[j - 1] : (int) -1e9;
                int upRight = (j < m - 1) ? prev[j + 1] : (int) -1e9;

                curr[j] = matrix[i][j] + Math.max(up, Math.max(upLeft, upRight));
            }

            // Critical step: the current row we just finished becomes the 'previous'
            // row for the next iteration of the loop!
            prev = curr;
        }

        // Find the absolute maximum path sum located in the final 'prev' array
        int maxPath = prev[0];
        for (int j = 1; j < m; j++) {
            maxPath = Math.max(maxPath, prev[j]);
        }
        return maxPath;
    }
}