package strivers.stackandqueues.monotonicstackqueue;

import java.util.*;
import java.util.stream.Stream;

/**
 * ============================================================================
 * 85. Maximal Rectangle
 * ============================================================================
 *
 * ROLE: Senior DSA Interviewer and Competitive Programming Evaluator
 *
 * PROBLEM STATEMENT:
 * Given a rows x cols binary matrix filled with 0's and 1's, find the largest
 * rectangle containing only 1's and return its area.
 *
 * Example 1:
 * Input: matrix = [
 * ["1","0","1","0","0"],
 * ["1","0","1","1","1"],
 * ["1","1","1","1","1"],
 * ["1","0","0","1","0"]
 * ]
 * Output: 6
 * Explanation: The maximal rectangle is formed by the 1s spanning rows 1 to 2
 * and columns 2 to 4 (area = 2 * 3 = 6).
 *
 * Example 2:
 * Input: matrix = [["0"]]
 * Output: 0
 *
 * Example 3:
 * Input: matrix = [["1"]]
 * Output: 1
 *
 * CONSTRAINTS:
 * rows == matrix.length
 * cols == matrix[i].length
 * 1 <= rows, cols <= 200
 * matrix[i][j] is '0' or '1'.
 *
 * ============================================================================
 * PROGRESSIVE IMPLEMENTATION ROADMAP (Non-DP Problem)
 * ============================================================================
 * Phase 1: Optimal Approach - Histogram & Monotonic Stack (O(R * C) Time)
 * Phase 2: Brute Force Approach - Bounded Expansion (O(R^2 * C) Time)
 * Phase 3: Alternative Approach - DP via Left/Right Bounds (O(R * C) Time)
 * ============================================================================
 */
public class MaximalRectangle {

    /**
     * ============================================================================
     * PHASE 1: OPTIMAL APPROACH (Histogram & Monotonic Stack)
     * ============================================================================
     * Detailed Intuition:
     * This problem is a brilliant 2D extension of "84. Largest Rectangle in Histogram".
     * If we look at the matrix row by row, we can treat each row as the base of a
     * histogram. The height of each bar in the histogram is the number of consecutive
     * 1s directly above it.
     * 1. We maintain a `heights` array of size `cols`.
     * 2. For each row, if `matrix[i][j] == '1'`, we increment `heights[j]`.
     * If it is '0', we break the streak and reset `heights[j] = 0`.
     * 3. After updating the `heights` array for a row, we pass it to the exact
     * O(C) monotonic stack algorithm from "Largest Rectangle in Histogram" to
     * find the maximum rectangle resting on that row.
     * 4. The overall maximum across all rows is our answer.
     *
     * Complexity Analysis:
     * - Time Complexity: O(R * C), where R is the number of rows and C is columns.
     * We visit each cell once to update heights, and the monotonic stack processes
     * the heights array in O(C) time per row.
     * - Space Complexity: O(C) auxiliary heap space for the `heights` array and
     * the Deque stack used to calculate the histogram area.
     * ============================================================================
     */
    public int maximalRectangleOptimal(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;

        int cols = matrix[0].length;
        int[] heights = new int[cols];
        int maxArea = 0;

        for (char[] row : matrix) {
            // Build the histogram for the current row
            for (int j = 0; j < cols; j++) {
                if (row[j] == '1') {
                    heights[j]++;
                } else {
                    heights[j] = 0; // The base is broken
                }
            }
            // Evaluate the largest rectangle resting on this row
            maxArea = Math.max(maxArea, largestRectangleInHistogram(heights));
        }

        return maxArea;
    }

    /**
     * Reused helper method: O(N) Monotonic Increasing Stack to find max area in a histogram.
     */
    private int largestRectangleInHistogram(int[] heights) {
        int n = heights.length;
        int maxArea = 0;
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i <= n; i++) {
            int currentHeight = (i == n) ? 0 : heights[i];

            while (!stack.isEmpty() && currentHeight < heights[stack.peek()]) {
                int heightOfPoppedBar = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, heightOfPoppedBar * width);
            }
            stack.push(i);
        }
        return maxArea;
    }

    /**
     * ============================================================================
     * PHASE 2: BRUTE FORCE APPROACH (Bounded Expansion) -> "Think it"
     * ============================================================================
     * Detailed Intuition:
     * We can compute the answer by finding the maximum contiguous sequence of 1s
     * ending at every cell `(i, j)`.
     * 1. Create a 2D array `widthDP` where `widthDP[i][j]` stores the length of
     * consecutive 1s ending at `(i, j)` on the same row.
     * 2. For every cell containing a '1', we know the maximum width of a rectangle
     * ending there of height 1 is `widthDP[i][j]`.
     * 3. We then scan *upwards* through the rows above it. For each row `k` we move up,
     * the maximum width of the rectangle is bottlenecked by the minimum width
     * seen so far.
     * 4. The area is this `minWidth * (i - k + 1)`. We keep a running maximum.
     *
     * Complexity Analysis:
     * - Time Complexity: O(R^2 * C). We visit each cell, and for each cell, we
     * potentially scan upwards through all previous rows.
     * - Space Complexity: O(R * C) heap space to store the precomputed widths.
     * ============================================================================
     */
    public int maximalRectangleBruteForce(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] widthDP = new int[rows][cols];
        int maxArea = 0;

        // Populate width map
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == '1') {
                    widthDP[i][j] = (j == 0) ? 1 : widthDP[i][j - 1] + 1;
                }
            }
        }

        // Calculate max area anchored at each cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == '1') {
                    int minWidth = widthDP[i][j];
                    // Scan upwards
                    for (int k = i; k >= 0; k--) {
                        if (widthDP[k][j] == 0) break; // Broken rectangle
                        minWidth = Math.min(minWidth, widthDP[k][j]);
                        int height = i - k + 1;
                        maxArea = Math.max(maxArea, minWidth * height);
                    }
                }
            }
        }

        return maxArea;
    }

    /**
     * ============================================================================
     * PHASE 3: ALTERNATIVE APPROACH (DP via Left/Right Bounds)
     * ============================================================================
     * Detailed Intuition:
     * A very fast, purely DP-based approach that avoids the stack completely.
     * We maintain three 1D arrays for the current row:
     * - `height[j]`: The number of continuous 1s above and including `(i, j)`.
     * - `left[j]`: The leftmost column index that can be part of a rectangle
     * of height `height[j]`.
     * - `right[j]`: The rightmost column index (exclusive) that can be part of a
     * rectangle of height `height[j]`.
     * For each row, we update these arrays dynamically based on the current row's
     * contiguous boundaries. The area at column `j` is `(right[j] - left[j]) * height[j]`.
     *
     * Complexity Analysis:
     * - Time Complexity: O(R * C). We process each cell a constant number of times.
     * - Space Complexity: O(C) auxiliary space for the three bounds arrays.
     * ============================================================================
     */
    public int maximalRectangleDP(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;

        int cols = matrix[0].length;
        int[] height = new int[cols];
        int[] left = new int[cols];
        int[] right = new int[cols];
        Arrays.fill(right, cols); // Initialize right bounds to maximum

        int maxArea = 0;

        for (char[] row : matrix) {
            int curLeft = 0;
            int curRight = cols;

            // 1. Compute heights
            for (int j = 0; j < cols; j++) {
                if (row[j] == '1') height[j]++;
                else height[j] = 0;
            }

            // 2. Compute left bounds
            for (int j = 0; j < cols; j++) {
                if (row[j] == '1') {
                    left[j] = Math.max(left[j], curLeft);
                } else {
                    left[j] = 0;
                    curLeft = j + 1; // Reset boundary for next 1s
                }
            }

            // 3. Compute right bounds
            for (int j = cols - 1; j >= 0; j--) {
                if (row[j] == '1') {
                    right[j] = Math.min(right[j], curRight);
                } else {
                    right[j] = cols;
                    curRight = j; // Reset boundary for next 1s
                }
            }

            // 4. Compute maximal area for the current row
            for (int j = 0; j < cols; j++) {
                maxArea = Math.max(maxArea, (right[j] - left[j]) * height[j]);
            }
        }

        return maxArea;
    }

    /**
     * ============================================================================
     * 4. TESTING SUITE
     * ============================================================================
     * Tests all the different approaches against standard and edge cases.
     * ============================================================================
     */
    public static void main(String[] args) {
        MaximalRectangle solver = new MaximalRectangle();

        char[][] matrix1 = {
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}
        };

        char[][] matrix2 = {{'0'}};
        char[][] matrix3 = {{'1'}};
        char[][] matrix4 = {
                {'1', '1', '1'},
                {'1', '1', '1'},
                {'1', '1', '1'}
        };

        System.out.println("=== Maximal Rectangle Testing Suite ===\n");

        runTest(solver, matrix1, 6, "Standard Grid (Example 1)");
        runTest(solver, matrix2, 0, "Single Zero (Example 2)");
        runTest(solver, matrix3, 1, "Single One (Example 3)");
        runTest(solver, matrix4, 9, "All Ones");
    }

    private static void runTest(MaximalRectangle solver, char[][] matrix, int expected, String testName) {
        int optRes = solver.maximalRectangleOptimal(matrix);
        int bfRes  = solver.maximalRectangleBruteForce(matrix);
        int dpRes  = solver.maximalRectangleDP(matrix);

        boolean pass = Stream.of(optRes, bfRes, dpRes).allMatch(res -> res == expected);

        System.out.println("Test: " + testName);
        System.out.printf("  Expected: %d\n", expected);
        System.out.printf("  Optimal:  %d | Brute: %d | DP: %d\n", optRes, bfRes, dpRes);
        System.out.printf("  Status:   %s\n\n", pass ? "PASS \u2714" : "FAIL \u2718");
    }
}