package strivers.binarysearch.bson2darray;

/**
 * ============================================================================
 * MASTERCLASS DSA EVALUATION
 * ============================================================================
 * * Find the row with maximum number of 1's
 * Solved | Medium
 * * * PROBLEM STATEMENT:
 * You have been given a non-empty grid ‘mat’ with 'n' rows and 'm' columns
 * consisting of only 0s and 1s. All the rows are sorted in ascending order.
 * Your task is to find the index of the row with the maximum number of ones.
 * Note: If two rows have the same number of ones, consider the one with a
 * smaller index. If there's no row with at least 1 one, return -1.
 * * * EXAMPLES:
 * Example 1:
 * Input Format: n = 3, m = 3,
 * mat[] =
 * 1 1 1
 * 0 0 1
 * 0 0 0
 * Result: 0
 * Explanation: The row with the maximum number of ones is 0 (0 - indexed).
 * * Example 2:
 * Input Format: n = 2, m = 2 ,
 * mat[] =
 * 0 0
 * 0 0
 * Result: -1
 * Explanation:  The matrix does not contain any 1. So, -1 is the answer.
 * * * CONSTRAINTS:
 * - 1 <= n, m <= 10^4
 * - 0 <= mat[i][j] <= 1
 * - Elements in each row are sorted in non-decreasing order.
 * ============================================================================
 */
public class RowWithMaxOnes {

    /**
     * ========================================================================
     * PHASE 1: Best and Recommended Approach (Top-Right Staircase Traversal)
     * ========================================================================
     * * APPROACH & STEPS:
     * We can exploit the fact that every row is sorted. If we know a row has
     * 'X' number of 1s, we only care if a subsequent row has 'X + 1' or more 1s.
     * 1. Start a pointer at the top-right corner of the matrix: `row = 0`,
     * `col = m - 1`.
     * 2. Initialize `maxRowIndex = -1`.
     * 3. Loop while `row < n` and `col >= 0`:
     * 4. If `mat[row][col] == 1`, it means the current row has a 1 at this
     * position. Since the row is sorted, all elements to the right are also 1.
     * This row currently holds the record for the most 1s seen so far. We
     * record `maxRowIndex = row` and move left (`col--`) to see if this same
     * row has even more 1s.
     * 5. If `mat[row][col] == 0`, this row does not have a 1 at the current
     * column position. It cannot possibly beat our current record. We abandon
     * this row and move down to the next row (`row++`).
     * * * DETAILED INTUITION:
     * This acts like a staircase. We only move left when we find a 1 (increasing
     * our maximum count of 1s), and we only move down when we see a 0 (skipping
     * rows that don't have enough 1s to beat our current record). Because we
     * only move down or left, we traverse the matrix in a strictly linear fashion
     * relative to its dimensions.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(n + m), where 'n' is the number of rows and 'm' is
     * the number of columns. In the worst case, the pointer moves from the
     * top-right to the bottom-left, taking at most 'n' steps down and 'm' steps left.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1) (Iterative loop).
     * - Heap Space: O(1) (No dynamic collections used).
     */
    public static int rowWithMax1sOptimal(int[][] mat, int n, int m) {
        if (mat == null || n == 0 || m == 0) {
            return -1;
        }

        int row = 0;
        int col = m - 1;
        int maxRowIndex = -1;

        while (row < n && col >= 0) {
            if (mat[row][col] == 1) {
                // Found a 1, record the row and move left to check for more 1s
                maxRowIndex = row;
                col--;
            } else {
                // Found a 0, this row can't beat the current max, move down
                row++;
            }
        }

        return maxRowIndex;
    }

    /**
     * ========================================================================
     * PHASE 2: Brute Force Approach - The "Think it" stage
     * ========================================================================
     * * APPROACH & STEPS:
     * 1. Initialize `maxOnes = 0` and `maxRowIndex = -1`.
     * 2. Iterate through each row of the matrix using a nested loop.
     * 3. For each row, count the number of 1s.
     * 4. If the count of 1s in the current row is strictly greater than `maxOnes`,
     * update `maxOnes` and `maxRowIndex`.
     * 5. Return `maxRowIndex`.
     * * * DETAILED INTUITION:
     * This is the most basic translation of the problem. We check every single
     * cell in the matrix. It completely ignores the crucial hint that the rows
     * are sorted, resulting in unnecessary computations for elements we could
     * otherwise skip.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(n * m), where 'n' is rows and 'm' is columns. We
     * process every single element.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1).
     * - Heap Space: O(1).
     */
    public static int rowWithMax1sBruteForce(int[][] mat, int n, int m) {
        int maxOnes = 0;
        int maxRowIndex = -1;

        for (int i = 0; i < n; i++) {
            int countOnes = 0;
            for (int j = 0; j < m; j++) {
                if (mat[i][j] == 1) {
                    countOnes++;
                }
            }
            if (countOnes > maxOnes) {
                maxOnes = countOnes;
                maxRowIndex = i;
            }
        }

        return maxRowIndex;
    }

    /**
     * ========================================================================
     * PHASE 3: Alternative Approaches (Binary Search per Row)
     * ========================================================================
     * * APPROACH & STEPS:
     * Since every row is sorted (0s followed by 1s), we don't need to linearly
     * count the 1s. We can use Binary Search (specifically, finding the lower
     * bound or first occurrence of 1) on each row.
     * 1. For each row `i` from 0 to n-1:
     * 2. Run a binary search to find the index of the first '1'.
     * 3. The number of 1s in that row is `m - firstOccurenceIndex`.
     * 4. Keep track of the row with the maximum count.
     * * * DETAILED INTUITION:
     * Binary Search optimizes the row-counting process from O(m) down to O(log m).
     * This is a solid intermediate approach and is often the first "optimal"
     * solution candidates think of before recognizing the O(n + m) staircase trick.
     * * * COMPLEXITY ANALYSIS:
     * - Time Complexity: O(n * log m). We perform a logarithmic search on each
     * of the 'n' rows.
     * - Space Complexity: O(1).
     * - Auxiliary Stack Space: O(1).
     * - Heap Space: O(1).
     */
    public static int rowWithMax1sBinarySearch(int[][] mat, int n, int m) {
        int maxOnes = 0;
        int maxRowIndex = -1;

        for (int i = 0; i < n; i++) {
            // Find first occurrence of 1 in row i
            int left = 0, right = m - 1;
            int firstOneIndex = m; // Default if no 1 is found

            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (mat[i][mid] == 1) {
                    firstOneIndex = mid;
                    right = mid - 1; // Look left for an earlier 1
                } else {
                    left = mid + 1; // Look right
                }
            }

            int countOnes = m - firstOneIndex;
            if (countOnes > maxOnes) {
                maxOnes = countOnes;
                maxRowIndex = i;
            }
        }

        return maxRowIndex;
    }

    /**
     * ========================================================================
     * TESTING SUITE
     * ========================================================================
     */
    public static void main(String[] args) {
        System.out.println("Running Row with Maximum 1s Test Suite...\n");

        // Test Case 1: Standard case (Example 1)
        int[][] mat1 = {
                {1, 1, 1},
                {0, 0, 1},
                {0, 0, 0}
        };
        runTestCase(1, mat1, 3, 3, 0);

        // Test Case 2: Standard case (Example 2) - No 1s
        int[][] mat2 = {
                {0, 0},
                {0, 0}
        };
        runTestCase(2, mat2, 2, 2, -1);

        // Test Case 3: 1s increase as rows go down
        int[][] mat3 = {
                {0, 0, 0, 0},
                {0, 0, 0, 1},
                {0, 0, 1, 1},
                {0, 1, 1, 1}
        };
        runTestCase(3, mat3, 4, 4, 3);

        // Test Case 4: Tie condition (Return the smaller index)
        int[][] mat4 = {
                {0, 0, 1, 1},
                {0, 1, 1, 1},
                {0, 1, 1, 1},
                {0, 0, 0, 1}
        };
        // Row 1 and Row 2 both have three 1s. Row 1 should be returned.
        runTestCase(4, mat4, 4, 4, 1);

        // Test Case 5: All 1s matrix
        int[][] mat5 = {
                {1, 1, 1},
                {1, 1, 1}
        };
        // All rows have max 1s. Row 0 is the smallest index.
        runTestCase(5, mat5, 2, 3, 0);
    }

    private static void runTestCase(int testNumber, int[][] mat, int n, int m, int expected) {
        long startTime = System.nanoTime();
        int resultOptimal = rowWithMax1sOptimal(mat, n, m);
        long endTime = System.nanoTime();

        System.out.println("Test Case " + testNumber + ":");
        System.out.println("Expected: " + expected);
        System.out.println("Output (Optimal): " + resultOptimal);
        System.out.println("Execution Time: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Status: " + (resultOptimal == expected ? "✅ PASS" : "❌ FAIL"));
        System.out.println("--------------------------------------------------");
    }
}