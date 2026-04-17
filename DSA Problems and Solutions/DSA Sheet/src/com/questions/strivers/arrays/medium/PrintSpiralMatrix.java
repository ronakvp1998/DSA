package com.questions.strivers.arrays.medium;

/**
 * ============================================================================
 * MASTERCLASS: SPIRAL MATRIX
 * ============================================================================
 * * 1. Header & Problem Context
 * 54. Spiral Matrix
 * Solved | Medium | Topics: Array, Matrix, Simulation | Companies
 * * Hint:
 * Given an m x n matrix, return all elements of the matrix in spiral order.
 * * Example 1:
 * Input: matrix = [[1,2,3],
 * [4,5,6],
 * [7,8,9]]
 * Output: [1,2,3,6,9,8,7,4,5]
 * * Example 2:
 * Input: matrix = [[1,2,3,4],
 * [5,6,7,8],
 * [9,10,11,12]]
 * Output: [1,2,3,4,8,12,11,10,9,5,6,7]
 * * Constraints:
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 10
 * -100 <= matrix[i][j] <= 100
 * * ============================================================================
 * CONCEPTUAL VISUALIZATION (Four Boundaries Approach)
 * ============================================================================
 * Since this is an Array/Matrix traversal problem (not DP), we don't use a
 * recursion tree. Instead, here is a visualization of the optimal Boundary Strategy.
 * * We maintain 4 walls/boundaries: TOP, BOTTOM, LEFT, RIGHT.
 * A spiral just systematically "peels" off the outermost layers of the matrix.
 * * Start state for 3x3 matrix:
 * [L]       [R]
 * [T] 1  2  3
 * 4  5  6
 * [B] 7  8  9
 * * Step 1: Traverse TOP row (left to right). Then shrink TOP downwards (T++).
 * Result: [1, 2, 3]
 * * Step 2: Traverse RIGHT col (top to bottom). Then shrink RIGHT inwards (R--).
 * Result: [..., 6, 9]
 * * Step 3: Traverse BOTTOM row (right to left). Then shrink BOTTOM upwards (B--).
 * Result: [..., 8, 7]
 * * Step 4: Traverse LEFT col (bottom to top). Then shrink LEFT inwards (L++).
 * Result: [..., 4]
 * * Repeat until T > B or L > R. Inner layer [5] is peeled last.
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrintSpiralMatrix {

    /**
     * ========================================================================
     * PHASE 1: BRUTE FORCE / SIMULATION APPROACH (The "Think it" stage)
     * ========================================================================
     * Approach:
     * We simulate the exact movement of a person walking through the matrix.
     * We use a `visited` boolean matrix to remember where we've been. We keep
     * track of our current direction (right, down, left, up). If we hit an edge
     * or a previously visited cell, we turn 90 degrees clockwise.
     * * Detailed Intuition:
     * When faced with a trajectory problem, the most literal translation is to
     * map out the trajectory mathematically using delta-X and delta-Y arrays
     * (e.g., `dr = {0, 1, 0, -1}`, `dc = {1, 0, -1, 0}`). By verifying our next
     * step against boundaries and a `visited` matrix, we guarantee a spiral.
     * * Complexity Analysis:
     * - Time Complexity: O(m * n)
     * We visit each cell in the m x n matrix exactly once.
     * - Space Complexity: O(m * n)
     * Heap Space is O(m * n) because we allocate a completely separate 2D boolean
     * array of size m x n to track visited states. Auxiliary Stack Space is O(1).
     */
    public List<Integer> spiralOrderSimulation(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) return result;

        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] visited = new boolean[m][n];

        // Direction arrays for: Right, Down, Left, Up
        int[] dr = {0, 1, 0, -1};
        int[] dc = {1, 0, -1, 0};

        int r = 0, c = 0;
        int dir = 0; // Starts facing "Right"

        for (int i = 0; i < m * n; i++) {
            result.add(matrix[r][c]);
            visited[r][c] = true;

            // Calculate candidate next step
            int nextR = r + dr[dir];
            int nextC = c + dc[dir];

            // If next step is valid, move there. Otherwise, turn 90 degrees.
            if (nextR >= 0 && nextR < m && nextC >= 0 && nextC < n && !visited[nextR][nextC]) {
                r = nextR;
                c = nextC;
            } else {
                dir = (dir + 1) % 4; // Turn clockwise
                r += dr[dir];
                c += dc[dir];
            }
        }

        return result;
    }

    /**
     * ========================================================================
     * PHASE 2: OPTIMAL BOUNDARY APPROACH (The "Perfect it" stage)
     * ========================================================================
     * Approach:
     * We discard the `visited` matrix entirely. Instead, we define 4 integer
     * pointers: `top`, `bottom`, `left`, `right` representing the current
     * unpeeled boundaries of the matrix. We run 4 distinct loops per cycle
     * (Top Row, Right Col, Bottom Row, Left Col), shrinking the boundaries
     * inwards after each corresponding loop.
     * * Detailed Intuition:
     * Why waste O(m*n) space tracking visited cells when the geometry of a
     * rectangle gives us all the information we need? The unvisited region is
     * ALWAYS a smaller rectangle. By pulling the 4 walls inwards one row/column
     * at a time, we naturally prevent overlapping without any extra memory.
     * * Complexity Analysis:
     * - Time Complexity: O(m * n)
     * We add each of the m x n elements to the list exactly once.
     * - Space Complexity: O(1)
     * Heap Space is O(1) because we only use four integer pointers. (Note: The
     * O(m*n) output list is typically excluded from auxiliary space analysis).
     * Auxiliary Stack Space is O(1).
     */
    public List<Integer> spiralOrderOptimal(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) return result;

        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;

        while (top <= bottom && left <= right) {
            // 1. Traverse from Left to Right along the TOP boundary
            for (int i = left; i <= right; i++) {
                result.add(matrix[top][i]);
            }
            top++; // Shrink top boundary downwards

            // 2. Traverse from Top to Bottom along the RIGHT boundary
            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]);
            }
            right--; // Shrink right boundary leftwards

            // CRITICAL CHECK: Ensure we haven't crossed horizontal boundaries
            // This prevents duplicate traversal if the inner core is a single row
            if (top <= bottom) {
                // 3. Traverse from Right to Left along the BOTTOM boundary
                for (int i = right; i >= left; i--) {
                    result.add(matrix[bottom][i]);
                }
                bottom--; // Shrink bottom boundary upwards
            }

            // CRITICAL CHECK: Ensure we haven't crossed vertical boundaries
            // This prevents duplicate traversal if the inner core is a single column
            if (left <= right) {
                // 4. Traverse from Bottom to Top along the LEFT boundary
                for (int i = bottom; i >= top; i--) {
                    result.add(matrix[i][left]);
                }
                left++; // Shrink left boundary rightwards
            }
        }

        return result;
    }

    /**
     * ========================================================================
     * 4. TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        PrintSpiralMatrix solution = new PrintSpiralMatrix();

        // Define Test Cases
        int[][][] testCases = {
                // Example 1: 3x3 square matrix
                {
                        {1, 2, 3},
                        {4, 5, 6},
                        {7, 8, 9}
                },
                // Example 2: 3x4 rectangular matrix
                {
                        {1, 2, 3, 4},
                        {5, 6, 7, 8},
                        {9, 10, 11, 12}
                },
                // Edge Case: 1x4 single row matrix
                {
                        {1, 2, 3, 4}
                },
                // Edge Case: 4x1 single column matrix
                {
                        {1},
                        {2},
                        {3},
                        {4}
                },
                // Edge Case: 1x1 single element
                {
                        {42}
                }
        };

        System.out.println("=========================================================");
        System.out.println("Executing Spiral Matrix Testing Suite");
        System.out.println("=========================================================\n");

        for (int i = 0; i < testCases.length; i++) {
            System.out.println("Test Case " + (i + 1) + ":");
            int[][] matrix = testCases[i];

            // Print original matrix layout
            for (int[] row : matrix) {
                System.out.println("  " + Arrays.toString(row));
            }

            // 1. Simulation Approach
            long start1 = System.nanoTime();
            List<Integer> res1 = solution.spiralOrderSimulation(matrix);
            long end1 = System.nanoTime();

            // 2. Optimal Boundary Approach
            long start2 = System.nanoTime();
            List<Integer> res2 = solution.spiralOrderOptimal(matrix);
            long end2 = System.nanoTime();

            System.out.println("\n  -> [Simulation] Time: " + (end1 - start1) + " ns");
            System.out.println("  -> Output: " + res1);
            System.out.println("  -> [Optimal]    Time: " + (end2 - start2) + " ns");
            System.out.println("  -> Output: " + res2);

            // Verification
            boolean isValid = res1.equals(res2);
            System.out.println("  [Verification] Matching results: " + (isValid ? "PASS" : "FAIL"));
            System.out.println("---------------------------------------------------------");
        }
    }
}