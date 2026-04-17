package com.questions.strivers.arrays.medium;
/**
 * ============================================================================
 * MASTERCLASS: ROTATE IMAGE
 * ============================================================================
 * * ### 1. Header & Problem Context
 * * **48. Rotate Image**
 * Solved | Medium | Topics: Array, Math, Matrix | Companies
 * * **Problem Statement:**
 * You are given an n x n 2D matrix representing an image, rotate the image by
 * 90 degrees (clockwise).
 * * You have to rotate the image in-place, which means you have to modify the
 * input 2D matrix directly. DO NOT allocate another 2D matrix and do the rotation.
 * * **Example 1:**
 * Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * Output: [[7,4,1],[8,5,2],[9,6,3]]
 * * **Example 2:**
 * Input: matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
 * Output: [[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]
 * * **Constraints:**
 * - n == matrix.length == matrix[i].length
 * - 1 <= n <= 20
 * - -1000 <= matrix[i][j] <= 1000
 * * ---
 * * ### Conceptual Visualization (Transpose + Reverse Strategy)
 * * Since this is not a DP problem, we visualize the matrix transformations to
 * achieve a 90-degree clockwise rotation in-place.
 * * The most elegant mathematical approach involves two simple reflections:
 * 1. Transpose the matrix (swap matrix[i][j] with matrix[j][i]).
 * 2. Reverse every individual row (swap matrix[i][j] with matrix[i][n-1-j]).
 * * **Step 0: Original Matrix**
 * [ 1, 2, 3 ]
 * [ 4, 5, 6 ]
 * [ 7, 8, 9 ]
 * * **Step 1: Transpose (Reflect across main diagonal \ )**
 * [ 1, 4, 7 ]
 * [ 2, 5, 8 ]
 * [ 3, 6, 9 ]
 * * **Step 2: Reverse Rows (Reflect across center vertical axis | )**
 * [ 7, 4, 1 ]
 * [ 8, 5, 2 ]
 * [ 9, 6, 3 ]  <-- Final 90-degree rotated matrix!
 * * ============================================================================
 */

import java.util.Arrays;

public class RotateMatrix90 {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE APPROACH (The "Think it" stage)
     * ========================================================================
     * * **Approach:**
     * Allocate a completely new n x n matrix. We map each element from the
     * original matrix to its new 90-degree rotated position. The formula for a
     * 90-degree clockwise rotation maps `matrix[i][j]` to `new_matrix[j][n-1-i]`.
     * Finally, copy the elements back to the original matrix.
     * * **Detailed Intuition:**
     * In an interview, even if the problem strictly forbids extra space, it is
     * critical to first establish that you understand the mathematical mapping
     * of the coordinates. Formulating `[j][n-1-i]` proves you understand the
     * rotation logic before tackling the constraints of in-place swapping.
     * * **Complexity Analysis:**
     * - Time Complexity: O(N^2)
     * We iterate through every cell of the n x n matrix twice (once to place
     * in the new matrix, once to copy back).
     * - Space Complexity: O(N^2)
     * Explicitly allocating a new `temp` 2D array of size n x n in Heap space.
     * Auxiliary stack space is O(1).
     * *Note: This violates the strict "DO NOT allocate" constraint, leading us to Phase 2.*
     */
    public void rotateBruteForce(int[][] matrix) {
        int n = matrix.length;
        int[][] temp = new int[n][n];

        // Map elements to their rotated positions in a new matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[j][n - 1 - i] = matrix[i][j];
            }
        }

        // Copy back to original matrix to simulate the result
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = temp[i][j];
            }
        }
    }

    /**
     * ========================================================================
     * PHASE 2: OPTIMAL APPROACH - TRANSPOSE & REVERSE (The "Perfect it" stage)
     * ========================================================================
     * * **Approach:**
     * We divide the rotation into two independent O(1) space operations:
     * 1. **Transpose:** Iterate through the upper triangle of the matrix
     * (where j >= i) and swap `matrix[i][j]` with `matrix[j][i]`.
     * 2. **Reverse:** Iterate through each row and swap elements symmetrically
     * from the outside in (using a two-pointer approach per row).
     * * **Detailed Intuition:**
     * To avoid allocating a new matrix or doing complex 4-way pointer tracking
     * (which is highly prone to off-by-one errors), we use linear algebra.
     * A 90-degree clockwise rotation is mathematically identical to a transposition
     * followed by a horizontal reflection. Both operations are simple isolated
     * 2-element swaps, making the code incredibly clean, bug-resistant, and
     * perfectly in-place.
     * * **Complexity Analysis:**
     * - Time Complexity: O(N^2)
     * Transposing the matrix takes (N^2 / 2) operations. Reversing the rows
     * takes (N^2 / 2) operations. Total time scales linearly with the number
     * of cells in the matrix.
     * - Space Complexity: O(1)
     * We strictly use constant extra space (a single `temp` integer) for swapping.
     * Heap space is O(1) and Auxiliary Stack space is O(1).
     */
    public void rotateOptimal(int[][] matrix) {
        int n = matrix.length;

        // Step 1: Transpose Matrix
        for (int i = 0; i < n; i++) {
            // j starts at i to only process the upper triangle (avoid double swapping)
            for (int j = i; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // Step 2: Reverse each row
        for (int i = 0; i < n; i++) {
            // Only iterate to the midpoint of the row
            for (int j = 0; j < n / 2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = temp;
            }
        }
    }

    /**
     * ========================================================================
     * PHASE 3: ALTERNATIVE APPROACH - FOUR-WAY SWAP (Layer by Layer)
     * ========================================================================
     * * **Approach:**
     * Imagine the matrix as a series of concentric rings or layers. We iterate
     * from the outermost layer to the inner layers. For each element in the top
     * row of a layer, we perform a 4-way cyclical swap to move it, the right
     * element, the bottom element, and the left element directly into their
     * new rotated positions.
     * * **Detailed Intuition:**
     * This achieves the rotation in a single pass over the matrix rather than two.
     * We grab the top-left, move bottom-left to top-left, bottom-right to bottom-left,
     * top-right to bottom-right, and finally place the original top-left into
     * the top-right. We shrink our boundaries inward after completing a layer.
     * * **Complexity Analysis:**
     * - Time Complexity: O(N^2)
     * We visit each cell exactly once in the 4-way swap.
     * - Space Complexity: O(1)
     * Everything is done entirely in-place.
     */
    public void rotateFourWaySwap(int[][] matrix) {
        int n = matrix.length;
        int left = 0;
        int right = n - 1;

        while (left < right) {
            for (int i = 0; i < right - left; i++) {
                int top = left;
                int bottom = right;

                // Save the top-left element
                int topLeft = matrix[top][left + i];

                // Move bottom-left into top-left
                matrix[top][left + i] = matrix[bottom - i][left];

                // Move bottom-right into bottom-left
                matrix[bottom - i][left] = matrix[bottom][right - i];

                // Move top-right into bottom-right
                matrix[bottom][right - i] = matrix[top + i][right];

                // Move saved top-left into top-right
                matrix[top + i][right] = topLeft;
            }
            left++;
            right--;
        }
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        RotateMatrix90 solution = new RotateMatrix90();

        // Define Test Cases
        int[][][] testCases = {
                // Example 1: 3x3 Matrix
                {
                        {1, 2, 3},
                        {4, 5, 6},
                        {7, 8, 9}
                },
                // Example 2: 4x4 Matrix
                {
                        {5, 1, 9, 11},
                        {2, 4, 8, 10},
                        {13, 3, 6, 7},
                        {15, 14, 12, 16}
                },
                // Edge Case: 1x1 Matrix
                {
                        {1}
                },
                // Edge Case: 2x2 Matrix with negative numbers
                {
                        {-1, -2},
                        {-3, -4}
                }
        };

        System.out.println("=========================================================");
        System.out.println("Executing Rotate Image Testing Suite");
        System.out.println("=========================================================\n");

        for (int t = 0; t < testCases.length; t++) {
            System.out.println("Test Case " + (t + 1) + ":");
            int[][] original = testCases[t];
            printMatrix(original);

            // Deep clone matrices to test all three approaches independently
            int[][] matBrute = deepClone(original);
            int[][] matOptimal = deepClone(original);
            int[][] matFourWay = deepClone(original);

            // 1. Brute Force
            long start1 = System.nanoTime();
            solution.rotateBruteForce(matBrute);
            long end1 = System.nanoTime();

            // 2. Optimal (Transpose + Reverse)
            long start2 = System.nanoTime();
            solution.rotateOptimal(matOptimal);
            long end2 = System.nanoTime();

            // 3. Alternative (Four-Way Swap)
            long start3 = System.nanoTime();
            solution.rotateFourWaySwap(matFourWay);
            long end3 = System.nanoTime();

            System.out.println("  -> [Brute Force] Time: " + (end1 - start1) + " ns");
            System.out.println("  -> [Optimal]     Time: " + (end2 - start2) + " ns");
            System.out.println("  -> [Four-Way]    Time: " + (end3 - start3) + " ns");

            // Verify all matrices match the optimal output
            boolean isValid = Arrays.deepEquals(matBrute, matOptimal) &&
                    Arrays.deepEquals(matOptimal, matFourWay);

            System.out.println("  [Verification] Matching results: " + (isValid ? "PASS" : "FAIL"));
            System.out.println("  Resulting Rotated Matrix:");
            printMatrix(matOptimal);
            System.out.println("---------------------------------------------------------");
        }
    }

    // Helper method to print matrix cleanly
    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.println("    " + Arrays.toString(row));
        }
    }

    // Helper method to deep clone a 2D array
    private static int[][] deepClone(int[][] matrix) {
        int[][] clone = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            clone[i] = matrix[i].clone();
        }
        return clone;
    }
}