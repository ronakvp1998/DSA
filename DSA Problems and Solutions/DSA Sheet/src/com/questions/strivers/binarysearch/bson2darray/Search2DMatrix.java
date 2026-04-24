package com.questions.strivers.binarysearch.bson2darray;

/**
 * ============================================================================
 * MASTERCLASS DSA EVALUATION
 * ============================================================================
 * * 74. Search a 2D Matrix
 * Solved | Medium
 * * * PROBLEM STATEMENT:
 * You are given an m x n integer matrix matrix with the following two properties:
 * 1. Each row is sorted in non-decreasing order.
 * 2. The first integer of each row is greater than the last integer of the previous row.
 * * Given an integer target, return true if target is in matrix or false otherwise.
 * You must write a solution in O(log(m * n)) time complexity.
 * * * EXAMPLES:
 * Example 1:
 * Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
 * Output: true
 * * Example 2:
 * Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
 * Output: false
 * * * CONSTRAINTS:
 * - m == matrix.length
 * - n == matrix[i].length
 * - 1 <= m, n <= 100
 * - -10^4 <= matrix[i][j], target <= 10^4
 * ============================================================================
 */
public class Search2DMatrix {

    /**
     * ========================================================================
     * PHASE 1: Best and Recommended Approach (1D Flattened Binary Search)
     * ========================================================================
     * * APPROACH & STEPS:
     * 1. Check for empty matrix edge cases.
     * 2. Imagine the entire 2D matrix as a single continuous 1D sorted array
     * of size `m * n`.
     * 3. Set standard binary search bounds: `left = 0` and `right = (m * n) - 1`.
     * 4. Calculate `mid`.
     * 5. Map the 1D `mid` index back to 2D coordinates:
     * - Row index = `mid / columns`
     * - Col index = `mid % columns`
     * 6. Compare `matrix[row][col]` with the `target`.
     * - If equal, return true.
     * - If smaller, move `left = mid + 1`.
     * - If larger, move `right = mid - 1`.
     * 7. If the loop completes without returning true, the target isn't there.
     * * * DETAILED INTUITION:
     * Because the first element of a row is strictly greater than the last
     * element of the previous row, concatenating all rows end-to-end forms a
     * perfectly sorted 1D array. We can run a standard binary search on this
     * conceptual 1D array. The magic lies in the division and modulo operators
     * mapping the imaginary 1D index directly to real physical memory addresses
     * in the 2D array without ever needing to actually construct the 1D array.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(log(m * n)), where m is rows and n is columns.
     * We divide the search space of size m*n in half each iteration.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1) (Iterative loop).
     * - Heap Space: O(1) (No new structures built).
     */
    public static boolean searchMatrixOptimal(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int m = matrix.length;
        int n = matrix[0].length;
        int left = 0;
        int right = m * n - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midValue = matrix[mid / n][mid % n];

            if (midValue == target) {
                return true;
            } else if (midValue < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return false;
    }

    /**
     * ========================================================================
     * PHASE 2: Brute Force Approach - The "Think it" stage
     * ========================================================================
     * * APPROACH & STEPS:
     * 1. Initialize nested `for` loops.
     * 2. The outer loop iterates through each row.
     * 3. The inner loop iterates through each column.
     * 4. Compare every single cell to the `target`.
     * 5. Return true if a match is found.
     * * * DETAILED INTUITION:
     * This is the literal, naive search. It entirely ignores the primary
     * conditions of the problem (that the matrix is sorted). It's a linear
     * scan that works, but severely violates the required O(log(m*n)) constraint.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(m * n). We check every element exactly once in the worst case.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1).
     * - Heap Space: O(1).
     */
    public static boolean searchMatrixBruteForce(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) return false;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * ========================================================================
     * PHASE 3: Alternative Approaches (Double Binary Search)
     * ========================================================================
     * * APPROACH & STEPS:
     * We can perform two separate binary searches: one to find the correct row,
     * and another to find the target within that row.
     * 1. Binary search over the first column of the matrix to locate the
     * potential row. We are looking for the row where `target` falls between
     * `matrix[row][0]` and `matrix[row][n-1]`.
     * 2. Once the target row is identified, perform a standard 1D binary search
     * strictly within that row.
     * * * DETAILED INTUITION:
     * Since `O(log(m * n))` is mathematically equivalent to `O(log m + log n)`,
     * splitting the search into two phases satisfies the complexity requirement.
     * This approach is useful if integer overflow of `m * n` is a massive concern
     * (e.g., if dimensions were exceptionally huge), though with `m, n <= 100`,
     * Phase 1 is definitively cleaner.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(log m + log n) = O(log(m * n)).
     * - Space Complexity: O(1).
     */
    public static boolean searchMatrixAlternative(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;

        int m = matrix.length;
        int n = matrix[0].length;

        // Binary search to find the correct row
        int top = 0, bottom = m - 1;
        int targetRow = -1;

        while (top <= bottom) {
            int midRow = top + (bottom - top) / 2;

            if (target >= matrix[midRow][0] && target <= matrix[midRow][n - 1]) {
                targetRow = midRow;
                break;
            } else if (target < matrix[midRow][0]) {
                bottom = midRow - 1;
            } else {
                top = midRow + 1;
            }
        }

        // If target doesn't fit in any row bounds
        if (targetRow == -1) return false;

        // Binary search within the found row
        int left = 0, right = n - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (matrix[targetRow][mid] == target) {
                return true;
            } else if (matrix[targetRow][mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return false;
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     * Validating implementations against examples, bounds, and edge cases.
     */
    public static void main(String[] args) {
        System.out.println("Running Search a 2D Matrix Test Suite...\n");

        // Test Case 1: Standard hit (Example 1)
        int[][] matrix1 = {
                {1, 3, 5, 7},
                {10, 11, 16, 20},
                {23, 30, 34, 60}
        };
        runTestCase(1, matrix1, 3, true);

        // Test Case 2: Standard miss (Example 2)
        runTestCase(2, matrix1, 13, false);

        // Test Case 3: Target is less than the smallest element
        runTestCase(3, matrix1, 0, false);

        // Test Case 4: Target is greater than the largest element
        runTestCase(4, matrix1, 100, false);

        // Test Case 5: 1x1 Matrix Hit
        int[][] matrix2 = {{1}};
        runTestCase(5, matrix2, 1, true);

        // Test Case 6: 1x1 Matrix Miss
        runTestCase(6, matrix2, 0, false);

        // Test Case 7: Target is the very last element
        runTestCase(7, matrix1, 60, true);
    }

    private static void runTestCase(int testNumber, int[][] matrix, int target, boolean expected) {
        long startTime = System.nanoTime();
        boolean resultOptimal = searchMatrixOptimal(matrix, target);
        long endTime = System.nanoTime();

        System.out.println("Test Case " + testNumber + ":");
        System.out.println("Target: " + target);
        System.out.println("Expected: " + expected);
        System.out.println("Output (Optimal): " + resultOptimal);
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Status: " + (resultOptimal == expected ? "✅ PASS" : "❌ FAIL"));
        System.out.println("--------------------------------------------------");
    }
}