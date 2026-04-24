package com.questions.strivers.binarysearch.bson2darray;

/**
 * ============================================================================
 * MASTERCLASS DSA EVALUATION
 * ============================================================================
 * * 1901. Find a Peak Element II
 * Solved | Medium
 * * * PROBLEM STATEMENT:
 * A peak element in a 2D grid is an element that is strictly greater than all
 * of its adjacent neighbors to the left, right, top, and bottom.
 * * Given a 0-indexed m x n matrix mat where no two adjacent cells are equal,
 * find any peak element mat[i][j] and return the length 2 array [i,j].
 * * You may assume that the entire matrix is surrounded by an outer perimeter
 * with the value -1 in each cell.
 * * You must write an algorithm that runs in O(m log(n)) or O(n log(m)) time.
 * * * EXAMPLES:
 * Example 1:
 * Input: mat = [[1,4],[3,2]]
 * Output: [0,1]
 * Explanation: Both 3 and 4 are peak elements so [1,0] and [0,1] are both
 * acceptable answers.
 * * Example 2:
 * Input: mat = [[10,20,15],[21,30,14],[7,16,32]]
 * Output: [1,1]
 * Explanation: Both 30 and 32 are peak elements so [1,1] and [2,2] are both
 * acceptable answers.
 * * * CONSTRAINTS:
 * - m == mat.length
 * - n == mat[i].length
 * - 1 <= m, n <= 500
 * - 1 <= mat[i][j] <= 10^5
 * - No two adjacent cells are equal.
 * ============================================================================
 */
public class FindPeakElementII {

    /**
     * ========================================================================
     * PHASE 1: Best and Recommended Approach (Binary Search on Columns)
     * ========================================================================
     * * APPROACH & STEPS:
     * To achieve O(m log n) time complexity, we must binary search over the
     * columns (or rows).
     * 1. Set our search space on the columns: `low = 0`, `high = n - 1`.
     * 2. Calculate the `mid` column.
     * 3. Linearly scan the `mid` column to find the global maximum element
     * in this specific column. Let its row index be `maxRow`.
     * 4. Because it is the maximum in its column, it is guaranteed to be
     * strictly greater than its top and bottom neighbors. We only need to
     * worry about its left and right neighbors.
     * 5. Check the left and right neighbors of `mat[maxRow][mid]`.
     * - If it is greater than both, we have found a peak! Return `[maxRow, mid]`.
     * - If the left neighbor is greater, it means a peak *must* exist on the
     * left side of the matrix. Shift search space: `high = mid - 1`.
     * - Otherwise, the right neighbor is greater. A peak *must* exist on the
     * right side. Shift search space: `low = mid + 1`.
     * * * DETAILED INTUITION:
     * Why does moving towards the greater neighbor guarantee a peak?
     * Think of it as climbing a mountain. If the element to your left is higher,
     * walking left moves you upwards. Because the matrix boundaries act as -1
     * cliffs, if you keep moving uphill, you will eventually reach a local maximum
     * (peak) before falling off the edge. By finding the global maximum of a column,
     * we securely anchor ourselves on the Y-axis, reducing the problem to a 1D
     * binary search on the X-axis.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(m * log(n)), where 'm' is the number of rows and 'n'
     * is the number of columns. The binary search takes log(n) steps, and finding
     * the maximum element in a column takes O(m) time per step.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1) (Iterative binary search).
     * - Heap Space: O(1) (We only allocate a length-2 array for the return value).
     */
    public static int[] findPeakGridOptimal(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;

        int low = 0;
        int high = n - 1;

        while (low <= high) {
            int midCol = low + (high - low) / 2;

            // Find the row index of the maximum element in the mid column
            int maxRow = findMaxRow(mat, m, midCol);

            // Handle edge boundaries gracefully (-1 as per problem statement)
            int leftNeighbor = (midCol - 1 >= 0) ? mat[maxRow][midCol - 1] : -1;
            int rightNeighbor = (midCol + 1 < n) ? mat[maxRow][midCol + 1] : -1;

            // Check if the current element is greater than its left and right neighbors
            if (mat[maxRow][midCol] > leftNeighbor && mat[maxRow][midCol] > rightNeighbor) {
                return new int[]{maxRow, midCol};
            }
            // If left is strictly greater, a peak must exist on the left side
            else if (leftNeighbor > mat[maxRow][midCol]) {
                high = midCol - 1;
            }
            // Otherwise, a peak must exist on the right side
            else {
                low = midCol + 1;
            }
        }

        return new int[]{-1, -1}; // Should never be reached based on problem guarantees
    }

    // Helper method to find the maximum element's row index in a specific column
    private static int findMaxRow(int[][] mat, int m, int col) {
        int maxValue = -1;
        int maxRow = -1;
        for (int i = 0; i < m; i++) {
            if (mat[i][col] > maxValue) {
                maxValue = mat[i][col];
                maxRow = i;
            }
        }
        return maxRow;
    }

    /**
     * ========================================================================
     * PHASE 2: Brute Force Approach - The "Think it" stage
     * ========================================================================
     * * APPROACH & STEPS:
     * 1. Iterate through every single cell `(i, j)` in the `m x n` matrix.
     * 2. For each cell, check its 4 adjacent neighbors (Top, Bottom, Left, Right).
     * 3. Handle boundary conditions by treating out-of-bounds indices as -1.
     * 4. If a cell is strictly greater than all 4 of its valid neighbors, return it.
     * * * DETAILED INTUITION:
     * The simplest way to find a peak is to ask every single element: "Are you
     * bigger than everyone next to you?" It ignores the mathematical guarantees
     * of traversing gradients, opting instead for an exhaustive search.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(m * n). We check every element once, performing up
     * to 4 constant-time comparisons per element. Fails the required O(m log n) constraint.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1).
     * - Heap Space: O(1).
     */
    public static int[] findPeakGridBruteForce(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int top = (i > 0) ? mat[i - 1][j] : -1;
                int bottom = (i < m - 1) ? mat[i + 1][j] : -1;
                int left = (j > 0) ? mat[i][j - 1] : -1;
                int right = (j < n - 1) ? mat[i][j + 1] : -1;

                if (mat[i][j] > top && mat[i][j] > bottom &&
                        mat[i][j] > left && mat[i][j] > right) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * ========================================================================
     * PHASE 3: Alternative Approaches
     * ========================================================================
     * * Binary Search on Rows:
     * - Instead of searching on columns, we could binary search on the `m` rows
     * and find the maximum element in the `mid` row taking O(n) time.
     * - Complexity: O(n log m).
     * - Choice between O(m log n) and O(n log m): In a truly optimized engine,
     * one would dynamically choose to binary search the *larger* dimension to
     * minimize the logarithmic base, ensuring max performance depending on
     * the matrix aspect ratio.
     * * * Greedy Ascent (Hill Climbing):
     * - Start at `(0,0)`. Move to the neighboring cell that has the maximum value.
     * - Repeat until all adjacent neighbors are smaller.
     * - Complexity: Worst-case O(m * n) if the matrix is a spiral increasing path.
     * Best-case O(1). While practically fast, it fails strict Big-O guarantees.
     */

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     * Validating implementations against examples, edge cases, and constraints.
     */
    public static void main(String[] args) {
        System.out.println("Running Find a Peak Element II Test Suite...\n");

        // Test Case 1: Standard case (Example 1)
        int[][] mat1 = {
                {1, 4},
                {3, 2}
        };
        // Valid answers: [0,1] (val 4) or [1,0] (val 3)
        runTestCase(1, mat1, new int[][]{{0, 1}, {1, 0}});

        // Test Case 2: Standard case (Example 2)
        int[][] mat2 = {
                {10, 20, 15},
                {21, 30, 14},
                {7,  16, 32}
        };
        // Valid answers: [1,1] (val 30) or [2,2] (val 32)
        runTestCase(2, mat2, new int[][]{{1, 1}, {2, 2}});

        // Test Case 3: 1D Matrix Column
        int[][] mat3 = {
                {1},
                {2},
                {3},
                {4}
        };
        // Valid answer: [3,0]
        runTestCase(3, mat3, new int[][]{{3, 0}});

        // Test Case 4: 1D Matrix Row
        int[][] mat4 = {
                {1, 5, 2, 8, 3}
        };
        // Valid answers: [0,1] (val 5) or [0,3] (val 8)
        runTestCase(4, mat4, new int[][]{{0, 1}, {0, 3}});

        // Test Case 5: Single element
        int[][] mat5 = {
                {42}
        };
        // Valid answer: [0,0]
        runTestCase(5, mat5, new int[][]{{0, 0}});
    }

    private static void runTestCase(int testNumber, int[][] mat, int[][] validAnswers) {
        long startTime = System.nanoTime();
        int[] resultOptimal = findPeakGridOptimal(mat);
        long endTime = System.nanoTime();

        boolean isPass = false;
        for (int[] ans : validAnswers) {
            if (ans[0] == resultOptimal[0] && ans[1] == resultOptimal[1]) {
                isPass = true;
                break;
            }
        }

        System.out.println("Test Case " + testNumber + ":");
        System.out.println("Output (Optimal): [" + resultOptimal[0] + ", " + resultOptimal[1] + "]");
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Status: " + (isPass ? "✅ PASS" : "❌ FAIL"));
        System.out.println("--------------------------------------------------");
    }
}