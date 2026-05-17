package com.questions.strivers.binarysearch.bson2darray;

/**
 * ============================================================================
 * MASTERCLASS DSA EVALUATION
 * ============================================================================
 * * 240. Search a 2D Matrix II
 * Solved | Medium
 * * * PROBLEM STATEMENT:
 * Write an efficient algorithm that searches for a value target in an m x n
 * integer matrix matrix. This matrix has the following properties:
 * 1. Integers in each row are sorted in ascending from left to right.
 * 2. Integers in each column are sorted in ascending from top to bottom.
 * * * EXAMPLES:
 * Example 1:
 * Input: matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 5
 * Output: true
 * * Example 2:
 * Input: matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 20
 * Output: false
 * * * CONSTRAINTS:
 * - m == matrix.length
 * - n == matrix[i].length
 * - 1 <= n, m <= 300
 * - -10^9 <= matrix[i][j] <= 10^9
 * - All the integers in each row are sorted in ascending order.
 * - All the integers in each column are sorted in ascending order.
 * - -10^9 <= target <= 10^9
 * ============================================================================
 */
public class Search2DMatrixII {

    /**
     * ========================================================================
     * PHASE 1: Best and Recommended Approach (Staircase Search)
     * ========================================================================
     * * APPROACH & STEPS:
     * We cannot flatten this matrix like a 1D array because the end of one row
     * is NOT guaranteed to be smaller than the start of the next row. Instead,
     * we leverage both sorting properties by starting at a corner where we can
     * make definitive binary decisions: the top-right (or bottom-left).
     * * 1. Initialize pointers at the top-right corner: `row = 0`, `col = n - 1`.
     * 2. Loop while `row` is within matrix bounds (row < m) and `col` is valid (col >= 0).
     * 3. Read the current value at `matrix[row][col]`.
     * 4. If `value == target`, we found it! Return true.
     * 5. If `value > target`, the target cannot be in the current column because
     * all elements below this value are even larger. Eliminate the column: `col--`.
     * 6. If `value < target`, the target cannot be in the current row because
     * all elements to the left are even smaller. Eliminate the row: `row++`.
     * 7. If the loop terminates without returning, the target is not in the matrix.
     * * * DETAILED INTUITION:
     * Starting at the top-right acts like a binary search tree root. Moving left
     * gives us smaller elements, and moving down gives us larger elements.
     * By comparing our target to the current cell, we can safely discard an entire
     * row or an entire column in a single O(1) step.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(m + n). In the worst-case scenario, the target is at
     * the bottom-left corner (or not present), forcing us to traverse exactly
     * 'm' steps down and 'n' steps left.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1) (Iterative traversal).
     * - Heap Space: O(1) (No dynamic collections instantiated).
     */
    public static boolean searchMatrixOptimal(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int m = matrix.length;
        int n = matrix[0].length;

        int row = 0;
        int col = n - 1;

        while (row < m && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                col--; // Target is smaller, must be to the left
            } else {
                row++; // Target is larger, must be further down
            }
        }

        return false;
    }

    /**
     * ========================================================================
     * PHASE 2: Brute Force Approach - The "Think it" stage
     * ========================================================================
     * * APPROACH & STEPS:
     * 1. Use two nested loops to traverse every single element in the matrix.
     * 2. Compare `matrix[i][j]` against the `target`.
     * 3. Return true immediately if found. Return false if the loops complete.
     * * * DETAILED INTUITION:
     * This represents a blind search that entirely ignores the sorted nature
     * of the rows and columns. It works universally for any 2D grid but is
     * drastically inefficient for this specific problem context.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(m * n). Worst case requires evaluating every cell.
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
     * PHASE 3: Alternative Approaches (Binary Search per Row)
     * ========================================================================
     * * APPROACH & STEPS:
     * Since we know every individual row is sorted, we can apply standard
     * 1D Binary Search on each row independently.
     * 1. Iterate through each row `i` from 0 to `m - 1`.
     * 2. For the current row, check if the `target` could theoretically exist
     * in it (i.e., `target >= matrix[i][0]` and `target <= matrix[i][n-1]`).
     * 3. If it falls within the bounds, run a binary search on `matrix[i]`.
     * 4. If binary search finds the target, return true.
     * * * DETAILED INTUITION:
     * This steps up from the brute force by acknowledging the horizontal sorting
     * constraint. While it skips elements faster than a linear scan, it fails
     * to utilize the vertical sorting constraint fully, making it slower than
     * the optimal O(m+n) approach.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(m * log n). We potentially perform a binary search
     * (taking log n time) on every single one of the 'm' rows.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1).
     * - Heap Space: O(1).
     */
    public static boolean searchMatrixBinarySearch(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int m = matrix.length;
        int n = matrix[0].length;

        for (int i = 0; i < m; i++) {
            // Optimization check: is the target even in this row's range?
            if (target >= matrix[i][0] && target <= matrix[i][n - 1]) {

                int left = 0;
                int right = n - 1;

                while (left <= right) {
                    int mid = left + (right - left) / 2;
                    if (matrix[i][mid] == target) {
                        return true;
                    } else if (matrix[i][mid] < target) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
            }
        }

        return false;
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     * Validating implementations against examples, edge cases, and bounds.
     */
    public static void main(String[] args) {
        System.out.println("Running Search a 2D Matrix II Test Suite...\n");

        int[][] matrix = {
                {1,   4,  7, 11, 15},
                {2,   5,  8, 12, 19},
                {3,   6,  9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}
        };

        // Test Case 1: Standard hit (Example 1)
        runTestCase(1, matrix, 5, true);

        // Test Case 2: Standard miss (Example 2)
        runTestCase(2, matrix, 20, false);

        // Test Case 3: Target is the very first element (Top-Left)
        runTestCase(3, matrix, 1, true);

        // Test Case 4: Target is the very last element (Bottom-Right)
        runTestCase(4, matrix, 30, true);

        // Test Case 5: Target is smaller than the smallest element
        runTestCase(5, matrix, 0, false);

        // Test Case 6: Target is larger than the largest element
        runTestCase(6, matrix, 100, false);

        // Test Case 7: Single element matrix - hit
        int[][] smallMatrix = {{5}};
        runTestCase(7, smallMatrix, 5, true);

        // Test Case 8: Single element matrix - miss
        runTestCase(8, smallMatrix, 10, false);
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