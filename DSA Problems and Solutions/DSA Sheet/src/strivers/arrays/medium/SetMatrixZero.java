package com.questions.strivers.arrays.medium;

/**
 * ============================================================================
 * MASTERCLASS: SET MATRIX ZEROES
 * ============================================================================
 * * 1. Header & Problem Context
 * 73. Set Matrix Zeroes
 * Solved | Medium | Topics: Array, Hash Table, Matrix | Companies
 * * Hint:
 * Given an m x n integer matrix matrix, if an element is 0, set its entire
 * row and column to 0's.
 * You must do it in place.
 * * Example 1:
 * Input: matrix = [[1,1,1],
 * [1,0,1],
 * [1,1,1]]
 * Output: [[1,0,1],
 * [0,0,0],
 * [1,0,1]]
 * * Example 2:
 * Input: matrix = [[0,1,2,0],
 * [3,4,5,2],
 * [1,3,1,5]]
 * Output: [[0,0,0,0],
 * [0,4,5,0],
 * [0,3,1,0]]
 * * Constraints:
 * m == matrix.length
 * n == matrix[0].length
 * 1 <= m, n <= 200
 * -2^31 <= matrix[i][j] <= 2^31 - 1
 * * Follow up:
 * - A straightforward solution using O(mn) space is probably a bad idea.
 * - A simple improvement uses O(m + n) space, but still not the best solution.
 * - Could you devise a constant space solution?
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION (In-Place Marker Strategy)
 * ============================================================================
 * Since this is a Matrix/Array problem, a DP Recursion Tree does not apply.
 * Instead, here is a visualization of the Optimal O(1) Space Approach.
 * * We use the FIRST ROW and FIRST COLUMN of the matrix itself to store the
 * "flags" for whether a particular row or column needs to be zeroed.
 * * Initial Matrix:
 * [1, 1, 1, 1]
 * [1, 0, 1, 1]
 * [1, 1, 1, 0]
 * * Step 1: Identify 0s and set markers in the first row/col.
 * - Found 0 at (1, 1) -> Set matrix[1][0] = 0, matrix[0][1] = 0
 * - Found 0 at (2, 3) -> Set matrix[2][0] = 0, matrix[0][3] = 0
 * * Marker State:
 * [1, 0, 1, 0]  <-- First row acts as column flags
 * [0, 0, 1, 1]  <-- matrix[1][0] acts as row flag
 * [0, 1, 1, 0]  <-- matrix[2][0] acts as row flag
 * * Step 2: Iterate through the inner matrix (from 1 to m-1, 1 to n-1).
 * If matrix[i][0] == 0 OR matrix[0][j] == 0, set matrix[i][j] = 0.
 * * Resulting Inner Matrix:
 * [1, 0, 1, 0]
 * [0, 0, 0, 0]
 * [0, 0, 0, 0]
 * * Step 3: Finally, zero out the first row and first column if they originally
 * contained any zeros.
 * ============================================================================
 */

import java.util.Arrays;

public class SetMatrixZero {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE APPROACH (The "Think it" stage)
     * ========================================================================
     * Approach:
     * We create an exact duplicate (copy) of the original matrix. We then iterate
     * through the original matrix. Whenever we encounter a 0, we update the
     * corresponding entire row and column in our duplicate matrix to 0. Finally,
     * we copy the modified duplicate back into the original matrix.
     * * Detailed Intuition:
     * If we modify the matrix while iterating through it, we will encounter the
     * 0s we just placed and wrongly propagate them across the entire board.
     * The simplest way to isolate "original 0s" from "placed 0s" is to use a
     * completely separate O(m*n) matrix.
     * * Complexity Analysis:
     * - Time Complexity: O(m * n * (m + n))
     * For every 0 found in the m x n matrix, we iterate over its row (n elements)
     * and column (m elements).
     * - Space Complexity: O(m * n)
     * Heap space is O(m * n) to hold the copy of the matrix. Auxiliary stack
     * space is O(1).
     */
    public void setZeroesBruteForce(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        // Create a deep copy of the matrix
        int[][] copy = new int[m][n];
        for (int i = 0; i < m; i++) {
            copy[i] = matrix[i].clone();
        }

        // Traverse original, modify copy
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    // Set entire row in copy to 0
                    for (int c = 0; c < n; c++) copy[i][c] = 0;
                    // Set entire col in copy to 0
                    for (int r = 0; r < m; r++) copy[r][j] = 0;
                }
            }
        }

        // Copy back to original matrix to satisfy "in-place"
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = copy[i][j];
            }
        }
    }

    /**
     * ========================================================================
     * PHASE 2: O(m + n) SPACE APPROACH (Alternative Approach)
     * ========================================================================
     * Approach:
     * We use two boolean arrays: one for the rows (size m) and one for the
     * columns (size n). We do a first pass over the matrix. If matrix[i][j] == 0,
     * we mark row[i] = true and col[j] = true. We do a second pass, and if
     * either row[i] or col[j] is true, we set matrix[i][j] = 0.
     * * Detailed Intuition:
     * Copying the entire matrix is overkill. All we really need to know is
     * WHICH rows and WHICH columns are supposed to be zeroed out. By keeping
     * track of these independently in two 1D arrays, we prevent the "domino effect"
     * of processing newly added zeros, dropping space from O(m*n) to O(m+n).
     * * Complexity Analysis:
     * - Time Complexity: O(m * n)
     * Two distinct passes over the matrix.
     * - Space Complexity: O(m + n)
     * Heap space is O(m) for the row array and O(n) for the col array.
     * Auxiliary stack space is O(1).
     */
    public void setZeroesBetter(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        boolean[] zeroRows = new boolean[m];
        boolean[] zeroCols = new boolean[n];

        // Pass 1: Mark rows and columns that should be zeroed
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    zeroRows[i] = true;
                    zeroCols[j] = true;
                }
            }
        }

        // Pass 2: Apply the zeroes based on the markers
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (zeroRows[i] || zeroCols[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    /**
     * ========================================================================
     * PHASE 3: OPTIMAL CONSTANT SPACE APPROACH (The "Perfect it" stage)
     * ========================================================================
     * Approach:
     * We use the matrix's own first row and first column as our "marker arrays"
     * from Phase 2. Since the first element matrix[0][0] overlaps for both the
     * row and column markers, we use an extra boolean variable `firstColZero`
     * to explicitly track if the first column needs to be zeroed.
     * * Detailed Intuition:
     * We already have an O(m*n) grid allocated in memory. Instead of bringing in
     * external arrays, we can hijack the borders of the grid to store our states.
     * We just need to check the borders FIRST to see if they naturally contained
     * zeros, then use them as scratchpads, process the inner grid, and finally
     * apply the border states at the very end.
     * * Complexity Analysis:
     * - Time Complexity: O(m * n)
     * Two passes over the matrix. Strictly linear with respect to the number of cells.
     * - Space Complexity: O(1)
     * We use exactly two boolean variables regardless of matrix size.
     * Heap space is O(1) and auxiliary stack space is O(1).
     */
    public void setZeroesOptimal(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        boolean firstRowZero = false;
        boolean firstColZero = false;

        // Step 1: Determine if the first row or first col originally had zeros
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) firstColZero = true;
        }
        for (int j = 0; j < n; j++) {
            if (matrix[0][j] == 0) firstRowZero = true;
        }

        // Step 2: Use the first row and col as markers for the inner matrix
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0; // Mark the row
                    matrix[0][j] = 0; // Mark the col
                }
            }
        }

        // Step 3: Zero out the inner matrix based on the markers
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        // Step 4: Zero out the first row and col if necessary
        if (firstColZero) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
        if (firstRowZero) {
            for (int j = 0; j < n; j++) {
                matrix[0][j] = 0;
            }
        }
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     * Helper method to deep copy a 2D array, ensuring our in-place modifications
     * in one phase do not poison the input for the next phase.
     */
    private static int[][] deepCopy(int[][] matrix) {
        int[][] copy = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            copy[i] = matrix[i].clone();
        }
        return copy;
    }

    public static void main(String[] args) {
        SetMatrixZero solution = new SetMatrixZero();

        // Define Test Cases
        int[][][] testCases = {
                // Example 1
                {{1, 1, 1},
                        {1, 0, 1},
                        {1, 1, 1}},

                // Example 2
                {{0, 1, 2, 0},
                        {3, 4, 5, 2},
                        {1, 3, 1, 5}},

                // Edge Case: No zeroes
                {{1, 2},
                        {3, 4}},

                // Edge Case: All zeroes
                {{0, 0},
                        {0, 0}},

                // Edge Case: Zero in the top-left corner (Tests overlap logic)
                {{0, 1, 2},
                        {3, 4, 5},
                        {6, 7, 8}}
        };

        System.out.println("=========================================================");
        System.out.println("Executing Set Matrix Zeroes Testing Suite");
        System.out.println("=========================================================\n");

        for (int i = 0; i < testCases.length; i++) {
            System.out.println("Test Case " + (i + 1) + ":");
            int[][] original = testCases[i];

            // Print original matrix
            for (int[] row : original) System.out.println("  " + Arrays.toString(row));
            System.out.println();

            // Create isolated copies for each approach
            int[][] mat1 = deepCopy(original);
            int[][] mat2 = deepCopy(original);
            int[][] mat3 = deepCopy(original);

            // 1. Brute Force
            long start1 = System.nanoTime();
            solution.setZeroesBruteForce(mat1);
            long end1 = System.nanoTime();

            // 2. Better O(m+n) Space
            long start2 = System.nanoTime();
            solution.setZeroesBetter(mat2);
            long end2 = System.nanoTime();

            // 3. Optimal O(1) Space
            long start3 = System.nanoTime();
            solution.setZeroesOptimal(mat3);
            long end3 = System.nanoTime();

            // Verification (Check if all matrices ended up identical)
            boolean isValid = Arrays.deepEquals(mat1, mat2) && Arrays.deepEquals(mat2, mat3);

            System.out.println("  [Brute Force] Time: " + (end1 - start1) + " ns");
            System.out.println("  [O(m+n) Appr] Time: " + (end2 - start2) + " ns");
            System.out.println("  [O(1) Optiml] Time: " + (end3 - start3) + " ns");
            System.out.println("  [Verification] Matching results: " + (isValid ? "PASS" : "FAIL"));

            // Print resulting matrix from optimal solution
            System.out.println("  Resulting Matrix (Optimal):");
            for (int[] row : mat3) System.out.println("    " + Arrays.toString(row));
            System.out.println("---------------------------------------------------------");
        }
    }
}